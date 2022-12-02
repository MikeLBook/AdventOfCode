import java.io.File

fun main() {
    val calorieList = File("src/main/kotlin/day01.txt").readLines()
    val emptyStringIndices = calorieList.mapIndexedNotNull { index, line -> if (line.isEmpty()) index else null }
    val solution = emptyStringIndices.mapIndexed { index, emptyStringIndex ->
        val fromIndex = if (index == 0) 0 else emptyStringIndices[index - 1] + 1
        calorieList.subList(fromIndex, emptyStringIndex).map { it.toInt() }.reduce { total, nextValue -> total + nextValue }
    }.max()

    print(solution)
}