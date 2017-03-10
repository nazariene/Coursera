def sqrt(x: Double):Double = {

    def sqrtIteration(guess: Double, x: Double): Double = {
        if (isGoodEnough(guess, x)) guess
        else sqrtIteration(improve(guess, x), x)
    }

    def isGoodEnough(guess: Double, x: Double): Boolean = {
        abs((guess*guess - x))/x < 0.001
    }

    def improve(guess: Double, x: Double): Double = {
        (x/guess + guess)/2
    }

    def abs(x:Double): Double = {
        if (x < 0) -x
        else x
    }


    //Block
    {
        sqrtIteration(1, x)
    }
}


//Not good for small numbers cause of presicion issues
//Not good for large numbers because of time required to calculate
//Fix = divide by X


sqrt(2)
sqrt(0.001)
sqrt(0.1e-20)
sqrt(1e60)
