package utils.LogicalExpresionTree;
public class MainLogicalExpressionTree {

    public static void main(String[] args) {
        String logicalExpression = "1&(0|1)&~0";
        LogicalExpressionTree expressionTree = new LogicalExpressionTree(logicalExpression);
        System.out.println("Logical Expression: " + logicalExpression);
        System.out.println("Result: " + expressionTree.evaluate());
    }
}

