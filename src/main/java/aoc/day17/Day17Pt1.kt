package aoc.day17

import aoc.readInputAsString

/**
 * Day 17 Part 1
 *
 * Quite a nice soln (think it works on some assumptions/assertions that the code does not verify)
 */
fun main() {
    val inputString = readInputAsString("src/main/java/aoc/day17/input.txt")

    val regex = """target area: x=(-?\d+)\.\.(-?\d+), y=(-?\d+)\.\.(-?\d+)""".toRegex()
    val matchResult = regex.find(inputString)
    val (_lx, _ux, _ly, _uy) = matchResult!!.destructured

    val lx = _lx.toInt()
    val ly = _ly.toInt()
    val ux = _ux.toInt()
    val uy = _uy.toInt()

    println(lx)
    println(ux)
    println(ly)
    println(uy)

    /*
    Let _x and _y be init velocities for x and y.

    We can always find an _x that causes x to sit in the x range of the hole.

    Thus we just need to look at when y will be in the hole. As y decreases consitently we know that
    we end up back at y=0 in the same steps as we rose so the next step doen will be _y + 1.
    Thus we find _y s.t. _y = |ly| - 1

    With this _y we can find the max value using calculus and the arithmetic sim formula:

    maxY = (_y^2 + _y + 1/4)/2 (can round down for integer answer)
     */
    val _y = (ly * -1) - 1
    println(getMaxYForGiven_Y(_y))
}

fun getMaxYForGiven_Y(_y: Int): Int{
    return (((_y*_y + _y).toFloat() + (1f/4f))/2f).toInt()
}

