fun main(args: Array<String>) {
    println(ReverseInteger.execute(2147483645))
}

object ReverseInteger {

    fun execute(input: Int): Int {
        var asString = input.toString()

        val negativeSign = if (input < 0) {
            asString = asString.drop(1)
            "-"
        } else {
            ""
        }

        val reversedWithoutLeadingZeroes = negativeSign + asString.reversed().trimStart('0')

        return try {
            reversedWithoutLeadingZeroes.toInt()
        } catch (_: NumberFormatException) {
            return 0
        }
    }
}