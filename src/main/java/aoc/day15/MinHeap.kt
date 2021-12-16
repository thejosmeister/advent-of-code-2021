package aoc.day15

/**
 * A min heap for Dijkstras that was half copied from an online source
 */
class MinHeap constructor(
        size: Int
) {
    // dont really do much checking of this but useful to have
    private var heapSize = 0
    // Not sure if I needed this absolute min but it's here anyway
    private val array = Array(size + 1) { IntArray(3) }.also { it[0] = intArrayOf(-1, -1, Int.MIN_VALUE) }
    // Compare function used to sort heap
    private fun compare(a: IntArray, b: IntArray): Boolean {
        return a[2] < b[2]
    }

    // Some stuff used in heapify
    private val FRONT = 1
    private fun parent(i: Int) = i shr 1
    private fun left(i: Int) = i shl 1
    private fun right(i: Int) = left(i) + 1
    private fun swap(i: Int, j: Int) {
        array[i] = array[j].also { array[j] = array[i] }
    }

    private fun peek() = if (heapSize > 0) array[1] else IntArray(3) { -1 }

    private tailrec fun heapify(i: Int) {
        val left = left(i)
        val right = right(i)

        var ordered = when {
            left <= heapSize && compare(array[i], array[left]) -> i
            left <= heapSize -> left
            else -> i
        }

        if (right <= heapSize && !compare(array[ordered], array[right]))
            ordered = right

        if (ordered != i) {
            swap(ordered, i)
            heapify(ordered)
        }
    }

    // Take elt off the top of the heap
    fun pop(): IntArray {
        require(heapSize > 0) { -1 }

        val popped = peek()
        array[FRONT] = array[heapSize--]
        heapify(FRONT)
        return popped
    }

    // Puts a point on the heap and sorts the heap
    fun assign(value: IntArray) {

        array[++heapSize] = value
        var current = heapSize
        var parent = parent(current)
        while (parent != 0 && compare(array[current], array[parent])) {
            swap(parent, current)
            current = parent.also { parent = parent(parent) }
        }
    }

    // Updates distance of the point
    fun updateDist(pointWithNewDist: IntArray) {
        var currentI = array.indexOf(array.filter { it[0] == pointWithNewDist[0] && it[1] == pointWithNewDist[1] }[0])
        array[currentI][2] = pointWithNewDist[2]
        var parent = parent(currentI)
        while (parent != 0 && compare(array[currentI], array[parent])) {
            swap(parent, currentI)
            currentI = parent.also { parent = parent(parent) }
        }
    }

    override fun toString() = array.map { it[2] }.joinToString(", ", "[", "]")
}
