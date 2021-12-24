package aoc.day19

import aoc.readInputAsString
import java.util.*
import kotlin.math.abs
import kotlin.math.sqrt
import kotlin.math.pow

/**
 * Day 19 Part 1
 *
 *
 */
fun main() {
    val inputString = readInputAsString("src/main/java/aoc/day19/testInput.txt")

    var scannerNumber = -1

    val axisPerms = listOf(intArrayOf(0,1,2), intArrayOf(2,0,1), intArrayOf(1,2,0))
    val directionPerms = mutableListOf<IntArray>()
    for (i in -1..1 step 2) {
        for (j in -1..1 step 2) {
            for (k in -1..1 step 2) {
                directionPerms.add(intArrayOf(i, j, k))
            }
        }
    }

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

    var arrayOfScanners = IntArray(scanners.size) { it + 1 }.map { it - 1 }



    while (arrayOfScanners.size > 1) {
        println("running through while")


        val threePointCombos = mutableMapOf<Int, MutableList<IntArray>>()
        for (a in arrayOfScanners) {

            val scannerCombos = mutableListOf<IntArray>()

            for (i in scanners[a].indices) {

                for (j in scanners[a].indices) {
                    if (i == j) continue
                    for (k in scanners[a].indices) {
                        if (k == i || k == j) continue
                        val combo = IntArray(9) { 10000 }
                        val objDistij = findObjectiveDistance(scanners[a][i], scanners[a][j])
                        val objDistik = findObjectiveDistance(scanners[a][i], scanners[a][k])

                        combo[0] = i

                        for (l in 0..2) combo[l + 1] = objDistij[l]
                        combo[4] = j
                        for (l in 0..2) combo[l + 5] = objDistik[l]
                        combo[8] = k
                        scannerCombos.add(combo)
                    }
                }
            }
            threePointCombos[a] = scannerCombos
        }


        // start at 0 and see if the other scanners have a point with similar properties.
        run lit@{

            for (closestPoint in threePointCombos[0]!!) {

                if (closestPoint[1] == closestPoint[2] || closestPoint[1] == closestPoint[3] ||
                        closestPoint[2] == closestPoint[3] || closestPoint[5] == closestPoint[6] || closestPoint[5] == closestPoint[7] ||
                        closestPoint[6] == closestPoint[7]) {
                    continue
                }

                for (i in arrayOfScanners) {
                    if (i == 0) continue

                    for (j in threePointCombos[i]!!.indices) {
                        if (diffsEqual(closestPoint, threePointCombos[i]!![j])) {
                            // find original points in both scanners



                            for( xyz in axisPerms){
                                for(inverts in directionPerms){
                                    val initialPoint1 = scanners[0][closestPoint[0]]
                                    var otherPoint1 = scanners[i][threePointCombos[i]!![j][0]].toIntArray()
                                    otherPoint1[0] = otherPoint1[xyz[0]].also { otherPoint1[1] = otherPoint1[xyz[1]] }.also { otherPoint1[2] = otherPoint1[xyz[2]] }

                                    val shifts = IntArray(3)
                                    for(x in 0..2){
                                        otherPoint1[x] = otherPoint1[x] * inverts[x]
                                    }

                                    shifts[0] = (initialPoint1[0] - (otherPoint1[0]))

                                    shifts[1] = (initialPoint1[1] - (otherPoint1[1]))

                                    shifts[2] = (initialPoint1[2] - (otherPoint1[2]))

//                                    println("${shifts[0]} ${shifts[1]} ${shifts[2]}")


                                    // shift coords
                                    val shiftedCoords = scanners[i].map { applyShiftsAndTransforms(it, xyz.toList(), shifts, inverts.toList()) }

                                    val newSet = scanners[0].intersect(shiftedCoords)

                                    if( newSet.size > 1) println("size ${newSet.size}")

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
    }


}

fun <T> concatenate(vararg lists: List<T>): MutableList<T> {
    return listOf(*lists).flatten().toSet().toMutableList()
}

fun applyShiftsAndTransforms(inp: List<Int>, xyz: List<Int>, shifts: IntArray, inverts: List<Int>): List<Int> {
    val out = IntArray(3)

    for (i in 0..2) {
        out[i] = (inp[xyz[i]] * inverts[i]) + shifts[i]
    }

    return out.toList()
}

fun findObjectiveDistance(point1: List<Int>, point2: List<Int>): List<Int> {
    val out = (point1[0] - point2[0]).toFloat().pow(2) +
            (point1[1]-point2[1]).toFloat().pow(2) + (point1[2]-point2[2]).toFloat().pow(2)
    return listOf(out.toInt(), abs(point1[1] - point2[1]), abs(point1[2] - point2[2]))
}

fun diffsEqual(diff1: IntArray, diff2: IntArray): Boolean {
    val one1two1 = diff1[1] == diff2[1] && diff1[2] == diff2[2] && diff1[3] == diff2[3]
    val one1two2 = diff1[1] == diff2[5] && diff1[2] == diff2[6] && diff1[3] == diff2[7]
    val one2two2 = diff1[5] == diff2[5] && diff1[6] == diff2[6] && diff1[7] == diff2[7]
    val one2two1 = diff1[5] == diff2[1] && diff1[6] == diff2[2] && diff1[7] == diff2[3]
    return (one1two1 && one2two2) || (one1two2 && one2two1)
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


