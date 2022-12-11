package day11

import Part
import java.io.File

// making the default constructor private forces usage of the static factory method for instantiation
class Monkey private constructor(
    val items: MutableList<Long> = mutableListOf(),
    val operation: List<String> = emptyList(),
    val divisibleBy: Int = 0,
    val trueTarget: Int = 0,
    val falseTarget: Int = 0,
    var itemsInspected: Int = 0
) {
    companion object {
        // static factory method
        fun fromLines(lines: List<String>): Monkey {
            val items = lines[1].split(": ")[1].split(", ").map { it.toLong() }.toMutableList()
            val operation = lines[2].split("= ").last().split(" ").takeLast(2)
            val divisibleBy = lines[3].split(" ").last().toInt()
            val trueTarget = lines[4].split(" ").last().toInt()
            val falseTarget = lines[5].split(" ").last().toInt()
            return Monkey(items, operation, divisibleBy, trueTarget, falseTarget)
        }
    }

    val hasItemsLeft: Boolean
        get() = (items.size > 0)

    fun performTurn(modulo: Int?): Pair<Long,Int> {
        val oldValue = items.removeFirst()
        val newValue = inspectItem(oldValue)
        val valueWhenThrown = modulo?.let { newValue % it } ?: (newValue / 3)
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
        return if (value % divisibleBy == 0.toLong()) {
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
        val modulo: Int? = if (part == Part.TWO) monkeys.map { it.divisibleBy }.reduce { acc, i ->  acc * i } else null
        for (round in rounds) {
            monkeys.forEach { monkey ->
                while (monkey.hasItemsLeft) {
                    val move = monkey.performTurn(modulo)
                    monkeys[move.second].catchItem(move.first)
                }
            }
        }
        val topInspections = monkeys.map { it.itemsInspected }.sorted().takeLast(2)
        return topInspections.first().toLong() * topInspections.last().toLong()
    }
}

fun main() {
    val day11 = Day11(File("src/main/resources/day11.txt").readLines().filter { it != "" && it != " " })

    println("Day 11 part 1 solution: ${day11.solve(Part.ONE)}") // 54253
    println("Day 11 part 2 solution: ${day11.solve(Part.TWO)}") // 13119526120
}