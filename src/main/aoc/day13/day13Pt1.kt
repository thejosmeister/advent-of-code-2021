package aoc.day13

import aoc.readInputAsString

/**
 * Day 13 Part 1
 *
 * Alot of faffing with upper and lower bounds of ranges but got there in the end.
 */
fun main() {
    val inputString = readInputAsString("src/main/aoc/day13/testInput.txt")
    val inputParts = inputString.split("\n")
    val coords = inputParts.slice(0 until inputParts.indexOf("")).map { it.split(",").map { it.toInt() } }
    val instructions = inputParts.slice((inputParts.indexOf("") + 1) until inputParts.size).map { it.split("fold along ")[1] }.map { it.split("=") }

    val maxX = (coords.map { it[0] }.max() ?: 0) + 1
    val maxY = (coords.map { it[1] }.max() ?: 0) + 1

    val grid = Array(maxY) { IntArray(maxX) { 0 } }

    coords.forEach { grid[it[1]][it[0]] = 1 }

    val newGrid = foldGrid(grid, instructions[0])

    println(newGrid.map { it.count { it > 0 } }.sum())
}

fun foldGrid(grid: Array<IntArray>, instruction: List<String>): Array<IntArray> {
    val axis = instruction[0]
    val number = instruction[1].toInt()

    val maxX: Int
    val maxY: Int

    // Why did I choose y here and x everywhere else?
    if (axis == "y") {
        maxX = grid[0].size
        maxY = number
    } else {
        maxY = grid.size
        maxX = number
    }

    val outGrid = grid.sliceArray(0 until maxY).map { it.sliceArray(0 until maxX) }.toTypedArray()

    val foldedOverBit: Array<IntArray>
    // Reversed the lists so that folded bit will sit over the other bit correctly (bit easier to visualise when doing
    // the indices stuff in the next bit
    if (axis == "x") {
        foldedOverBit = grid.map { it.sliceArray(number + 1 until grid[0].size) }.toTypedArray()
        foldedOverBit.forEach { it.reverse() }
    } else {
        foldedOverBit = grid.sliceArray(number + 1 until grid.size)
        foldedOverBit.reverse()
    }

    // Work from either the far right of each row or bottom of each column
    if (axis == "x") {
        for (i in outGrid.indices) {
            var k = foldedOverBit[0].size - 1
            for (j in outGrid[0].size - 1 downTo 0) {
                outGrid[i][j] = outGrid[i][j] + foldedOverBit[i][k]
                k--
                if (k < 0) {
                    break
                }
            }
        }
    } else {
        for (i in outGrid[0].indices) {
            var k = foldedOverBit.size - 1
            for (j in outGrid.size - 1 downTo 0) {
                outGrid[j][i] = outGrid[j][i] + foldedOverBit[k][i]
                k--
                if (k < 0) {
                    break
                }
            }
        }
    }

    return outGrid
}
