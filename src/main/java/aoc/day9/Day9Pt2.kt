package aoc.day9

import aoc.readInputAsString

/**
 * Day 9 Part 2
 *
 * A good bit of recursion.
 * Not the most efficient, I think it may be vulnerable to a race condition if more than one thread is at play.
 */
fun main() {
    val inputString = readInputAsString("src/main/java/aoc/day9/input.txt")
    val inputGrid = inputString.split("\n").map { it.toList().map { it.toString().toInt() } }

    val maxX = inputGrid[0].size
    val maxY = inputGrid.size

    val basinSizes = mutableListOf<Int>()
    val basinCoords = mutableListOf<String>()
    var size: Int

    for (y in 0 until maxY) {
        for (x in 0 until maxX) {
            size = findBasin(inputGrid, basinCoords, x, y, maxX, maxY)
            if( size > 0){
                basinSizes.add(size)
            }
        }
    }

    basinSizes.sort()

    println(basinSizes.takeLast(3).reduce { acc, i -> acc * i })
}

// Ultimately returns the size of the basin
fun findBasin(inputGrid: List<List<Int>>, basinCoords: MutableList<String>, x: Int, y: Int, maxX: Int, maxY: Int): Int {
    val coord = "$x$y"
    if(coord in basinCoords)
    {
        return 0
    }

    val point = inputGrid[y][x]

    // 9s are the only points never in a basin
    if(point == 9)
    {
        return 0
    }

    basinCoords.add(coord)
    var out = 1 // add 1 for the current point

    if (x != 0) {
        out += findBasin(inputGrid, basinCoords, x - 1, y, maxX, maxY)
    }

    if (y != 0) {
        out += findBasin(inputGrid, basinCoords, x, y - 1, maxX, maxY)
    }

    if (x != maxX - 1) {
        out += findBasin(inputGrid, basinCoords, x + 1, y, maxX, maxY)
    }

    if (y != maxY - 1) {
        out += findBasin(inputGrid, basinCoords, x, y + 1, maxX, maxY)
    }

    return out
}
