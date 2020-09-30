package recfun

object RecFun extends RecFunInterface {

  def main(args: Array[String]): Unit = {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(s"${pascal(col, row)} ")
      println()
    }
  }

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int = {
    if(c == 0 || c == r) {
      1
    } else {
      pascal(c, r - 1) + pascal(c - 1, r - 1)
    }
  }

  /**
   * Exercise 2
   */
  def balance(chars: List[Char]): Boolean = {
    def loop(acc: Int, chars: List[Char]): Boolean = {
      if(chars.isEmpty || acc < 0) {
        acc == 0
      } else {
        chars.head match {
          case '(' => loop(acc + 1, chars.tail)
          case ')' => loop(acc - 1, chars.tail)
          case _ => loop(acc, chars.tail)
        }
      }
    }
    loop(0, chars)
  }

  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int = {
    def loop(money: Int, coins: List[Int], acc: Int): Int = {
      if(money < 0 || coins.isEmpty) {
        acc
      } else {
        if(money == 0) {
          acc + 1
        } else {
          loop(money, coins.tail, acc) + loop(money - coins.head, coins, acc)
        }
      }
    }
    loop(money, coins, 0)
  }
}
