//Retroactively extending classes
//часть 1
extension (x: String)
def +(y: String): Int = (x + y).toInt

"1"+"3"

//Viewing one type as another
// часть 2
object Completions {
  enum CompletionArg {
    case ShowItIsString(s: String)
    case ShowItIsInt(i: Int)
    case ShowItIsFloat(f: Float)
  }

  object CompletionArg {
    given fromString : Conversion[String, CompletionArg] = ShowItIsString(_)
    given fromInt : Conversion[Int, CompletionArg] = ShowItIsInt(_)
    given fromFloat: Conversion[Float, CompletionArg] = ShowItIsFloat(_)

  }
  import CompletionArg.*

  def complete[T](arg: CompletionArg) = arg match
  case ShowItIsString(s) => println(s)
  case ShowItIsInt(i) => println(i)
  case ShowItIsFloat(f) => println(f)

}
Completions.complete("a")
Completions.complete(1)
Completions.complete(2f)

//Opaque Types
// часть 3
object MyMath {
  opaque type Logarithm = Double

  object Logarithm {

    def apply(d: Double): Logarithm = math.log(d)
    def safe(d: Double): Option[Logarithm] =
      if d > 0.0 then Some(math.log(d)) else None

  }
  extension (x: Logarithm)
  def toDouble: Double = math.exp(x)
  def +(y: Logarithm): Logarithm = Logarithm(math.exp(x) + math.exp(y))
  def *(y: Logarithm): Logarithm = x + y
}

val l = MyMath.Logarithm(1.0)
val l2 = MyMath.Logarithm(2.0)
val l3 = l * l2
val l4 = l + l2