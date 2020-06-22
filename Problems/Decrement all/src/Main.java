import java.util.Scanner;

import static java.util.stream.Collectors.joining;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // put your code here
        final String input = scanner.nextLine();
        final String output = new Scanner(input).tokens().mapToInt(Integer::parseInt).map(i -> --i)
                .mapToObj(Integer::toString)
                .collect(joining(" "));
        System.out.println(output);
    }
}