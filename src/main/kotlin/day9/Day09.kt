package day9

import java.io.File
import kotlin.math.abs

/*
    The use of x and y vars, as well as the knotPositions vars prompted me to try to solve this immutably.
    While I pull it off in the next file, it appears to come with a significant performance cost.
    Maybe there is another way to do this immutably and improve the runtime.
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
        return positions.map { (headX, headY) ->
            val (newX, newY) = when {
                // it took me too long to realize that knots that aren't the head could move diagonally
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