package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class CicloMientras (var expresionLogica: ExpresionLogica, var listaSentencia: ArrayList<Sentencia>): Sentencia() {

    override fun toString(): String {
        return "CicloMientras(expresionLogica=$expresionLogica, listaSentencias=$listaSentencia)"
    }

    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem<String>("Ciclo Mientras")

        var expresion = TreeItem<String>("Expresion Ciclo Mientras")
        expresion.children.add(expresionLogica.getArbolVisual())
        raiz.children.add(expresion)

        var raizSentencias = TreeItem("Sentencias")

        for (i in listaSentencia) {
            raizSentencias.children.add(i.getArbolVisual())
        }
        raiz.children.add(raizSentencias)
        return raiz
    }

    override fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: String) {
        for (s in listaSentencia) {
            s.llenarTablaSimbolos(tablaSimbolos, listaErrores, ambito)
        }
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: String) {
        for (s in listaSentencia) {
            s.analizarSemantica(tablaSimbolos, listaErrores, ambito)
        }
    }

}