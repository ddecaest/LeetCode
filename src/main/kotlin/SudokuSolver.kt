fun main() {
    val board = arrayOf(
        charArrayOf('.', '.', '9', '7', '4', '8', '.', '.', '.'),
        charArrayOf('7', '.', '.', '.', '.', '.', '.', '.', '.'),
        charArrayOf('.', '2', '.', '1', '.', '9', '.', '.', '.'),
        charArrayOf('.', '.', '7', '.', '.', '.', '2', '4', '.'),
        charArrayOf('.', '6', '4', '.', '1', '.', '5', '9', '.'),
        charArrayOf('.', '9', '8', '.', '.', '.', '3', '.', '.'),
        charArrayOf('.', '.', '.', '8', '.', '3', '.', '2', '.'),
        charArrayOf('.', '.', '.', '.', '.', '.', '.', '.', '6'),
        charArrayOf('.', '.', '.', '2', '7', '5', '9', '.', '.'),
    )
    SudokuSolver.execute(board)
    prettyPrint(board)
}

fun prettyPrint(charArray: Array<CharArray>) {
    println("----")
    for (row in charArray) {
        for (char in row) {
            print("$char ")
        }
        println()  // Move to the next line after printing each row
    }
}

data class Cell(var solution: Int? = null, var options: List<Int> = listOf()) {

    companion object {
        private val ALL_OPTIONS = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
        fun solved(solution: Int?) = Cell(solution = solution)
        fun notSolved(): Cell = Cell(options = ALL_OPTIONS)
    }

    fun removeOption(option: Int): Boolean {
        if (solution != null) {
            return false
        }

        options = options.filter { it != option }
        if (options.size == 1) {
            solution = options[0]
            return true
        }
        return false
    }
}

object SudokuPresenter {
    fun toCells(board: Array<CharArray>): Array<Array<Cell>> {
        return board.map { row ->
            row.map {
                if (it == '.') {
                    Cell.notSolved()
                } else {
                    Cell.solved(it.digitToInt())
                }
            }.toTypedArray()
        }.toTypedArray()
    }

    fun copyToCharArrays(board: Array<CharArray>, cells: Array<Array<Cell>>) {
        for (x in 0..8) {
            for (y in 0..8) {
                val solution = cells[x][y].solution
                board[x][y] = solution?.digitToChar() ?: '.'
            }
        }
    }
}

object SudokuSolver {

    fun execute(board: Array<CharArray>) {
        val cells = SudokuPresenter.toCells(board)
        val solvedAfterFirstIteration = firstPass(cells)
        iterate(cells, solvedAfterFirstIteration)
        SudokuPresenter.copyToCharArrays(board, cells)
    }

    private fun firstPass(cells: Array<Array<Cell>>): MutableList<Pair<Int, Int>> {
        val solvedAfterFirstIteration = mutableListOf<Pair<Int, Int>>()
        for (x in 0..8) {
            for (y in 0..8) {
                val solution = cells[x][y].solution
                if (solution != null) {
                    solvedAfterFirstIteration.addAll(updateCells(solution, x, y, cells))
                }
            }
        }
        return solvedAfterFirstIteration
    }

    private fun iterate(cells: Array<Array<Cell>>, solvedAfterFirstIteration: MutableList<Pair<Int, Int>>) {
        var newSolutions = solvedAfterFirstIteration.toList()
        while (newSolutions.isNotEmpty()) {
            // Use the solutions from the previous iteration to generate new solutions
            val newSolutionsForNextIteration = applySolutions(newSolutions, cells)
            // Additionally, check solutions due to only one valid choice remaining for that row/column/grid
            val onlyOptions = solveOnlyOptions(cells)

            newSolutions = newSolutionsForNextIteration + onlyOptions
        }
    }

    private fun applySolutions(solutions: List<Pair<Int, Int>>, cells: Array<Array<Cell>>) = solutions.flatMap {
        val x = it.first
        val y = it.second
        updateCells(cells[x][y].solution!!, x, y, cells)
    }

    private fun solveOnlyOptions(cells: Array<Array<Cell>>): List<Pair<Int, Int>> {
        // Check if horizontally/vertically there is only one option for any row
        val solved = mutableListOf<Pair<Int, Int>>()

        for (j in 0..8) {
            val row = (0..8).map { cells[j][it] }
            for (uniqueNumber in findUniqueOptions(row)) {
                for (x in 0..8) {
                    if (cells[j][x].options.contains(uniqueNumber) && cells[j][x].solution != uniqueNumber) {
                        cells[j][x].solution = uniqueNumber
                        cells[j][x].options = listOf(uniqueNumber)
                        solved.add(j to x)
                    }
                }
            }

            val column = (0..8).map { cells[it][j] }
            for (uniqueNumber in findUniqueOptions(column)) {
                for (y in 0..8) {
                    if (cells[y][j].options.contains(uniqueNumber) && cells[y][j].solution != uniqueNumber) {
                        cells[y][j].solution = uniqueNumber
                        cells[y][j].options = listOf(uniqueNumber)
                        solved.add(y to j)
                    }
                }
            }
        }

        // TODO: can also check in grid, but doesn't matter for the given input

        return solved
    }

    private fun findUniqueOptions(cells: List<Cell>): Set<Int> {
        // Count occurrences of each option across cells
        val numberOccurrences = mutableMapOf<Int, Int>()
        cells.forEach { cell ->
            cell.options.forEach { numberOccurrences[it] = numberOccurrences.getOrDefault(it, 0) + 1 }
        }
        return numberOccurrences.filter { it.value == 1 }.keys
    }

    // Updates the grid based on a solution at a certain coordinate, returns the new cells solved due to this update
    private fun updateCells(solution: Int, x: Int, y: Int, cells: Array<Array<Cell>>): List<Pair<Int, Int>> {
        val solvedDueToUpdate = mutableListOf<Pair<Int, Int>>()

        // Update horizontal && vertical lines
        for (position in 0..8) {
            if (cells[position][y].removeOption(solution)) {
                solvedDueToUpdate.add(position to y)
            }
            if (cells[x][position].removeOption(solution)) {
                solvedDueToUpdate.add(x to position)
            }
        }
        // Update 3x3 grid
        val topXSquare = (x / 3) * 3
        val topYSquare = (y / 3) * 3
        for (xOffset in 0..2) {
            for (yOffset in 0..2) {
                val xUpdated = topXSquare + xOffset
                val yUpdated = topYSquare + yOffset
                if (cells[xUpdated][yUpdated].removeOption(solution)) {
                    solvedDueToUpdate.add(xUpdated to yUpdated)
                }
            }
        }

        return solvedDueToUpdate
    }
}