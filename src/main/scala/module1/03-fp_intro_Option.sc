sealed trait Option[+T] {

  def isEmpty: Boolean = this match {
    case Option.Some(v) => false
    case Option.None => true
  }

  def map[B](f: T => B): Option[B] = flatMap(v => Option(f(v)))

  def flatMap[B](f: T => Option[B]): Option[B] = this match {
    case Option.Some(v) => f(v)
    case Option.None => Option.None
  }

  /**
   *
   * Реализовать метод printIfAny, который будет печатать значение, если оно есть
   */
  def printIfAny = this match {
    case Option.Some(v) => Some(v).map(println)
    case Option.None => ()
  }

  /**
   *
   * Реализовать метод zip, который будет создавать Option от пары значений из 2-х Option
   */
  def get: T = this match {
    case Option.Some(v) => v
    case Option.None => throw new Exception("get empty Option")
  }

//  def zip[A >: T, B](that: Option[B]):  Option[(A, B)] = {
//    if (this.isEmpty || that.isEmpty)
//      Option.None
//    else
//      Option(this.get, that.get)
//  }
//*******************************
// исправленый вариант
  def zip[A >: T, B](that: Option[B]): Option[(A, B)] =
    (that , this) match {
    case (Option.Some(x), Option.Some(y)) => Option(this.get, that.get)
    case _ => Option.None
  }

  /**
   *
   * Реализовать метод filter, который будет возвращать не пустой Option
   * в случае если исходный не пуст и предикат от значения = true
   */
//  def filter(f: T => Boolean): Option[T] = {
//    if (this.isEmpty || f(this.get)) this else Option.None
//  }
//  option match {
//    case Some(x) if p(x) => Some(x)
//    case _ => None
//  }
//*******************************
// исправленый вариант
  def filter(f: T => Boolean): Option[T] = this match {
    case Option.Some(x) if f(x) => Option.Some(x)
    case _ => Option.None
  }
}

object Option {
  case class Some[T](v: T) extends Option[T]
  case object None extends Option[Nothing]
  def apply[T](v: T): Option[T] = Some(v)
}




val optSome1 = Option("optSome1")
val optSome2 = Option("optSome2")
val optNone = Option.None

optSome1.printIfAny   // optSome1
optNone.printIfAny    //

optSome1.zip(optSome2) // Some((optSome1,optSome2))
optSome1.zip(optNone)  // None

optSome1.filter( v => v.head == 'o') // Some(optSome1)
optSome1.filter( v => v.head == 'h') // None
