package aoc.day16

import aoc.readInputAsString

var cursorIndex = 0
//var currentVersion = 0

/**
 * Day 16 Part 1
 *
 *
 */
fun main() {
    val inputString = readInputAsString("src/main/aoc/day16/input.txt")
    val binaryString = inputString.toList().map { it.toString().toInt(16) }.map { Integer.toBinaryString(it).padStart(4, '0') }.joinToString("")

    val versions = mutableListOf<Int>()

    do {
        consumePacket(binaryString, versions)

    } while (shouldCursorContinue(binaryString))

    println(versions.sum())
}

private fun consumePacket(binaryString: String, versions: MutableList<Int>) {
    val version = readVersionOrTypeId(binaryString)
    versions.add(version)
    val typeId = readVersionOrTypeId(binaryString)

    when (typeId) {
        4 -> consumeLiteralValue(binaryString)
        else -> consumeOperator(binaryString, versions)
    }
}

fun consumeOperator(binaryString: String, versions: MutableList<Int>) {
    cursorIndex++
    if (binaryString[cursorIndex - 1] == '0') {
        consumeTotalLength(binaryString, versions)
    } else {
        consumeTotalNumber(binaryString, versions)
    }
}

fun consumeTotalNumber(binaryString: String, versions: MutableList<Int>) {
    cursorIndex += 11
    val numberOfPackets = binaryString.slice(cursorIndex - 11 until cursorIndex).toInt(2)

    for( i in 1..numberOfPackets){
        consumePacket(binaryString, versions)
    }

}

fun consumeTotalLength(binaryString: String, versions: MutableList<Int>) {
    cursorIndex += 15
    val lengthLimit = binaryString.slice(cursorIndex - 15 until cursorIndex).toInt(2) + cursorIndex

    do {
        consumePacket(binaryString, versions)

    } while (cursorIndex < lengthLimit)

}

fun consumeLiteralValue(binaryString: String): Long {
    var numberAsString = ""
    do {
        numberAsString += binaryString.slice(cursorIndex + 1..cursorIndex + 4).toInt(2).toString()
        cursorIndex += 5
    } while (binaryString[cursorIndex - 5] == '1')
    println( "literal $numberAsString" )
    return numberAsString.toLong()
}

fun readVersionOrTypeId(binaryString: String): Int {
    cursorIndex += 3
    return binaryString.slice(cursorIndex - 3 until cursorIndex).toInt(2)
}

fun shouldCursorContinue(binaryString: String): Boolean {
    if (cursorIndex >= binaryString.length - 1) {
        return false
    }
    val endAsSet = binaryString.takeLast(binaryString.length - cursorIndex).toSet()

    return endAsSet != setOf('0')
}
