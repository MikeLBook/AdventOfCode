package day9

import java.io.File
import kotlin.math.abs

/*
    Doing this one immutably was quite difficult for me.
    While I'm happy to have pulled it off, folding this much seems like it significantly adds to the run time.
    The mutable solution completes almost instantly.
 */

class Day09Immutable(private val lines: List<String>) {
    fun solve(knots: Int): Int {
        val headKnotPositions = recordHeadPositions()
        return List(knots - 1) { it }.fold(headKnotPositions) {leadingPositions, _ ->
            followLeadingKnot(leadingPositions)
        }.toSet().count()
    }

    private fun followLeadingKnot(positions: List<Pair<Int,Int>>): List<Pair<Int,Int>> {
        return positions.fold(listOf()) { coordinates, (headX, headY) ->
            val (x, y) = if (coordinates.isNotEmpty()) coordinates.last() else 0 to 0
            val newCoordinates = when {
                headX isTwoAwayFrom x && headY isTwoAwayFrom y -> {
                    val xValue = if (headX > x) x + 1 else x - 1
                    val yValue = if (headY > y) y + 1 else y - 1
                    xValue to yValue
                }
                headX - x > 1 -> headX - 1 to headY
                x - headX > 1 -> headX + 1 to headY
                headY - y > 1 -> headX to headY - 1
                y - headY > 1 -> headX to headY + 1
                else -> x to y
            }
            coordinates + newCoordinates
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
    val day9 = Day09Immutable(File("src/main/resources/day09.txt").readLines())
    println("Day 09 part 1 solution: ${day9.solve(2)}") // 6090
    println("Day 09 part 2 solution: ${day9.solve(10)}") // 2566
}