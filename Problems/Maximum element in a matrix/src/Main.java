import java.util.Scanner;

class Main {
    private static final Scanner logger = new Scanner(System.in);

    public static void main(String[] args) {
        final String sizeStr = logger.nextLine();
        Scanner stringScanner = new Scanner(sizeStr);
        final int n = stringScanner.nextInt();
        final int m = stringScanner.nextInt();
        int[][] twoDimArray = initializeArray(n, m);

        int nIndex = 0;
        int mIndex = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (twoDimArray[i][j] > twoDimArray[nIndex][mIndex]) {
                    nIndex = i;
                    mIndex = j;
                }
            }
        }
        System.out.println(nIndex + " " + mIndex);
    }

    private static int[][] initializeArray(final int n, final int m) {
        int[][] twoDimArray = new int[n][m];
        for (int i = 0; i < n; i++) {
            final String elements = logger.nextLine();
            final Scanner stringScanner = new Scanner(elements);
            for (int j = 0; j < m; j++) {
                twoDimArray[i][j] = stringScanner.nextInt();
            }
        }
        return twoDimArray;
    }
}