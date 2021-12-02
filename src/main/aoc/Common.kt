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
