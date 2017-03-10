val tolerance = 0.0001

def isCloseEnough(x:Double, y:Double):Boolean = {
    abs((x-y)/x) /x < tolerance
}
def abs(x:Double):Double = {
    if (x > 0) x else -x
}
def fixedPoint(f:Double => Double)(firstGuess: Double) = {
    def iterate(guess:Double): Double = {
        println("guess = " + guess)
        val next = f(guess)
        if (isCloseEnough(guess, next)) next
        else iterate(next)
    }

    iterate(firstGuess)
}


fixedPoint(x => 5*x)(-10)

def sqrt(x:Double) = fixedPoint(y => (y + x/y) / 2)(1.0)

sqrt(2)

def averageDamp(f:Double => Double)(x:Double) = (x + f(x)) /2
def sqrtUpgraded(x:Double) = fixedPoint(averageDamp(y => x/y))(1)


def aaa(f:Int => Int)(a:Int, b:Int):Int = {a + f(b)}
def bbb(a:Int, b:Int) = aaa(x => x*x)(a:Int, b:Int)
def ccc() = aaa(x => x*x)(1,2)

aaa(x => x*x)(1,2)
bbb(1,2)
ccc()

