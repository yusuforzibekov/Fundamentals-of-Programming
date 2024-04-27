package com.epam.rd.autotasks.matrices;

import java.util.Arrays;

public class TransposeMatrix {
    public static int[][] transpose(int[][] matrix) {
        int[][] a = new int[matrix[0].length][matrix.length];

        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                a[i][j] = matrix[j][i];
            }
        }
        return a;
    }

    public static void main(String[] args) {
        int[][] matrix = {
                { 1, 2 },
                { 7, -13 } };

        int[][] result = transpose(matrix);
        System.out.println(Arrays.deepToString(result).replace("],", "]\n"));
    }

}
