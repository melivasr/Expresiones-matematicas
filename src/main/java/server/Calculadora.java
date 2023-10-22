package server;

import org.json.JSONObject;
import utils.ExpresionTree.ExpressionTree;
import server.modelo.RegistroOperaciones;
import utils.LogicalExpresionTree.LogicalExpressionTree;


/**
 * Clase que representa el juego y se encarga de gestionar las conexiones de los clientes, el estado del juego y la lógica del juego.
 */
public class Calculadora {

    public static String EfectuarOperacion(JSONObject jsonObject) {
        System.out.println(jsonObject.toString());
        switch (jsonObject.getString("comando")) {
            case "evaluarExpression":
                if (jsonObject.has("expression")) {
                    String expression = jsonObject.getString("expression");
                    String resultado = ((Double)evaluarExpression(expression)).toString();
                    RegistroOperaciones.registrarOperacion(jsonObject.getString("expression"), resultado);
                    System.out.println("Resultado: " + resultado);
                    return resultado;
                }
                break;
            case "evaluarExpressionLogica":
                if (jsonObject.has("expression")) {
                    String expression = jsonObject.getString("expression");
                    String resultado = ((Boolean)evaluarExpressionLogical(expression)).toString();
                    RegistroOperaciones.registrarOperacion(jsonObject.getString("expression"), resultado);
                    System.out.println("Resultado: " + resultado);
                    return resultado;
                }
                break;
            default:
                System.err.println("Comando no encontrado");
        }
        return "CALCULATION ERROR";
    }

    public static double evaluarExpression(String nombre) {
        return new ExpressionTree(nombre).evaluate();
    }

    public static boolean evaluarExpressionLogical(String nombre) {
        return new LogicalExpressionTree(nombre).evaluate();
    }


}
