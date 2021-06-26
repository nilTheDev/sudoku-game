package com.example.sudokugame

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

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
        parent.findViewById<Button>(R.id.re_generate_button).setOnClickListener(sudoku.generateFreshBox)
        parent.findViewById<Button>(R.id.solve_button).setOnClickListener(sudoku.solveBoard)
        setInputOnClickListeners(parent)
        return parent
    }



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

}