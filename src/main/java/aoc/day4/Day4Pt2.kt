package aoc.day4

import aoc.readInputAsString

/**
 * Day 4 Part 2
 *
 * We are rewarded with a speedy part 2 due to the trudge through part 1.
 */
fun main() {
    val inputString = readInputAsString("src/main/java/aoc/day4/input.txt")
    val inputParts = inputString.split('\n')
    val numbers = inputParts[0].split(",").map{ it.toInt() }
    val boards = mutableListOf<BingoBoard>()

    buildBoards(inputParts, boards)

    var winBoard: List<BingoBoard>
    var score = 0

    // This one is even more hideous than the last
    run lit@{
        numbers.forEach { number ->
            if( boards.size == 1){
                if(boards[0].crossOffNumber(number)){
                    score = boards[0].calculateScore() * number
                    return@lit
                }
            }
            else {
                winBoard = boards.filter {
                    it.crossOffNumber(number)
                }

                winBoard.forEach {
                    boards.remove(it)
                }
            }
        }
    }


    println(score)
}
