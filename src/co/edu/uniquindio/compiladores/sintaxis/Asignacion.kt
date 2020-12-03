package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.AnalizadorSemantico
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class Asignacion(var identificador:Token , var operadorAsignacion: Token):Sentencia() {

    var expresion:Expresion? = null
    var invocacion:Invocacion?= null

    constructor(identificador: Token, operadorAsignacion: Token, expresion: Expresion):this(identificador,operadorAsignacion) {
        this.expresion = expresion
    }

    constructor(identificador: Token, operadorAsignacion: Token, invocacion: Invocacion):this(identificador,operadorAsignacion) {
        this.invocacion = invocacion
    }

    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem<String>("asignacion")
        raiz.children.add(TreeItem("Nombre Función: ${identificador.lexema}"))
        raiz.children.add(TreeItem("Operador: ${operadorAsignacion.lexema}"))
        if (expresion != null){
            raiz.children.add(expresion!!.getArbolVisual())
        }
        if (invocacion != null){
            raiz.children.add(invocacion!!.getArbolVisual())
        }
        return raiz
    }

    override fun toString(): String {
        return if (expresion!= null) {
            "Asignacion(identificador=$identificador, operadorAsignacion=$operadorAsignacion, expresion=$expresion)"
        }else{
            "Asignacion(identificador=$identificador, operadorAsignacion=$operadorAsignacion, invocacion=$invocacion)"
        }
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: String) {

        var s= tablaSimbolos.buscarSimboloValor(identificador.lexema, ambito)

        if (s==null){
            listaErrores.add ( Error("El campo ${identificador.lexema} no existe dentro del ambito $ambito", identificador.fila, identificador.columna))
        }else {
            var tipo = s.tipo
            if (expresion != null) {
                var tipoExp = expresion!!.obtenerTipo(tablaSimbolos, ambito, listaErrores)
                if ( tipoExp != tipo){
                    listaErrores.add(Error("El tipo de dato de la expresión ($tipoExp) no coincide con el tipo de dato del arreglo (${identificador.lexema}) la cual es $tipo", identificador.fila, identificador.columna))
                }
            }
            //Falta validar el tipo de retorno de la invocación
        }
    }


}