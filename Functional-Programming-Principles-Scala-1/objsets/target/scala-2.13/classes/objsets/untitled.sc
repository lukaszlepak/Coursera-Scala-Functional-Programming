import java.util.NoSuchElementException

import scala.annotation.tailrec

trait Lista[T] {
  def isEmpty: Boolean
  def head: T
  def tail: Lista[T]
}

class Cons[T](val head: T, val tail: Lista[T]) extends Lista[T] {
  def isEmpty = false
}

class Nil[T] extends Lista[T] {
  def isEmpty = true
  def head = throw new NoSuchElementException
  def tail = throw new NoSuchElementException
}

//@tailrec
def nth[T](n: Int, list: Lista[T]): T = {
  if(n == 0){
    list.head
  } else {
    try {
      nth(n - 1, list.tail)
    } catch {
      case e: NoSuchElementException => throw new IndexOutOfBoundsException
    }
  }
}

val lista = new Cons(1, new Nil)
val lista2 = new Cons(2, lista)

nth(0, lista2)
nth(1, lista2)
nth(3, lista2)