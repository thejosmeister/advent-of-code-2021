package aoc.day20

import aoc.readInputAsString

/**
 * Day 20 Part 1
 *
 * Some light relief after making a mess of 19.
 */
fun main() {
    val inputString = readInputAsString("src/main/java/aoc/day20/input.txt")
    val inputLines = inputString.split("\n")

    val algorithm = inputLines[0]
    val initialPicture = inputLines.slice(2 until inputLines.size).map { it.toMutableList() }.toMutableList()

    var picture = padPicture(initialPicture, '.')

    for( times in 0..2 ) {

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

fun determineChar(map: List<List<Char>>, algorithm: String): Char {
    var bitString = ""
    for( i in 0..2 ){
        for( j in 0..2 ){
            when( map[i][j] ){
                '.' -> bitString += "0"
                '#' -> bitString += "1"
            }
        }
    }

    return algorithm[bitString.toInt(2)]
}

fun padPicture(initialPicture: MutableList<MutableList<Char>>, c: Char): MutableList<MutableList<Char>> {
    val out = initialPicture
    val w = out[0].size

    for( i in 0..2) {
        out.add(0, MutableList(w + 6) { c })
        out.add( MutableList(w + 6) { c })
    }

    val h = out.size
    for( i in 3..h-4){
        out[i].add(0, c)
        out[i].add(0, c)
        out[i].add(0, c)
        out[i].add(c)
        out[i].add(c)
        out[i].add(c)
    }

    return out
}
