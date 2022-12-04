fun String.splitCorrectly(delimiter: String): List<String> {
    return this.trim().split(delimiter).filter { item -> item.isNotEmpty() }
}