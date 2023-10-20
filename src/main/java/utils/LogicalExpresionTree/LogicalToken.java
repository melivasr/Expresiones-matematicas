package utils.LogicalExpresionTree;


public class LogicalToken {
    String data;

    LogicalTokenType type;

    public LogicalToken(String data, LogicalTokenType type) {
        this.data = data;
        this.type = type;
    }

    public void addChar(char c)
    {
        data += c;
    }

    public boolean isType(LogicalTokenType type)
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







