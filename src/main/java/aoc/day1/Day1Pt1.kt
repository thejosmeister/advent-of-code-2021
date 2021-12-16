package aoc.day1

import aoc.readInputAsString

/**
 * Day 1 Part 1
 *
 * The IDE is helping me out with my Kotlin illiteracy.
 */
fun main() {
    val inputString = readInputAsString("src/main/aoc/day1/input.txt")
    val inputNums = inputString.split('\n').map { it.toInt() }

    val diffs = mutableListOf<Int>()
    for (num in 1 until inputNums.size) {
        diffs.add(inputNums[num] - inputNums[num - 1])
    }
    println(diffs.count { it > 0 })
}
