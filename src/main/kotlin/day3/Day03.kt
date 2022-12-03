package day3

import splitCorrectly
import java.io.File

val itemTypes = arrayOf(
    "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r",
    "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
    "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
)

fun main() {
    val rucksacks = File("src/main/kotlin/day3/day03.txt").readLines()

    fun part1(): Int {
       return rucksacks.sumOf {
           val rucksackItems = it.splitCorrectly()
           val firstCompartment = rucksackItems.subList(0, it.length / 2)
           val secondCompartment = rucksackItems.subList(it.length / 2, it.length)
           val intersection = firstCompartment.intersect(secondCompartment.toSet()).first()
           itemTypes.indexOf(intersection) + 1
       }
    }

    fun part2(): Int {
        return rucksacks.windowed(3, 3) {
            val intersection = it[0].splitCorrectly()
                .intersect(it[1].splitCorrectly().toSet())
                .intersect(it[2].splitCorrectly().toSet())
                .first()
            itemTypes.indexOf(intersection) + 1
        }.sum()
    }

    println("Day 03 part 1 solution: ${part1()}")
    println("Day 03 part 2 solution: ${part2()}")
}