package aoc.day6

import aoc.readInputAsString

/**
 * Day 6 Part 1
 *
 * Pretty quick and dirty.
 */
fun main() {
    val inputString = readInputAsString("src/main/aoc/day6/input.txt")
    var inputNumbers = inputString.split(",").map { it.toInt() }.toMutableList()

    var day = 0

    while(day < 80){
        inputNumbers = incrementDay(inputNumbers)
        day++
    }

    print(inputNumbers.size)
}

fun incrementDay(fish: MutableList<Int>): MutableList<Int>{
    val out = mutableListOf<Int>()

    fish.forEach {
        when (it){
            0 -> { out.add(6); out.add(8) }
            else -> out.add(it - 1)
        }
    }
    return out
}
