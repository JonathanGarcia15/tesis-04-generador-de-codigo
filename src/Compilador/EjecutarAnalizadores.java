package Compilador;

import Compilador.Default.AnalizadorLexico.AnalizarLexicoDefault;
import Compilador.Default.Compiler.Compiler;
import org.json.JSONObject;

public class EjecutarAnalizadores {

    public JSONObject AnalizarSoloLexico(String codigoFuente, String lenguaje){
        return this.ejecutarAnalizadorLexicoDefault(codigoFuente);
    }

    public JSONObject EjecutarCompilador(String codigoFuente, String lenguaje){
        return this.ejecutarCompiladorDefault(codigoFuente);
    }

    private JSONObject ejecutarAnalizadorLexicoDefault(String codigoFuente){
        //Analisis únicamente del Analizador Léxico por Default
        AnalizarLexicoDefault LexicoLenguajeNatural = new AnalizarLexicoDefault();
        LexicoLenguajeNatural.EjecutarAnalisisLexico(codigoFuente);
        return (LexicoLenguajeNatural.getResultadoDelAnalisis());
    }

    private JSONObject ejecutarCompiladorDefault(String codigoFuente){
        Compiler compilador = new Compiler();
        compilador.ejecutarCompilador(codigoFuente);
        return compilador.getResultadoDelAnalisis();
    }

}
