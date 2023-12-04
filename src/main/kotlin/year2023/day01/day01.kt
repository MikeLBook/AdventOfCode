package year2023.day01

import java.io.File

data class Day01(
    val lines: List<String> = File("src/main/resources/year2023/day01.txt").readLines()
) {
    fun part1() = lines.sumOf { line -> "${line.first { it.isDigit() }}${line.last { it.isDigit() }}".toInt() }

    fun part2(): Int {
        val numberMap: Map<String, String> = mapOf(
            "one" to "1", "two" to "2", "three" to "3", "four" to "4", "five" to "5",
            "six" to "6", "seven" to "7", "eight" to "8", "nine" to "9"
        )
        return lines.sumOf { line ->
            val matchMap = mutableMapOf<Int, String>()

            // Locate String Numbers
            numberMap.keys.forEach { stringNumber ->
                val firstIndex = line.indexOf(stringNumber)
                matchMap[firstIndex] = stringNumber
                val lastIndex = line.lastIndexOf(stringNumber)
                matchMap[lastIndex] = stringNumber
            }

            // Locate Digit Numbers
            val firstIndex = line.indexOfFirst { it.isDigit() }
            matchMap[firstIndex] = line.first { it.isDigit() }.toString()
            val lastIndex = line.indexOfLast { it.isDigit() }
            matchMap[lastIndex] = line.last { it.isDigit() }.toString()

            // Remove -1 key tossed by indexOf functions
            matchMap.remove(-1)

            // Sort the map to locate the first and last number values
            val firstNumber = matchMap[matchMap.toSortedMap().firstKey()]!!
            val lastNumber = matchMap[matchMap.toSortedMap().lastKey()]!!

            // Make sure to grab the digit representation of each number
            val firstInt = if (firstNumber.length > 1) numberMap[firstNumber] else firstNumber
            val lastInt = if (lastNumber.length > 1) numberMap[lastNumber] else lastNumber

            // Return the two digits together as an Int
            "$firstInt$lastInt".toInt()
        }
    }
}


fun main() {
    val day1 = Day01()

    println("Day 01 part 1 solution: ${day1.part1()}")
    println("Day 01 part 1 solution: ${day1.part2()}")
}