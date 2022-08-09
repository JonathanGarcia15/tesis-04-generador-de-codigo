package InterfazDeUsuario;

import Compilador.CompiladorLenguajeUsuario;
import Compilador.EjecutarAnalizadores;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Fenix extends JFrame{
    private JPanel jpnInterfazDeUsuario;
    private JLabel lblEditorCode;
    private JLabel lblLog;
    private JLabel lblResultados;
    private JTextArea txtCodigoFuente;
    private JTextArea txtResultados;
    private JScrollPane jscpResultados;
    private JScrollPane jscpCodigoFuente;
    private JScrollPane scrResultados;

    //Declaración de MenuBar
    private JMenuBar menuBar;

    //Elementos principales de MenuBar
    private JMenu archivo;
    private JMenu codigo;
    private JMenu editar;
    private JMenu compilador;
    private JMenu acercaDe;

    //Submenus de MenuBar de Archivo
    private JMenuItem abrirMapa;
    private JMenuItem abrirCodigo;

    //Submenus de MenuBar de codigo
    private JMenuItem compilar;
    private JMenuItem ejecutar;
    private JMenuItem compilarYEjecutar;

    //Submenus de MenuBar de compilador
    private JMenuItem recompilarLexico;
    private JMenuItem recompilarSintactico;
    private JMenuItem recompilarTodo;


    private final CompiladorLenguajeUsuario generadorDeCompilador = new CompiladorLenguajeUsuario();

    CompiladorLenguajeUsuario CompiladorLexico;
    EjecutarAnalizadores Analizadores;


    public Fenix(){
        super("{Fenix}");

        CompiladorLexico = new CompiladorLenguajeUsuario();
        Analizadores = new EjecutarAnalizadores();

        //Inicializando los objetos creados
        menuBar = new JMenuBar();
        setContentPane(jpnInterfazDeUsuario);

        archivo = new JMenu("{ARCHIVO}");
        codigo = new JMenu("{CODIGO}");
        editar = new JMenu("{EDITAR}");
        compilador = new JMenu("{COMPILADOR}");
        acercaDe = new JMenu("{ACERCA DE}");

        abrirMapa = new JMenuItem("{ABRIR MAPA}");
        abrirCodigo = new JMenuItem("{ABRIR CODIGO}");

        compilar = new JMenuItem("{COMPILAR}");
        ejecutar = new JMenuItem("{EJECUTAR}");
        compilarYEjecutar = new JMenuItem("{COMPILAR Y EJECUTAR}");

        recompilarLexico = new JMenuItem("{RECOMPILAR ANALIZADOR LÉXICO}");
        recompilarSintactico = new JMenuItem("{RECOMPILAR ANALIZADOR SINTÁCTICO}");
        recompilarTodo = new JMenuItem("{RECOMPILAR ANALIZADORES}");

        //Construyendo el MenuBar
        menuBar.add(archivo);
        menuBar.add(codigo);
        menuBar.add(editar);
        menuBar.add(compilador);
        menuBar.add(acercaDe);

        archivo.add(abrirMapa);
        archivo.add(abrirCodigo);

        codigo.add(compilar);
        codigo.add(ejecutar);
        codigo.add(compilarYEjecutar);

        compilador.add(recompilarLexico);
        compilador.add(recompilarSintactico);
        compilador.add(recompilarTodo);

        //Mostrando el MenuBar
        this.setJMenuBar(menuBar);

        //Este método se ejecuta  cuando se requiere la generación de el Analizador Léxico:
        recompilarLexico.addActionListener(e -> {
            //Generador del Analizador Lexico
            generadorDeCompilador.GenerarAnalizadorLexico();
        });

        //Este método se ejecuta cuando se requiere la generación de los Analizadores Léxico y Sintáctico
        recompilarSintactico.addActionListener(e -> {
            //Generando Solamente Léxico y Sintáctico
            generadorDeCompilador.GenerarAnalizadoresLexicoYSintactico();
        });

        //Este código se ejecuta cuando se ejecutan los analizadores léxico y sintáctico
        ejecutar.addActionListener(e -> {
            JSONObject resultado;
            resultado = Analizadores.EjecutarCompilador(leerCodigoFuenteDelUsuario(),"");
            StringBuilder tmp = new StringBuilder();
            //Analizando los resultados:
            if(resultado.getBoolean("status")){
                tmp.append("\n").append("JSON recibido: ").append(resultado);
                tmp.append("\n").append("JSON del Programa: \n").append(resultado.getJSONObject("result"));
                txtResultados.setText(resultado.getString("message") + "\n" + tmp);
                txtResultados.setForeground(new Color(25, 111, 61));
            }else{
                //Errores en el resultado
                tmp.append("\n").append("JSON recibido: ").append(resultado);
                txtResultados.setText(resultado.getString("message")+tmp);
                txtResultados.setForeground(Color.red);
            }

        });

        //Este método se ejecuta cuando solo se requiere únicamente el Análisis Léxico del código fuente:
        compilar.addActionListener(e -> {
            //En este objeto se guardará el resultado del Analisis Léxico:
            JSONObject resultado;
            //Ejecutando el Analisis Léxico:
            resultado = Analizadores.AnalizarSoloLexico(leerCodigoFuenteDelUsuario(),"");

            //Declaración de una cadena temporal.
            StringBuilder tmp = new StringBuilder();

            //Analizando los resultados:
            if(resultado.getBoolean("status")){
                //Enlistando los tokens recibidos
                JSONObject objetoTemporal;
                for(int i=0;i<resultado.getJSONArray("tokens").length();i++){
                    objetoTemporal = (JSONObject) resultado.getJSONArray("tokens").get(i);
                    if(objetoTemporal.get("token") == "SALTOLINEA"){
                        tmp.append("\n");
                    }else{
                        tmp.append("\"").append(objetoTemporal.get("token")).append("\"");
                        tmp.append("(").append(objetoTemporal.get("lexema")).append(") ");
                    }
                }
                tmp.append("\n").append("JSON recibido: ").append(resultado);
                txtResultados.setText(resultado.getString("message") + "\n" + tmp);
                txtResultados.setForeground(new Color(25, 111, 61));
            }else{
                //Errores en el resultado
                tmp.append("\n").append("JSON recibido: ").append(resultado);
                txtResultados.setText(resultado.getString("message")+tmp);
                txtResultados.setForeground(Color.red);
            }

        });

    }

    private String leerCodigoFuenteDelUsuario(){
        return txtCodigoFuente.getText();
    }
}
