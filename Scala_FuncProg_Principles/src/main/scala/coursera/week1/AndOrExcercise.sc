def and(x:Boolean, y:Boolean):Boolean = {
    if (x) y else false
}

def or(x:Boolean, y:Boolean):Boolean = {
    if (x) x else y
}

println(and(true, true))
println(and(false, true))
println(and(true, false))
println(and(false, false))

println(or(true, true))
println(or(false, true))
println(or(true, false))
println(or(false, false))
