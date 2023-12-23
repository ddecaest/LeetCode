fun main(args: Array<String>) {
    println(PhoneNumberCombinations.execute("232"))
}

object PhoneNumberCombinations {

    private val digitToLetters = mapOf(
        '2' to "abc",
        '3' to "def",
        '4' to "ghi",
        '5' to "jkl",
        '6' to "mno",
        '7' to "pqrs",
        '8' to "tuv",
        '9' to "wxyz"
    )

    fun execute(digits: String): List<String> {
        val combinations = digits.map { digitToLetters[it]!! }
        return unroll(combinations)
    }

    private fun unroll(combinationsLeft: List<String>): List<String> {
        return when (combinationsLeft.size) {
            1 -> combinationsLeft[0].map { it.toString() }
            0 -> return listOf()
            else -> {
                combinationsLeft[0].flatMap { character ->
                    val unrolled = unroll(combinationsLeft.drop(1))
                    unrolled.map { character + it }
                }
            }
        }
    }
}