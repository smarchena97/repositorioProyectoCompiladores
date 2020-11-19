package co.edu.uniquindio.compiladores.sintaxis

import javafx.scene.control.TreeItem

class Decision(var expresionLogica: ExpresionLogica, var listaSentencia: ArrayList<Sentencia>, var listaSentenciaAltro:ArrayList<Sentencia>?) :Sentencia(){

    override fun toString(): String {
        return "Decision(expresionLogica=$expresionLogica, listaSentencia=$listaSentencia, listaSentenciaAltro=$listaSentenciaAltro)"
    }

    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem<String>("Desición")

        var condicion = TreeItem("Condición")
        condicion.children.add(expresionLogica.getArbolVisual())
        raiz.children.add(condicion)

        var raizSentencias= TreeItem("Sentencias Verdaderas")

        for(i in listaSentencia!!){
            raizSentencias.children.add(i.getArbolVisual())
        }
        raiz.children.add(raizSentencias)

        var raizSentenciasElse= TreeItem("Sentencias Falsas")

        for(s in listaSentencia!!){
            raizSentenciasElse.children.add(s.getArbolVisual())
        }

        raiz.children.add(raizSentenciasElse)
        return  raiz
    }
}