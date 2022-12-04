import java.io.File

fun main() {
    fun assignmentsOverlapCompletely(list1: List<Int>, list2: List<Int>): Boolean {
        return (list1.first() >= list2.first() && list1.last() <= list2.last())
                || (list2.first() >= list1.first() && list2.last() <= list1.last())
    }

    fun assignmentsOverlap(list1: List<Int>, list2: List<Int>): Boolean {
        return (list1.first() >= list2.first() && list1.first() <= list2.last())
                || (list2.first() >= list1.first() && list2.first() <= list1.last())
    }

    val lines = File("src/main/kotlin/day4/day4.txt").readLines()

    fun countHowMany(doOverlap: (list1: List<Int>, list2: List<Int>) -> Boolean): Int {
        // I love these shorthand methods, e.g. count as a replacement for map().size
        return lines.count { line ->
            val assignments = line.split(",").map { assignment ->
                assignment.split("-").map { it.toInt() }
            }
            doOverlap(assignments.first(), assignments.last())
        }
    }

    println("Day 04 part 1 solution: ${countHowMany(::assignmentsOverlapCompletely)}")
    println("Day 04 part 2 solution: ${countHowMany(::assignmentsOverlap)}")
}