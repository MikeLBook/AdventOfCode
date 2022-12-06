package day5

import java.io.File

/*
    First solution where I am resorting to mutable data structures.
    Still no mutable variables at least, we will see if I can complete AOC without vars.
    Filtering "" after splitting "" feels so redundant but was repeatedly necessary
 */

enum class Part {
    ONE, TWO
}

class Day05(lines: List<String>) {
    private val stackLines = lines.subList(0, lines.indexOf("") - 1).reversed()
    private val stackNumbers = lines[lines.indexOf("") - 1].split("").filter { it != " " && it != "" }
    private val instructions = lines.subList(lines.indexOf("") + 1, lines.size)


    fun solveFor(part: Part): String {
        val stacks = populateStacks()
        instructions.forEach { instruction ->
            val (howMany, from, onto) = instruction
                .split(" ")
                .filter { it.toIntOrNull() != null }
                .map { it.toInt() }
            val cratesToMove = mutableListOf<String>()
            for (i in 1..howMany) {
                stacks[from]?.removeLast()?.let { cratesToMove.add(it) }
            }
            if (part == Part.ONE) stacks[onto]?.addAll(cratesToMove) else stacks[onto]?.addAll(cratesToMove.reversed())
        }
        return stringifyTopCrates(stacks)
    }

    private fun populateStacks(): MutableMap<Int, MutableList<String>> {
        val stacks = mutableMapOf<Int, MutableList<String>>()

        stackNumbers.forEach {
            stacks[it.toInt()] = mutableListOf()
        }

        stackLines.forEach { stackLine ->
            val crates = stackLine.split("").filter { it != "" }
            for (i in 1..stacks.keys.size * 4 step 4) {
                crates.getOrNull(i)?.let {
                    if (it != " ") {
                        stacks[(i / 4) + 1]?.add(it)
                    }
                }
            }
        }
        return stacks
    }

    private fun stringifyTopCrates(stackMap: Map<Int, MutableList<String>>): String {
        return stackMap.keys.map {
            stackMap[it]?.last()
        }.joinToString("")
    }
}

fun main() {
    val day5 = Day05(File("src/main/resources/day05.txt").readLines())

    println("Day 05 part 1 solution: ${day5.solveFor(Part.ONE)}")
    println("Day 05 part 2 solution: ${day5.solveFor(Part.TWO)}")
}