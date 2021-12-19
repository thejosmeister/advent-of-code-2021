package aoc.day4

import aoc.readInputAsString

/**
 * Day 4 Part 1
 *
 * One of the less preferable days where its a lot of effort for a problem that's not too hard to solve
 */
fun main() {
    val inputString = readInputAsString("src/main/java/aoc/day4/input.txt")
    val inputParts = inputString.split('\n')
    val numbers = inputParts[0].split(",").map{ it.toInt() }
    val boards = mutableListOf<BingoBoard>()

    buildBoards(inputParts, boards)

    var winBoard: List<BingoBoard>
    var score = 0

    // A pretty hideous way to exit a for each, will refactor if I can be bothered
    run lit@{
        numbers.forEach { number ->
            winBoard = boards.filter {
                it.crossOffNumber(number)
            }

            if (winBoard.isNotEmpty()) {
                score = winBoard[0].calculateScore() * number
                return@lit
            }
        }
    }

    println(score)
}

fun buildBoards(inputParts: List<String>, boards: MutableList<BingoBoard>) {
    for (i in 2..inputParts.size step 6) {
        val newBoard = Array(5) { IntArray(5) }

        for (j in i..i + 4) {
            newBoard[j - i] = inputParts[j].split(" ").filter { it != "" }.map { it.toInt() }.toIntArray()
        }

        boards.add(BingoBoard(newBoard))
    }
}

// Chucked a class together for the bingo board
class BingoBoard(var board: Array<IntArray>){

    var wins = Array(5) { IntArray(5) { 0 } }
    var transposedWins = Array(5) { IntArray(5) { 0 } }

    fun crossOffNumber(num: Int): Boolean{

        for( i in 0.rangeTo(4) ){
            for( j in 0.rangeTo(4)){
                if (board[i][j] == num) {
                    wins[i][j] = 1
                    transposedWins[j][i] = 1

                    return checkForWin()
                }
            }
        }
        return false
    }

    private fun checkForWin(): Boolean{
        for (i in 0.rangeTo(4)) {
            if( wins[i].reduce { acc, j -> acc * j } == 1 ){
                return true
            }
            if (transposedWins[i].reduce { acc, j -> acc * j } == 1) {
                return true
            }
        }

        return false
    }

    fun calculateScore(): Int{
        var out = 0

        for (i in 0.rangeTo(4)) {
            for (j in 0.rangeTo(4)) {
                if (wins[i][j] == 0) {
                    out += board[i][j]
                }
            }
        }
        return out
    }




}
