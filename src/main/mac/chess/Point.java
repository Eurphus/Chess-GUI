package mac.chess;

import java.util.Arrays;

public class Point {
    final int row;
    final int column;

    public Point(int inputRow, int inputColumn) {
        row = inputRow;
        column = inputColumn;
    }

    @Override
    public boolean equals(Object object) {
        boolean isEqual= false;

        if (object instanceof Point) {
            isEqual = (this.row == ((Point) object).row) && (this.column == ((Point) object).column);
        }
        return isEqual;
    }

    @Override
    public String toString() {
        return Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H").get(column-1) + row;
    }
}
