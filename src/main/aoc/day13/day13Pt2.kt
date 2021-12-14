package aoc.day13

import aoc.readInputAsString
import java.lang.Integer.max

/**
 * Day 13 Part 2
 *
 * Exposed out a few floors in my folding function from pt1
 */
fun main() {
    val inputString = readInputAsString("src/main/aoc/day13/input.txt")
    val inputParts = inputString.split("\n")
    val coords = inputParts.slice(0 until inputParts.indexOf("")).map { it.split(",").map { it.toInt() } }
    val instructions = inputParts.slice((inputParts.indexOf("") + 1) until inputParts.size).map { it.split("fold along ")[1] }.map { it.split("=") }

    val maxX = (coords.map { it[0] }.max() ?: 0) + 1
    val maxY = (coords.map { it[1] }.max() ?: 0) + 1

    var grid = Array(maxY) { IntArray(maxX) { 0 } }

    coords.forEach { grid[it[1]][it[0]] = 1 }

    instructions.forEach { grid = foldGrid(grid, it) }

    // Output folded paper
    grid.forEach {
        it.forEach {
            if (it > 0) {
                print("#")
            } else {
                print(" ")
            }
        }
        println()
    }
}
