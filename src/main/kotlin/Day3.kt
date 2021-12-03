import Day3.getLines
import Day3.part1
import Day3.part2
import java.io.File

fun main() {

    val testLines = getLines("/home/arthur/advent-of-code-2021/src/main/resources/3/test.txt")
    val inputLines = getLines("/home/arthur/advent-of-code-2021/src/main/resources/3/input.txt")

    println("Part 1 - test:${part1(testLines)} - actual:${part1(inputLines)}")
    println("Part 2 - test:${part2(testLines)} - actual:${part2(inputLines)}")
}

object Day3 {

    data class Position(val horizontal:Int=0, val depth:Int=0, val aim:Int=0)

    fun getLines(path:String) = File(path).readLines()

    fun part1(instructions:List<String>):Int {
        val gamma = gamma(instructions)
        val epsilon = inverse(gamma)

        val gammaNum = Integer.parseInt(gamma, 2)
        val epsilonNum = Integer.parseInt(epsilon, 2)

        return gammaNum * epsilonNum
    }

    fun part2(instructions:List<String>):Int {
        println("find Oxygen")
        val oxygen = findValue(instructions, true)
        println("find Scrubber")
        val scrubber = findValue(instructions, false)
        return oxygen * scrubber
    }

    fun findValue(list: List<String>, mostCommon:Boolean, index:Int=0):Int {
        if (list.size == 1) return Integer.parseInt(list.first(), 2)

        val sum = list.map { it[index] }.map { Integer.parseInt(it.toString()) }.sum()
        val filter = if (sum*2 >= list.size) {
            if (mostCommon) '1' else '0'
        } else {
            if (mostCommon) '0' else '1'
        }
        val remaining = list.filter { it[index] == filter }

        return findValue(remaining, mostCommon, index + 1)
    }

    tailrec fun gamma(list:List<String>, index:Int=0, gammaStr:String=""):String {
        if (index >= list.first().length) return gammaStr

        val sum = list.map { it[index] }.map { Integer.parseInt(it.toString()) }.sum()
        val newGammaBit = if (sum > list.size/2) "1" else "0"

        return gamma(list, index+1, gammaStr + newGammaBit)
    }

    fun inverse(str:String) = str.map { char ->
            when (char) {
                '0' -> '1'
                '1' -> '0'
                else -> char
            }
        }.joinToString(separator = "")

}