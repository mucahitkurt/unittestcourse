package io.mucahit.course.unittest.junit.basic.mincostpath;

/**
 * @author mucahitkurt
 * @since 18.04.2018
 */
public class Cell {

    private final int row;
    private final int column;

    public Cell(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int row() {
        return this.row;
    }

    public int column() {
        return column;
    }

    @Override
    public boolean equals(Object obj) {
        if (!Cell.class.isInstance(obj)) {
            return false;
        }

        final Cell cell2 = (Cell) obj;

        return cell2.row() == this.row() && cell2.column() == this.column();
    }
}
