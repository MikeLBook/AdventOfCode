package day5

import java.io.File

/*
    First solution where I am resorting to mutable data structures.
    Still no mutable variables at least, we will see if I can complete AOC without vars.
    Filtering "" after splitting "" feels so redundant but was repeatedly necessary
 */

class Day05(private val lines: List<String>) {
    private val partOneStacks = mutableMapOf<Int, MutableList<String>>()
    private val partTwoStacks = mutableMapOf<Int, MutableList<String>>()

    fun prepareStacks() {
        lines[lines.indexOf("") - 1]
            .split("")
            .filter { it != " " && it != "" }
            .forEach {
                partOneStacks[it.toInt()] = mutableListOf()
                partTwoStacks[it.toInt()] = mutableListOf()
            }
    }

    fun populateStacks() {
        lines
            .subList(0, lines.indexOf("") - 1)
            .reversed()
            .forEach { stackLine ->
                val crates = stackLine.split("").filter { it != "" }
                for (i in 1..partOneStacks.keys.size * 4 step 4) {
                    crates.getOrNull(i)?.let {
                        if (it != " ") {
                            partOneStacks[(i / 4) + 1]?.add(it)
                            partTwoStacks[(i / 4) + 1]?.add(it)
                        }
                    }
                }
            }
    }

    fun partOne(): String {
        lines
            .subList(lines.indexOf("") + 1, lines.size)
            .forEach { instruction ->
                val (howMany, from, onto) = instruction
                    .split(" ")
                    .filter { it.toIntOrNull() != null }
                    .map { it.toInt() }
                for (i in 1..howMany) {
                    partOneStacks[from]?.removeLast()?.let { partOneStacks[onto]?.add(it) }
                }
            }
        return stringifyTopCrates(partOneStacks)
    }

    fun partTwo() {
        print(partTwoStacks)
    }

    private fun stringifyTopCrates(stackMap: Map<Int, MutableList<String>>): String {
        return stackMap.keys.map {
            stackMap[it]?.last()
        }.joinToString("")
    }
}

fun main() {
    val day5 = Day05(File("src/main/resources/day05Sample.txt").readLines())
    day5.prepareStacks()
    day5.populateStacks()


    println("Day 05 part 1 solution: ${day5.partOne()}")
    day5.partTwo()
    println("Day 05 part 2 solution: ")
}