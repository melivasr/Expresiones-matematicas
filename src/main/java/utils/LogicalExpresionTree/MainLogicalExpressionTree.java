package utils.LogicalExpresionTree;
public class MainLogicalExpressionTree {
    public static void main(String[] args) {
        String expression = "(1 & 0) | ~(1 ^ 0) | ~~0";
        LogicalExpressionTree expressionTree = new LogicalExpressionTree(expression);

        System.out.print("Expresión lógica: ");
        expressionTree.printExpression();
        System.out.println();

        boolean result = expressionTree.evaluate();

        System.out.println("Resultado: " + result);
    }

}
