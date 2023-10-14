package utils.ExpresionTree;

public class Token {
    String data;

    TokenType type;

    public Token(String data, TokenType type) {
        this.data = data;
        this.type = type;
    }

    public void addChar(char c)
    {
        data += c;
    }

    public boolean isType(TokenType type)
    {
        return this.type == type;
    }

    public String getData() {
        return data;
    }

    @Override
    public String toString() {
        return data ;
    }
}

