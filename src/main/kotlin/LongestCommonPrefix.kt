fun main(args: Array<String>) {
    val test = arrayOf("flower", "flow", "flowery", "flightery", "flight")
    println(LongestCommonPrefix.execute(test))
}

object LongestCommonPrefix {

    fun execute(strs: Array<String>): String {
        strs.sort()

        val first = strs[0]
        val last = strs[strs.size - 1]
        for (i in first.length downTo 0 step 1) {
            val firstSubString = first.substring(0, i)
            if (last.startsWith(firstSubString)) {
                return firstSubString
            }
        }
        return ""
    }
}