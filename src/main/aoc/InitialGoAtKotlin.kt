/**
 * Giving Kotlin a whirl before AoC starts.
 */

package aoc

fun main() {
    findPrimesUpTo100()
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
