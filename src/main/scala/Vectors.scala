import scala.quoted._ // import Expr, Type, Quotes, quotes

object Vectors {

  /** Compute the dot product of the vectors (represented as arrays)
   *  Returns (v1(0) * v2(0)) + (v1(1) * v2(1)) + ... + (v1(v1.length - 1) * v2(v2.length - 1))
   *  Or throws an exception if v1.length != v2.length
   *  Both arrays are assumed to be immutable.
   */
  inline def dot(inline v1: Array[Int], inline v2: Array[Int]): Int = 
    ${ dotImpl('v1, 'v2) }
  
  /** Generates code to compute the dot product.
   *  Will try to partially evaluate any statically available data.
   */
  def dotImpl(v1: Expr[Array[Int]], v2: Expr[Array[Int]])(using Quotes): Expr[Int] = {
    // import quotes.reflect._ // To enable reflection (not striclty necessary)

    // Useful methods
    // Use Expr(i) to lift an i:Int into an Expr[Int]
    // Use q.asTerm to transform a q:Expr[_] to a reflect.Tree (requires `import quotes.reflect._`)
    // Use tree.asExprOf[Int] to transform a tree:reflect.Tree to a Expr[Int]
    // Use q.show to show the code of a q:Expr[_]
    // Use tree.show to show the extractors needed to pattern match a tree:Tasty.Tree

    val generatedCode = (v1, v2) match {
      case (EmptyArray(), EmptyArray()) => 
        Expr(0)
      // TODO Exercise: optimize more cases
      // case (EmptyArray(), _) => '()
      // ...
      case (tv1, tv2) =>
        // Print tv1 and tv2
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
  def dynamicDot(v1: Expr[Array[Int]], v2: Expr[Array[Int]])(using Quotes): Expr[Int] = '{
    val vv1 = $v1
    val vv2 = $v2
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

  object EmptyArray {
    def unapply[T: Type](arg: Expr[T])(using Quotes): Boolean = arg match {
      case '{ Array[T]()(using $ct) } => true
      case _ => false
    }
  }
}
