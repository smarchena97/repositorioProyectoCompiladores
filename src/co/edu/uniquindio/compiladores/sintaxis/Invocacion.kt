package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class Invocacion(var nombreFuncion: Token, var listaArgumentos: ArrayList<Expresion>?): Sentencia() {

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

    fun obtenerTiposArgumentos (tablaSimbolos: TablaSimbolos, ambito: String):ArrayList<String>{
        var listaArgs = ArrayList<String>()
        for (a in listaArgumentos!!){
            listaArgs.add( a.obtenerTipo(tablaSimbolos, ambito))
        }
        return listaArgs
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: String) {
        var listaTipoArgs= obtenerTiposArgumentos(tablaSimbolos, ambito)
        var s= tablaSimbolos.buscarSimboloFuncion(nombreFuncion.lexema, listaTipoArgs)
        if (s==null){
            listaErrores.add( Error("la función ${nombreFuncion.lexema} $listaTipoArgs no existe", nombreFuncion.fila, nombreFuncion.columna))
        }
    }

    override fun getJavaCode(): String {
        var codigo = nombreFuncion.lexema+"("
        for(a in listaArgumentos!!){
            codigo += a.getJavaCode()+","
        }
        codigo = codigo.substring(0,codigo.length-1)
        codigo += ");"
        return codigo
    }


}