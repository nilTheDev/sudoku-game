package com.example.sudokugame

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import kotlin.random.Random
import kotlin.random.nextInt

import android.view.ViewGroup.LayoutParams as VLParams

const val TAG = "SUDOKUFRAGMENT"

class SudokuFragment : Fragment() {

    private var manualId = 1000
    private var inFocus = object {
        var id = -1
        var previousBackground: Drawable? = null
    }
    private lateinit var sudokuParent: LinearLayout
    private val initialisedCells = mutableSetOf<Int>()

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val parent = inflater.inflate(R.layout.fragment_sudoku, container, false)
        sudokuParent = parent.findViewById(R.id.sudoku_wrapper)

        generateBox()
        generateBoard()
        setInputOnClickListeners(parent)

        return parent
    }

    private val generateBox = {for(i in 0..2) sudokuParent.addView(generateSquareRow())}

    // returns a row of a square
    // i.e three cells, properly marked with ids, horizontally aligned
    // and wrapped in a LinearLayout
    private fun generateCellRow(): LinearLayout {
        val linearLayout = LinearLayout(context)
        linearLayout.apply{
            layoutParams = VLParams(VLParams.WRAP_CONTENT, VLParams.WRAP_CONTENT)
            orientation = LinearLayout.HORIZONTAL
        }

        for (i in 0..2) {
            val text = layoutInflater.inflate(R.layout.cells, linearLayout, false)
            text.id = manualId++
            text.setOnClickListener(cellOnClickListener)
            linearLayout.addView(text)
        }


        return linearLayout
    }

    // returns a row of the whole sudoku box wrapped in a
    // LinearLayout
    // i.e three squares horizontally aligned
    // uses generateRow() nine times
    private fun generateSquareRow(): LinearLayout {
        val parent = LinearLayout(context)
        // the parent layout for creating the box row
        parent.apply{
            layoutParams = VLParams(VLParams.WRAP_CONTENT, VLParams.WRAP_CONTENT)
            orientation = LinearLayout.HORIZONTAL
        }

        // the list would hold nine LinearLayouts containing three cells each
        val rows = mutableListOf<LinearLayout>()

        for(i in 0..8) rows.add(generateCellRow())

        for(i in 0..2){
            val square = LinearLayout(context)
            square.apply {
                layoutParams = VLParams(VLParams.WRAP_CONTENT, VLParams.WRAP_CONTENT)
                orientation = LinearLayout.VERTICAL
                setBackgroundResource(R.drawable.square_border)
            }

            for(j in i..8 step 3) square.addView(rows[j])
            parent.addView(square)
        }

        return parent
    }

    private fun gainFocus(v: TextView){
        if (v.id in initialisedCells) return
        inFocus.apply{
            id = v.id
            previousBackground = v.background
        }
        v.setBackgroundResource(R.drawable.cell_infocus)
    }

    fun loseFocus() {
        inFocus.apply {
            if(id == -1) return
            sudokuParent.findViewById<TextView>(id).background = previousBackground
        }
    }

    private val cellOnClickListener = {v: View ->
        loseFocus()
        if(v is TextView) gainFocus(v)
    }


    private fun setInputOnClickListeners(parentLayout: View){
        parentLayout.apply{
            findViewById<TextView>(R.id.key_1).setOnClickListener(::inputOnClickListener)
            findViewById<TextView>(R.id.key_2).setOnClickListener(::inputOnClickListener)
            findViewById<TextView>(R.id.key_3).setOnClickListener(::inputOnClickListener)
            findViewById<TextView>(R.id.key_4).setOnClickListener(::inputOnClickListener)
            findViewById<TextView>(R.id.key_5).setOnClickListener(::inputOnClickListener)
            findViewById<TextView>(R.id.key_6).setOnClickListener(::inputOnClickListener)
            findViewById<TextView>(R.id.key_7).setOnClickListener(::inputOnClickListener)
            findViewById<TextView>(R.id.key_8).setOnClickListener(::inputOnClickListener)
            findViewById<TextView>(R.id.key_9).setOnClickListener(::inputOnClickListener)
        }
    }

    // click listener for the input panel
    fun inputOnClickListener(v: View){
        if(inFocus.id == -1) return
        if(v !is TextView) return

        sudokuParent.findViewById<TextView>(inFocus.id).apply{
            text = v.text


            if(Sudoku.isValidInput(sudokuParent, id, v.text.toString().toInt())) setBackgroundResource(R.drawable.cell_right)
            else setBackgroundResource(R.drawable.cell_wrong)

            inFocus.previousBackground = background
        }

    }

    // fills the preliminary cells to make the game playable
    // uses random algorithm heavily
    // iterates over all the rows
    // randomly decides how many cells to fill
    // randomly decides which cells to fill
    // fills the cells with randomly generated integers
    // uses Sudoku.isValidInput to avoid repetition
    private fun generateBoard(){
        // iterating each row
        for (i in 1000..1080 step 9){
            val numOfCellsToFill = Random.nextInt(1..5)
            val cellIndicesToFill = mutableSetOf<Int>()

            // generate the cells that would be filled
            for(j in 1..numOfCellsToFill){
                while(true){
                    val currentRandomCell = Random.nextInt(0..8) + i
                    if(currentRandomCell !in cellIndicesToFill){
                        cellIndicesToFill.add(currentRandomCell)
                        break
                    }
                }
            }

            // filling the cells
            for(cell in cellIndicesToFill){
                while(true){
                    val currentRandomValue = Random.nextInt(1..9)
                    if (Sudoku.isValidInput(sudokuParent, cell, currentRandomValue)){

                        sudokuParent.findViewById<TextView>(cell).apply{
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
}