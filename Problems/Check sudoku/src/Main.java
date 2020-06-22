import java.util.HashSet;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // write your code here
        Scanner scanner = new Scanner(System.in);
        final int n = scanner.nextInt();
        final int size = n * n;
        int[][] matrix = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = scanner.nextInt();
            }
        }
        SudokuChecker sudokuChecker = new SudokuChecker(size);
        boolean isValid = true;
        outer:
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                final int number = matrix[i][j];
                final int quadrant = getQuadrant(n, i, j);
                if (!sudokuChecker.isValid(number, SudokuChecker.Type.ROW, i)) {
                    isValid = false;
                    break outer;
                }
                if (!sudokuChecker.isValid(number, SudokuChecker.Type.COLUMN, j)) {
                    isValid = false;
                    break outer;
                }
                if (!sudokuChecker.isValid(number, SudokuChecker.Type.QUADRANT, quadrant)) {
                    isValid = false;
                    break outer;
                }
            }
        }
        if (isValid) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }
    }

    private static int getQuadrant(int n, int row, int col) {
        return (row / n) * n + (col / n);
    }

    private static class SudokuChecker {
        private final Checker[] rowChecker;
        private final Checker[] columnChecker;
        private final Checker[] quadrantChecker;

        private SudokuChecker(int size) {
            quadrantChecker = initialize(size);
            columnChecker = initialize(size);
            rowChecker = initialize(size);
        }

        private Checker[] initialize(final int size) {
            Checker[] checker = new Checker[size];
            for (int i = 0; i < size; i++) {
                checker[i] = new Checker(size);
            }
            return checker;
        }

        private boolean isValid(int number, Type type, int typeId) {
            switch (type) {
                case ROW:
                    return rowChecker[typeId].isValid(number);
                case COLUMN:
                    return columnChecker[typeId].isValid(number);
                case QUADRANT:
                    return quadrantChecker[typeId].isValid(number);
                default:
                    return false;
            }
        }

        private enum Type {
            ROW, COLUMN, QUADRANT;
        }

        private static class Checker {
            private final int size;
            private final HashSet<Integer> set = new HashSet<>();

            private Checker(int size) {
                this.size = size;
            }

            private boolean isValid(final int number) {
                if (number < 1 || number > size) {
                    return false;
                }
                if (set.contains(number)) {
                    return false;
                }
                set.add(number);
                return true;
            }
        }
    }
}