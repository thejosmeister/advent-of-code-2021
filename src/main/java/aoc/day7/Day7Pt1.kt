package aoc.day7

import aoc.readInputAsString
import kotlin.math.absoluteValue


/**
 * Day 7 Part 1
 *
 * Wasted a lot of time trying to think up a clever way to find the min. Ended up doing it the brute force way.
 */
fun main() {
    val inputString = readInputAsString("src/main/java/aoc/day7/input.txt")
    val numbers = inputString.split(",").map { it.toInt() }.toIntArray()

    val outs = mutableListOf<Int>()

    for (i in numbers.toSet().max()?.let { 0.rangeTo(it) }!!){
        outs.add(calculateAbs(i, numbers))
    }

    println(outs.min())
}

fun calculateAbs(x: Int, nums: IntArray): Int {
    var out = 0
    nums.forEach { out += (x - it).absoluteValue }
    return out
}
