package BasicSyntaxConditionalStatementsAndLoops.Labs;

import java.util.Scanner;

public class EvenNumber {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int num = 1;

        while (num % 2 != 0) {
            num = Integer.parseInt(scan.nextLine());
            if (num % 2 != 0) {
                System.out.println("Please write an even number.");
            } else {
                System.out.println("The number is: " + Math.abs(num));
            }
        }
    }
}
