package day10

import java.io.File

/*
    This solution originally was just the two solution functions.
    Afterwards, I extracted the common elements of the two into the CRT class.
    I think this is about the safest way to handle mutability, and also avoided code duplication.
 */

class Day10(private val lines: List<String>) {
    class CRT(lines: List<String>) {
        private var x = 1
        private var addX: Int? = null
        private val iterator = lines.iterator()

        fun getX() = x

        fun nextCycle() {
            addX?.let {
                x += it
                addX = null
            } ?: run {
                val instruction = iterator.next().split(" ")
                if (instruction.size > 1) {
                    addX = instruction.last().toInt()
                }
            }
        }

        fun getPixel(i: Int): String {
            return when (i) {
                in x-1..x+1 -> "#"
                else -> "."
            }
        }
    }

    fun part1(): Int {
        return buildList {
            val crt = CRT(lines)
            for(i in 1..220) {
                if (i in listOf(20, 60, 100, 140, 180, 220)) {
                    add(crt.getX() * i)
                }
                crt.nextCycle()
            }
        }.sum()
    }

    fun part2() {
        buildList {
            val crt = CRT(lines)
            for(row in 0..5) {
                for (i in 0..39) {
                    add(crt.getPixel(i))
                    crt.nextCycle()
                }
            }
        }.chunked(40) {
            println(it.joinToString(" "))
        }
    }
}

fun main() {
    val day10 = Day10(File("src/main/resources/day10.txt").readLines())

    println("Day 10 part 1 solution: ${day10.part1()}")
    println("Day 10 part 2 solution: ")
    day10.part2()
}