package aoc.day19

import aoc.readInputAsString

/**
 * Day 19 Part 1
 *
 *
 */
fun main() {
    val inputString = readInputAsString("src/main/java/aoc/day19/input.txt")

    var scannerNumber = -1

    val axisPerms = listOf(intArrayOf(0, 1, 2), intArrayOf(2, 0, 1), intArrayOf(1, 2, 0))
    val directionPerms = mutableListOf<IntArray>()
    for (i in -1..1 step 2) {
        for (j in -1..1 step 2) {
            for (k in -1..1 step 2) {
                directionPerms.add(intArrayOf(i, j, k))
            }
        }
    }

    val orientString = "0,1,2,1,1,1\n" +
            "0,2,1,1,1,-1\n" + //rotate about x
            "0,1,2,1,-1,-1\n" +
            "0,2,1,1,-1,1\n" +
            "2,1,0,-1,1,1\n" + //rotate about y
            "0,1,2,-1,1,-1\n" +
            "2,1,0,1,1,-1\n" +
            "1,0,2,-1,1,1\n" + // rotate about z
            "0,1,2,-1,-1,1\n" +
            "1,0,2,1,-1,1\n" +
            "2,0,1,1,1,1\n" + // -y on bottom
            "0,2,1,-1,1,1\n" +
            "2,0,1,-1,-1,1\n" +
            "1,2,0,-1,-1,1\n" + // -x on bottom
            "2,1,0,1,-1,1\n" +
            "1,2,0,1,1,1\n" +
            "1,2,0,1,-1,-1\n" + // x on bottom
            "2,1,0,-1,-1,-1\n" +
            "1,2,0,-1,1,-1\n" +
            "1,0,2,-1,-1,-1\n" + // z on bottom
            "1,0,2,1,1,-1\n" +
            "2,1,0,1,-1,-1\n" + // y on bottom
            "0,2,1,-1,-1,-1\n" +
            "2,0,1,-1,1,-1"

    val orientations = orientString.split("\n").map{ it.split(",").map{ it.toInt() }}

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

                    val combo = IntArray(5)
                    val objDistij = findObjectiveDistance(scanners[a][i], scanners[a][j])


                    combo[0] = i

                    combo[1] = objDistij[0]
                    combo[4] = j
                    scannerCombos.add(combo)

                }
            }
            threePointCombos[a] = scannerCombos
        }


        // start at 0 and see if the other scanners have a point with similar properties.
        run lit@{

            for (closestPoint in threePointCombos[0]!!) {

//                if (closestPoint[1] == closestPoint[2] || closestPoint[1] == closestPoint[3] ||
//                        closestPoint[2] == closestPoint[3]
////                        || closestPoint[5] == closestPoint[6] || closestPoint[5] == closestPoint[7] ||
////                        closestPoint[6] == closestPoint[7]
//                ) {
//                    continue
//                }

                for (i in arrayOfScanners) {
                    if (i == 0) continue

                    for (j in threePointCombos[i]!!.indices) {
                        if (diffsEqual2(closestPoint, threePointCombos[i]!![j])) {
                            // find original points in both scanners
                            val initialPoint1 = scanners[0][closestPoint[0]]
                            val initialPoint2 = scanners[0][closestPoint[4]]
                            var otherPoint1 = scanners[i][threePointCombos[i]!![j][0]].toIntArray()
                            var otherPoint2 = scanners[i][threePointCombos[i]!![j][4]].toIntArray()

                            for (o in orientations) {

                                    val newOp1 = IntArray(3)
                                    val newOp2 = IntArray(3)
                                    newOp1[0] = otherPoint1[o[0]].also { newOp1[1] = otherPoint1[o[1]] }.also { newOp1[2] = otherPoint1[o[2]] }
                                    newOp2[0] = otherPoint2[o[0]].also { newOp2[1] = otherPoint2[o[1]] }.also { newOp2[2] = otherPoint2[o[2]] }

                                    for (newOp in listOf(newOp1, newOp2)) {
                                        for (x in 0..2) {
                                            newOp[x] = newOp[x] * o[x + 3]
                                        }
                                        for( shiftMult in directionPerms) {

                                            val shifts = IntArray(3)


                                            shifts[0] = (initialPoint1[0] - (newOp[0]))

                                            shifts[1] = (initialPoint1[1] - (newOp[1]))

                                            shifts[2] = (initialPoint1[2] - (newOp[2]))

                                            for (x in 0..2) {
                                                shifts[x] = shifts[x] * shiftMult[x]
                                            }

//                                    println("${shifts[0]} ${shifts[1]} ${shifts[2]}")


                                            // shift coords
                                            val shiftedCoords = scanners[i].map { applyShiftsAndTransforms(it, o.slice(0..2), shifts, o.slice(3..5)) }

                                            val newSet = scanners[0].intersect(shiftedCoords)

                                            if (newSet.size > 1) println("size ${newSet.size}")

                                            if (newSet.size < 12) continue

                                            scanners[0] = concatenate(scanners[0], shiftedCoords)



                                            arrayOfScanners = arrayOfScanners.filter { it != i }

                                            println("adding $i")
                                            println(arrayOfScanners.size)

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

    println(scanners[0].size)


}

fun diffsEqual2(diff1: IntArray, diff2: IntArray): Boolean {
    return diff1[1] == diff2[1]
}


// 24 possible axis orientations
// try to match shapes if we can
// the areas will overlap in particular sections


