package aoc.day19

import aoc.readInputAsString
import java.util.*
import kotlin.math.abs

/**
 * Day 19 Part 1
 *
 *
 */
fun main() {
    val inputString = readInputAsString("src/main/java/aoc/day19/testInput.txt")

    var scannerNumber = -1

    var scanners = mutableListOf<MutableList<List<Int>>>()

    for (line in inputString.split("\n")) {
        if (line.length == 0) {
            continue
        }

        if (line.contains("---")) {
            scannerNumber++
            scanners.add(mutableListOf())
            continue
        }

        scanners[scannerNumber].add(line.split(",").map { it.toInt() })
    }

//    println(scanners.map{it.size - 12}.sum() + 12)

    var arrayOfScanners = IntArray(scanners.size){it + 1}.map{ it - 1}



    while( arrayOfScanners.size > 1) {


        val closestPoints = mutableMapOf<Int,MutableList<IntArray>>()
        for (a in arrayOfScanners) {

            val scannerClosest = mutableListOf<IntArray>()

            for (i in scanners[a].indices) {
                val closestScore = IntArray(8) { 10000 }
                for (j in scanners[a].indices) {
                    if (i == j) continue

                    val objDist = findObjectiveDistance(scanners[a][i], scanners[a][j])

                    if (closestScore.take(3).sum() > objDist.sum()) {
                        for (k in 0..2) closestScore[k] = objDist[k]
                        closestScore[3] = j
                    }
                }
                scannerClosest.add(closestScore)
            }
            closestPoints[a] = scannerClosest
        }

        // Add in second closest
        for (scannerIndex in arrayOfScanners) {

            for (i in scanners[scannerIndex].indices) {
                val closestScore = IntArray(4) { 10000 }
                for (j in scanners[scannerIndex].indices) {
                    if (i == j) continue
                    if (j == closestPoints[scannerIndex]!![i][3]) continue

                    val objDist = findObjectiveDistance(scanners[scannerIndex][i], scanners[scannerIndex][j])

                    if (closestScore.take(3).sum() > objDist.sum()) {
                        for (k in 0..2) closestScore[k] = objDist[k]
                        closestScore[3] = j
                    }
                }

                for (k in closestScore.indices) {
                    closestPoints[scannerIndex]!![i][k + 4] = closestScore[k]
                }
            }
        }


        // start at 0 and see if the other scanners have a point with similar properties.
        run lit@{

            for ((pointIndex, closestPoint) in closestPoints[0]!!.withIndex()) {

                if (closestPoint[0] == closestPoint[1] || closestPoint[0] == closestPoint[2] ||
                        closestPoint[1] == closestPoint[2] || closestPoint[4] == closestPoint[5] || closestPoint[4] == closestPoint[6] ||
                        closestPoint[5] == closestPoint[6]) {
                    continue
                }

                for (i in arrayOfScanners) {
                    if( i == 0 ) continue

                    for (j in closestPoints[i]!!.indices) {
                        if (diffsEqual(closestPoint, closestPoints[i]!![j])) {
                            // find original points in both scanners
                            val initialPoint1 = scanners[0][pointIndex]
                            val initialPoint2 = scanners[0][closestPoint[3]]
                            val initialPoint3 = scanners[0][closestPoint[7]]

                            var otherPoint1 = scanners[i][j].toIntArray()
                            var otherPoint2 = scanners[i][closestPoints[i]!![j][3]].toIntArray()
                            var otherPoint3 = scanners[i][closestPoints[i]!![j][7]].toIntArray()

                            // link coords with differences
                            val initialXDiff = abs(initialPoint1[0] - initialPoint2[0])
                            val initialYDiff = abs(initialPoint1[1] - initialPoint2[1])
                            val initialZDiff = abs(initialPoint1[2] - initialPoint2[2])

                            val xyz = IntArray(3)

                            for (k in 0..2) {
                                if (abs(otherPoint1[k] - otherPoint2[k]) == initialXDiff) {
                                    xyz[0] = k
                                }
                                if (abs(otherPoint1[k] - otherPoint2[k]) == initialYDiff) {
                                    xyz[1] = k
                                }
                                if (abs(otherPoint1[k] - otherPoint2[k]) == initialZDiff) {
                                    xyz[2] = k
                                }
                            }

                            otherPoint1[0] = otherPoint1[xyz[0]].also { otherPoint1[1] = otherPoint1[xyz[1]] }.also { otherPoint1[2] = otherPoint1[xyz[2]] }
                            otherPoint2[0] = otherPoint2[xyz[0]].also { otherPoint2[1] = otherPoint2[xyz[1]] }.also { otherPoint2[2] = otherPoint2[xyz[2]] }
                            otherPoint3[0] = otherPoint3[xyz[0]].also { otherPoint3[1] = otherPoint3[xyz[1]] }.also { otherPoint3[2] = otherPoint3[xyz[2]] }


                            val shifts = IntArray(3)
                            val inverts = IntArray(3) { 1 }

                            if (initialPoint2[0] - initialPoint1[0] == otherPoint2[0] - otherPoint1[0]) {
                                // axis right way and ip2 = op2
                                shifts[0] = (initialPoint1[0] - otherPoint1[0])
                            } else if (initialPoint2[0] - initialPoint1[0] == otherPoint1[0] - otherPoint2[0]) {
                                // axis wrong way and ip2 = op2
                                shifts[0] = (initialPoint1[0] + otherPoint1[0])
                                inverts[0] = -1
                            } else {
                                continue
                            }

                            if (initialPoint3[0] != inverts[0] * otherPoint3[0] + shifts[0]) continue


                            // do for y
                            if (initialPoint2[1] - initialPoint1[1] == otherPoint2[1] - otherPoint1[1]) {
                                // axis right way and ip2 = op2
                                shifts[1] = (initialPoint1[1] - otherPoint1[1])
                            } else if (initialPoint2[1] - initialPoint1[1] == otherPoint1[1] - otherPoint2[1]) {
                                // axis wrong way and ip2 = op2
                                shifts[1] = (initialPoint1[1] + otherPoint1[1])
                                inverts[1] = -1
                            } else {
                                continue
                            }

                            if (initialPoint3[1] != inverts[1] * otherPoint3[1] + shifts[1]) continue

                            // do for z
                            if (initialPoint2[2] - initialPoint1[2] == otherPoint2[2] - otherPoint1[2]) {
                                // axis right way and ip2 = op2
                                shifts[2] = (initialPoint1[2] - otherPoint1[2])
                            } else if (initialPoint2[2] - initialPoint1[2] == otherPoint1[2] - otherPoint2[2]) {
                                // axis wrong way and ip2 = op2
                                shifts[2] = (initialPoint1[2] + otherPoint1[2])
                                inverts[2] = -1
                            } else {
                                continue
                            }

                            if (initialPoint3[2] != inverts[2] * otherPoint3[2] + shifts[2]) continue

                            // shift coords
                            val shiftedCoords = scanners[i].map { applyShiftsAndTransforms(it, xyz, shifts, inverts) }

                            val newSet = scanners[0].intersect(shiftedCoords)

                            if (newSet.size < 12) continue

                            scanners[0] = concatenate(scanners[0], shiftedCoords)

//                            scanners.removeAt(i)

                            arrayOfScanners = arrayOfScanners.filter { it != i }

                            println(i)

                            return@lit
                        }
                    }
                }
            }
        }
    }


}

