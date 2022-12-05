package day5

import java.io.File

class Day05(private val lines: List<String>) {
    private val stacks = mutableMapOf<Int, MutableList<String>>()
    private val stackLines = lines.subList(0, lines.indexOf("") - 1)
    private val instructions = lines.subList(lines.indexOf("") + 1, lines.size)

    fun prepareStacks() {
        lines[lines.indexOf("") - 1]
            .split("")
            .filter { it != " " && it != "" }
            .forEach { stacks[it.toInt()] = mutableListOf() }
    }

    fun populateStacks() {
        stackLines.reversed().forEach { stackLine ->
            val formattedStackList = stackLine.split("").filter { it != "" }
            for (i in 1..stacks.keys.size * 4 step 4) {
                formattedStackList.getOrNull(i)?.let { if (it != " ") stacks[(i / 4) + 1]?.add(it) }
            }
        }
    }

    fun processInstructions() {
        print(stacks)
    }
}

fun main() {
    val day5 = Day05(File("src/main/resources/day05Sample.txt").readLines())
    day5.prepareStacks()
    day5.populateStacks()
    day5.processInstructions()

    println("Day 05 part 1 solution: ")
    println("Day 05 part 2 solution: ")
}