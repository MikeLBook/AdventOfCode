package day6

import java.io.File

class Day06(input: List<String>) {
    private val signal = input.first().split("").filter { it != "" && it != " " }

    fun solve(windowSize: Int): Int {
        val marker = signal.windowed(windowSize, 1).first { it.toSet().size == windowSize }
        return signal.joinToString("").indexOf(marker.joinToString("")) + marker.size
    }

    // using window to map the booleans inspired by jetbrains yt channel
    fun alternateSolution(windowSize: Int): Int {
        return signal.windowed(windowSize, 1) {
            it.toSet().size == windowSize
        }.indexOf(true) + windowSize
    }
}

fun main() {
    val day6 = Day06(File("src/main/resources/day06.txt").readLines())
    println("Day 06 part 1 solution: ${day6.solve(4)}")
    println("Day 06 part 2 solution: ${day6.solve(14)}")
}