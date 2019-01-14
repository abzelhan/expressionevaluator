public enum Operands {

    PLUS(1, '+') {
        @Override
        int calculate(int left, int right) {
            return left + right;
        }
    },
    MULTIPLY(2, '*') {
        @Override
        int calculate(int left, int right) {
            return left * right;
        }
    };

    Operands(Integer priority, Character symbol) {
        this.priority = priority;
        this.symbol = symbol;
    }

    Integer priority;
    Character symbol;

    abstract int calculate(int left, int right);

    public static boolean isSupported(Character character) {
        Operands[] operands = values();
        for (Operands operand : operands) {
            if (operand.symbol.equals(character)) {
                return true;
            }
        }
        return false;
    }

    public static Operands getBySymbolStr(String symbol) {
        if (symbol.length() == 1) {
            return getBySymbol(symbol.charAt(0));
        }
        return null;
    }

    public static Operands getBySymbol(Character symbol) {
        Operands[] operands = values();
        for (Operands operand : operands) {
            if (operand.symbol.equals(symbol)) {
                return operand;
            }
        }
        return null;
    }

}
