package com.example.sudokugame

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import org.w3c.dom.Text
import kotlin.random.Random
import kotlin.random.nextInt

import android.view.ViewGroup.LayoutParams as VLParams

const val TAG = "SUDOKUFRAGMENT"

class SudokuFragment : Fragment() {

    private var manualId = 1000
    private var inFocus = -1
    private lateinit var sudokuParent: LinearLayout

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val parent = inflater.inflate(R.layout.fragment_sudoku, container, false)
        sudokuParent = parent.findViewById(R.id.sudoku_wrapper)

        generateBox()
        generateBoard()

        return parent
    }

    private val generateBox = {for(i in 0..2) sudokuParent.addView(generateSquareRow())}

    // returns a row of a square
    // i.e three cells, properly marked with ids, horizontally aligned
    // and wrapped in a LinearLayout
    private fun generateCellRow(): LinearLayout {
        val linearLayout = LinearLayout(context)
        linearLayout.layoutParams = VLParams(VLParams.WRAP_CONTENT, VLParams.WRAP_CONTENT)
        linearLayout.orientation = LinearLayout.HORIZONTAL

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
        parent.layoutParams = VLParams(VLParams.WRAP_CONTENT, VLParams.WRAP_CONTENT)
        parent.orientation = LinearLayout.HORIZONTAL

        // the list would hold nine LinearLayout containing three cells each
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

    private fun onFocus(v: TextView){
        inFocus = v.id
        v.setBackgroundResource(R.drawable.cell_onclick)
    }

    private val deFocus = {if(inFocus != -1) {sudokuParent.findViewById<TextView>(inFocus).setBackgroundResource(R.drawable.cell_border)}}

    private val cellOnClickListener = {v: View ->
        deFocus()
        if(v is TextView) onFocus(v)
    }

    private fun generateBoard(){
        for (i in 1000..1080 step 9){
            val numOfCellsToFill = Random.nextInt(1..5)
            val cellIndicesToFill = mutableSetOf<Int>()
            val alreadyAddedValues = mutableSetOf<Int>()

            for(j in 0 until numOfCellsToFill){
                while(true){
                    val currentRandomCell = Random.nextInt(0..8) + i
                    if(currentRandomCell in cellIndicesToFill) continue
                    else{
                        cellIndicesToFill.add(currentRandomCell)
                        break
                    }
                }
            }

            for(cell in cellIndicesToFill){
                while(true){
                    val currentRandomValue = Random.nextInt(0..9)
                    if (currentRandomValue in alreadyAddedValues) continue
                    else{
                        sudokuParent.findViewById<TextView>(cell).text = currentRandomValue.toString()
                        alreadyAddedValues.add(currentRandomValue)
                        break
                    }
                }
            }
        }
    }
}