import scala.annotation.tailrec

def factorial(n:Int) = {
    @tailrec
    def loop(result:Int, n:Int):Int = {
        if (n == 0) result
        else loop(result*n, n-1)
    }

    loop(1, n)
}


factorial(4)

@tailrec
def factorialNonTailRec(n:Int):Int = {
    if (n==0) 1 else n*factorialNonTailRec(n-1)
}

factorialNonTailRec(4)