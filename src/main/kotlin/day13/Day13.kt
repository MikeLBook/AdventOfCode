package day13

import java.io.File

class Day13(lines: List<String>) {
    private val packetPairs = lines.chunked(2) { listOf(listParser(it.first()), listParser(it.last())) }
    private val packets = lines.map { listParser(it) }

    fun part1(): Int = packetPairs.mapIndexedNotNull { index, (left, right) ->
        if (pairsAreInRightOrder(left, right) == 1) index + 1 else null
    }.sum()

    fun part2(): Int {
        val dividerPackets = listOf(listOf(listOf(2)), listOf(listOf(6)))
        val comparator = { a: List<*>, b: List<*> ->
            pairsAreInRightOrder(a, b)
        }
        return (dividerPackets + packets)
            .sortedWith(comparator)
            .reversed()
            .mapIndexed { index, it -> if (dividerPackets.contains(it)) index+1 else 1 }
            .reduce(Int::times)
    }

    private fun pairsAreInRightOrder(left: List<*>, right: List<*>): Int {
        val indices = if (left.size > right.size) left.indices else right.indices

        indices.forEach { i ->
            val leftE = left.getOrNull(i) ?: return 1
            val rightE = right.getOrNull(i) ?: return -1

            if (leftE is Int && rightE is Int) when {
                leftE < rightE -> return 1
                leftE > rightE -> return -1
                else -> return@forEach
            } else {
                val subListComparison = when {
                    leftE is Int -> pairsAreInRightOrder(listOf(leftE), rightE as List<*>)
                    rightE is Int -> pairsAreInRightOrder(leftE as List<*>, listOf(rightE))
                    else -> pairsAreInRightOrder(leftE as List<*>, rightE as List<*>)
                }
                if (subListComparison == 0) return@forEach else return subListComparison
            }
        }

        return 0
    }

    private fun listParser(stringList: String): List<*> {
        return buildList {
            var nestingLevel = 0
            val tempListMap: MutableMap<Int, MutableList<Any>> = mutableMapOf()
            stringList.forEachIndexed { index, character ->
                when {
                    character == '[' -> {
                        nestingLevel += 1
                        tempListMap[nestingLevel] = mutableListOf()
                    }
                    character == ']' -> {
                        if (nestingLevel == 2) {
                            add(tempListMap[nestingLevel]!!)
                        } else if (nestingLevel > 2) {
                            tempListMap[nestingLevel - 1]?.add(tempListMap[nestingLevel]!!)
                        }
                        tempListMap.remove(nestingLevel)
                        nestingLevel -= 1
                    }
                    character.isDigit() -> {
                        val prevChar = stringList[index - 1]
                        when {
                            prevChar.isDigit() && nestingLevel == 1 -> {
                                val previousElement = removeLast().toString()
                                val newNumber = previousElement + character
                                add(newNumber.toInt())
                            }
                            prevChar.isDigit() -> {
                                val previousElement = tempListMap[nestingLevel]?.removeLast().toString()
                                val newNumber = previousElement + character
                                tempListMap[nestingLevel]?.add(newNumber.toInt())
                            }
                            nestingLevel == 1 -> add(character.toString().toInt())
                            else -> tempListMap[nestingLevel]?.add(character.toString().toInt())
                        }
                    }
                }
            }
        }
    }
}

fun main() {
    val day13 = Day13(File("src/main/resources/day13.txt").readLines().filter { it != "" })
    println("Day 13 part 1 solution: ${day13.part1()}")
    println("Day 13 part 2 solution: ${day13.part2()}")
}