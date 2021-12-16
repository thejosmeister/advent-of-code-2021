/**
 * Common stuff
 */

package aoc

import java.io.File
import java.io.InputStream

/**
 * Easiest way to fetch the input from the file
 */
fun readInputAsString(path: String): String {
    val inputStream: InputStream = File(path).inputStream()

    return inputStream.bufferedReader().use { it.readText() }.trim()
}

/**
 * Subtract array of ints from another
 */
fun arraySubtract(one: Array<Int>, two: Array<Int>): IntArray {
    val out = IntArray(one.size)
    for (i in one.indices) {
        out[i] = one[i] - two[i]
    }
    return out
}
