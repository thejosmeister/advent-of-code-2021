/**
 * Giving Kotlin a whirl before AoC starts.
 */

package aoc

fun main() {
//    val numbers = listOf(9, 3, 7, 6, 6, 6, 11, 11, 6, 3)
//    val mode = numbers.groupingBy { it }.eachCount().maxBy { it.value }
    val fasdf = "1234567890"
    println(fasdf.slice(2..4))
}

fun findPrimesUpTo100() {
    for (num in 1.rangeTo(100)) {
        val divisors = mutableListOf<Int>()
        for (num2 in 1.rangeTo(num)) {
            if (num % num2 == 0) divisors.add(num2)
        }

        if (divisors.size == 2) println(num)
    }
}
