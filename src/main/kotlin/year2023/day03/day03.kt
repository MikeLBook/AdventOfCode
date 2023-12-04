package year2023.day03

import java.io.File

data class Day03(
    val lines: List<String> = File("src/main/resources/year2023/day03Sample.txt").readLines()
) {
    fun part1(): Int {
        return 0
    }

    fun part2(): Int {
        return 0
    }
}

fun main() {
    val day3 = Day03()

    println("Day 02 part 1 solution: ${day3.part1()}")
    println("Day 02 part 2 solution: ${day3.part2()}")
}