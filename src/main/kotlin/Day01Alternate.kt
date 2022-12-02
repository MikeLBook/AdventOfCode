import java.io.File

fun main() {
    val lines = File("src/main/kotlin/day01Sample.txt").readLines()

    val maxCalories = lines.mapIndexedNotNull { index, line ->
        if (line.isEmpty() || index == 0 || index == lines.size - 1) index else null
    }.windowed(2, 1) {
        when {
            it.first() == 0 -> lines.subList(it.first(), it.last())
            it.last() == lines.size - 1 -> lines.subList(it.first() + 1, lines.size)
            else -> lines.subList(it.first() + 1, it.last())
        }
    }.maxOfOrNull { calorieList ->
        calorieList.map { it.toInt() }.reduce { total, nextValue -> total + nextValue }
    }

    print(maxCalories)
}