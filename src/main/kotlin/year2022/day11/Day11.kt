package year2022.day11

import year2022.Part
import java.io.File

/*
    https://en.wikipedia.org/wiki/Modular_arithmetic
 */

// Making the primary constructor private forces usage of the static factory method
class Monkey private constructor(
    private val items: MutableList<Long> = mutableListOf(),
    private val operation: List<String> = emptyList(),
    private val trueTarget: Int = 0,
    private val falseTarget: Int = 0,
    val divisibilityValue: Int = 0,
    var itemsInspected: Long = 0
) { // Companion objects contain what are basically static values and methods on the class
    companion object {
        // The static factory method
        fun fromLines(lines: List<String>): Monkey {
            val items = lines[1].split(": ")[1].split(", ").map { it.toLong() }.toMutableList()
            val operation = lines[2].split("= ").last().split(" ").takeLast(2)
            val divisibilityValue = lines[3].split(" ").last().toInt()
            val trueTarget = lines[4].split(" ").last().toInt()
            val falseTarget = lines[5].split(" ").last().toInt()
            return Monkey(items, operation, trueTarget, falseTarget, divisibilityValue)
        }
    }
    // Computed Property
    val hasItemsLeft: Boolean
        get() = (items.size > 0)

    fun performTurn(modulo: Int?): Pair<Long,Int> {
        val worryValue = inspectItem(items.removeFirst())
        // left side of the elvis operator is the main solution to part 2
        val valueWhenThrown = modulo?.let { worryValue % it } ?: (worryValue / 3)
        return throwItem(valueWhenThrown)
    }

    fun catchItem(value: Long) {
        items.add(value)
    }

    private fun inspectItem(value: Long): Long {
        itemsInspected += 1
        val modifier = operation.last().toLongOrNull() ?: value
        return when(operation.first()) {
            "+" -> value + modifier
            "*" -> value * modifier
            else -> throw RuntimeException("Unknown Operation")
        }
    }

    private fun throwItem(value: Long): Pair<Long,Int> {
        return if (value % divisibilityValue == 0.toLong()) {
            value to trueTarget
        } else {
            value to falseTarget
        }
    }
}

class Day11(private val lines: List<String>) {
    fun solve(part: Part): Long {
        val monkeys = lines.chunked(6).map { lines ->
            Monkey.fromLines(lines)
        }
        val rounds = if (part == Part.ONE) 1..20 else 1..10000
        // The value to modulo for the part two solution. Is null if handling part one
        val modulo: Int? = if (part == Part.TWO) monkeys.map { it.divisibilityValue }.reduce { acc, i ->  acc * i } else null
        for (round in rounds) {
            monkeys.forEach { monkey ->
                while (monkey.hasItemsLeft) {
                    val (item, thrownTo) = monkey.performTurn(modulo)
                    monkeys[thrownTo].catchItem(item)
                }
            }
        }
        return monkeys.map { it.itemsInspected }
            .sorted()
            .takeLast(2)
            .reduce { acc, l -> acc * l }
    }
}

fun main() {
    val day11 = Day11(File("src/main/resources/year2022.day11.txt").readLines().filter { it != "" && it != " " })

    println("Day 11 part 1 solution: ${day11.solve(Part.ONE)}") // 54253
    println("Day 11 part 2 solution: ${day11.solve(Part.TWO)}") // 13119526120
}