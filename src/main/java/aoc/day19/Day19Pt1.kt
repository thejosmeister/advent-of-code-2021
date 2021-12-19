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

    var scanners = mutableListOf<MutableList<List<Int>>>()

    for(line in inputString.split("\n")){
        if( line.length == 0){
            continue
        }

        if(line.contains("---")){
            scannerNumber ++
            scanners.add(mutableListOf())
            continue
        }

        scanners[scannerNumber].add(line.split(",").map{ it.toInt()})
    }

    println(scanners.size)
    println(scanners[1].size)
}
