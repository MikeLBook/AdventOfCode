package day11

import Part
import java.io.File

// making the default constructor private forces usage of the static factory method for instantiation
class Monkey private constructor(
    val items: MutableList<Int> = mutableListOf(),
    val operation: List<String> = emptyList(),
    val divisibleBy: Int = 0,
    val trueTarget: Int = 0,
    val falseTarget: Int = 0,
    var itemsInspected: Int = 0
) {
    companion object {
        // static factory method
        fun fromLines(lines: List<String>): Monkey {
            val items = lines[1].split(": ")[1].split(", ").map { it.toInt() }.toMutableList()
            val operation = lines[2].split("= ").last().split(" ").takeLast(2)
            val divisibleBy = lines[3].split(" ").last().toInt()
            val trueTarget = lines[4].split(" ").last().toInt()
            val falseTarget = lines[5].split(" ").last().toInt()
            return Monkey(items, operation, divisibleBy, trueTarget, falseTarget)
        }
    }

    val hasItemsLeft: Boolean
        get() = (items.size > 0)

    fun performTurn(part: Part): Pair<Int,Int> {
        val oldValue = takeItem()
        val newValue = inspectItem(oldValue)
        val valueWhenThrown = if (part == Part.ONE) newValue / 3 else newValue
        return throwItem(valueWhenThrown)
    }

    fun catchItem(value: Int) {
        items.add(value)
    }

    private fun takeItem(): Int {
        val value = items.first()
        items.remove(value)
        return value
    }

    private fun inspectItem(value: Int): Int {
        itemsInspected += 1
        val modifier = operation.last().toIntOrNull() ?: value
        return when(operation.first()) {
            "+" -> value + modifier
            "*" -> value * modifier
            else -> throw RuntimeException("Unknown Operation")
        }
    }

    private fun throwItem(value: Int): Pair<Int,Int> {
        return if (value % divisibleBy == 0) {
            value to trueTarget
        } else {
            value to falseTarget
        }
    }
}

class Day11(private val lines: List<String>) {
    fun solve(part: Part): Int {
        val monkeys = lines.chunked(6).map { lines ->
            Monkey.fromLines(lines)
        }
        val iterations = if (part == Part.ONE) 1..20 else 1..10000
        for (i in iterations) {
            monkeys.forEach { monkey ->
                while (monkey.hasItemsLeft) {
                    val move = monkey.performTurn(part)
                    monkeys[move.second].catchItem(move.first)
                }
            }
        }
        val topInspections = monkeys.map { it.itemsInspected }.sorted().takeLast(2)
        return topInspections.first() * topInspections.last()
    }
}

fun main() {
    val day11 = Day11(File("src/main/resources/day11Sample.txt").readLines().filter { it != "" && it != " " })

    println("Day 11 part 1 solution: ${day11.solve(Part.ONE)}") // 54253
    println("Day 11 part 2 solution: ${day11.solve(Part.TWO)}")
}