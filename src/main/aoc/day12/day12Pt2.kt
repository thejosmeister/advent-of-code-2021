package aoc.day12

import aoc.readInputAsString

/**
 * Day 12 Part 2
 *
 * Part 1 soln design made this part ok.
 */
fun main() {
    val inputString = readInputAsString("src/main/aoc/day12/input.txt")
    val edges = inputString.split("\n").map { it.split("-") }
    val points = edges.flatten().toSet().toList()

    val adjMatrix = createAdjMatrix(points, edges)
    // Used a map to keep track of if a point had been used twice
    val usedPoints = mutableMapOf<Int,Int>()
    for( j in points.indices){
        usedPoints[j] = 0
    }

    val paths = findAllPathsStartToEnd(points.indexOf("start"), points.indexOf("end"), adjMatrix, points, usedPoints)

    print(paths.size)
}

// Using the map of used points instead.
fun findAllPathsStartToEnd(start: Int, end: Int, adjMatrix: Array<IntArray>, points: List<String>, _usedPoints: MutableMap<Int,Int>): MutableList<MutableList<String>> {
    val paths = mutableListOf<MutableList<String>>()
    val usedPoints = _usedPoints.toMap().toMutableMap()

    if (start == end) {
        paths.add(mutableListOf(points[end]))
        return paths
    }

    if (points[start][0].isLowerCase()) {
        usedPoints[start] = (usedPoints[start] ?: 0) + 1
    }

    for (neig in findNeigs(adjMatrix, start)) {
        // Can only use start once (at the start)
        if (points[neig] == "start"){
            continue
        }
        // Only start checking for used points after we have two for one.
        if(usedPoints.filterValues { it > 1 }.isNotEmpty()){
            if (usedPoints[neig]!! > 0) {
                continue
            }
        }
        findAllPathsStartToEnd(neig, end, adjMatrix, points, usedPoints).forEach {
            it.add(points[start])
            paths.add(it)
        }
    }

    return paths
}
