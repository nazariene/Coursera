/*
 * Copyright (c) 2002-2016 EMC Corporation All Rights Reserved
 */
object ScalaObj {

    //val main = Main;
    def main(args: Array[String]): Unit = {
        val numbers = Array(1, 2, 3)
        //numbers = Array(1,2,2,2); val .. is final

        val names = List("Ted", "David", "Someone")
        for (n <- numbers; name <- names) {
            println(n + " " + name)
        }

        var numbers1 = Array(1,2,2)
        println(numbers1.toString())
        numbers1 = Array(2,2,2)
        println(numbers1.toString())
    }
}
