package aoc.day15

import aoc.readInputAsString

var maxX = 0
var maxY = 0

/**
 * Day 15 Part 1
 *
 * No idea if there is a specific library that performs dijkstras alg
 * Had to implement it with a min heap as the normal dijkstras was too slow.
 */
fun main() {
    val inputString = readInputAsString("src/main/aoc/day15/input.txt")
    val inputGrid = inputString.split("\n").map { it.toList().map { it.toString().toInt() } }

    maxX = inputGrid[0].size - 1
    maxY = inputGrid.size - 1

    // Used this rather than looking through heap in attempt to save a bit of time
    val visitedGrid = Array(inputGrid.size) { IntArray(inputGrid[0].size) { 0 } }
    // Max dist was set to 1 billion rather than max int as that caused comparison problems
    val distanceGrid = Array(inputGrid.size) { IntArray(inputGrid[0].size) { 1000000000 } }
    distanceGrid[0][0] = 0

    val minHeap = addValuesToMinHeap(inputGrid)

    applyDijkstras(minHeap, visitedGrid, inputGrid, distanceGrid)
}

fun addValuesToMinHeap(inputGrid: List<List<Int>>): MinHeap {
    val minHeap = MinHeap(inputGrid.size * inputGrid[0].size)

    // Add stuff to min heap
    for (i in 0..maxX) {
        for (j in 0..maxY) {
            if (i == 0 && j == 0) {
                minHeap.assign(intArrayOf(0, 0, 0))
            } else {
                minHeap.assign(intArrayOf(i, j, 1000000000))
            }
        }
    }
    return minHeap
}

fun applyDijkstras(minHeap: MinHeap, visitedGrid: Array<IntArray>, inputGrid: List<List<Int>>, distanceGrid: Array<IntArray>) {
    while (true) {
        val newPoint = minHeap.pop()

        visitedGrid[newPoint[1]][newPoint[0]] = 1

        if (newPoint[0] == maxX && newPoint[1] == maxY) {
            println(newPoint[2])
            break
        }

        alterNeighbouringPointsInHeap(newPoint, minHeap, inputGrid, visitedGrid, distanceGrid)
    }
}

fun alterNeighbouringPointsInHeap(point: IntArray, minHeap: MinHeap, inputGrid: List<List<Int>>, visitedGrid: Array<IntArray>, distanceGrid: Array<IntArray>) {

    val x = point[0]
    val y = point[1]

    val ly: Int
    val uy: Int
    val lx: Int
    val ux: Int

    if (x == 0) {
        lx = 0
    } else {
        lx = x - 1
    }
    if (x == maxX) {
        ux = x
    } else {
        ux = x + 1
    }

    if (y == 0) {
        ly = 0
    } else {
        ly = y - 1
    }
    if (y == maxY) {
        uy = y
    } else {
        uy = y + 1
    }

    val pointRange = mutableListOf<List<Int>>()

    for (i in lx..ux) {
        for (j in ly..uy) {
            if (i == x || j == y) {
                pointRange.add(listOf(i, j))
            }

        }
    }

    for (neig in pointRange) {
        if (visitedGrid[neig[1]][neig[0]] == 0) {
            val cost = inputGrid[neig[1]][neig[0]]
            if (point[2] + cost < distanceGrid[neig[1]][neig[0]]) {
                distanceGrid[neig[1]][neig[0]] = point[2] + cost
                minHeap.updateDist(intArrayOf(neig[0], neig[1], point[2] + cost))
            }
        }
    }
}
