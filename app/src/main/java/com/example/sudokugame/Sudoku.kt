package com.example.sudokugame

import android.widget.LinearLayout
import android.widget.TextView

class Sudoku {
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
        fun checkNumber(sudokuParent: LinearLayout, cellId: Int, number: Int): Boolean{
            // find the row and check it
            for(i in 1000..1080 step 9){
                // find out the correct row
                if(cellId in i..(i + 8)){
                    // as the row is found
                    // check the current row
                    for(j in i..(i + 8)){
                        if(sudokuParent.findViewById<TextView>(j).text == number.toString()) return false
                    }
                    // the row does not already have the number inputted into it
                    // so break out of the loop to continue the inspection
                    // with the columns and squares
                    break
                }
            }

            // check the column
            for(i in 1000..1008){
                if(cellId in i..(i + 72) step 9){
                    for(j in i..(i + 72) step 9){
                        if(sudokuParent.findViewById<TextView>(j).text == number.toString()) return false
                    }
                    break
                }
            }

            for(square in squares){
                if(cellId in square){
                    for(cell in square){
                        if(sudokuParent.findViewById<TextView>(cell).text == number.toString()) return false
                    }
                    break
                }
            }

            return true

        }
    }
}