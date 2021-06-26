package com.example.sudokugame

import android.graphics.drawable.Drawable
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import java.lang.IllegalStateException
import java.lang.NullPointerException

class Sudoku (private val sudokuParent: ViewGroup){
    private var manualId = 1000
    private val inFocus = object {
        var id: Int? = null
        var background: Drawable? = null
    }
    private val initialisedCells = mutableSetOf<Int>()


    private fun gainFocus(v: TextView){
        if (v.id in initialisedCells) return
        inFocus.apply{
            id = v.id
            background = v.background
        }
        v.setBackgroundResource(R.drawable.cell_infocus)
    }

    fun loseFocus() {
        inFocus.apply {
            try {
                sudokuParent.findViewById<TextView>(id!!).background = background
            }
            catch(e: NullPointerException){
                throw IllegalStateException("no cell is in focus", NullPointerException())
            }
        }
    }

    companion object {
        private val squares = setOf(
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
        fun isValidInput(sudokuParent: LinearLayout, cellId: Int, input: Int): Boolean{
            // iterate the rows
            for(i in 1000..1080 step 9){
                // whether the cellId in the current row
                if(cellId in i..(i + 8)){
                    // as the row is found
                    // inspect the cells of the row
                    for(j in i..(i + 8)){
                        // skip the proposed cellId
                        if (j == cellId) continue
                        if(sudokuParent.findViewById<TextView>(j).text == input.toString()) return false
                    }
                    // the proposed input does not have any row-wise repetition
                    // so break out from the loop to continue the inspection
                    // with the columns and squares
                    break
                }
            }

            // iterate the columns
            for(i in 1000..1008){
                // whether the cellId is in the current column
                if(cellId in i..(i + 72) step 9){
                    // as the column is found
                    // inspect the cells of the entire column
                    for(j in i..(i + 72) step 9){
                        // skip the proposed cellId
                        if(j == cellId) continue
                        if(sudokuParent.findViewById<TextView>(j).text == input.toString()) return false
                    }
                    // the proposed input does not have any column-wise repetition
                    // so break out from the loop to continue the inspection
                    // with the squares
                    break
                }
            }

            // iterate the squares
            for(square in squares){
                // whether the cellId is in the current square
                if(cellId in square){
                    // inspect all the cells of the square
                    for(cell in square){
                        // skip the proposed cellId
                        if(cell == cellId) continue
                        if(sudokuParent.findViewById<TextView>(cell).text == input.toString()) return false
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
}