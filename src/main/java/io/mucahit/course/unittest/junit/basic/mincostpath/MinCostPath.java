package io.mucahit.course.unittest.junit.basic.mincostpath;

/**
 * @author mucahitkurt
 * @since 18.04.2018
 */
public class MinCostPath {
    public int find(int[][] matrix, Cell start, Cell target) {

        validateIfTheCellIsOutOfMatrixBound(matrix, start);
        validateIfTheCellIsOutOfMatrixBound(matrix, target);

        if (start.equals(target)) {
            return matrix[start.row()][start.column()];
        }

        return findMinCostPath(matrix, start, target);
    }

    private int findMinCostPath(int[][] matrix, Cell start, Cell target) {

        if (start.row() > target.row() || start.column() > target.column()) {
            return Integer.MAX_VALUE;
        }

        if (start.equals(target)) {
            return matrix[start.row()][start.column()];
        }

        int rightPathCost = findMinCostPath(matrix, new Cell(start.row(), start.column() + 1), target);
        int downPathCost = findMinCostPath(matrix, new Cell(start.row() + 1, start.column()), target);
        int diagonalPathCost = findMinCostPath(matrix, new Cell(start.row() + 1, start.column() + 1), target);

        final int min = Math.min(rightPathCost, Math.min(downPathCost, diagonalPathCost));

        return min + matrix[start.row()][start.column()];

    }

    private void validateIfTheCellIsOutOfMatrixBound(int[][] matrix, Cell cell) {
        if (cell.row() >= matrix.length || cell.row() < 0) {
            throw new IllegalArgumentException();
        }

        if (cell.column() >= matrix[0].length || cell.column() < 0) {
            throw new IllegalArgumentException();
        }
    }
}
