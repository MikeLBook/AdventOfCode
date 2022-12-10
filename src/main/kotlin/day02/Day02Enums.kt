package day02

import java.io.File

/*
    Unlike a traditional class, enums classes are instantiated within the definition.
    Like the name suggests, enum classes have a fixed number of instances.
    For the below Part enum, only ONE and TWO exist.
    THREE can be added, but again it must be added here where Part is defined at all.
    A common use case for enums is to help with what would otherwise be regular string comparisons.
    For example, instead of having a condition that compares a string against a string "ONE",
    you can use an enum. This makes it easy for a developer to easily spot what values are accepted
    and also allows the compiler to enforce that when statements are exhaustive when switching on an enum value.

    To invoke an enum, you can use: Part.ONE
    To get the string value of an enum, you can use: Part.ONE.name
    To get a list of all possible enums, you can use: Part.values() // [ONE, TWO]
    To get an enum matching a string, you can use Part.valueOf("ONE") // ONE

    The Part enum below is the minimal implementation.
 */
enum class Part {
    ONE, TWO
}

/*
    Enums can also be supplied with additional properties.
    In the case of Outcome, each instantiated enum is supplied with a property called 'points' of type Int.
    e.g. Outcome.WIN.points == 6 // true
    Using enums is a bit more imperative than I am normally used to, but very easy to conceptualize.
    Instead of a traditional map, you can declare any number of permutations of a given concept.
    In this case, there are only three outcomes to a game of rock paper scissors.
 */

enum class Outcome(val points: Int) {
    WIN(6), LOSE(0), DRAW(3)
}

/*
    The legend supplied is different for part 1 and part 2 of the day 2 challenges.
    For the Legend enum, then, I've supplied two properties for each instance.
    The property named part1 is of type Symbol (the next enum to be defined).
    The property named part2 is of type Outcome, defined just above.
    e.g. Legend.X.part1 == Symbol.A // true
         Legend.Z.part2 == Outcome.WIN // true
 */
enum class Legend(val part1: Symbol, val part2: Outcome) {
    X(Symbol.A, Outcome.LOSE),
    Y(Symbol.B, Outcome.DRAW),
    Z(Symbol.C, Outcome.WIN);
}

/*
    The symbol enum has two properties.
    The 'points' property is of type Int, and is supplied in the constructors.
    The 'beats' property is supplied to the enums as an abstract value and implemented with an override in each instance
    This was necessary, because 'beats' is also of type Symbol.
    Symbol.A would not be able to pass Symbol.B to its constructor.
    Other than that, there's nothing new going on with the Symbol enum.
    e.g. Symbol.A.points == 1 // true
         Symbol.B.beats == Symbol.A // true
 */

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
    abstract val beats: Symbol
}

fun main() {
    val lines = File("src/main/resources/day02.txt").readLines()
    // takes either Part.ONE or Part.TWO as an argument - safer to accept a Part than a String
    fun calculate(part: Part): Int {
        return lines.sumOf { line ->
            // split an individual line in the file to a Pair
            val pair = line.split(" ").zipWithNext().first()
            // if pair.first == "A", then elfMove == Symbol.A
            val elfMove = Symbol.valueOf(pair.first)
            // if pair.second == "X", then legend == Legend.X
            val legend = Legend.valueOf(pair.second)
            // calculating points differently depending on which Part was passed into calculate
            if (part == Part.ONE) {
                when {
                    // if the elfMove.beats my move as determined by legend.part1,
                    // then return my moves points + the points associated with losing
                    // if the points associated with a loss were to be updated, we could update it in the enum
                    // without having to search around through core logic
                    elfMove.beats == legend.part1 -> legend.part1.points + Outcome.LOSE.points
                    // if the elfMovie and my move as determined by legend.part1 are the same, then it is a draw
                    elfMove == legend.part1 -> legend.part1.points + Outcome.DRAW.points
                    // the inferred else is a WIN outcome
                    else -> legend.part1.points + Outcome.WIN.points
                }
            } else {
                // unlike the above when statement, we pass legend.part2 into this when statement as an argument
                when (legend.part2) {
                    // as such, the left side of the arrows is implicitly checking if legend.part2 == Outcome.WIN etc.
                    // on the right side of the arrows, we know we SHOULD win lose or draw, but we need to determine which Symbol to play
                    // Symbol.values() gives a list of all Symbols e.g. [A, B, C]
                    // List.first() is a shorthand for filtering a List and then grabbing the first element of the resulting List
                    // This line says give me the Symbol that is not the one that elfMove.beats and is not the elfMove itself
                    Outcome.WIN -> Outcome.WIN.points + Symbol.values()
                        .first { elfMove.beats != it && elfMove != it }.points
                    // Here, give me the Symbol that the elfMove.beats
                    Outcome.LOSE -> Outcome.LOSE.points + Symbol.values().first { elfMove.beats == it }.points
                    // Here, give me the Symbol that is the same as the elfMove
                    Outcome.DRAW -> Outcome.DRAW.points + Symbol.values().first { elfMove == it }.points
                }
            }
        }
    }

    println("Day 02 part 1 solution: ${calculate(Part.ONE)}")
    println("Day 02 part 2 solution: ${calculate(Part.TWO)}")
}