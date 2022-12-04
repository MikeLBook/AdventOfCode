fun String.splitEmptyString(): List<String> {
    return this.trim().split("").filter { item -> item.isNotEmpty() }
}