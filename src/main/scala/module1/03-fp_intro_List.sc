sealed trait List[+T] {
  /**
   * Метод cons, добавляет элемент в голову списка, для этого метода можно воспользоваться названием `::`
   *
   */
  def ::[TT >: T](elem: TT): List[TT] = new List.::(elem, this)

  /**
   * Метод mkString возвращает строковое представление списка, с учетом переданного разделителя
   *
   */
  def mkString(sep: String): String = {
    def loop(l: List[T], acc: String): String = {
      l match {
        case List.Nil => acc
        case List.::(head, List.Nil) => acc + head.toString
        case List.::(head, tail) => loop(tail, acc + head.toString + sep)
        }
    }
    loop(this, sep).drop(sep.length)
  }

  /**
   *
   * Реализовать метод reverse который позволит заменить порядок элементов в списке на противоположный
   */
  def reverse: List[T] = {
    def loop(input: List[T], acc: List[T] = List[T]()): List[T] = {
      input match {
        case List.Nil => acc
        case List.::(head, tail) => loop(tail, head :: acc)
      }
    }
    loop(this)
  }

  /**
   *
   * Реализовать метод map для списка который будет применять некую ф-цию к элементам данного списка
   */
  def map[B](f: T => B): List[B] = {
    def loop(xs: List[T], acc: List[B] = List[B]()): List[B] = {
      xs match {
        case List.::(head, tail) => loop(tail, f(head)::acc)
        case List.Nil => acc
      }
    }
    loop(this)
  }

  /**
   *
   * Реализовать метод filter для списка который будет фильтровать список по некому условию
   */
  def filter(f: T => Boolean): List[T] = {

    def loop(xs: List[T], acc: List[T] = List[T]()): List[T] = {
      xs match {
        case List.::(head, tail) if f(head) == true => loop(tail, head :: acc)
        case List.Nil => acc
      }
    }
    loop(this)
  }

  /**
   *
   * Написать функцию incList котрая будет принимать список Int и возвращать список,
   * где каждый элемент будет увеличен на 1
   */
  def incList(xs: List[Int]): List[Int] = {
    def loop(xs: List[Int], acc: List[Int] = List[Int]()): List[Int] = {
      xs match {
        case List.Nil => acc
        case List.::(head, tail) => loop(tail, head + 1 :: acc)
      }
    }
    loop(xs)
  }

  /**
   *
   * Написать функцию shoutString котрая будет принимать список String и возвращать список,
   * где к каждому элементу будет добавлен префикс в виде '!'
   */
  def shoutString(xs: List[String]): List[String] = {
    def loop(xs: List[String], acc: List[String] = List[String]()): List[String] = {
      xs match {
        case List.Nil => acc
        case List.::(head, tail) => loop(tail, "!" + head :: acc)
      }
    }
    loop(xs)
  }

}


object List {

  case class ::[A](head: A, tail: List[A]) extends List[A]

  case object Nil extends List[Nothing]

  def apply[A](v: A*): List[A] =
    if (v.isEmpty) Nil else ::(v.head, apply(v.tail: _*))


}

val test = List(12,23,45,6)

test.map(x=>x+1).reverse.mkString(",")

//test.filter(x => x >= 10)

test.incList(List(12,23,45,6))
test.shoutString(List("1","2"))















