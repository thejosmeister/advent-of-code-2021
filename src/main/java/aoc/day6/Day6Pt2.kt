package aoc.day6

import aoc.readInputAsString

/**
 * Day 6 Part 2
 *
 * Refining part one and then also needed to use a long. Could have used this for both days.
 */
fun main() {
    val inputString = readInputAsString("src/main/aoc/day6/input.txt")
    val inputNumbers = inputString.split(",").map { it.toInt() }.toMutableList()

    var countsOfDays = LongArray(9)

    for(i in 0.rangeTo(8)){
        countsOfDays[i] = inputNumbers.count { it == i }.toLong()
    }

    var day = 0

    while(day < 256){
        countsOfDays = incrementDayForCounts(countsOfDays)
        day++
    }

    print(countsOfDays.sum())

}

fun incrementDayForCounts(fish: LongArray): LongArray{
    val out = LongArray(9) {0}

    for(i in 0.rangeTo(8)){
        if(i == 0){
            out[6] += fish[i]
            out[8] += fish[i]
        }
        else
        {
            out[i - 1] += fish[i]
        }
    }
    return out
}
