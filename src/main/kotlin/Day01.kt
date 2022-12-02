import java.io.File

fun main() {
    val lines = File("src/main/kotlin/day01.txt").readLines()
    val splitPoints = lines.mapIndexedNotNull { index, line -> if (line.isEmpty()) index else null }
    val elfCalorieLists = splitPoints.mapIndexed { index, splitPoint ->
        val startIndex = if (index == 0) 0 else splitPoints[index - 1] + 1
        lines.subList(startIndex, splitPoint)
    }
    val maxElfCalories = elfCalorieLists.maxOfOrNull { calorieList ->
        calorieList.map { it.toInt() }.reduce { total, nextValue -> total + nextValue }
    }
    print(maxElfCalories)
}