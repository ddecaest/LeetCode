fun main() {
    println(WaterContainer.execute(intArrayOf(1,8,6,2,5,4,8,3,7)))
}

object WaterContainer {

    fun execute(height: IntArray): Int {
        var leftIndex = 0
        var rightIndex = height.size - 1

        var currentBestWater = calculateWater(height, leftIndex, rightIndex)

        while (leftIndex != rightIndex) {
            if (height[leftIndex] < height[rightIndex]) {
                leftIndex++
            } else {
                rightIndex--
            }
            val newWater = calculateWater(height, leftIndex, rightIndex)
            if (newWater > currentBestWater) {
                currentBestWater = newWater
            }
        }

        return currentBestWater
    }

    private fun calculateWater(height: IntArray, leftIndex: Int, rightIndex: Int) =
        Math.min(height[leftIndex], height[rightIndex]) * (rightIndex - leftIndex)
}