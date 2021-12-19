package aoc.day16

import aoc.readInputAsString


//315301169067626 too high

/**
 * Day 16 Part 2
 *
 *
 */
fun main() {
    val inputString = readInputAsString("src/main/java/aoc/day16/input.txt")
    val binaryString = inputString.toList().map { it.toString().toInt(16) }.map { Integer.toBinaryString(it).padStart(4, '0') }.joinToString("")

    val out: Long
//    do {
    out = consumePacket(binaryString)
//
//    } while (shouldCursorContinue(binaryString))
    println(binaryString.length)
    println(cursorIndex)

    println(out)
}

private fun consumePacket(binaryString: String): Long {
    readVersionOrTypeId(binaryString)

    when (readVersionOrTypeId(binaryString)) {
        0 -> return consumeSum(binaryString)
        1 -> return consumeProduct(binaryString)
        2 -> return consumeMin(binaryString)
        3 -> return consumeMax(binaryString)
        4 -> return consumeLiteralValue(binaryString)
        5 -> return consumeGreaterThan(binaryString)
        6 -> return consumeLessThan(binaryString)
        7 -> return consumeEqualTo(binaryString)
        else -> return 0
    }
}

fun consumeEqualTo(binaryString: String): Long {
    val compares = consumeOperator(binaryString)
    println("equal? ${compares[0]} ${compares[1]}")
    return if (compares[0] == compares[1]) {
        1
    } else {
        0
    }
}

fun consumeLessThan(binaryString: String): Long {
    val compares = consumeOperator(binaryString)
    println("less? ${compares[0]} ${compares[1]}")
    return if (compares[0] < compares[1]) {
        1
    } else {
        0
    }
}

fun consumeGreaterThan(binaryString: String): Long {
    val compares = consumeOperator(binaryString)
    println("greater? ${compares[0]} ${compares[1]}")
    return if (compares[0] > compares[1]) {
        1
    } else {
        0
    }
}

fun consumeMax(binaryString: String): Long {
    val interm = consumeOperator(binaryString)
    println("max ${interm.contentToString()}")

    return interm.max()!!
}

fun consumeMin(binaryString: String): Long {
    val interm = consumeOperator(binaryString)
    println("min ${interm.contentToString()}")

    return interm.min()!!
}

fun consumeProduct(binaryString: String): Long {
    val interm = consumeOperator(binaryString)
    println("prod ${interm.contentToString()}")
    return interm.reduce { acc, i -> acc * i }
}

fun consumeSum(binaryString: String): Long {
    val interm = consumeOperator(binaryString)
    println("sum ${interm.contentToString()}")
    return interm.sum()
}

fun consumeOperator(binaryString: String): LongArray {
    cursorIndex++
    if (binaryString[cursorIndex - 1] == '0') {
        return consumeTotalLength(binaryString)
    } else {
        return consumeTotalNumber(binaryString)
    }
}

fun consumeTotalNumber(binaryString: String): LongArray {
    cursorIndex += 11
    val numberOfPackets = binaryString.slice(cursorIndex - 11 until cursorIndex).toLong(2)
    val out = mutableListOf<Long>()

    for (i in 1..numberOfPackets) {
        out.add(consumePacket(binaryString))
    }
    return out.toLongArray()
}

fun consumeTotalLength(binaryString: String): LongArray {
    cursorIndex += 15
    val lengthLimit = binaryString.slice(cursorIndex - 15 until cursorIndex).toLong(2) + cursorIndex
    val out = mutableListOf<Long>()

    do {
        out.add(consumePacket(binaryString))

    } while (cursorIndex < lengthLimit)

    return out.toLongArray()
}
