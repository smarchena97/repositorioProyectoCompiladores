package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class Funcion(var nombreFuncion:Token, var tipoRetorno:Token, var listaParametros: ArrayList<Parametro>?, var listaSentencias:ArrayList<Sentencia>) {

    override fun toString(): String {
        return "Funcion(nombreFuncion=$nombreFuncion, tipoRetorno=$tipoRetorno, listaParametros=$listaParametros, listaSentencias=$listaSentencias)"
    }
    fun getArbolVisual():TreeItem<String>{
        var raiz = TreeItem<String>("Funcion")
        raiz.children.add(TreeItem("Nombre: "+nombreFuncion.lexema))
        raiz.children.add(TreeItem("Tipo Retorno: "+tipoRetorno.lexema))
        var raizParametros = TreeItem("Parametros")
        for(i in listaParametros!!){
            raizParametros.children.add(i.getArbolVisual())
        }
            raiz.children.add(raizParametros)

      var raizSentencias= TreeItem("Sentencias")

        for(i in listaSentencias){
            raizSentencias.children.add(i.getArbolVisual())
        }
        raiz.children.add(raizSentencias)

        return  raiz
    }

    fun obtenerTipoParametros():ArrayList<String>{
        var lista = ArrayList<String>()

            for (p in listaParametros!!) {
                lista.add(p.tipoDato.lexema)
            }
    return lista
    }

    fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos,listaErrores:ArrayList<Error>, ambito:String){

        tablaSimbolos.guardarSimboloFuncion(nombreFuncion.lexema,tipoRetorno.lexema,obtenerTipoParametros(),ambito,nombreFuncion.fila, nombreFuncion.columna)

        for(p in listaParametros!!){
            tablaSimbolos.guardarSimboloValor(p.nombre.lexema,p.tipoDato.lexema, true,nombreFuncion.lexema, p.nombre.fila, p.nombre.columna)
        }
        for(s in listaSentencias){
            s.llenarTablaSimbolos(tablaSimbolos, listaErrores, nombreFuncion.lexema)
        }
    }

    fun analizarSemantica(tablaSimbolos:TablaSimbolos, listaErrores:ArrayList<Error>) {

        for(s in listaSentencias){
            s.analizarSemantica(tablaSimbolos, listaErrores, nombreFuncion.lexema)
        }

    }
}