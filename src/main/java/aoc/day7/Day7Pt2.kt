package aoc.day7

import aoc.readInputAsString
import kotlin.math.absoluteValue


/**
 * Day 7 Part 2
 *
 * Have to shove a bunch of extra chars into some lines just because the input numbers might not have a max.
 */
fun main() {
    val inputString = readInputAsString("src/main/java/aoc/day7/input.txt")
    val numbers = inputString.split(",").map { it.toInt() }.toIntArray()

    val sums = numbers.max()?.let { IntArray(it + 1) }
    sums?.set(0, 0)

    for(i in numbers.max()?.let { 1 until it + 1 }!!){
        sums?.set(i, sums[i-1] + i)
    }

    val outs = mutableListOf<Int>()

    // Looks more confusing than it is. Thank you to the IDE for writing this line.
    for (i in numbers.toSet().max()?.let { 0.rangeTo(it) }!!){
        outs.add(calculateNewAbs(i, numbers, sums))
    }

    println(outs.min())
}

fun calculateNewAbs(x: Int, nums: IntArray, sums: IntArray?): Int {
    var out = 0
    nums.forEach { out += sums?.get((x - it).absoluteValue) ?: 0 }
    return out
}
