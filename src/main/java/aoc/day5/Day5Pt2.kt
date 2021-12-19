package aoc.day5

import aoc.readInputAsString

/**
 * Day 5 Part 2
 *
 *  Trying my best not to make it a load of if and while loops.
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
        } else if (line[0][1] == line[1][1]) {
            addHorizontal(line, coords)
        } else { // diagonal lines
            var x: Int; var y: Int; var theLimit: Int

            // work out which end of the line we want to start from
            if (line[1][0] >= line[0][0]) {
                x = line[0][0]
                y = line[0][1]
                theLimit = line[1][0]
            } else {
                x = line[1][0]
                y = line[1][1]
                theLimit = line[0][0]
            }

            val param = (line[1][0] - line[0][0]) * (line[1][1] - line[0][1])

            if (param > 0) { // Both increasing/decreasing
                while (x < theLimit + 1) {
                    coords[y][x] += 1
                    y += 1
                    x += 1
                }
            } else {
                while (x < theLimit + 1) {
                    coords[y][x] += 1
                    y -= 1
                    x += 1
                }
            }
        }
    }

    var count = 0

    for (i in 0 until maxY + 1) {
        count += coords[i].count { it > 1 }
    }

    println(count)
}
