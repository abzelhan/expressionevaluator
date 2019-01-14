import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Enter the expression to evaluate (example: \"5 * 4 + 12\"):");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        ExpressionEvaluator evaluator = new ExpressionEvaluator();
        Integer integer = evaluator.eval(input);
        System.out.println("The answer is: " + integer);
    }

}
