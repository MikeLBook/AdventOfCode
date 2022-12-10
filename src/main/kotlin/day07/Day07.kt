package day07

import java.io.File

class Day07(private val lines: List<String>) {
    private var directory: String = ""
    private val fs = mutableMapOf<String, Int>()
    private val totalDiscSpace = 70000000
    private val requiredDiscSpace = 30000000

    fun part1(): Int {
        resetFileSystem()
        processInputs()
        filterLargeDirectories()
        return fs.keys.sumOf {
            fs[it]!!
        }
    }

    fun part2(): Int {
        resetFileSystem()
        processInputs()
        return findFolderSizeToDelete()
    }

    private fun resetFileSystem() {
        fs.clear()
    }

    private fun processInputs() {
        lines.forEach { line ->
            val input = line.split(" ")
            if (input[0] == "$" && input[1] == "cd") {
                changeDirectory(input[2])
            } else if (input.first() != "dir") {
                recordFileSize(input[0])
            }
        }
    }

    private fun changeDirectory(dir: String) {
        if ( dir == "..") {
            directory = directory.split("/").dropLast(1).joinToString("/")
        } else {
            when {
                (dir == "/") -> directory = dir
                (directory == "/") -> directory += dir
                else -> directory += "/$dir"
            }
        }
        if (!fs.keys.contains(directory)) {
            fs[directory] = 0
        }
    }

    private fun recordFileSize(size: String) {
        size.toIntOrNull()?.let { fileSize ->
            for (i in 0..directory.length) {
                val path = directory.substring(0, i)
                fs[path]?.plus(fileSize)?.let {
                    fs[path] = it
                }
            }
        }
    }

    private fun filterLargeDirectories() {
        val iterator: MutableIterator<Map.Entry<String, Int>> = fs.entries.iterator()
        while (iterator.hasNext()) {
            if (iterator.next().value > 100000) {
                iterator.remove()
            }
        }
    }

    private fun findFolderSizeToDelete(): Int {
        val spaceNeeded = requiredDiscSpace - (totalDiscSpace - fs["/"]!!)
        val iterator: MutableIterator<Map.Entry<String, Int>> = fs.entries.iterator()
        while (iterator.hasNext()) {
            if (iterator.next().value < spaceNeeded) {
                iterator.remove()
            }
        }
        return fs.values.min()
    }
}

fun main() {
    val day7 = Day07(File("src/main/resources/day07.txt").readLines())
    println("Day 07 part 1 solution: ${day7.part1()}")
    println("Day 07 part 2 solution: ${day7.part2()}")
}