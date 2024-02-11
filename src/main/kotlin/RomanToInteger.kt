fun main() {
    println(RomanToInteger.execute("MCMXCIV"))
}

object RomanToInteger {
    // Ugly but very fast! :D
    fun execute(input: String): Int {
        val chars = input.toCharArray()
        var sum = 0

        var current = 0
        while (current < chars.size - 1) {
            val currentChar = chars[current]
            if (currentChar == 'I') {
                if (chars[current + 1] == 'V') {
                    sum += 4
                    current += 2
                    continue
                }
                if (chars[current + 1] == 'X') {
                    sum += 9
                    current += 2
                    continue
                } else {
                    sum += 1
                }
            } else if (currentChar == 'X') {
                if (chars[current + 1] == 'L') {
                    sum += 40
                    current += 2
                    continue
                } else if (chars[current + 1] == 'C') {
                    sum += 90
                    current += 2
                    continue
                } else {
                    sum += 10
                }
            } else if (currentChar == 'C') {
                if (chars[current + 1] == 'D') {
                    sum += 400
                    current += 2
                    continue
                } else if (chars[current + 1] == 'M') {
                    sum += 900
                    current += 2
                    continue
                } else {
                    sum += 100
                }
            } else if (currentChar == 'V') {
                sum += 5
            } else if (currentChar == 'L') {
                sum += 50
            } else if (currentChar == 'D') {
                sum += 500
            } else if (currentChar == 'M') {
                sum += 1000
            }
            current++
        }
        if(current < chars.size) {
            when (chars[current]) {
                'I' -> sum += 1
                'V' -> sum += 5
                'X' -> sum += 10
                'L' -> sum += 50
                'C' -> sum += 100
                'D' -> sum += 500
                'M' -> sum += 1000
            }
        }
        return sum
    }
}