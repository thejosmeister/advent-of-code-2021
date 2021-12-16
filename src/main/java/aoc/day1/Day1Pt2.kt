package aoc.day1

import aoc.readInputAsString

/**
 * Day 1 Part 2
 *
 * Part 1 solution made this part easier.
 */
fun main() {
    val inputString = readInputAsString("src/main/aoc/day1/input.txt")
    val inputNums = inputString.split('\n').map { it.toInt() }

    val windowSums = mutableListOf<Int>()
    for (num in 0.rangeTo(inputNums.size - 3)) {
        windowSums.add(inputNums[num] + inputNums[num + 1] + inputNums[num + 2])
    }

    val diffs = mutableListOf<Int>()
    for (num in 1 until windowSums.size) {
        diffs.add(windowSums[num] - windowSums[num - 1])
    }
    println(diffs.count { it > 0 })
}
