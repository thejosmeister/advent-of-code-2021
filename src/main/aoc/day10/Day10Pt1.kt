package aoc.day10

import aoc.readInputAsString

/**
 * Day 10 Part 1
 *
 * Did not read the Q right in the first place so was not taking into account that the chunks must have complete chunks inside.
 */
fun main() {
    val inputString = readInputAsString("src/main/aoc/day10/input.txt")
    val inputLines = inputString.split("\n")


    val closingChars = charArrayOf(')', ']', '}', '>')
    val charPairs = arrayOf("()", "[]", "{}", "<>")
    val charScores = intArrayOf(3, 57, 1197, 25137)
    var score = 0
    var reducedLine: String

    for (line in inputLines) {
        reducedLine = reduceLine(line, charPairs)
        score += checkLine(reducedLine.toList(), closingChars, charScores)
    }

    println(score)
}

fun reduceLine(line: String, charPairs: Array<String>): String {
    var outLine = line

    if (charPairs.all { !line.contains(it) }) {
        return line
    }

    for (pair in charPairs) {
        outLine = outLine.split(pair).joinToString("")
    }

    return reduceLine(outLine, charPairs)
}

private fun checkLine(line: List<Char>, closingChars: CharArray, charScores: IntArray): Int {
    for (char in line) {
        if (char in closingChars) {
            return charScores[closingChars.indexOf(char)]
        }
    }
    return 0
}
