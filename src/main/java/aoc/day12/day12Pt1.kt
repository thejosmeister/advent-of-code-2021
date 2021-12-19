package aoc.day12

import aoc.readInputAsString

/**
 * Day 12 Part 1
 *
 * Was wondering if there was a clever way to use an adjacency matrix but just used a fairly standard formula in the end.
 */
fun main() {
    val inputString = readInputAsString("src/main/java/aoc/day12/input.txt")
    val edges = inputString.split("\n").map { it.split("-") }
    val points = edges.flatten().toSet().toList()

    val adjMatrix = createAdjMatrix(points, edges)
    val usedPoints = mutableListOf<Int>()

    val paths = findAllPathsStartToEnd(points.indexOf("start"), points.indexOf("end"), adjMatrix, points, usedPoints)

    print(paths.size)
}

fun findAllPathsStartToEnd(start: Int, end: Int, adjMatrix: Array<IntArray>, points: List<String>, _usedPoints: MutableList<Int>): MutableList<MutableList<String>> {
    val paths = mutableListOf<MutableList<String>>()
    // Copying the list to avoid problems with object references.
    val usedPoints = _usedPoints.toList().toMutableList()

    if (start == end) {
        paths.add(mutableListOf(points[end]))
        return paths
    }

    // Can only use lowercase ones once
    if (points[start][0].isLowerCase()) {
        usedPoints.add(start)
    }

    for (neig in findNeigs(adjMatrix, start)) {
        if (neig in usedPoints) {
            continue
        }
        findAllPathsStartToEnd(neig, end, adjMatrix, points, usedPoints).forEach {
            it.add(points[start])
            paths.add(it)
        }
    }

    return paths
}

// finds horses
fun findNeigs(adjMatrix: Array<IntArray>, start: Int): List<Int> {
    val out = mutableListOf<Int>()
    for (i in adjMatrix[start].indices) {
        if (adjMatrix[start][i] == 1) {
            out.add(i)
        }
    }

    return out
}

fun createAdjMatrix(points: List<String>, edges: List<List<String>>): Array<IntArray> {
    val adjMatrix = Array(points.size) { IntArray(points.size) { 0 } }

    for (i in points.indices) {
        for (j in points.indices) {
            if (i == j) {
                continue
            }

            val point1 = points[i]
            val point2 = points[j]

            adjMatrix[i][j] = edges.filter { it[0] == point1 && it[1] == point2 || it[1] == point1 && it[0] == point2 }.size
        }
    }

    return adjMatrix
}
