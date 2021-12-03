import java.io.File

fun main() {

    val testLines = getLines("/home/arthur/advent-of-code-2021/src/main/resources/1/test.txt")
    val inputLines = getLines("/home/arthur/advent-of-code-2021/src/main/resources/1/input.txt")

    println("Part 1 - test:${part1(testLines)} - actual:${part1(inputLines)}")
    println("Part 2 - test:${part2(testLines)} - actual:${part2(inputLines)}")
}

fun getLines(path:String) = File(path).readLines().map { it.toInt() }

fun part1(lines:List<Int>) = numberOfIncreases(lines)

fun part2(lines:List<Int>) = numberOfIncreasesSlidingWindow(lines)

tailrec fun numberOfIncreases(remaining:List<Int>, number:Int=0, previous:Int?=null):Int {
    if (remaining.isEmpty()) return number

    val newNumber = if (previous != null && remaining.first() > previous) number+1 else number

    return numberOfIncreases(remaining.drop(1), newNumber, remaining.first())
}

tailrec fun numberOfIncreasesSlidingWindow(remaining:List<Int>, number:Int=0, currentWindow:List<Int> = emptyList()):Int {
    if (remaining.isEmpty()) return number

    val previous = if (currentWindow.size == 3) currentWindow.sum() else null

    val newWindow = if (currentWindow.size == 3) {
        currentWindow.drop(1) + remaining.first()
    } else {
        currentWindow + remaining.first()
    }

    val increases = if (previous != null && newWindow.size == 3 && newWindow.sum() > previous) number + 1 else number

    return numberOfIncreasesSlidingWindow(remaining.drop(1), increases, newWindow)
}