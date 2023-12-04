package year2022.day04

import java.io.File

/*
    This solution is plagiarized from the Kotlin by Jetbrains channel.
    It features destructuring as opposed to pairs,
    and a custom operator to be able to use 'in' keyword between two IntRanges
 */

fun String.toRange(): IntRange {
    val (start, end) = split("-")
    return start.toInt()..end.toInt()
}

operator fun IntRange.contains(other: IntRange): Boolean {
    return other.first >= this.first && other.last <= this.last
}

val input = File("src/main/resources/year2022.day04.txt").readLines()

fun main() {
    val ranges: List<Pair<IntRange, IntRange>> = input.map {
        val (a, b) = it.split(",")
        a.toRange() to b.toRange()
    }

    val part1 = ranges.count { (a, b) ->
        a in b || b in a
    }

    val part2 = ranges.count { (a, b) ->
        a.any { it in b }
    }

    println("Day 04 part 1 solution: $part1")
    println("Day 04 part 2 solution: $part2")
}