package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class Invocacion(var nombreFuncion: Token, var listaArgumentos: ArrayList<Argumento>?): Sentencia() {

    override fun toString(): String {
        return "Invocacion(nombreFuncion=$nombreFuncion, listaArgumentos=$listaArgumentos)"
    }

    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem<String>("Invocación")
        raiz.children.add(TreeItem(nombreFuncion.lexema))
        var raizArgumentos = TreeItem("Argumentos")

        for(i in listaArgumentos!!){
            raizArgumentos.children.add(i.getArbolVisual())
        }

        raiz.children.add(raizArgumentos)
        return  raiz
    }


}