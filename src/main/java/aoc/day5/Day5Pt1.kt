package aoc.day5

import aoc.readInputAsString

/**
 * Day 5 Part 1
 *
 *  Spent a while figuring out that the rangeTo method doesn't count down
 */
fun main() {
    val inputString = readInputAsString("src/main/java/aoc/day5/input.txt")
    val inputParts = inputString.split('\n').map { it.split(" -> ").map { it.split(",").map { it.toInt() } } }

    var maxX = 0; var maxY = 0

    for (line in inputParts) {
        for (coord in line) {
            if (coord[0] > maxX) {
                maxX = coord[0]
            }
            if (coord[1] > maxY) {
                maxY = coord[1]
            }
        }
    }

    val coords = Array(maxY + 1) { IntArray(maxX + 1) { 0 } }

    for (line in inputParts) {
        if (line[0][0] == line[1][0]) {
            addVertical(line, coords)

        }
        if (line[0][1] == line[1][1]) {
            addHorizontal(line, coords)
        }
    }

    var count = 0

    for (i in 0 until maxY + 1) {
        count += coords[i].count { it > 1 }
    }

    println(count)
}

fun addHorizontal(line: List<List<Int>>, coords: Array<IntArray>) {
    if (line[0][0] <= line[1][0]) {
        for (x in line[0][0].rangeTo(line[1][0])) {
            coords[line[0][1]][x] += 1
        }
    } else {
        for (x in line[0][0] downTo line[1][0]) {
            coords[line[0][1]][x] += 1
        }
    }
}

fun addVertical(line: List<List<Int>>, coords: Array<IntArray>) {
    if (line[0][1] <= line[1][1]) {
        for (y in line[0][1].rangeTo(line[1][1])) {
            coords[y][line[0][0]] += 1
        }
    } else {
        for (y in line[0][1] downTo line[1][1]) {
            coords[y][line[0][0]] += 1
        }
    }
}
