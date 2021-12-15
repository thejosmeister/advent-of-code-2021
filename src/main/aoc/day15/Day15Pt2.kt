package aoc.day15

import aoc.readInputAsString
import java.util.*

/**
 * Day 15 Part 2
 *
 * Probably not the most succinct way to create the larger input
 * Took around 3 mins to run so I guess there is scope for streamlining the min heap
 */
fun main() {
    val inputString = readInputAsString("src/main/aoc/day15/input.txt")
    val inputGrid = inputString.split("\n").map { it.toList().map { it.toString().toInt() } }

    val newInputLines = mutableListOf<Array<Int>>()
    val maxX1 = inputGrid[0].size
    val maxY1 = inputGrid.size

    for (i in 0 until inputGrid.size * 5) {
        newInputLines.add(arrayOf(inputGrid[i % inputGrid.size].toTypedArray(), inputGrid[i % inputGrid.size].toTypedArray(), inputGrid[i % inputGrid.size].toTypedArray(), inputGrid[i % inputGrid.size].toTypedArray(), inputGrid[i % inputGrid.size].toTypedArray()).flatten().toTypedArray())
    }

    maxX = inputGrid[0].size * 5 - 1
    maxY = inputGrid.size * 5 - 1

    for (i in 0..maxX) {
        for (j in 0..maxY) {
            newInputLines[j][i] = (newInputLines[j][i] - 1 + i / maxX1 + j / maxY1) % 9 + 1
        }
    }

    val largeInputGrid = mutableListOf<List<Int>>()
    newInputLines.forEach { largeInputGrid.add(it.toList()) }
    largeInputGrid.toList()

    val visitedGrid = Array(largeInputGrid.size) { IntArray(largeInputGrid[0].size) { 0 } }
    val distanceGrid = Array(largeInputGrid.size) { IntArray(largeInputGrid[0].size) { 1000000000 } }
    distanceGrid[0][0] = 0

    val minHeap = addValuesToMinHeap(largeInputGrid)

    applyDijkstras(minHeap, visitedGrid, largeInputGrid, distanceGrid)
}
