import java.io.File

fun main() {
    val calorieList = File("src/main/kotlin/day01.txt").readLines()

    val subListIndices = calorieList.mapIndexedNotNull { index, line ->
        when {
            index == 0 -> -1 // first item of the first window coming up
            line.isEmpty() -> index // a proper index of an empty string
            index == calorieList.size - 1 -> calorieList.size // last item of the last window
            else -> null
        }
    }

    val maxCalories = subListIndices.windowed(2, 1) { window ->
        // subList start index is inclusive and ending index is exclusive
        calorieList.subList(window.first() + 1, window.last())
            .map { it.toInt() }
            .reduce { total, nextValue -> total + nextValue }
    }.max()

    print(maxCalories)
}