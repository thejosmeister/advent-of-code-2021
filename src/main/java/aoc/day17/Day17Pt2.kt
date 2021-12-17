package aoc.day17

import aoc.readInputAsString

/**
 * Day 17 Part 2
 *
 * Just a brute force using the extreme points of the hole as a limit.
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

    val velocities = mutableListOf<List<Int>>()

    for(initialX in 1..ux){
        for(initialY in ly..(ly * -1)){
            var t = 0
            var xPos = 0
            var yPos = 0
            while(xPos <= ux && yPos >= ly){
                t++
                xPos = xForGivenT(t,initialX)
                yPos = yForGivenT(t, initialY)

                if(xPos <= ux && yPos >= ly && xPos >= lx && yPos <= uy){
                    velocities.add(listOf(initialX,initialY))
                    break
                }
            }
        }
    }

    println(velocities.size)
}

// x and y functions are derived from arithmetic sum formula.

fun yForGivenT(t: Int, _y: Int): Int{
    return (t*(1-t))/2 + (t*_y)
}

fun xForGivenT(t: Int, _x: Int): Int{
    return if(t > _x){
        (_x * (1 - _x)) / 2 + (_x * _x)
    } else {
        (t * (1 - t)) / 2 + (t * _x)
    }
}

