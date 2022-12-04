package day4

import java.io.File

/*
    This solution is plagiarized from the Kotlin by Jetbrains channel.
    It features destructuring as opposed to pairs,
    and a custom operator to be able to use 'in' keyword between two IntRanges
 */

val input = File("src/main/resources/day4.txt").readLines()

fun main() {
    val part1 = input.map {
        val (a, b) = it.split(",")
        a.toRange() to b.toRange()
    }.count { (a, b) ->
        a in b || b in a
    }

    println(part1)

    val part2 = input.map {
        val (a, b) = it.split(",")
        a.toRange() to b.toRange()
    }.count { (a, b) ->
        a.any { it in b }
    }

    println(part2)
}

fun String.toRange(): IntRange {
    val (start, end) = split("-")
    return start.toInt()..end.toInt()
}

operator fun IntRange.contains(other: IntRange): Boolean {
    return other.first >= this.first && other.last <= this.last
}