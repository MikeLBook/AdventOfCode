import java.io.File

enum class Symbol(val points: Int) {
    A(1) {
        override val beats: Symbol
            get() = C
    },
    B(2) {
        override val beats: Symbol
            get() = A
    },
    C(3) {
        override val beats: Symbol
            get() = B
    };
    // had to do this to avoid circular ref in definition
    abstract val beats: Symbol
}

enum class Outcome(val points: Int) {
    WIN(6), LOSE(0), DRAW(3)
}

enum class Legend(val part1: Symbol, val part2: Outcome) {
    X(Symbol.A, Outcome.LOSE),
    Y(Symbol.B, Outcome.DRAW),
    Z(Symbol.C, Outcome.WIN);
}

enum class Part {
    ONE, TWO
}

fun main() {
    val lines = File("src/main/kotlin/day02.txt").readLines()

    fun calculate(part: Part): Int {
        return lines.map { line ->
            val pair = line.split(" ").zipWithNext().first()
            val elfMove = Symbol.valueOf(pair.first)
            val legend = Legend.valueOf(pair.second)
            if (part == Part.ONE) {
                when {
                    elfMove.beats == legend.part1 -> legend.part1.points + Outcome.LOSE.points
                    elfMove == legend.part1 -> legend.part1.points + Outcome.DRAW.points
                    else -> legend.part1.points + Outcome.WIN.points
                }
            } else {
                when(legend.part2) {
                    Outcome.WIN -> Outcome.WIN.points + Symbol.values().first { elfMove.beats != it && elfMove != it }.points
                    Outcome.LOSE -> Outcome.LOSE.points + Symbol.values().first { elfMove.beats == it }.points
                    Outcome.DRAW -> Outcome.DRAW.points + Symbol.values().first { elfMove == it }.points
                }
            }
        }.reduce { total, value -> total + value }
    }

    println("Day 02 part 1 solution: ${calculate(Part.ONE)}")
    println("Day 02 part 2 solution: ${calculate(Part.TWO)}")
}