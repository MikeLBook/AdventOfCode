package day3

import splitEmptyString
import java.io.File

// This function inspired by Maia Grotepass on the Kotlin by JetBrains channel
// much cleaner than my manually written string List
fun Char.getPriority(): Int {
    return if (this.isUpperCase()) this.code - 'A'.code + 27 else this.code - 'a'.code + 1
}

fun main() {
    val rucksacks = File("src/main/resources/day03.txt").readLines()

    fun part1(): Int {
       return rucksacks.sumOf {
           val rucksackItems = it.splitEmptyString()
           val firstCompartment = rucksackItems.subList(0, it.length / 2)
           val secondCompartment = rucksackItems.subList(it.length / 2, it.length)
           firstCompartment.intersect(secondCompartment.toSet())
               .first()
               .single()
               .getPriority()
       }
    }

    fun part2(): Int {
        // instead of windowed(3, 3), can used chunked(3)
        return rucksacks.chunked(3) {
            it[0].splitEmptyString()
                .intersect(it[1].splitEmptyString().toSet())
                .intersect(it[2].splitEmptyString().toSet())
                .first()
                .single()
                .getPriority()
        }.sum()
    }

    println("Day 03 part 1 solution: ${part1()}")
    println("Day 03 part 2 solution: ${part2()}")
}