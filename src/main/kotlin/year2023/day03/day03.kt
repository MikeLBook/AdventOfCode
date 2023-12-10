package year2023.day03

import java.io.File

data class Day03(
    val lines: List<String> = File("src/main/resources/year2023/day03.txt").readLines(),
    var engine: List<List<String>> = emptyList()
) {
    init {
        engine = lines.map { it.split("").filter { char -> char != "" } }
    }
    fun part1(): Long {
        var total: Long = 0
        var isAdjacent = false
        var numberString = ""
        val xAxisLength = engine.first().size
        engine.forEachIndexed { yIndex, line ->
            line.forEachIndexed { xIndex, c ->
                if (c.first().isDigit()) {
                    numberString += c
                    isAdjacent = hasAdjacentToken(yIndex, xIndex) || isAdjacent
                    if (xIndex == xAxisLength) {
                        if (isAdjacent) {
                            total += numberString.toLong()
                        }
                        isAdjacent = false
                        numberString = ""
                    }
                } else {
                    if (numberString != "") {
                        if (isAdjacent) {
                            total += numberString.toLong()
                        }
                        isAdjacent = false
                        numberString = ""
                    }
                }
            }
        }
        return total
    }

    fun part2(): Long {
        val gears: MutableMap<Pair<Int, Int>, MutableList<String>> = mutableMapOf()
        var numberString: String = ""
        val xAxisLength = engine.first().size

        engine.forEachIndexed { yIndex, line ->
            line.forEachIndexed { xIndex, c ->
                if (c.first().isDigit()) {
                    numberString += c
                    if (xIndex == xAxisLength) {
                        numberString = ""
                    }
                } else {
                    if (numberString != "") {
                        numberString = ""
                    }
                }
            }
        }

        return gears.values.filter { it.size == 2 }.sumOf { it.first().toLong() * it.last().toLong() }
    }

    private fun hasAdjacentToken(y: Int, x: Int): Boolean {
        return engine.getOrNull(y-1)?.getOrNull(x-1)?.first()?.isToken() ?: false ||
                engine.getOrNull(y-1)?.get(x)?.first()?.isToken() ?: false ||
                engine.getOrNull(y-1)?.getOrNull(x+1)?.first()?.isToken() ?: false ||
                engine[y].getOrNull(x-1)?.first()?.isToken() ?: false ||
                engine[y].getOrNull(x+1)?.first()?.isToken() ?: false ||
                engine.getOrNull(y+1)?.getOrNull(x-1)?.first()?.isToken() ?: false ||
                engine.getOrNull(y+1)?.get(x)?.first()?.isToken() ?: false ||
                engine.getOrNull(y+1)?.getOrNull(x+1)?.first()?.isToken() ?: false
    }

    private fun Char.isToken(): Boolean {
        return !isDigit() && this != '.'
    }
}

fun main() {
    val day3 = Day03()

    println("Day 02 part 1 solution: ${day3.part1()}")
    println("Day 02 part 2 solution: ${day3.part2()}")
}