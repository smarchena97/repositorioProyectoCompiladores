package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class Arreglo (var nombreArreglo: Token, var tipoDato:Token, var listaDatos: ArrayList<Argumento>? ):Sentencia(){

    override fun toString(): String {
        return "Arreglo(nombreArreglo=$nombreArreglo, tipoDato=$tipoDato, listaDatos=$listaDatos)"
    }

    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem<String>("Nombre Arreglo")
        raiz.children.add(TreeItem(nombreArreglo.lexema))
        var raizTipoDato= TreeItem("Tipo Dato")
        raizTipoDato.children.add(TreeItem(tipoDato.lexema))
        raiz.children.add(raizTipoDato)
        var raizListaDatos= TreeItem("Lista Datos")

        for(i in listaDatos!!){
            raizListaDatos.children.add(i.getArbolVisual())
        }
        raiz.children.add(raizListaDatos)
        return raiz
    }

    override fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: String) {
        tablaSimbolos.guardarSimboloValor(nombreArreglo.lexema, tipoDato.lexema, true, ambito, nombreArreglo.fila, nombreArreglo.columna)
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: String) {

    }
}