package day07

import java.io.File

/*
    After the original submission, saw a solution on the kotlin yt
    that utilizes this buildMap function. Instead of removing entries from the map,
    we can filter on the values in both solutions since the keys are never actually needed.
    After that optimization, since the only mutations left are additive, we can construct
    a single immutable map to filter the values of.

    While the buildMap fn uses a mutableMap under the hood for 'it', this solution is mostly an immutable one.
    I think it's noticeably less readable as a drawback, however. Being able to break chunks up into properly named
    functions really helps to consider each part of the build process in isolation.
 */

class Day07Alternate(private val lines: List<String>) {
    private val totalDiscSpace = 70000000
    private val requiredDiscSpace = 30000000

    fun part1(): Int {
        val fs = buildFileSystem()
        return fs.values.filter { it < 100000 }.sum()
    }

    fun part2(): Int {
        val fs = buildFileSystem()
        return fs.values
            .filter { it >= requiredDiscSpace - (totalDiscSpace - fs["/"]!!)  }
            .min()
    }

    private fun buildFileSystem(): Map<String, Int> {
        var directory = ""
        return buildMap {
            lines.forEach { line ->
                val input = line.split(" ")
                // change directory
                if (input[0] == "$" && input[1] == "cd") {
                    if ( input[2] == "..") {
                        directory = directory.split("/").dropLast(1).joinToString("/")
                    } else {
                        when {
                            (input[2] == "/") -> directory = input[2]
                            (directory == "/") -> directory += input[2]
                            else -> directory += "/$input[2]"
                        }
                    }
                    // initialize to 0 if necessary
                    if (keys.contains(directory)) {
                        put(directory, 0)
                    }
                // update file size
                } else if (input.first() != "dir") {
                    input[0].toIntOrNull()?.let { fileSize ->
                        for (i in 0..directory.length) {
                            val path = directory.substring(0, i)
                            get(path)?.plus(fileSize)?.let {
                                put(path, it)
                            }
                        }
                    }
                }
            }
        }
    }
}

fun main() {
    val day7 = Day07Alternate(File("src/main/resources/day07.txt").readLines())
    println("Day 07 part 1 solution: ${day7.part1()}")
    println("Day 07 part 2 solution: ${day7.part2()}")
}