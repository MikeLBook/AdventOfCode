package year2022.day09

import java.io.File
import kotlin.math.abs

/*
    Doing this one immutably tool some figuring out.
    While I'm happy to have pulled it off,
    folding this much seems like it significantly adds to the run time.
 */

class Day09Immutable(private val lines: List<String>) {
    fun solve(knots: Int): Int {
        val headKnotPositions = recordHeadPositions()
        return List(knots - 1) { it }.fold(headKnotPositions) {leadingPositions, _ ->
            followLeadingKnot(leadingPositions)
        }.toSet().count()
    }

    private fun followLeadingKnot(leadingPositions: List<Pair<Int,Int>>): List<Pair<Int,Int>> {
        return leadingPositions.fold(listOf(0 to 0)) { trailingPositions, (leadingX, leadingY) ->
            val (x, y) = trailingPositions.last()
            val newCoordinates = when {
                leadingX isTwoAwayFrom x && leadingY isTwoAwayFrom y -> {
                    val xValue = if (leadingX > x) x + 1 else x - 1
                    val yValue = if (leadingY > y) y + 1 else y - 1
                    xValue to yValue
                }
                leadingX - x > 1 -> leadingX - 1 to leadingY
                x - leadingX > 1 -> leadingX + 1 to leadingY
                leadingY - y > 1 -> leadingX to leadingY - 1
                y - leadingY > 1 -> leadingX to leadingY + 1
                else -> x to y
            }
            trailingPositions + newCoordinates
        }
    }

    private fun recordHeadPositions(): List<Pair<Int,Int>> {
        return lines.fold(listOf(0 to 0)) { coordinates, line ->
            val (direction, quantity) = line.split(" ")
            val (x, y) = coordinates.last()
            val newCoordinates = List(quantity.toInt()) {
                when(direction) {
                    "U" -> x to y + 1 + it
                    "D" -> x to y - 1 - it
                    "L" -> x - 1 - it to y
                    "R" -> x + 1 + it to y
                    else -> x to y
                }
            }
            coordinates + newCoordinates
        }
    }

    private infix fun Int.isTwoAwayFrom(other: Int): Boolean {
        return abs(this - other) > 1
    }
}

fun main() {
    val day9 = Day09Immutable(File("src/main/resources/year2022.day09.txt").readLines())
    println("Day 09 part 1 solution: ${day9.solve(2)}") // 6090
    println("Day 09 part 2 solution: ${day9.solve(10)}") // 2566
}