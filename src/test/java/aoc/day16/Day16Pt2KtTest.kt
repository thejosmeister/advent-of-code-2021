package aoc.day16

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * @author josiah.filleul
 */
internal class Day16Pt2KtTest {
    @BeforeEach
    fun setUp(){
        cursorIndex = 0
    }

    @Test
    fun consumeOperator() {
        val inputString = "0" + "000000000010110" + "01010000001" + "01010000001"

        assertEquals(consumeOperator(inputString)[0], longArrayOf(1,1)[0])
    }

    @Test
    fun consumeOperatorProduct() {
        val inputString = "1" + "00000000001" + "010001100000000010" + "01010000001" + "01010000001"

        assertEquals(1, consumeOperator(inputString)[0])
    }

    @Test
    fun consumeOperatorSum() {
        val inputString = "1" + "00000000001" + "010000100000000010" + "01010000001" + "01010000001"

        assertEquals(2, consumeOperator(inputString)[0])
    }

    @Test
    fun consumeOperatorMin() {
        val inputString = "1" + "00000000001" + "010010100000000010" + "01010000010" + "01010000001"

        assertEquals(1, consumeOperator(inputString)[0])
    }

    @Test
    fun consumeOperatorMax() {
        val inputString = "1" + "00000000001" + "010011100000000010" + "01010000010" + "01010000001"

        assertEquals(2, consumeOperator(inputString)[0])
    }

    @Test
    fun consumeOperatorGreater() {
        val inputString = "1" + "00000000001" + "010101100000000010" + "0101001000110001100011000100001" + "01010000001"

        assertEquals(1, consumeOperator(inputString)[0])
    }

    @Test
    fun consumeOperatorLess() {
        val inputString = "1" + "00000000001" + "010110100000000010" + "0101001000110001100011000100001" + "01010000001"

        assertEquals(0, consumeOperator(inputString)[0])
    }

    @Test
    fun consumeOperatorEqual() {
        val inputString = "1" + "00000000001" + "010111100000000010" + "01010000001" + "01010000001"

        assertEquals(1, consumeOperator(inputString)[0])
    }

    @Test
    fun consumeOperatorLiteral() {
        val inputString = "1" + "00000000001" + "01010010001100011000110001100011000110001100011000110001100011000110001100011000110001100011000100001"

        assertEquals(1111111111111111111, consumeOperator(inputString)[0])
    }

}
