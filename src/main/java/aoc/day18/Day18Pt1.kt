package aoc.day18

import aoc.readInputAsString
import kotlin.math.ceil
import kotlin.math.floor

/**
 * Day 18 Part 1
 *
 * Created a tree from the lists of lists. Needed to create a class to do this as there isn't one readily available
 * in Kotlin.  Working back down the tree after an explosion is probably not done in the most obvious or efficient
 * way, if only I could remember some tree traversal stuff from 1st year of uni.
 */
// Strange tag just so I can use ceil and floor functions.
@ExperimentalStdlibApi
fun main() {
    val inputString = readInputAsString("src/main/java/aoc/day18/input.txt")
    val snailNumbers = inputString.split("\n").map { parseTree(it) }.toMutableList()

    var sum = snailNumbers.removeFirst()

    while (snailNumbers.isNotEmpty()) {
        sum = addSnailNumbers(sum, snailNumbers.removeFirst())
        reduceSnailNumber(sum)
    }

    println(calculateMagnitude(sum))
}

fun calculateMagnitude(sum: BinaryTreeNode): Int {
    if (sum.nodeType() == 0) return sum.value

    return (3 * calculateMagnitude(sum.left!!)) + (2 * calculateMagnitude(sum.right!!))
}

fun addSnailNumbers(left: BinaryTreeNode, right: BinaryTreeNode): BinaryTreeNode {
    val out = BinaryTreeNode(-1)
    out.left = left
    out.right = right

    return out
}

fun reduceSnailNumber(snailNumber: BinaryTreeNode) {
    var reduced = false

    while (!reduced) {
        var explodeResult: Array<Int>
        do {
            explodeResult = checkForExplode(snailNumber, 0)
        } while (explodeResult[0] != -2 && explodeResult[1] != -2)

        // Now we dont need to explode anything we will split if we can
        val splitResult = checkSplit(snailNumber)

        if (!splitResult) {
            reduced = true
        }
    }
}

fun checkSplit(snailNumber: BinaryTreeNode): Boolean {
    val nodeType = snailNumber.nodeType()

    if (nodeType == 0) {
        if (snailNumber.value > 9) {
            snailNumber.left = BinaryTreeNode(floor(snailNumber.value.toFloat() / 2).toInt())
            snailNumber.right = BinaryTreeNode(ceil(snailNumber.value.toFloat() / 2).toInt())
            snailNumber.value = -1
            return true
        }

        return false
    }

    val left = checkSplit(snailNumber.left!!)

    return if (!left) {
        checkSplit(snailNumber.right!!)
    } else {
        true
    }
}

// Returns [-2,-2] if no explosion, [-1,-1] if explosion but numbers already added, [n, m] for the numbers exploded
// that need to be added to left and right 'neighbours' resp.
fun checkForExplode(snailNumber: BinaryTreeNode, height: Int): Array<Int> {

    val nodeType = snailNumber.nodeType()

    if (nodeType == 0) return arrayOf(-2, -2)

    if (nodeType == 1 && height + 1 > 4) {
//        hasExploded = true
        val l = snailNumber.left!!.value
        val r = snailNumber.right!!.value
        snailNumber.value = 0
        snailNumber.left = null
        snailNumber.right = null
        return arrayOf(l, r)
    }

    var leftResult = checkForExplode(snailNumber.left!!, height + 1)

    if (leftResult[0] == -2 && leftResult[1] == -2) {
        var rightResult = checkForExplode(snailNumber.right!!, height + 1)
        if (rightResult[0] == -2 && rightResult[1] == -2) return rightResult

        if (rightResult[0] != -1) {
            rightResult[0] = addToRight(snailNumber.left!!, rightResult[0])
        }

        return rightResult
    }

    if (leftResult[1] != -1) {
        leftResult[1] = addToLeft(snailNumber.right!!, leftResult[1])
    }

    return leftResult
}

// Ensures number is added to the furthest left child up the tree
fun addToLeft(node: BinaryTreeNode, numToAdd: Int): Int {
    val nodeType = node.nodeType()
    if (nodeType == 0) {
        node.value += numToAdd
        return -1
    }

    return addToLeft(node.left!!, numToAdd)
}

// Ensures number is added to the furthest right child up the tree
fun addToRight(node: BinaryTreeNode, numToAdd: Int): Int {
    val nodeType = node.nodeType()
    if (nodeType == 0) {
        node.value += numToAdd
        return -1
    }

    return addToRight(node.right!!, numToAdd)
}

fun parseTree(treeAsString: String): BinaryTreeNode {
    if (treeAsString.length == 1) {
        return BinaryTreeNode(treeAsString.toInt())
    }

    val internals = treeAsString.slice(1..treeAsString.length - 2).toList()

    var oTot = 0
    var cTot = 0
    var splitIndex = -1

    for (char in internals) {
        splitIndex++
        if (char == '[') oTot++
        if (char == ']') cTot++
        if (char == ',') {
            if (cTot == oTot) {
                break
            }
        }
    }

    val half1 = treeAsString.slice(1..splitIndex)
    val half2 = treeAsString.slice((splitIndex + 2)..(treeAsString.length - 2))


    val out = BinaryTreeNode(-1)

    out.left = parseTree(half1)
    out.right = parseTree(half2)

    return out
}

// Pretty simple tree node class to build the tree.
class BinaryTreeNode constructor(_value: Int) {
    var value = _value
    var left: BinaryTreeNode? = null
    var right: BinaryTreeNode? = null

    fun nodeType(): Int {
        if (left == null) return 0

        if (right != null) {
            if (left!!.nodeType() == 0 && right!!.nodeType() == 0) return 1
        }
        return 2
    }
}

// Spare from debugging, will keep in case I need something like this in future.
fun printSnailNumber(snailNumber: BinaryTreeNode, height: Int) {
    if (snailNumber.nodeType() == 0) {
        print(snailNumber.value)
    } else {
        print("[")
        if (height > 3) print("_")
        printSnailNumber(snailNumber.left!!, height + 1)
        print(",")
        printSnailNumber(snailNumber.right!!, height + 1)
        if (height > 3) print("_")
        print("]")
    }
}
