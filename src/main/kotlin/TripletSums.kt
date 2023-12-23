fun main(args: Array<String>) {
    val test = intArrayOf(-1, 0, 1, 2, -1, -4)
    println(TripletSums.execute(test))
}

object TripletSums {
    fun execute(nums: IntArray): List<List<Int>> {
        val results = mutableSetOf<List<Int>>()

        val lookupTable = nums.withIndex().associate { (index, value) -> value to index }

        for (i in nums.indices) {
            for (j in 0..<i) {
                val sumOfTwos = nums[i] + nums[j]

                val oppositeOfSumOfTwo = sumOfTwos * -1

                val indexOf = lookupTable[oppositeOfSumOfTwo]
                if (indexOf != null && indexOf != i && indexOf != j) {
                    results.add(listOf(nums[i], nums[j], oppositeOfSumOfTwo).sorted())
                }
            }
        }

        return results.toList()
    }
}