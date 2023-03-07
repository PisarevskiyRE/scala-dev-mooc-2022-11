package module3

import module3.zioConcurrency.{currentTime, printEffectRunningTime}
import module3.zio_homework.printTimeService.PrintTimeService
import module3.zio_homework.{doWhile, guessProgram}
import zio.{Has, Task, UIO, ULayer, URIO, ZIO, ZLayer, random}
import zio.clock.{Clock, sleep}
import zio.console._
import zio.duration.durationInt
import zio.macros.accessible

import java.io.IOException
import java.util.concurrent.TimeUnit
import scala.io.StdIn
import scala.language.postfixOps
import scala.util.Random
import zio.random._

import scala.concurrent.duration.MILLISECONDS

package object zio_homework {
  /**
   * 1.
   * Используя сервисы Random и Console, напишите консольную ZIO программу которая будет предлагать пользователю угадать число от 1 до 3
   * и печатать в когнсоль угадал или нет. Подумайте, на какие наиболее простые эффекты ее можно декомпозировать.
   */


  lazy val getRandomInt = ZIO.effect(Random.nextInt(3))
  lazy val readLine = ZIO.effect(StdIn.readLine)

  lazy val guessProgram = for {
    random <- getRandomInt
    _ <- putStrLn("Угадайте число от 1 до 3:")
    answer <- readLine.map(_.toInt)
    _ <- putStrLn(if (random == answer) "Верно" else "Не верно, правильный ответ - " + random.toString)
  } yield ()



  /**
   * 2. реализовать функцию doWhile (общего назначения), которая будет выполнять эффект до тех пор, пока его значение в условии не даст true
   * 
   */

  def doWhile[R, E, A](body: ZIO[R, E, A])(condition: A => Boolean):ZIO[R, E, A] = {
    body.flatMap{
      result => if (condition(result)) doWhile(body)(condition)
      else ZIO.succeed(result)
    }
  }

  /**
   * 3. Реализовать метод, который безопасно прочитает конфиг из файла, а в случае ошибки вернет дефолтный конфиг
   * и выведет его в консоль
   * Используйте эффект "load" из пакета config
   */

  def loadConfigOrDefault = config.load.orElse(ZIO.succeed(new config.AppConfig("Тест", "80")))


  /**
   * 4. Следуйте инструкциям ниже для написания 2-х ZIO программ,
   * обратите внимание на сигнатуры эффектов, которые будут у вас получаться,
   * на изменение этих сигнатур
   */


  /**
   * 4.1 Создайте эффект, который будет возвращать случайеым образом выбранное число от 0 до 10 спустя 1 секунду
   * Используйте сервис zio Random
   */
  lazy val eff: ZIO[random.Random with Clock, Nothing, Int] = ZIO.sleep(1.second) *> zio.random.nextIntBetween(0, 11)

  /**
   * 4.2 Создайте коллукцию из 10 выше описанных эффектов (eff)
   */
  lazy val effects = List.fill(10)(eff)

  
  /**
   * 4.3 Напишите программу которая вычислит сумму элементов коллекци "effects",
   * напечатает ее в консоль и вернет результат, а также залогирует затраченное время на выполнение,
   * можно использовать ф-цию printEffectRunningTime, которую мы разработали на занятиях
   */

  def printEffectRunningTime[R, E, A](effect: ZIO[R, E, A]): ZIO[Clock with Console with R, E, A] = for {
    startTime <- currentTime
    effVal <- effect
    endTime <- currentTime
    _ <- putStrLn(s"Время : ${endTime - startTime} ms")
  } yield effVal


  lazy val sumEff = ZIO.collectAll(effects).map(col => col.foldLeft(0)((acc, el) => acc + el))

  lazy val app = printEffectRunningTime(sumEff).tap(v => putStrLn(s"Общее время : $v"))

  /**
   * 4.4 Усовершенствуйте программу 4.3 так, чтобы минимизировать время ее выполнения
   */
  lazy val sunEffPar = ZIO.collectAllPar(effects).map(col => col.foldLeft(0)((acc, el) => acc+el))

  lazy val appSpeedUp = printEffectRunningTime(sunEffPar).tap( v => putStrLn(s"Общее время :  $v"))


  /**
   * 5. Оформите ф-цию printEffectRunningTime разработанную на занятиях в отдельный сервис, так чтобы ее
   * молжно было использовать аналогично zio.console.putStrLn например
   */

  type PrintTimeService = Has[PrintTimeService.Service]

  object printTimeService {




    object PrintTimeService {

      trait Service {
        def printEffectRunningTime[R, E, A](effect: ZIO[R, E, A]): ZIO[Clock with Console with R, E, A]
      }

      val live: ULayer[Has[Service]] = ZLayer.succeed(new Service {
        //val currentTime: URIO[Clock, Long] = currentTime

        override def printEffectRunningTime[R, E, A](effect: ZIO[R, E, A]): ZIO[Clock with Console with R, E, A] = for {
          startTime <- currentTime
          effVal <- effect
          endTime <- currentTime
          _ <- putStrLn(s"Running time : ${endTime - startTime}")
        } yield effVal
      })

      def printEffectRunningTime[R, E, A](effect: ZIO[R, E, A]): ZIO[PrintTimeService with Clock with Console with R, E, A] =
        ZIO.accessM(_.get.printEffectRunningTime(effect))
    }
  }


  /**
     * 6.
     * Воспользуйтесь написанным сервисом, чтобы созадть эффект, который будет логировать время выполнения прогаммы из пункта 4.3
     *
     * 
     */
  lazy val appWithTimeLogg: ZIO[PrintTimeService with Clock with Console with random.Random, Nothing, Int] =
    PrintTimeService.printEffectRunningTime(sumEff).tap(v => putStrLn(s"Общее время : $v"))


  /**
    *
    * Подготовьте его к запуску и затем запустите воспользовавшись ZioHomeWorkApp
    */

  lazy val runApp = appWithTimeLogg.provideSomeLayer[Console with Clock with random.Random](PrintTimeService.live)
}
