import java.io.File

fun main() {
    fun day01(): List<Int> { // returns a list of total calories per elf
        val calorieList = File("src/main/kotlin/day01.txt").readLines()
        val emptyStringIndices = calorieList.mapIndexedNotNull { index, line -> if (line.isEmpty()) index else null }
        return emptyStringIndices.mapIndexed { index, emptyStringIndex ->
            val fromIndex = if (index == 0) 0 else emptyStringIndices[index - 1] + 1
            calorieList.subList(fromIndex, emptyStringIndex).map { it.toInt() }.reduce { total, nextValue -> total + nextValue }
        }
    }

    fun part1() {
        println("Day 01 part 1 solution: ${day01().max()}")
    }

    fun part2() {
        println("Day 01 part 2 solution: ${day01().sortedDescending().subList(0, 3).reduce { total, nextValue -> total + nextValue }}")
    }

    part1()
    part2()
}

fun day01Alternate(): List<Int> {
    val calorieList = File("src/main/kotlin/day01.txt").readLines()
    val subListIndices = calorieList.mapIndexedNotNull { index, line ->
        when {
            index == 0 -> -1 // first item of the first window coming up
            line.isEmpty() -> index // a proper index of an empty string
            index == calorieList.size - 1 -> calorieList.size // last item of the last window
            else -> null
        }
    }
    return subListIndices.windowed(2, 1) { window ->
        calorieList.subList(window.first() + 1, window.last())
            .map { it.toInt() }.reduce { total, nextValue -> total + nextValue }
    }
}