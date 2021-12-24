package aoc.day20

import aoc.readInputAsString

/**
 * Day 20 Part 2
 *
 * Only had to change a 1 to 49.
 */
fun main() {
    val inputString = readInputAsString("src/main/java/aoc/day20/input.txt")
    val inputLines = inputString.split("\n")

    val algorithm = inputLines[0]
    val initialPicture = inputLines.slice(2 until inputLines.size).map { it.toMutableList() }.toMutableList()

    var picture = padPicture(initialPicture, '.')

    for( times in 0..49 ) {

        var newPicture = mutableListOf<MutableList<Char>>()

        for (i in 1..picture.size - 2) {
            newPicture.add(mutableListOf<Char>())
            for (j in 1..picture[0].size - 2) {
                newPicture[i - 1].add(determineChar(picture.slice(i - 1..i + 1).map { it.slice(j - 1..j + 1) }, algorithm))
            }
        }

        picture = padPicture(newPicture, determineChar(picture.slice(0..2).map { it.slice(0..2) }, algorithm))
    }

    println(picture.map { it.count{ it == '#'} }.sum())

}
