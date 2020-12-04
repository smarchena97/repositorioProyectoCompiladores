package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class Lectura(var cadena:Token):Sentencia() {

    override fun toString(): String {
        return "Lectura(cadena='$cadena')"
    }

    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem<String>("Lectura")
        raiz.children.add(TreeItem("Cadena:${cadena.lexema}"))
        return raiz
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: String) {
            var s= tablaSimbolos.buscarSimboloValor(cadena.lexema, ambito)
            if (s==null) {
                listaErrores.add(
                        Error(" El campo (${cadena.lexema}) no existe dentro del ambito ($ambito)",
                        cadena.fila, cadena.columna)
                )
            }
    }
}