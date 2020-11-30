package co.edu.uniquindio.compiladores.semantica
import co.edu.uniquindio.compiladores.lexico.Error

class TablaSimbolos(erroresSemanticos:ArrayList<Error>) {
    var listaSimbolos:ArrayList<Simbolo> = ArrayList()
    var listaError:ArrayList<Error> = erroresSemanticos

    /**
     * Permite guardar un simbolo que representa una variable, constante, parametros
     */
    fun guardarSimboloValor(nombre:String, tipoDato:String, modificable:Boolean, ambito:String, fila:Int, columna:Int){
        val s = buscarSimboloValor(nombre,ambito)
        if(s ==  null){
            listaSimbolos.add(Simbolo(nombre, tipoDato, modificable, ambito, fila, columna))
        }else{
            listaError.add(Error("El campo con el nombre $nombre ya existe dentro del ambito $ambito",fila,columna))
        }

    }

    /**
     * Permite guardar un simbolo que represente un simbolo
     */
    fun guardarSimboloFuncion(nombre: String,tipoRetorno:String,tipoParametros:ArrayList<String>,ambito:String,fila:Int,columna:Int){
        val s = buscarSimboloFuncion(nombre,tipoParametros)
        if(s ==  null){
            listaSimbolos.add(Simbolo(nombre,tipoRetorno,tipoParametros, ambito))
        }else{
            listaError.add(Error("La funcion con el nombre $nombre ya existe dentro del ambito $ambito",fila,columna))
        }
    }

    /**
     * Permite buscar un valor dentro de la tabla de simbolos
     */
    fun buscarSimboloValor(nombre:String,ambito:String):Simbolo?{

        for(s in listaSimbolos){
            if(s.tiposParametros == null) {
                if (s.nombre == nombre && s.ambito == ambito) {
                    return s
                }
            }
        }
        return null
    }

    /**
     * Permite buscar una funcion dentro de la tabla de simbolos
     */
    fun buscarSimboloFuncion(nombre:String,tiposParametros:ArrayList<String>):Simbolo?{

        for(s in listaSimbolos){
            if(tiposParametros != null) {
                if (s.nombre == nombre && s.tiposParametros == tiposParametros) {
                    return s
                }
            }
        }
        return null
    }

    override fun toString(): String {
        return "TablaSimbolos(listaSimbolos=$listaSimbolos)"
    }


}