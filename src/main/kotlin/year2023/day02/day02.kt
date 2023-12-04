package year2023.day02

import java.io.File

data class Day02(
    val lines: List<String> = File("src/main/resources/year2023/day02.txt").readLines()
) {
    fun part1(): Int {
        val cubeMap = mapOf("red" to 12, "green" to 13, "blue" to 14)
        return lines.sumOf { line ->
            val game = line.split(":")
            val id = game.first().split(" ").last()
            val rounds = game.last().split(";")
            val possibleRounds = rounds.filter {round ->
                val cubes = round.split(",").map { it.trim() }
                cubes.none {
                    val colors = it.split(" ")
                    (colors.first().toInt() > cubeMap[colors.last()]!!)
                }
            }
            if (possibleRounds.size < rounds.size) 0 else id.toInt()
        }
    }

    fun part2(): Int {
        return lines.sumOf { line ->
            val minMap = mutableMapOf<String, Int>()
            line.split(":").last().split(";").forEach { round ->
                val diceDrawn = round.split(",").map { it.trim() }
                diceDrawn.forEach { colorDrawn ->
                    val (count, color) = colorDrawn.split(" ")
                    if (minMap[color] == null || count.toInt() > minMap[color]!!) {
                        minMap[color] = count.toInt()
                    }
                }
            }
            minMap.values.reduce { acc, i -> acc * i }
        }
    }
}

fun main() {
    val day2 = Day02()

    println("Day 02 part 1 solution: ${day2.part1()}")
    println("Day 02 part 2 solution: ${day2.part2()}")
}