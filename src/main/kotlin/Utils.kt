fun String.splitCorrectly(): List<String> {
    return this.trim().split("").filter { item -> item.isNotEmpty() }
}