package aoc.day3

import aoc.arraySubtract
import aoc.readInputAsString

/**
 * Day 3 Part 1
 *
 * Would like to find a smoother way of dealing with binary strings/numbers
 */
fun main() {
    val inputString = readInputAsString("src/main/aoc/day3/input.txt")
    val inputNums = inputString.split('\n')

    val lengthOfNum = inputNums[0].length

    val zeroSums = Array(lengthOfNum) { 0 }
    val oneSums = Array(lengthOfNum) { 0 }

    inputNums.forEach {
        for (num in it.indices) {
            when (it[num]) {
                '0' -> zeroSums[num] += 1
                '1' -> oneSums[num] += 1
            }
        }
    }

    val diffs = arraySubtract(zeroSums, oneSums)
    var gamma = ""

    for (i in diffs.indices) {
        when {
            (diffs[i]) > 0 -> gamma += "0"
            (diffs[i]) < 0 -> gamma += "1"
        }
    }

    val gammaAsNumber = gamma.toInt(2)
    val epsilon = "1".repeat(lengthOfNum).toInt(2) - gammaAsNumber

    println(epsilon * gammaAsNumber)
}


