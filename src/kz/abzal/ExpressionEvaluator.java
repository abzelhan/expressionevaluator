package kz.abzal;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

public class ExpressionEvaluator {

    private List<Token> shuntingYard(String expression) {
        List<Token> outputQueue = new ArrayList<>();
        Stack<Token> stack = new Stack<>();
        Tokenizer tokenizer = new Tokenizer(expression);
        while (tokenizer.hasNext()) {
            Token token = tokenizer.next();
            switch (token.type) {
                case LITERAL:
                    outputQueue.add(token);
                    break;
                case OPERATOR: {
                    Operands operand = Operands.getBySymbol(token.value.charAt(0));
                    sortByPriority(outputQueue, stack, operand);
                    stack.push(token);
                    break;
                }
            }
        }

        while (!stack.isEmpty()) {
            Token element = stack.pop();
            outputQueue.add(element);
        }
        return outputQueue;
    }

    private void sortByPriority(
            List<Token> outputQueue,
            Stack<Token> stack,
            Operands operand) {
        Token nextToken = stack.isEmpty() ? null : stack.peek();

        while (nextToken != null && nextToken.type == TokenType.OPERATOR
                &&
                (operand.priority <= Operands.getBySymbolStr(nextToken.value).priority)) {
            outputQueue.add(stack.pop());
            nextToken = stack.isEmpty() ? null : stack.peek();
        }
    }

    private void validate(String expression) {
        for (int i = 0; i < expression.length(); i++) {
            char character = expression.charAt(i);
            if (!Character.isDigit(character) && !Operands.isSupported(character) && !Character.isWhitespace(character)) {
                throw new UnsupportedCharacterException("The character \'" + character + "\' is not supported!");
            }
        }
    }

    public Integer eval(String expression) throws UnsupportedCharacterException {
        validate(expression);
        Stack<Integer> stack = new Stack<>();
        for (final Token token : shuntingYard(expression)) {
            switch (token.type) {
                case OPERATOR:
                    final Integer first = stack.pop();
                    final Integer second;
                    try {
                        second = stack.pop();
                    } catch (EmptyStackException e) {
                        throw new MissingDigitException("Missing digit after operand \'" + token.value + "\'");
                    }
                    Integer result = Operands.getBySymbol(token.value.charAt(0)).calculate(second, first);
                    stack.push(result);
                    break;
                case LITERAL:
                    stack.push(Integer.valueOf(token.value));
                    break;
            }
        }
        Integer result = stack.pop();
        return result;
    }

}
