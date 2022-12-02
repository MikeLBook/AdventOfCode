import java.io.File
fun main() {
    val lines = File("src/main/kotlin/day02.txt").readLines()

    fun formatPartOnePairs(): List<Pair<String, String>> {
        val legend = mapOf(
            "A" to "Rock",
            "B" to "Paper",
            "C" to "Scissors",
            "X" to "Rock",
            "Y" to "Paper",
            "Z" to "Scissors"
        )
        return lines.map {it.split(" ").map { key -> legend[key]!! }.zipWithNext()[0]}
    }

    fun formatPartTwoPairs(): List<Pair<String, String>> {
        fun Pair<String, String>.playStrategy(): Pair<String, String> {
            return when (this) {
                Pair("A", "X") -> Pair("Rock", "Scissors")
                Pair("B", "X") -> Pair("Paper", "Rock")
                Pair("C", "X") -> Pair("Scissors", "Paper")
                Pair("A", "Y") -> Pair("Rock", "Rock")
                Pair("B", "Y") -> Pair("Paper", "Paper")
                Pair("C", "Y") -> Pair("Scissors", "Scissors")
                Pair("A", "Z") -> Pair("Rock", "Paper")
                Pair("B", "Z") -> Pair("Paper", "Scissors")
                Pair("C", "Z") -> Pair("Scissors", "Rock")
                else -> throw Exception("Invalid Pair")
            }
        }
        return lines.map { it.split(" ").zipWithNext()[0].playStrategy() }
    }

    fun computeAllScores(rounds: List<Pair<String, String>>): Int {
        fun computeScore(round: Pair<String, String>): Int {
            val points = mapOf(
                "Rock" to 1,
                "Paper" to 2,
                "Scissors" to 3,
                "Win" to 6,
                "Lose" to 0,
                "Draw" to 3
            )

            val outcomes = mapOf(
                Pair("Rock", "Rock") to "Draw",
                Pair("Rock", "Paper") to "Win",
                Pair("Rock", "Scissors") to "Lose",
                Pair("Paper", "Rock") to "Lose",
                Pair("Paper", "Paper") to "Draw",
                Pair("Paper", "Scissors") to "Win",
                Pair("Scissors", "Rock") to "Win",
                Pair("Scissors", "Paper") to "Lose",
                Pair("Scissors", "Scissors") to "Draw",
            )
            return points[outcomes[round]]?.let {
                points[round.second]?.plus(it)
            } ?: throw Exception("Invalid Pair")
        }
        return rounds.map { computeScore(it) }.reduce { total, score -> total + score }
    }

    println("Day 02 part 1 solution: ${computeAllScores(formatPartOnePairs())}")
    println("Day 02 part 2 solution: ${computeAllScores(formatPartTwoPairs())}")
}