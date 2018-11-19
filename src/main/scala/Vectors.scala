// Import Expr and some extension methods
import scala.quoted._
import scala.tasty._

object Vectors {

  // Needed to show quotes
  implicit val toolbox: scala.quoted.Toolbox = scala.quoted.Toolbox.make

  /** Compute the dot product of the vectors (represented as arrays)
   *  Returns (v1(0) * v2(0)) + (v1(1) * v2(1)) + ... + (v1(v1.length - 1) * v2(v2.length - 1))
   *  Or throws an exception if v1.length != v2.length
   *  Both arrays are assumed to be immutable.
   */
  inline def dot(v1: => Array[Int], v2: => Array[Int]): Int = ~dotImpl('(v1), '(v2))(Tasty.macroContext)
  
  /** Generates code to compute the dot product.
   *  Will try to partially evaluate any statically available data.
   */
  def dotImpl(v1: Expr[Array[Int]], v2: Expr[Array[Int]])(reflect: Tasty): Expr[Int] = {
    import reflect._

    object EmptyArray {
      def unapply(arg: Tree): Boolean = arg match {
        case Term.Apply(Term.Apply(Term.TypeApply(Term.Select(Term.Ident("Array"), "apply", _), List(TypeTree.Synthetic())), List(Term.Typed(Term.Repeated(Nil), TypeTree.Synthetic()))), _) => true
        case _ => false
      }
    }

    // Useful methods
    // Use i.toExpr to lift an i:Int into an quoted.Expr[Int]
    // Use q.toTasty to transform a q:quoted.Expr[_] to a Tasty.Tree
    // Use tree.toExpr[Int] to transform a tree:Tasty.Tree to a quoted.Expr[Int]
    // Use q.show to show the code of a q:quoted.Expr[_]
    // Use tree.show to show the extractors needed to pattern match a tree:Tasty.Tree

    val generatedCode = (v1.toTasty.underlyingArgument, v2.toTasty.underlyingArgument) match {
      case (EmptyArray(), EmptyArray()) => '(0)
      // TODO Exercise: optimize more cases
      // case (EmptyArray(), _) => '()
      // ...
      case (tv1, tv2) => 
        // Print the extractors of tv1 and tv2
        // println(tv1.show)
        // println(tv2.show)
        dynamicDot(v1, v2)
    }

    println(
      s"""============================================
         |Expanding macro 
         |
         |Vectors.dot(${v1.show}, ${v2.show})
         |
         |to 
         |
         |${generatedCode.show}
         |
       """.stripMargin)

    generatedCode
  }

  /** Staged code that computes the the dot product with a while loop */
  def dynamicDot(v1: Expr[Array[Int]], v2: Expr[Array[Int]]): Expr[Int] = '{
    val vv1 = ~v1
    val vv2 = ~v2
    val len = vv1.length
    if (vv2.length != len)
      throw new Exception(s"Vectors must have the same sizes ($len, ${vv2.length}")
    var sum = 0
    var i = 0
    while (i < len) {
      sum += vv1(i) * vv2(i)
      i += 1
    }
    sum
  }

}
