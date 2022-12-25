import scala.util.Random

case class BallsExperiment() {
  // true - белый шар
  // false - черный шар
  val balls = List(true, true, true, false, false, false)


  def isFirstBlackSecondWhite(): Boolean = {
    // вспомогательная функция принимает мешок с шарами, возвращает 1 выбранный и остальные оставшиеся
    def getBall(balls: List[Boolean]): (Boolean, List[Boolean]) = {

      val rand = Random.nextInt(balls.length)

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

// 1000000  - 0.29993 / 0.29993 / 0.299464
// 100000   - 0.30069 / 0.30094 / 0.30048
// 10000    - 0.2934 / 0.305 / 0.2968
// 1000     - 0.298 / 0.285 / 0.292
// 100      - 0.23 / 0.23 / 0.33
// 10       - 0.5 / 0.4 / 0.3
BallsTest.main()