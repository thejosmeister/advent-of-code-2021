package aoc.day14

import aoc.readInputAsString
import java.util.Collections.max
import java.util.Collections.min

/**
 * Day 14 Part 2
 *
 * First balls to the wall hard one, most likely missed a trick.
 * Decided to calc each pair to twenty iterations and then apply this twice to the main string.
 * Was tricky business getting some residual counts right.
 */
fun main() {
    val inputString = readInputAsString("src/main/java/aoc/day14/input.txt")
    val inputParts = inputString.split("\n")

    var polymerString = inputParts[0]
    val instructions = inputParts.slice(2 until inputParts.size).map{it.split(" -> ")}

    val inserts = mutableMapOf<String,String>()
    for(i in instructions.indices){
        inserts[instructions[i][0]] = instructions[i][1]
    }

    // Create maps of the resulting state after applying insert rules to each pair of chars after 20 iterations
    val twentyStepResults = mutableMapOf<String,Map<Char,Int>>()
    val twentyStepStrings = mutableMapOf<String, String>()

    for(lhs in inserts.keys){
        val twentyStepsString = createTwentyStepString(lhs, inserts)

        twentyStepResults[lhs] = twentyStepsString.slice(1 until twentyStepsString.length - 1).toList().groupingBy { it }.eachCount()
        twentyStepStrings[lhs] = twentyStepsString.slice(1 until twentyStepsString.length - 1)
    }

    // Now with these maps we can create a string after 20 steps
    val charsToAdd = polymerString.toList()
    // Contains the numbers of chars and strings of the intermediate chars between the original chars above
    val charLists = mutableListOf<Map<Char, Int>>()
    val strings = mutableListOf<String>()

    for(i in 0 until polymerString.length - 1){
        val slice = polymerString.substring(i, i + 2)
        charLists.add(twentyStepResults[slice]!!)
        strings.add(twentyStepStrings[slice]!!)
    }

    // combine to form one big string after 20 steps
    var bigString = ""

    for(i in charsToAdd.indices){
        bigString += charsToAdd[i].toString()
        if(i < strings.size) {
            bigString += strings[i]
        }
    }

    // Now we can apply the 20steps maps for each of the char pairs in the 'bigString'
    val charLists2 = mutableListOf<Map<Char, Int>>()

    for (i in 0 until bigString.length - 1) {
        val slice = bigString.substring(i, i + 2)
        charLists2.add(twentyStepResults[slice]!!)
    }

    val allChars = instructions.map { it[1][0] }.toSet()

    // We now just need to add all char counts up from the original string, first 20 steps and the last 20 steps
    val charNumbers = mutableMapOf<Char, Long>()

    // original string
    allChars.forEach { charNumbers[it] = polymerString.count { _it -> it == _it }.toLong() }
    // 1st 20 steps
    charLists.forEach{ _it -> _it.keys.forEach { charNumbers[it] = (charNumbers[it] ?: 0) + (_it[it] ?: 0) }}
    // last 20 steps
    charLists2.forEach { _it -> _it.keys.forEach { charNumbers[it] = (charNumbers[it] ?: 0) + (_it[it] ?: 0) } }

    println(max(charNumbers.values) - min(charNumbers.values))
}

private fun createTwentyStepString(polymerString: String, replacements: MutableMap<String, String>): String {
    var polymerString1 = polymerString
    for (i in 1..20) {
        polymerString1 = replacePolymers(polymerString1, replacements)
    }
    return polymerString1
}
