class Person {

    var name = "Ted"
    val lastName = "Neward"

    def sayHello() = println("Hey!")
}

object App {

    def main(args: Array[String]) {
        val p = new Person()
        println(p.name)
        p.name = "123"  //
        println(p.name)

        var y = ((x: Int) => x +1).apply(1)
        println(y)
        y = ((x: Int) => x +1)(2)
        println(y)

        val addOne = (x: Int) => x + 1
        y = addOne(3)
        println(y)

        y = addOne.apply(4)
        println(y)
    }
}


case class Account(amount: Double, currency: String)