fun <T> concatenate(vararg lists: List<T>): MutableList<T> {
    return listOf(*lists).flatten().toSet().toMutableList()
}

fun applyShiftsAndTransforms(inp: List<Int>, xyz: IntArray, shifts: IntArray, inverts: IntArray): List<Int> {
    val out = IntArray(3)

    for (i in 0..2) {
        out[i] = (inp[xyz[i]] * inverts[i]) + shifts[i]
    }

    return out.toList()
}

fun findObjectiveDistance(point1: List<Int>, point2: List<Int>): List<Int> {
    return listOf(abs(point1[0] - point2[0]), abs(point1[1] - point2[1]), abs(point1[2] - point2[2])).sorted()
}

fun diffsEqual(diff1: IntArray, diff2: IntArray): Boolean {
    return diff1[0] == diff2[0] && diff1[1] == diff2[1] && diff1[2] == diff2[2] && diff1[4] == diff2[4] && diff1[5] == diff2[5] && diff1[6] == diff2[6]
}

class rotatableCoordDifferences constructor(ix: Int, iy: Int, iz: Int) {
    var coords = listOf(ix, iy, iz)

    fun rotateCoords() {
        Collections.rotate(coords, 1)
    }
}


// 24 possible axis orientations
// try to match shapes if we can
// the areas will overlap in particular sections


