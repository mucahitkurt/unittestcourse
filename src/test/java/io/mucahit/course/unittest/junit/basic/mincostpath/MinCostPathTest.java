package io.mucahit.course.unittest.junit.basic.mincostpath;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author mucahitkurt
 * @since 18.04.2018
 */
public class MinCostPathTest {

    private MinCostPath minCostPath;

    @BeforeEach
    void setUp() {
        minCostPath = new MinCostPath();
    }

    /**
     * matrix, int[][], start cell, target cell, Cell(row, column)
     * start cell or target cell is out of matrix bound
     * start cell equals target cell then return cost of start cell
     * find min cost path for one row matrix
     * find min cost path for multi row matrix
     * right path cost, down path cost, diagonal path cost
     */

    private Cell cell(int row, int column) {
        return new Cell(row, column);
    }

    @Test
    @DisplayName("Throws IllegalArgumentException when the start or target cell is out of matrix bound")
    void throwsIllegalArgumentExceptionWhenTheCellIsOutOfMatrixBound() {

        final int[][] matrix = {
                {4, 5, 6},
                {7, 8, 1}
        };

        int row = 2;
        int column = 1;

        assertAll("Start cell must be in matrix",
                () -> assertThrows(IllegalArgumentException.class, () -> minCostPath.find(matrix, cell(row, column), cell(0, 2))),
                () -> assertThrows(IllegalArgumentException.class, () -> minCostPath.find(matrix, cell(-1, 1), cell(0, 2)))
        );

        assertAll("Target cell must be in matrix",
                () -> assertThrows(IllegalArgumentException.class, () -> minCostPath.find(matrix, cell(0, 0), cell(2, 1))),
                () -> assertThrows(IllegalArgumentException.class, () -> minCostPath.find(matrix, cell(0, 0), cell(-1, 2)))
        );


    }

    @Test
    @DisplayName("Return the cost of start cell when the start cell equals to target cell")
    void returnTheCostStartCellWhenTheStartCellIsEqualToTargetCell() {

        final int[][] matrix = {
                {3, 5, 7, 9}
        };

        assertEquals(3, minCostPath.find(matrix, cell(0,0), cell(0,0)));
        assertEquals(7, minCostPath.find(matrix, cell(0,2), cell(0,2)));
    }

    @Test
    @DisplayName("Find min cost path for one row matrix")
    void findMinCostPathForOneRowMatrix() {

        final int[][] matrix = {
                {3, 5, 7, 9}
        };

        assertEquals(8, minCostPath.find(matrix, cell(0,0), cell(0,1)));
        assertEquals(15, minCostPath.find(matrix, cell(0,0), cell(0,2)));
        assertEquals(16, minCostPath.find(matrix, cell(0,2), cell(0,3)));
    }

    @Test
    @DisplayName("Find min cost path for multi row matrix")
    void findMinCostPathForMultiRowMatrix() {

        final int[][] matrix = {
                {1, 2, 3, 4},
                {1, 3, 1, 2},
                {1, 2, 4, 5}
        };

        assertEquals(4, minCostPath.find(matrix, cell(0,0), cell(1,2)));
        assertEquals(4, minCostPath.find(matrix, cell(0,0), cell(2,1)));
        assertEquals(9, minCostPath.find(matrix, cell(0,0), cell(2,3)));
    }
}
