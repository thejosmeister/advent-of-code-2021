package aoc.day10

import aoc.readInputAsString

/**
 * Day 10 Part 2
 *
 * The old int being too small situation caused problems again. Today was a slow one.
 */
fun main() {
    val inputString = readInputAsString("src/main/java/aoc/day10/input.txt")
    val inputLines = inputString.split("\n")

    val closingChars = charArrayOf(')', ']', '}', '>')
    val charPairs = arrayOf("()", "[]", "{}", "<>")
    val charScores = mapOf('(' to 1, '[' to 2, '{' to 3, '<' to 4)
    var scores = mutableListOf<Long>()
    var reducedLine: String

    for (line in inputLines) {
        reducedLine = reduceLine(line, charPairs)
        scores.add(addEndAndScore(reducedLine.toList(), closingChars, charScores))
    }
    scores = scores.filter { it != 0.toLong() }.toMutableList()
    scores.sort()

    println(scores[scores.size/2])
}

private fun addEndAndScore(line: List<Char>, closingChars: CharArray, charScores: Map<Char, Int>): Long {
    for (char in line) {
        if (char in closingChars) {
            return 0
        }
    }

    var out:Long = 0

    for(char in line.reversed()){
        out = out * 5 + charScores[char]!!
    }

    return out
}
