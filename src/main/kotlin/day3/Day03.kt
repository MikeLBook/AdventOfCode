package day3

import splitCorrectly
import java.io.File

// inspired by Maia Grotepass on the Kotlin by JetBrains channel
// much cleaner than my manually written string List
fun Char.getPriority(): Int {
    return if (this.isUpperCase()) this.code - 'A'.code + 27 else this.code - 'a'.code + 1
}

fun main() {
    val rucksacks = File("src/main/kotlin/day3/day03.txt").readLines()

    fun part1(): Int {
       return rucksacks.sumOf {
           val rucksackItems = it.splitCorrectly()
           val firstCompartment = rucksackItems.subList(0, it.length / 2)
           val secondCompartment = rucksackItems.subList(it.length / 2, it.length)
           firstCompartment.intersect(secondCompartment.toSet())
               .first()
               .single()
               .getPriority()
       }
    }

    fun part2(): Int {
        return rucksacks.windowed(3, 3) {
            it[0].splitCorrectly()
                .intersect(it[1].splitCorrectly().toSet())
                .intersect(it[2].splitCorrectly().toSet())
                .first()
                .single()
                .getPriority()
        }.sum()
    }

    println("Day 03 part 1 solution: ${part1()}")
    println("Day 03 part 2 solution: ${part2()}")
}