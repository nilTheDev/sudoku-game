package com.example.sudokugame

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView


const val TAG = "SUDOKUFRAGMENT"

class SudokuFragment : Fragment() {

    private lateinit var sudoku: Sudoku

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val parent = inflater.inflate(R.layout.fragment_sudoku, container, false) as ViewGroup
        sudoku = Sudoku(parent, requireContext(), inflater)
        setInputOnClickListeners(parent)
        return parent
    }

//    private val generateBox = {for(i in 0..2) sudokuParent.addView(generateSquareRow())}

//    // returns a row of a square
//    // i.e three cells, properly marked with ids, horizontally aligned
//    // and wrapped in a LinearLayout
//    private fun generateCellRow(): LinearLayout {
//        val linearLayout = LinearLayout(context)
//        linearLayout.apply{
//            layoutParams = ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.WRAP_CONTENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT
//            )
//            orientation = LinearLayout.HORIZONTAL
//        }
//
//        for (i in 0..2) {
//            val text = layoutInflater.inflate(R.layout.cells, linearLayout, false)
//            text.id = manualId++
//            text.setOnClickListener(cellOnClickListener)
//            linearLayout.addView(text)
//        }
//
//
//        return linearLayout
//    }

//    // returns a row of the whole sudoku box wrapped in a
//    // LinearLayout
//    // i.e three squares horizontally aligned
//    // uses generateRow() nine times
//    private fun generateSquareRow(): LinearLayout {
//        val parent = LinearLayout(context)
//        // the parent layout for creating the box row
//        parent.apply{
//            layoutParams = VLParams(VLParams.WRAP_CONTENT, VLParams.WRAP_CONTENT)
//            orientation = LinearLayout.HORIZONTAL
//        }
//
//        // the list would hold nine LinearLayouts containing three cells each
//        val rows = mutableListOf<LinearLayout>()
//
//        for(i in 0..8) rows.add(generateCellRow())
//
//        for(i in 0..2){
//            val square = LinearLayout(context)
//            square.apply {
//                layoutParams = VLParams(VLParams.WRAP_CONTENT, VLParams.WRAP_CONTENT)
//                orientation = LinearLayout.VERTICAL
//                setBackgroundResource(R.drawable.square_border)
//            }
//
//            for(j in i..8 step 3) square.addView(rows[j])
//            parent.addView(square)
//        }
//
//        return parent
//    }



//    private val cellOnClickListener = {v: View ->
//        loseFocus()
//        if(v is TextView) gainFocus(v)
//    }


    private fun setInputOnClickListeners(parentLayout: ViewGroup){
        val cellInputListener = {inputButton: View -> sudoku.cellInputListener(inputButton.id)}
        parentLayout.apply{
            findViewById<TextView>(R.id.key_1).setOnClickListener(cellInputListener)
            findViewById<TextView>(R.id.key_2).setOnClickListener(cellInputListener)
            findViewById<TextView>(R.id.key_3).setOnClickListener(cellInputListener)
            findViewById<TextView>(R.id.key_4).setOnClickListener(cellInputListener)
            findViewById<TextView>(R.id.key_5).setOnClickListener(cellInputListener)
            findViewById<TextView>(R.id.key_6).setOnClickListener(cellInputListener)
            findViewById<TextView>(R.id.key_7).setOnClickListener(cellInputListener)
            findViewById<TextView>(R.id.key_8).setOnClickListener(cellInputListener)
            findViewById<TextView>(R.id.key_9).setOnClickListener(cellInputListener)
        }
    }



//    // fills the preliminary cells to make the game playable
//    // uses random algorithm heavily
//    // iterates over all the rows
//    // randomly decides how many cells to fill
//    // randomly decides which cells to fill
//    // fills the cells with randomly generated integers
//    // uses Sudoku.isValidInput to avoid repetition
//    private fun generateBoard(){
//        // iterating each row
//        for (i in 1000..1080 step 9){
//            val numOfCellsToFill = Random.nextInt(1..5)
//            val cellIndicesToFill = mutableSetOf<Int>()
//
//            // generate the cells that would be filled
//            for(j in 1..numOfCellsToFill){
//                while(true){
//                    val currentRandomCell = Random.nextInt(0..8) + i
//                    if(currentRandomCell !in cellIndicesToFill){
//                        cellIndicesToFill.add(currentRandomCell)
//                        break
//                    }
//                }
//            }
//
//            // filling the cells
//            for(cell in cellIndicesToFill){
//                while(true){
//                    val currentRandomValue = Random.nextInt(1..9)
//                    if (Sudoku.isValidInput(sudokuParent, cell, currentRandomValue)){
//
//                        sudokuParent.findViewById<TextView>(cell).apply{
//                            text = currentRandomValue.toString()
//                            setTypeface(null, Typeface.BOLD)
//                        }
//                        break
//                    }
//
//                }
//            }
//            initialisedCells.addAll(cellIndicesToFill)
//        }
//    }
}