import org.junit.Test
import org.junit.Assert._

import Vectors._

class VectorTests {

  @Test def emptyVector(): Unit = {
    assertEquals(dot(Array(), Array()), 0)
    val empty: Array[Int] = Array()
    assertEquals(dot(empty, Array()), 0)
  }
  
  @Test def zeroVector(): Unit = {
    assertEquals(dot(Array(1, 2, 3, 4, 5), Array(0, 0, 0, 0, 0)), 0)
    val zeros: Array[Int] = Array(0, 0, 0, 0, 0)
    assertEquals(dot(Array(1, 2, 3, 4, 5), zeros), 0)
  }

  @Test def unitVector(): Unit = {
    assertEquals(dot(Array(1, 2, 3, 4, 5), Array(1, 1, 1, 1, 1)), 15)
    val vec1: Array[Int] = Array(1, 2, 3, 4, 5)
    val unit: Array[Int] = Array(1, 1, 1, 1, 1)
    assertEquals(dot(Array(1, 2, 3, 4, 5), unit), 15)
    assertEquals(dot(vec1,  Array(1, 1, 1, 1, 1)), 15)
  }

  @Test def sparceVector(): Unit = {
    assertEquals(dot(Array(1, 2, 3, 4, 5), Array(0, 20, 0, 0, 50)), 290)
    val vec1: Array[Int] = Array(1, 2, 3, 4, 5)
    val vec2: Array[Int] = Array(0, 20, 0, 0, 50)
    assertEquals(dot(Array(1, 2, 3, 4, 5), vec2), 290)
    assertEquals(dot(vec1, vec2), 290)
  }

  @Test def denseVector(): Unit = {
    assertEquals(dot(Array(1, 2, 3, 4, 5), Array(10, 20, 30, 40, 50)), 550)
    val vec: Array[Int] = Array(1, 2, 3, 4, 5)
    assertEquals(dot(vec, Array(10, 20, 30, 40, 50)), 550)
  }


  @Test def mixedVector(): Unit = {
    val v1 = 1
    val z = 0
    assertEquals(dot(Array(1, 2, z, 4, 0), Array(v1, 0, 2, z, 2)), 1)
    val vec = Array(1, 2, z, 4, 0)
    assertEquals(dot(vec, Array(v1, 0, 2, z, 2)), 1)
  }
}
