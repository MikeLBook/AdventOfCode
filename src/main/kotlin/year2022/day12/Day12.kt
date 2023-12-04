package year2022.day12

import java.io.File

/*
    https://en.wikipedia.org/wiki/Breadth-first_search
 */

class Day12(lines: List<String>) {
    private val originalHeightMap = lines.map { line ->
        line.split("").filter { it != "" }
    }
    private val startPosition = locatePositions('S').first()
    private val endPosition = locatePositions('E').first()
    private val heightmap = originalHeightMap.map {
        it.map { char ->
            when(char) {
                "S" -> "a"
                "E" -> "z"
                else -> char
            }
        }
    }

    fun part1(): Int {
        return findShortestDistance(listOf(startPosition))!!
    }

    fun part2(): Int {
        val startPositions = locatePositions('a') + startPosition
        return startPositions.mapNotNull { findShortestDistance(listOf(it)) }.min()
    }

    private fun findShortestDistance(
        fromPositions: List<Pair<Int,Int>>,
        movements: Int = 0,
        visitedPositions: List<Pair<Int,Int>> = listOf()
    ): Int? {
        val allPositions = visitedPositions + fromPositions
        if (endPosition in allPositions) return movements
        val newPositions = fromPositions.map { position -> listPossibleMovements(position)
            .filter { it !in visitedPositions } }
            .flatten()
            .distinct()
        return if (newPositions.isNotEmpty()) {
            findShortestDistance(newPositions, movements + 1, allPositions)
        } else {
            null
        }
    }

    private fun listPossibleMovements(currentLocation: Pair<Int,Int>): List<Pair<Int,Int>> {
        val (first, second) = currentLocation
        val up = if (first - 1 >= 0) first - 1 to second else null
        val down = if (first + 1 < heightmap.size) first + 1 to second else null
        val left = if (second - 1 >= 0) first to second - 1 else null
        val right = if (second + 1 < heightmap[0].size) first to second + 1 else null
        val currentCharacterCode = heightmap[currentLocation.first][currentLocation.second].single().code
        return listOfNotNull(up, down, left, right).filter {
            (heightmap[it.first][it.second].single().code - currentCharacterCode < 2)
        }
    }

    private fun locatePositions(char: Char): List<Pair<Int,Int>> {
        return originalHeightMap.mapIndexed { lineIndex, line ->
            val characterIndices = line.mapIndexedNotNull{ charIndex, character ->
                charIndex.takeIf{ character == char.toString()}
            }
            characterIndices.map { lineIndex to it }
        }.flatten()
    }
}

fun main() {
    val day12 = Day12(File("src/main/resources/year2022.day12.txt").readLines())
    println("Day 12 part 1 solution: ${day12.part1()}") // 412
    println("Day 12 part 2 solution: ${day12.part2()}") // 402
}