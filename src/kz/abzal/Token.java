public class Token {

    public String value = "";
    public TokenType type;
    public int pos;

    public void append(char c) {
        value += c;
    }

    public void append(String s) {
        value += s;
    }

    @Override
    public String toString() {
        return value;
    }

}
