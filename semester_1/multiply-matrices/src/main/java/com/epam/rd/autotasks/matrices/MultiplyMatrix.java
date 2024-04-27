package com.epam.rd.autotasks.matrices;
import java.util.Arrays;

/**
 * This class provides a method to multiply two matrices.
 */
public class MultiplyMatrix {
    /**
     * Multiplies two matrices and returns the result.
     *
     * @param matrix1 the first matrix
     * @param matrix2 the second matrix
     * @return the result of multiplying the two matrices
     */
    public static int[][] multiply(int[][] matrix1, int[][] matrix2) {
        int[][] result = new int[matrix1.length][matrix2[0].length];

        // Iterate over each row of the first matrix
        for (int i = 0; i < result.length; i++) {
            // Iterate over each column of the second matrix
            for (int j = 0; j < result[i].length; j++) {
                // Iterate over each element of the current row of the first matrix
                // and the current column of the second matrix
                for (int k = 0; k < matrix2.length; k++) {
                    // Multiply the corresponding elements and accumulate the result
                    result[i][j] += matrix1[i][k] * matrix2[k][j];
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {

        System.out.println("Test your code here!\n");

        // Define two matrices to multiply
        int[][] a = {
                {0, 12345},
                {4509, 0},
                {3, 567} };

        int[][] b = {
                {653, 0, 25353},
                {0, 61, 6} };

        // Multiply the matrices and print the result
        int[][] result = multiply(a, b);
        System.out.println(Arrays.deepToString(result).replace("],", "]\n"));
    }
}
