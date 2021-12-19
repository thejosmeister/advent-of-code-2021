package aoc.day18

import aoc.readInputAsString

/**
 * Day 18 Part 2
 *
 * Pretty quick once I sorted problems with object references.
 */
@ExperimentalStdlibApi
fun main() {
    val inputString = readInputAsString("src/main/java/aoc/day18/input.txt")
    val snailNumbers = inputString.split("\n")

    val magnitudes = mutableListOf<Int>()

    for( snail1 in snailNumbers){
        for(snail2 in snailNumbers){
            if( snail1 == snail2 ) continue

            // Do the parsing here to avoid using the same object(s).
            val sum1 = addSnailNumbers(parseTree(snail1), parseTree(snail2))
            val sum2 = addSnailNumbers(parseTree(snail1), parseTree(snail1))

            reduceSnailNumber(sum1)
            reduceSnailNumber(sum2)

            magnitudes.add(calculateMagnitude(sum1))
            magnitudes.add(calculateMagnitude(sum2))
        }
    }

    println(magnitudes.max())
}

