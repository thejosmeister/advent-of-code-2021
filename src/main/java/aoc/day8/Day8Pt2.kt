package aoc.day8

import aoc.readInputAsString

/**
 * Day 8 Part 2
 *
 * Initially tried doing a thorough method but the input allows shortcuts to be taken.
 */
fun main() {
    val inputString = readInputAsString("src/main/aoc/day8/input.txt")
    val inputValues = inputString.split("\n").map { it.split(" | ").map { it.split(" ").map { it.toCharArray().sorted().joinToString("") } } }

    var out = 0
    val inputStrings = mutableListOf<List<String>>()

    inputValues.forEach {
        inputStrings.add(it[0].toSet().sortedBy { _it -> _it.length })
    }

    var i = 0

    inputValues.forEach {
        out += determineChars(inputStrings[i], it[1])
        i++
    }

    print(out)
}

fun determineChars(inputList: List<String>, outputList: List<String>): Int {
    val outputMap = mutableMapOf<String, Int>()
    val segmentMap = mutableMapOf<Char, MutableList<Char>>()

    val twoChar = inputList.filter { it.length == 2 }
    val threeChar = inputList.filter { it.length == 3 }
    val fourChar = inputList.filter { it.length == 4 }
    val fiveChar = inputList.filter { it.length == 5 }
    val sixChar = inputList.filter { it.length == 6 }
    val sevenChar = inputList.filter { it.length == 7 }

    if (twoChar.isNotEmpty()) {
        outputMap[twoChar[0]] = 1

        segmentMap['c'] = twoChar[0].toMutableList()
        segmentMap['f'] = twoChar[0].toMutableList()
    }

    if (threeChar.isNotEmpty()) {
        outputMap[threeChar[0]] = 7

        segmentMap['a'] = threeChar[0].toList().filter { !segmentMap['c']?.contains(it)!! }.toMutableList()
    }

    if (fourChar.isNotEmpty()) {
        outputMap[fourChar[0]] = 4

        segmentMap['d'] = fourChar[0].toList().filter { !segmentMap['c']?.contains(it)!! }.toMutableList()
        segmentMap['b'] = fourChar[0].toList().filter { !segmentMap['c']?.contains(it)!! }.toMutableList()
    }

    if (sevenChar.isNotEmpty()) {
        outputMap[sevenChar[0]] = 8
    }

    // There are instances of each of the 2,3,4 char ones in each of the input lines so don't need to check for this.
    if (fiveChar.isNotEmpty()) {
        for (s in fiveChar) {
            if (segmentMap['c']?.all { it in s }!!) {
                outputMap[s] = 3
            } else if (segmentMap['b']?.all { it in s }!!) {
                outputMap[s] = 5
            } else {
                outputMap[s] = 2
            }
        }
    }

    if (sixChar.isNotEmpty()) {
        for (s in sixChar) {
            if (segmentMap['c']?.all { it in s }!!) {
                if (segmentMap['b']?.all { it in s }!!) {
                    outputMap[s] = 9
                } else {
                    outputMap[s] = 0
                }
            } else {
                outputMap[s] = 6
            }
        }
    }

    return outputList.joinToString("") { outputMap[it].toString() }.toInt()
}
