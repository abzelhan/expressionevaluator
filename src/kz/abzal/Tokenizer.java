import java.util.Iterator;

public class Tokenizer implements Iterator<Token> {

    private int pos = 0;

    private String input;

    public Tokenizer(String input) {
        this.input = input.trim();
    }

    @Override
    public boolean hasNext() {
        return (pos < input.length());
    }

    @Override
    public Token next() {
        Token token = new Token();

        if (pos >= input.length()) {
            return null;
        }
        char ch = input.charAt(pos);
        while (Character.isWhitespace(ch) && pos < input.length()) {
            ch = input.charAt(++pos);
        }
        token.pos = pos;

        if (Character.isDigit(ch)) {
            while (Character.isDigit(ch) && (pos < input.length())) {
                token.append(input.charAt(pos++));
                ch = pos == input.length() ? 0 : input.charAt(pos);
            }
            token.type = TokenType.LITERAL;
        } else {
            String greedyMatch = "";
            int initialPos = pos;
            ch = input.charAt(pos);
            int validOperatorPosition = -1;
            while (!Character.isDigit(ch)
                    && !Character.isWhitespace(ch)
                    && (pos < input.length())) {
                greedyMatch += ch;
                pos++;
                if (Operands.isSupported(greedyMatch.charAt(0))) {
                    validOperatorPosition = pos;
                }
                ch = pos == input.length() ? 0 : input.charAt(pos);
            }
            if (validOperatorPosition != -1) {
                token.append(input.substring(initialPos, validOperatorPosition));
                pos = validOperatorPosition;
            } else {
                token.append(greedyMatch);
            }
            token.type = TokenType.OPERATOR;
        }
        return token;
    }

}
