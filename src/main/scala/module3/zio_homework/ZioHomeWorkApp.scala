package module3.zio_homework

import module3.zio_homework._
import zio.clock.Clock
import zio.console.Console
import zio.random.Random
import zio.{ExitCode, URIO}


object ZioHomeWorkApp extends zio.App {
  override def run(args: List[String]): URIO[Clock with Random with Console, ExitCode] = runApp.exitCode

}
