package com.example.sudokugame

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.DimenRes
import androidx.core.view.marginStart
import androidx.core.view.marginTop
import android.view.ViewGroup.LayoutParams as GroupLayoutParams
import java.util.*


class SudokuFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sudoku, container, false)
    }

    private fun getRow(): LinearLayout {
        val layout = LinearLayout(context)
        layout.layoutParams = GroupLayoutParams(GroupLayoutParams.WRAP_CONTENT, GroupLayoutParams.WRAP_CONTENT)
        layout.orientation = LinearLayout.HORIZONTAL


    }

}