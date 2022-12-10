package day08

import java.io.File

class Day08(lines: List<String>) {
    private val trees = lines.map { it.split("").filter { tree -> tree != "" && tree != " " }.map { tree -> tree.toInt() } }

    fun part1(): Int {
        return trees.mapIndexed { rowIndex, row ->
            List(row.size) { treeIndex ->
                (isVisible(rowIndex, treeIndex))
            }.count { it }
        }.sumOf { it }
    }

    fun part2(): Int {
        return trees.mapIndexed { rowIndex, row ->
            List(row.size) { treeIndex ->
                calculateVisibility(rowIndex, treeIndex)
            }.max()
        }.maxOf { it }
    }

    private fun calculateVisibility(rowIndex: Int, treeIndex: Int): Int {
        val up = getViewDistanceUp(rowIndex, treeIndex)
        val left = getViewDistanceLeft(rowIndex, treeIndex)
        val down = getViewDistanceDown(rowIndex, treeIndex)
        val right = getViewDistanceRight(rowIndex, treeIndex)
        return up * left * down * right
    }

    private fun getViewDistanceUp(rowIndex: Int, treeIndex: Int): Int {
        if (rowIndex == 0) return 0
        var score = 0
        val treeHeight = trees[rowIndex][treeIndex]
        for (i in rowIndex - 1 downTo 0) {
            if (trees[i][treeIndex] >= treeHeight) {
                return score + 1
            } else {
                score += 1
            }
        }
        return score
    }

    private fun getViewDistanceDown(rowIndex: Int, treeIndex: Int): Int {
        if (rowIndex == trees.size - 1) return 0
        var score = 0
        val treeHeight = trees[rowIndex][treeIndex]
        for (i in rowIndex + 1 until trees.size) {
            if (trees[i][treeIndex] >= treeHeight) {
                return score + 1
            } else {
                score += 1
            }
        }
        return score
    }

    private fun getViewDistanceLeft(rowIndex: Int, treeIndex: Int): Int {
        if (treeIndex == 0) return 0
        var score = 0
        val treeHeight = trees[rowIndex][treeIndex]
        for (i in treeIndex - 1 downTo 0) {
            if (trees[rowIndex][i] >= treeHeight) {
                return score + 1
            } else {
                score += 1
            }
        }
        return score
    }

    private fun getViewDistanceRight(rowIndex: Int, treeIndex: Int): Int {
        if (treeIndex == trees.size - 1) return 0
        var score = 0
        val treeHeight = trees[rowIndex][treeIndex]
        for (i in treeIndex + 1 until trees.size) {
            if (trees[rowIndex][i] >= treeHeight) {
                return score + 1
            } else {
                score += 1
            }
        }
        return score
    }

    private fun isVisible(rowIndex: Int, treeIndex: Int): Boolean {
        return when {
            rowIndex == 0 -> true
            rowIndex == trees.size - 1 -> true
            treeIndex == 0 -> true
            treeIndex == trees.size - 1 -> true
            else -> {
                isVisibleUp(rowIndex, treeIndex) || isVisibleDown(rowIndex, treeIndex)
                        || isVisibleLeft(rowIndex, treeIndex) || isVisibleRight(rowIndex, treeIndex)
            }
        }

    }

    private fun isVisibleUp(rowIndex: Int, treeIndex: Int): Boolean {
        val treeHeight = trees[rowIndex][treeIndex]
        for (i in 0 until rowIndex) {
            if (trees[i][treeIndex] >= treeHeight) {
                return false
            }
        }
        return true
    }

    private fun isVisibleDown(rowIndex: Int, treeIndex: Int): Boolean {
        val treeHeight = trees[rowIndex][treeIndex]
        for (i in rowIndex + 1 until trees.size) {
            if (trees[i][treeIndex] >= treeHeight) {
                return false
            }
        }
        return true
    }

    private fun isVisibleLeft(rowIndex: Int, treeIndex: Int): Boolean {
        val treeHeight = trees[rowIndex][treeIndex]
        for (i in 0 until treeIndex) {
            if (trees[rowIndex][i] >= treeHeight) {
                return false
            }
        }
        return true
    }

    private fun isVisibleRight(rowIndex: Int, treeIndex: Int): Boolean {
        val treeHeight = trees[rowIndex][treeIndex]
        for (i in treeIndex + 1 until trees.size) {
            if (trees[rowIndex][i] >= treeHeight) {
                return false
            }
        }
        return true
    }
}

fun main() {
    val day8 = Day08(File("src/main/resources/day08.txt").readLines())
    println("Day 08 part 1 solution: ${day8.part1()}")
    println("Day 08 part 2 solution: ${day8.part2()}")
}