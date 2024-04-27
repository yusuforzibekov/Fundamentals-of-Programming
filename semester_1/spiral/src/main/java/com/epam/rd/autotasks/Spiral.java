package com.epam.rd.autotasks;

class Spiral {
    static int[][] spiral(int rows, int columns) {
        int[][] array = new int[rows][columns];
        int number = 1;
        int left = 0;
        int right = columns - 1;
        int top = 0;
        int bottom = rows - 1;

        while (left <= right && top <= bottom) {
            // Fill top row
            for (int i = left; i <= right; i++) {
                array[top][i] = number++;
            }
            top++;

            // Fill right column
            for (int i = top; i <= bottom; i++) {
                array[i][right] = number++;
            }
            right--;

            // Fill bottom row
            if (top <= bottom) {
                for (int i = right; i >= left; i--) {
                    array[bottom][i] = number++;
                }
                bottom--;
            }

            // Fill left column
            if (left <= right) {
                for (int i = bottom; i >= top; i--) {
                    array[i][left] = number++;
                }
                left++;
            }
        }
        return array;
    }
}
