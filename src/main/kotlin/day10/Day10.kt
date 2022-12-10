package day10

import java.io.File

class Day10(private val lines: List<String>) {
    private val cyclesOfImport = listOf(20, 60, 100, 140, 180, 220)

    fun solve() {
        val signalMap = buildMap<Int, Int> {
            val iterator = lines.iterator()
            var addX: Int? = null
            var x: Int = 1
            for(i in 1..cyclesOfImport.last()) {
                if (i in cyclesOfImport) {
                    put(i, x)
                }
                if (addX != null) {
                    x += addX
                    addX = null
                } else {
                    val instruction = iterator.next().split(" ")
                    if (instruction.size > 1) {
                        addX = instruction.last().toInt()
                    }
                }
            }
        }
        print(signalMap)
    }
}

fun main() {
    val day10 = Day10(File("src/main/resources/day10Sample.txt").readLines())

    println("Day 10 part 1 solution: ${day10.solve()}")
    println("Day 10 part 2 solution: ")
}