package coursera.week1

import scala.annotation.tailrec

object Main {
    def main(args: Array[String]) {
        println("Pascal's Triangle")
        for (row <- 0 to 10) {
            for (col <- 0 to row)
                print(pascal(col, row) + " ")
            println()
        }


        println(countChange(5, List(1, 2, 3)))
    }

    /**
      * Exercise 1
      */
    def pascal(c: Int, r: Int): Int = {
        if (c < 0 || c > r) {
            0
        }
        else if (c == 0 || c == r) {
            1
        }
        else {
            pascal(c - 1, r - 1) + pascal(c, r - 1)
        }
    }

    /**
      * Exercise 2
      */
    def balance(chars: List[Char]): Boolean = {
        @tailrec
        def balanceSubArray(chars: List[Char], count: Int): Boolean = {
            if (chars.isEmpty || count < 0) {
                count == 0
            }
            else {
                balanceSubArray(chars.tail, countBracket(chars.head, count))
            }
        }


        def countBracket(char: Char, count: Int): Int = {
            if (char == ')') {
                count - 1
            }
            else if (char == '(') {
                count + 1
            }
            else {
                count
            }
        }

        balanceSubArray(chars, 0)

    }

    /**
      * Exercise 3
      */
    def countChange(money: Int, coins: List[Int]): Int = {
        def countChangeAcc(money: Int, coins: List[Int], acc: Int): Int = {
            if (money == 0 && !coins.isEmpty) {
                acc + 1
            }
            else if (coins.isEmpty || money < 0) {
                acc
            }
            else {
                acc + countChangeAcc(money - coins.head, coins, acc) + countChangeAcc(money, coins.tail, acc)
            }
        }


        if (money <= 0 || coins.isEmpty) {
            0
        }

        countChangeAcc(money, coins, 0)
    }
}
