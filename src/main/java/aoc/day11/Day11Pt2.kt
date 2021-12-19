package aoc.day11

import aoc.readInputAsString
import java.util.*

/**
 * Day 11 Part 2
 *
 * Pretty simple part 2, once they are all 0 I guess they must cycle through the numbers together.
 */
fun main() {
    val inputString = readInputAsString("src/main/java/aoc/day11/input.txt")
    var inputGrid = inputString.split("\n").map { it.toList().map { it.toString().toInt() }.toIntArray() }.toMutableList()

    val maxX = inputGrid[0].size
    val maxY = inputGrid.size

    var step = 1

    while (true) {
        for (y in 0.until(maxY)) {
            inputGrid[y] = IntArray(maxX) { index ->
                inputGrid[y][index] + 1
            }
        }

        for (y in 0 until maxY) {
            for (x in 0 until maxX) {
                inputGrid = flashOctopus(inputGrid, x, y, maxX, maxY)
            }
        }

        if (inputGrid.map { it.count { it == 0 } }.sum() == 100) {
            println(step)
            break
        }
        step++
    }
}

