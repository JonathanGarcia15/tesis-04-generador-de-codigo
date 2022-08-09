package Compilador.Default.Compiler;

import java_cup.runtime.Symbol;
import org.json.JSONObject;

import java.io.StringReader;

public class Compiler {
    private JSONObject resultadoDelAnalisis = new JSONObject();

    public JSONObject getResultadoDelAnalisis() {
        return resultadoDelAnalisis;
    }

    public void ejecutarCompilador(String codigoFuente){
        this.ejecutar(codigoFuente);
    }

    private void ejecutar(String codigoFuente){

        Sintax compilador = new Sintax(new LexerCup(new StringReader(codigoFuente)));

        try {
            compilador.parse();

            resultadoDelAnalisis.put("status",true);
            resultadoDelAnalisis.put("message","No hay problemas con el Análisis Léxico y Sintáctico");
            resultadoDelAnalisis.put("result", compilador.getResultadoJSON());

            //resultadoDelAnalisis.put("result", new JSONObject());

            System.out.println(compilador.getResultadoJSON());

        } catch (Exception e) {

            String mensaje = "";
            try{
                Symbol simbolo = compilador.getSimbolo();
                resultadoDelAnalisis.put("status",false);
                mensaje += "Error de sintaxis en programa. Línea: ";
                mensaje += simbolo.right + 1 ;
                mensaje += " Columna: ";
                mensaje += (simbolo.left + 1);
                mensaje += ", Texto: ";
                mensaje += simbolo.value;
                resultadoDelAnalisis.put("message",mensaje);

            }catch(Exception error){
                resultadoDelAnalisis.put("status",false);
                mensaje = "Error de sintaxis desconocido, revise las variables que utilizó. ";
                resultadoDelAnalisis.put("message",mensaje);
            }
            //throw new RuntimeException(e);
        }
    }

}
