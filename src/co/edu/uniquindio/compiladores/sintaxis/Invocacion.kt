package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class Invocacion(var nombreFuncion: Token, var listaArgumentos: ArrayList<Argumento>?): Sentencia() {

    override fun toString(): String {
        return "Invocacion(nombreFuncion=$nombreFuncion, listaArgumentos=$listaArgumentos)"
    }

    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem<String>("Invocación")
        raiz.children.add(TreeItem("Nombre: "+nombreFuncion.lexema))
        var raizArgumentos = TreeItem("Argumentos")

        for(i in listaArgumentos!!){
            raizArgumentos.children.add(i.getArbolVisual())
        }

        raiz.children.add(raizArgumentos)
        return  raiz
    }

    override fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: String) {
    }


}