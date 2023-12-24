fun main(args: Array<String>) {
    println(MedianTwoArrays.execute(intArrayOf(1, 3), intArrayOf(2, 4)))
}

object MedianTwoArrays {

    var lastReturnedNum1 = -1
    var lastReturnedNum2 = -1
    var nums1Cp = intArrayOf()
    var nums2Cp = intArrayOf()

    fun execute(nums1: IntArray, nums2: IntArray): Double {
        nums1Cp = nums1
        nums2Cp = nums2

        val totalNr = nums1.size + nums2.size

        var lastHighest = -1.0
        for (i in 0..<totalNr / 2) {
            lastHighest = getNextHighest()
        }

        return if (totalNr % 2 == 0) {
            (lastHighest + getNextHighest()) / 2
        } else {
            getNextHighest()
        }
    }

    private fun getNextHighest(): Double {
        // None left in num1?
        if (lastReturnedNum1 + 1 == nums1Cp.size) {
            lastReturnedNum2 += 1
            return nums2Cp[lastReturnedNum2].toDouble()
        }
        // None left in num 2?
        if (lastReturnedNum2 + 1 == nums2Cp.size) {
            lastReturnedNum1 += 1
            return nums1Cp[lastReturnedNum1].toDouble()
        }

        // Compare last element in both arrays
        return if (nums1Cp[lastReturnedNum1 + 1] < nums2Cp[lastReturnedNum2 + 1]) {
            lastReturnedNum1 += 1
            nums1Cp[lastReturnedNum1].toDouble()
        } else {
            lastReturnedNum2 += 1
            nums2Cp[lastReturnedNum2].toDouble()
        }
    }
}