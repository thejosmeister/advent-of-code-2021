package aoc.day11

import aoc.readInputAsString
import java.util.*

/**
 * Day 11 Part 1
 *
 * Have to remember which way round x and y go with these arrays of arrays.
 */
fun main() {
    val inputString = readInputAsString("src/main/java/aoc/day11/input.txt")
    var inputGrid = inputString.split("\n").map { it.toList().map { it.toString().toInt() }.toIntArray() }.toMutableList()

    val maxX = inputGrid[0].size
    val maxY = inputGrid.size

    var flashes = 0

    for (step in 1.rangeTo(100)) {
        // increment all of them
        for (y in 0.until(maxY)) {
            inputGrid[y] = IntArray(maxX) { index ->
                inputGrid[y][index] + 1
            }
        }

        // flash any that need it
        for (y in 0 until maxY) {
            for (x in 0 until maxX) {
                inputGrid = flashOctopus(inputGrid, x, y, maxX, maxY)
            }
        }

        flashes += inputGrid.map { it.count { it == 0 } }.sum()
    }

    println(flashes)
}

fun flashOctopus(inputGrid: MutableList<IntArray>, x: Int, y: Int, maxX: Int, maxY: Int): MutableList<IntArray> {
    var _inputGrid = inputGrid

    if (_inputGrid[y][x] <= 9) {
        return _inputGrid
    }

    _inputGrid[y][x] = 0

    // A load of set up to increment the nearby octopuses (octopi?)
    val ly: Int
    val uy: Int
    val lx: Int
    val ux: Int

    if (x == 0) {
        lx = 0
    } else {
        lx = x - 1
    }
    if (x == maxX - 1) {
        ux = x
    } else {
        ux = x + 1
    }

    if (y == 0) {
        ly = 0
    } else {
        ly = y - 1
    }
    if (y == maxY - 1) {
        uy = y
    } else {
        uy = y + 1
    }

    for (i in ly.rangeTo(uy)) {
        for (j in lx.rangeTo(ux)) {
            if (_inputGrid[i][j] != 0) {
                _inputGrid[i][j]++
                if (_inputGrid[i][j] > 9) {
                    _inputGrid = flashOctopus(_inputGrid, j, i, maxX, maxY)
                }
            }
        }
    }

    return _inputGrid
}
