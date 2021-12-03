import Day2.getLines
import Day2.part1
import Day2.part2
import java.io.File

fun main() {

    val testLines = getLines("/home/arthur/advent-of-code-2021/src/main/resources/2/test.txt")
    val inputLines = getLines("/home/arthur/advent-of-code-2021/src/main/resources/2/input.txt")

    println("Part 1 - test:${part1(testLines)} - actual:${part1(inputLines)}")
    println("Part 2 - test:${part2(testLines)} - actual:${part2(inputLines)}")
}

object Day2 {

    data class Position(val horizontal:Int=0, val depth:Int=0, val aim:Int=0)

    fun getLines(path:String) = File(path).readLines().map { it.split(" ") }.map { it[0] to it[1].toInt() }

    fun part1(instructions:List<Pair<String,Int>>):Int {
        val grouped = instructions.groupBy { it.first }.mapValues { it.value.sumOf { it.second } }
        val horizontal = grouped["forward"] ?: 0
        val down = grouped["down"] ?: 0
        val up = grouped["up"] ?: 0
        return horizontal * (down - up)
    }

    fun part2(instructions:List<Pair<String,Int>>):Int {
        val position = process(instructions)
        return position.horizontal * position.depth
    }

    tailrec fun process(instructions:List<Pair<String, Int>>, position: Position = Position()): Position {
        if (instructions.isEmpty()) return position

        val instruction = instructions.first().first
        val value = instructions.first().second

        val newPosition = when(instruction) {
            "forward" -> position.copy(horizontal=position.horizontal+value, depth=position.depth + (position.aim*value))
            "down" -> position.copy(aim=position.aim + value)
            "up" -> position.copy(aim=position.aim - value)
            else -> position
        }

        return process(instructions.drop(1), newPosition)
    }
}