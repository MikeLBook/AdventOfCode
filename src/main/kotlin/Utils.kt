fun List<Int>.total(): Int {
    return this.reduce { total, nextValue -> total + nextValue }
}

fun String.splitCorrectly(): List<String> {
    return this.trim().split("").filter { item -> item.isNotEmpty() }
}