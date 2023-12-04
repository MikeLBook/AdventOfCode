package year2022.day09

import java.io.File
import kotlin.math.abs

/*
    Finally a solution that I am happy with!
    Mutable variables worked just find in the initial solution,
    but as a masochist I wanted to know how I could solve this one immutably.

    In the second solution, I achieved an immutable solution at the expense of runtime.

    This solution provides a happy middle ground and has a runtime competitive with my mutable solution!

    The solve function still uses a fold, as you do actually want the entirety of the accumulated list for each iteration.
    For followLeadingKnot and recordHeadPositions, all I really needed was the previously determined x and y,
    not the entire list up to that point. So I knew intuitively that a fold was not the best option.

    Here I use a buildList function, similar to the buildMap function I learned about on day 7.
    While it returns an immutable List, under the hood the builder is using a mutable list to build out the values.
    This solution avoids the 'ugly' manual x and y vars while still providing me access to previous elements in
    the list during its construction.

    -Average run times-
    Day09 Part 1: ~35ms avg
    Day09 Part 2: ~35ms avg

    Day09Immutable Part 1: ~150ms avg
    Day09Immutable Part 2: ~600ms avg

    Day09FastFauxImmutable Part 1: ~50ms avg
    Day09FastFauxImmutable Part 2: ~26ms avg

    Why part 2 in this solution is consistently faster than part 1 must be due to JVM warmup.

    Immutable data structures are nice.
    When they become expensive, mutable data structures can be utilized safely and effectively by limiting
    the code that can access the data structure at all. Classes can help provide encapsulation,
    but the buildList and buildMap functions are excellent in this regard. Such useful abstraction!
    I still feel very strongly that vars should be avoided, which is what this solution accomplished most of all.

    val > var
    Mutability of data structures != Mutability of variables that point to data
 */

class Day09FastFauxImmutable(private val lines: List<String>) {
    fun solve(knots: Int): Int {
        val headKnotPositions = recordHeadPositions()
        return List(knots - 1) { it }.fold(headKnotPositions) {leadingPositions, _ ->
            followLeadingKnot(leadingPositions)
        }.toSet().count()
    }

    private fun followLeadingKnot(leadingPositions: List<Pair<Int,Int>>): List<Pair<Int,Int>> {
        return buildList {
            leadingPositions.forEach { (leadingX, leadingY) ->
                val (prevX, prevY) = if (size != 0) get(size - 1) else 0 to 0
                when {
                    leadingX isTwoAwayFrom prevX && leadingY isTwoAwayFrom prevY -> {
                        val newX = if (leadingX > prevX) prevX + 1 else prevX - 1
                        val newY = if (leadingY > prevY) prevY + 1 else prevY - 1
                        add(newX to newY)
                    }
                    leadingX - prevX > 1 -> add(leadingX - 1 to leadingY)
                    prevX - leadingX > 1 -> add(leadingX + 1 to leadingY)
                    leadingY - prevY > 1 -> add(leadingX to leadingY - 1)
                    prevY - leadingY > 1 -> add(leadingX to leadingY + 1)
                    else -> add(prevX to prevY)
                }
            }
        }
    }

    private fun recordHeadPositions(): List<Pair<Int,Int>> {
        return buildList {
            lines.forEach { line ->
                val (direction, quantity) = line.split(" ")
                val (x, y) = if (size != 0) get(size - 1) else 0 to 0
                for (i in 1 .. quantity.toInt()) {
                    when(direction) {
                        "U" -> add(x to y + i)
                        "D" -> add(x to y - i)
                        "L" -> add(x - i to y)
                        "R" -> add(x + i to y)
                        else -> add(x to y)
                    }
                }
            }
        }
    }

    private infix fun Int.isTwoAwayFrom(other: Int): Boolean {
        return abs(this - other) > 1
    }
}

fun main() {
    val day9 = Day09FastFauxImmutable(File("src/main/resources/year2022.day09.txt").readLines())
    println("Day 09 part 1 solution: ${day9.solve(2)}")
    println("Day 09 part 2 solution: ${day9.solve(10)}")
}