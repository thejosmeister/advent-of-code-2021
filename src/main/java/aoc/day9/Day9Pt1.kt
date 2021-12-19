package aoc.day9

import aoc.readInputAsString

/**
 * Day 9 Part 1
 *
 * Spent a while figuring out why my answer was too high, turns out Char.toInt returns the ASCII value.
 */
fun main() {
    val inputString = readInputAsString("src/main/java/aoc/day9/input.txt")
    val inputGrid = inputString.split("\n").map { it.toList().map { it.toString().toInt() } }

    val maxX = inputGrid[0].size
    val maxY = inputGrid.size

    var lowPointScore = 0

    for (y in 0 until maxY) {
        for (x in 0 until maxX) {
            lowPointScore += isLowPoint(inputGrid, x, y, maxX, maxY)
        }
    }

    println(lowPointScore)
}

// Checks adjacent points and returns risk score if it is a low point
fun isLowPoint(inputGrid: List<List<Int>>, x: Int, y: Int, maxX: Int, maxY: Int): Int {
    val point = inputGrid[y][x]

    if (x != 0) {
        if (inputGrid[y][x - 1] <= point) {
            return 0
        }
    }

    if (y != 0) {
        if (inputGrid[y - 1][x] <= point) {
            return 0
        }
    }

    if (x != maxX - 1) {
        if (inputGrid[y][x + 1] <= point) {
            return 0
        }
    }

    if (y != maxY - 1) {
        if (inputGrid[y + 1][x] <= point) {
            return 0
        }
    }
    return point + 1
}
