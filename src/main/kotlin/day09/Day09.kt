package day09

import java.io.File
import kotlin.math.abs

/*
    Not totally happy with the use of vars and I feel like there's probably a way to do it without them.
 */

class Day09(private val lines: List<String>) {
    fun solve(knots: Int): Int {
        var knotPositions = recordHeadPositions()
        for (i in 2..knots) {
            knotPositions = followLeadingKnot(knotPositions)
        }
        return knotPositions.toSet().count()
    }

    private fun followLeadingKnot(positions: List<Pair<Int,Int>>): List<Pair<Int,Int>> {
        var x = 0
        var y = 0
        return positions.map { (leadingX, leadingY) ->
            val (newX, newY) = when {
                // it took me too long to realize that knots that aren't the head could move diagonally
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
            x = newX
            y = newY
            x to y
        }
    }

    private fun recordHeadPositions(): List<Pair<Int,Int>> {
        var x = 0
        var y = 0
        return listOf(0 to 0) + lines.map { line ->
            val (direction, quantity) = line.split(" ")
            List(quantity.toInt()) {
                when(direction) {
                    "U" -> y += 1
                    "D" -> y -= 1
                    "L" -> x -= 1
                    "R" -> x += 1
                }
                x to y
            }
        }.flatten()
    }

    private infix fun Int.isTwoAwayFrom(other: Int): Boolean {
        return abs(this - other) > 1
    }
}

fun main() {
    val day9 = Day09(File("src/main/resources/day09.txt").readLines())
    println("Day 09 part 1 solution: ${day9.solve(2)}") // 6090
    println("Day 09 part 2 solution: ${day9.solve(10)}") // 2566
}