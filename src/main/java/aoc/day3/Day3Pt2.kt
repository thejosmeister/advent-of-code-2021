package aoc.day3

import aoc.readInputAsString

/**
 * Day 3 Part 2
 *
 * Seems wasteful doubling up the list refinement, could alter the bitCriteriaSplit function to make it recursive?
 */
fun main() {
    val inputString = readInputAsString("src/main/java/aoc/day3/input.txt")
    val inputNums = inputString.split('\n')

    val lengthOfNum = inputNums[0].length
    var oxList = inputNums
    var co2List = inputNums

    for (i in 0 until lengthOfNum) {
        oxList = bitCriteriaSplit(oxList, i)[0]
        co2List = bitCriteriaSplit(co2List, i)[1]
    }

    val oxRating = oxList[0].toInt(2)
    val co2Rating = co2List[0].toInt(2)

    println(oxRating * co2Rating)
}

fun bitCriteriaSplit(inp: List<String>, bit: Int): Array<MutableList<String>> {
    if (inp.size == 1) {
        return arrayOf(inp.toMutableList(), inp.toMutableList())
    }
    var oneCount = 0
    var zeroCount = 0
    val ones = mutableListOf<String>()
    val zeros = mutableListOf<String>()

    inp.forEach {
        if (it[bit] == '1') {
            oneCount += 1
            ones.add(it)
        } else {
            zeroCount += 1
            zeros.add(it)
        }
    }

    if (zeroCount > oneCount) {
        return arrayOf(zeros, ones)
    }
    return arrayOf(ones, zeros)
}
