package day4

import java.io.File

/*
    I originally attempted to do this with ranges, but all I could find - in a hurry - for their application was loops.
    This solution is plagiarized from the Kotlin by Jetbrains channel where they do utilize ranges.
 */

class Day04Alternate(input: List<String>) {
    private val ranges: List<Pair<IntRange, IntRange>> = input.map { it.asRanges() }

    // I like setting these extension functions to be private to this class so that they don't leak out
    // to places in a project where it doesn't make sense to use them.
    // note: the extension and infix functions here omit the use of 'this', which is something I forgot you can do
    private fun String.asRanges(): Pair<IntRange, IntRange> =
        // I really like substringBefore and substringAfter when you don't actually want the full
        // list returned by split(","), like I normally use e.g. split(",").first() and split(",").last()
        substringBefore(",").asIntRange() to substringAfter(",").asIntRange()

    private fun String.asIntRange(): IntRange =
        substringBefore("-").toInt()..substringAfter("-").toInt()

    // I have been wanting to incorporate infix functions a bit more, so I am glad to see them here.
    private infix fun IntRange.fullyOverlaps(other: IntRange): Boolean =
        first <= other.first && last >= other.last

    private infix fun IntRange.overlaps(other: IntRange): Boolean =
        first <= other.last && other.first <= last

    fun solvePart1(): Int =
        // using fullyOverlaps as an infix function looks awesome
        // without infix functions it would look something like it.first.fullyOverlaps(it.second)
        ranges.count { it.first fullyOverlaps it.second || it.second fullyOverlaps it.first}

    fun solvePart2(): Int =
        ranges.count { it.first overlaps it.second }
}

fun main() {
    val day4 = Day04Alternate(File("src/main/kotlin/day4/day4.txt").readLines())
    println("Day 04 part 1 solution: ${day4.solvePart1()}")
    println("Day 04 part 2 solution: ${day4.solvePart2()}")
}