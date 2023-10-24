package server;

import org.json.JSONObject;
import utils.ExpresionTree.ExpressionTree;
import server.modelo.RegistroOperaciones;
import utils.LogicalExpresionTree.LogicalExpressionTree;


/**
 * La clase Calculadora se encarga de evaluar las operaciones matemáticas y lógicas que se le pasan en formato JSON.
 */
public class Calculadora {
    /**
     * Método que realiza la operación especificada en el JSON recibido.
     * @param jsonObject JSON que contiene la operación a realizar.
     * @return Resultado de la operación.
     */

    public static String EfectuarOperacion(JSONObject jsonObject) {
        System.out.println(jsonObject.toString());
        switch (jsonObject.getString("comando")) {
            case "evaluarExpression":
                if (jsonObject.has("expression") && jsonObject.has("nombre")) {
                    String expression = jsonObject.getString("expression");
                    String nombre = jsonObject.getString("nombre");
                    String resultado = ((Double)evaluarExpression(expression)).toString();
                    RegistroOperaciones.registrarOperacion(jsonObject.getString("nombre"), jsonObject.getString("expression"), resultado);
                    System.out.println("Resultado: " + resultado);
                    return resultado;
                }
                break;
            case "evaluarExpressionLogica":
                if (jsonObject.has("expression") && jsonObject.has("nombre")) {
                    String expression = jsonObject.getString("expression");
                    String resultado = ((Boolean)evaluarExpressionLogical(expression)).toString();
                    RegistroOperaciones.registrarOperacion(jsonObject.getString("nombre"), jsonObject.getString("expression"), resultado);
                    System.out.println("Resultado: " + resultado);
                    return resultado;
                }
                break;
            case "tableRequest":
                if (jsonObject.has("nombre")) {
                    String nombre = jsonObject.getString("nombre");
                    return RegistroOperaciones.leerOperaciones(nombre);
                }
                break; //tableRequest
            default:
                System.err.println("Comando no encontrado");
        }
        return "Calculation Error";
    }


    /**
     * Método que evalúa una expresión matemática.
     * @param nombre Expresión matemática a evaluar.
     * @return Resultado de la evaluación de la expresión.
     */
    public static double evaluarExpression(String nombre) {
        return new ExpressionTree(nombre).evaluate();
    }

    /**
     * Método que evalúa una expresión lógica.
     * @param nombre Expresión lógica a evaluar.
     * @return Resultado de la evaluación de la expresión.
     */
    public static boolean evaluarExpressionLogical(String nombre) {
        return new LogicalExpressionTree(nombre).evaluate();
    }


}
