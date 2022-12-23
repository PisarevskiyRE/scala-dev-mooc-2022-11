import scala.util.Random

case class BallsExperiment() {
  // true - белый шар
  // false - черный шар
  val balls = List(true, true, true, false, false, false)


  def isFirstBlackSecondWhite(): Boolean = {
    // вспомогательная функция принимает мешок с шарами, возвращает 1 выбранный и остальные оставшиеся
    def getBall(balls: List[Boolean]): (Boolean, List[Boolean]) = {
      // от 0 до 5
      val rand = Random.nextInt(5)

      // возвращаем выбраный шар и остальные шары в мешке, кроме выбранного
      (balls(rand), balls.zipWithIndex.collect{ case (a, i) if i != rand => a })
    }

    // достаем шар и если услоовие выполнилось еще раз достаем шар
    getBall(balls) match {
      case (x, y) if (x == false) => getBall(y) match {
        case (x, y) if (x == true) => true
        case _ => false
      }
      case _ => false
    }
  }

  override def toString: String = balls.mkString(" | ")
}

object BallsExperiment {
  def apply(count: Int): List[BallsExperiment] = {
    List.fill(count)(new BallsExperiment)
  }
}



object BallsTest {
  def main(): Unit = {
    val count = 1000000
    val listOfExperiments: List[BallsExperiment] = BallsExperiment(count)
    //listOfExperiments.map(println)

    val countOfExperiments = listOfExperiments.map(x =>x.isFirstBlackSecondWhite())
    //countOfExperiments.map(println)

    val countOfPositiveExperiments: Float = countOfExperiments.count(_ == true)

    println(countOfPositiveExperiments / count)
  }

}

// 1000000  - 0.23966
// 100000   - 0.2409
// 10000    - 0.2421
// 1000     - 0.222
// 100      - 0.21
// 10       - 0.4
BallsTest.main()