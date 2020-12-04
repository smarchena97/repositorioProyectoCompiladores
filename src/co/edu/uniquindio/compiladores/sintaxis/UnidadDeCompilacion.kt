package co.edu.uniquindio.compiladores.sintaxis
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import co.edu.uniquindio.compiladores.lexico.Error
import javafx.scene.control.TreeItem

class UnidadDeCompilacion(var listaFunciones: ArrayList<Funcion>) {

    override fun toString(): String {
        return "UnidadDeCompilacion(listaFunciones=$listaFunciones)"
    }

    fun getArbolVisual():TreeItem<String>{

        var raiz = TreeItem<String>("Unidad de compilacion")

        for( f in listaFunciones){
            raiz.children.add(f.getArbolVisual())
        }
        return raiz
    }
    fun llenarTablaSimbolos(tablaSimbolos:TablaSimbolos, listaErrores:ArrayList<Error>){
        for(f in listaFunciones){
            f.llenarTablaSimbolos(tablaSimbolos,listaErrores,"unidadCompilacion")
        }
    }

    fun analizarSemantica(tablaSimbolos:TablaSimbolos, listaErrores:ArrayList<Error>){
        for(f in listaFunciones){
            f.analizarSemantica(tablaSimbolos,listaErrores)
        }
    }
}