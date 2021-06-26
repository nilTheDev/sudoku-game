package com.example.sudokugame

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import kotlin.random.Random
import kotlin.random.nextInt

class Sudoku(
    private val sudokuParent: ViewGroup,
    private val context: Context,
    private val layoutInflater: LayoutInflater
) {


    private var manualId = 1000
    private val inFocus = object {
        var id: Int? = null
        var background: Drawable? = null
    }
    private val initialisedCells = mutableSetOf<Int>()

    init {
        generateBox()

        while (true) {
            val randomCell = Random.nextInt(1000..1080)
            if (randomCell in initialisedCells) continue
            gainFocus(randomCell)
            break
        }
    }

    // generate the whole sudoku box by
    // generating three rows of three squares each
    // and adding it into the LinearLayout of the view
    private fun generateBox() {
        for (i in 0..2) sudokuParent.findViewById<LinearLayout>(R.id.sudoku_wrapper)
            .addView(generateSquareRow())
        autoFillPreliminaryValues()
    }


    // returns a row of a square
    // i.e three cells, properly marked with ids, horizontally aligned
    // and wrapped in a LinearLayout
    private fun generateCellRow(): LinearLayout {
        val parentLayout = LinearLayout(context)
        parentLayout.apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            orientation = LinearLayout.HORIZONTAL
        }

        for (i in 0..2) {
            val cell = layoutInflater.inflate(R.layout.cells, parentLayout, false)
            cell.id = manualId++
            cell.setOnClickListener(::cellOnClickListener)
            parentLayout.addView(cell)
        }

        return parentLayout
    }

    // returns a row of the whole sudoku box wrapped in a
    // LinearLayout
    // i.e three squares horizontally aligned
    // uses generateRow() nine times
    private fun generateSquareRow(): LinearLayout {
        val parentLayout = LinearLayout(context)
        // the parent layout for creating the box row
        parentLayout.apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            orientation = LinearLayout.HORIZONTAL
        }

        // the list would hold nine LinearLayouts containing three cells each
        val rows = mutableListOf<LinearLayout>()

        for (i in 0..8) rows.add(generateCellRow())

        for (i in 0..2) {
            val square = LinearLayout(context)
            square.apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                orientation = LinearLayout.VERTICAL
                setBackgroundResource(R.drawable.square_border)
            }

            for (j in i..8 step 3) square.addView(rows[j])
            parentLayout.addView(square)
        }

        return parentLayout
    }

    // fills the preliminary cells to make the game playable
    // uses random algorithm heavily
    // iterates over all the rows
    // randomly decides how many cells to fill
    // randomly decides which cells to fill
    // fills the cells with randomly generated integers
    // uses Sudoku.isValidInput to avoid repetition
    private fun autoFillPreliminaryValues() {
        // iterating each row
        for (i in 1000..1080 step 9) {
            val numOfCellsToFill = Random.nextInt(1..5)
            val cellIndicesToFill = mutableSetOf<Int>()

            // generate the cells that would be filled
            for (j in 1..numOfCellsToFill) {
                while (true) {
                    val currentRandomCell = Random.nextInt(0..8) + i
                    if (currentRandomCell !in cellIndicesToFill) {
                        cellIndicesToFill.add(currentRandomCell)
                        break
                    }
                }
            }

            // filling the cells
            for (cell in cellIndicesToFill) {
                while (true) {
                    val currentRandomValue = Random.nextInt(1..9)
                    if (isValidInput(cell, currentRandomValue)) {

                        sudokuParent.findViewById<TextView>(cell).apply {
                            text = currentRandomValue.toString()
                            setTypeface(null, Typeface.BOLD)
                        }
                        break
                    }

                }
            }
            initialisedCells.addAll(cellIndicesToFill)
        }
    }

    // saves the previous style of the cell in the inFocus object
    // and switches the style to cell_infocus
    private fun gainFocus(cellId: Int) {
        // whether the user clicked a cell
        // that was automatically initialised
        if (cellId in initialisedCells) return

        val view = sudokuParent.findViewById<TextView>(cellId)

        // make the inFocus object point the
        // current cell
        inFocus.apply {
            id = view.id
            background = view.background
        }
        // switch the style of the cell
        // from default/right/wrong to infocus
        view.setBackgroundResource(R.drawable.cell_infocus)

    }

    // restores the previous style of the cell that was saved in
    // the inFocus object
    private fun loseFocus() {
        // switch the style of cell that was already inFocused
        // form inFocus to default/right/wrong
        sudokuParent.findViewById<TextView>(inFocus.id!!).background = inFocus.background
    }

    // de focus the cell that was previously inFocused
    // in focus the cell that is clicked
    private fun cellOnClickListener(cell: View) {
        // deFocus from the cell that is already in focus
        loseFocus()
        // inFocus the currently clicked object
        if (cell is TextView) gainFocus(cell.id)
    }

    // iterates all the cells
    // validate in user inputs
    // sets background accordingly
    private fun refreshBoard() {
        for (i in 1000..1080) {
            val cell = sudokuParent.findViewById<TextView>(i)
            if (i in initialisedCells || cell.text.toString() == "") continue

            if (isValidInput(
                    i,
                    cell.text.toString().toInt()
                )
            ) cell.setBackgroundResource(R.drawable.cell_right)
            else cell.setBackgroundResource(R.drawable.cell_wrong)

            if(inFocus.id == cell.id) inFocus.background = cell.background
        }
    }

    // click listener for the input panel
    fun cellInputListener(inputId: Int) {
        val inputBtn = sudokuParent.findViewById<TextView>(inputId)
        sudokuParent.findViewById<TextView>(inFocus.id!!).apply {
            text = inputBtn.text
            refreshBoard()
        }

    }

    // validates the input in the
    // current context by comparing the input
    // with the content of other cells of the
    // corresponding square, row and the column
    private fun isValidInput(cellId: Int, input: Int): Boolean {

        // the indices of the squares
        val squares = setOf(
            setOf(1000, 1001, 1002, 1009, 1010, 1011, 1018, 1019, 1020),
            setOf(1003, 1004, 1005, 1012, 1013, 1014, 1021, 1022, 1023),
            setOf(1006, 1007, 1008, 1015, 1016, 1017, 1024, 1025, 1026),
            setOf(1027, 1028, 1029, 1036, 1037, 1038, 1045, 1046, 1047),
            setOf(1030, 1031, 1032, 1039, 1040, 1041, 1048, 1049, 1050),
            setOf(1033, 1034, 1035, 1042, 1043, 1044, 1051, 1052, 1053),
            setOf(1054, 1055, 1056, 1063, 1064, 1065, 1072, 1073, 1074),
            setOf(1057, 1058, 1059, 1066, 1067, 1068, 1075, 1076, 1077),
            setOf(1060, 1061, 1062, 1069, 1070, 1071, 1078, 1079, 1080)
        )

        // iterate the rows
        for (i in 1000..1080 step 9) {
            // whether the cellId in the current row
            if (cellId in i..(i + 8)) {
                // as the row is found
                // inspect the cells of the row
                for (j in i..(i + 8)) {
                    // skip the proposed cellId
                    if (j == cellId) continue
                    if (sudokuParent.findViewById<TextView>(j).text == input.toString()) return false
                }
                // the proposed input does not have any row-wise repetition
                // so break out from the loop to continue the inspection
                // with the columns and squares
                break
            }
        }

        // iterate the columns
        for (i in 1000..1008) {
            // whether the cellId is in the current column
            if (cellId in i..(i + 72) step 9) {
                // as the column is found
                // inspect the cells of the entire column
                for (j in i..(i + 72) step 9) {
                    // skip the proposed cellId
                    if (j == cellId) continue
                    if (sudokuParent.findViewById<TextView>(j).text == input.toString()) return false
                }
                // the proposed input does not have any column-wise repetition
                // so break out from the loop to continue the inspection
                // with the squares
                break
            }
        }

        // iterate the squares
        for (square in squares) {
            // whether the cellId is in the current square
            if (cellId in square) {
                // inspect all the cells of the square
                for (cell in square) {
                    // skip the proposed cellId
                    if (cell == cellId) continue
                    if (sudokuParent.findViewById<TextView>(cell).text == input.toString()) return false
                }
                // as there is no repetition of the proposed input
                // in the square, break out from the loop
                // the input number is perfectly valid
                break
            }
        }

        return true

    }

}