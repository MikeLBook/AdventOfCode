package day13

import java.io.File

class Day13(private val lines: List<String>) {
    fun part1() {
        val test = lines.filter { it != "" }.chunked(2) {
            val first = listParser(it.first())
            val second = listParser(it.last())
            listOf(first, second)
        }
        println(test)
    }

    private fun listParser(stringList: String): List<Any> {
        return buildList {
            var nestingLevel = 0
            val tempListMap: MutableMap<Int, MutableList<Int>> = mutableMapOf()
            stringList.forEach { character ->
                if (character == '[') {
                    nestingLevel += 1
                    tempListMap[nestingLevel] = mutableListOf()
                } else if (character == ']') {
                    if (nestingLevel != 1) {
                        add(tempListMap[nestingLevel]!!)
                        tempListMap.remove(nestingLevel)
                        nestingLevel -= 1
                    }
                } else if (character.isDigit()) {
                    if (nestingLevel == 1) {
                        add(character.toString().toInt())
                    } else {
                        tempListMap[nestingLevel]?.add(character.toString().toInt())
                    }
                }
            }
        }
    }
}

fun main() {
    val day13 = Day13(File("src/main/resources/day13Sample.txt").readLines())
    println("Day 13 part 1 solution: ${day13.part1()}")
    println("Day 13 part 2 solution: ")
}