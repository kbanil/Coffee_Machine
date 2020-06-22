import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        final int n = scanner.nextInt();
        final int m = scanner.nextInt();
        int[][] matrix = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                matrix[i][j] = scanner.nextInt();
            }
        }
        final int srcCol = scanner.nextInt();
        final int destCol = scanner.nextInt();
        for (int i = 0; i < n; i++) {
            swapColumn(matrix, i, srcCol, destCol);
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                sb.append(matrix[i][j]).append(" ");
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }

    private static void swapColumn(final int[][] matrix, final int row, final int src, final int dest) {
        int s = matrix[row][src];
        matrix[row][src] = matrix[row][dest];
        matrix[row][dest] = s;
    }
}