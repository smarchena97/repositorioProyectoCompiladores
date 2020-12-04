package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class Arreglo (var nombreArreglo: Token, var tipoDato:Token, var listaDatos: ArrayList<Expresion> ):Sentencia(){

    override fun toString(): String {
        return "Arreglo(nombreArreglo=$nombreArreglo, tipoDato=$tipoDato, listaDatos=$listaDatos)"
    }

    override fun getArbolVisual(): TreeItem<String> {
        val raiz = TreeItem<String>("Nombre Arreglo")
        raiz.children.add(TreeItem(nombreArreglo.lexema))
        val raizTipoDato= TreeItem("Tipo Dato")
        raizTipoDato.children.add(TreeItem(tipoDato.lexema))
        raiz.children.add(raizTipoDato)
        val raizListaDatos= TreeItem("Lista Datos")

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
        for(s in listaDatos){
            s.analizarSemantica(tablaSimbolos, listaErrores, ambito)
            var tipo= s.obtenerTipo(tablaSimbolos,ambito)
            if (tipo != tipoDato.lexema){
                listaErrores.add(Error("El tipo de dato de la expresión ($tipo) no coincide con el tipo de dato del arreglo (${tipoDato.lexema})", nombreArreglo.fila, nombreArreglo.columna))
            }
        }
    }

    override fun getJavaCode(): String {
        var codigo = tipoDato.getJavaCode()+"[] "+ nombreArreglo.lexema + "= {"

        for (l in listaDatos){

            codigo += l.getJavaCode()+","

        }
        codigo = codigo.substring(0,codigo.length-1)
        codigo += "};"
        return codigo
    }
}