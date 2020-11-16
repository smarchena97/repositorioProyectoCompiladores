package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Categoria
import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token

class AnalizadorSintactico(var listaTokens:ArrayList<Token>) {

    var posicionActual = 0
    var tokenActual = listaTokens[0]
    var listaErrores = ArrayList<Error>()

    fun obtenerSiguienteToken(){

        posicionActual ++
        if(posicionActual < listaTokens.size){
            tokenActual = listaTokens[posicionActual]
        }
    }

    fun reportarError(mensaje:String){
        listaErrores.add(Error(mensaje, tokenActual.fila, tokenActual.columna))
    }

    /**
     * <UnidadDeCompilacion> ::=  <ListaFunciones>
     */
    fun esUnidadDeCompilacion(): UnidadDeCompilacion? {
        val listaFunciones: ArrayList<Funcion> = esListaFunciones()
        return if (listaFunciones.size > 0) {
            UnidadDeCompilacion(listaFunciones)
        } else null
    }

    /**
     * <ListaFunciones> ::= <Funcion> [<ListaFunciones>]
     */
    fun esListaFunciones(): ArrayList<Funcion>{
        var listaFunciones = ArrayList<Funcion>()
        var funcion = esFuncion()

        while (funcion!=null){
            listaFunciones.add(funcion)
            funcion = esFuncion()
        }
        return listaFunciones
    }

    /**
     * fun <TipoRetorno> identificador "("[<ListaParametros>]")" “<” <ListaSentencias> “>”
     */
    fun esFuncion():Funcion?{

        if(tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "fun"){

            obtenerSiguienteToken()

            var tipoRetorno = estipoRetorno()
            if(tipoRetorno != null){
                obtenerSiguienteToken()

                if(tokenActual.categoria == Categoria.IDENTIFICADOR){
                    var nombreFuncion = tokenActual
                    obtenerSiguienteToken()

                    if(tokenActual.categoria == Categoria.PARENTESIS_IZQ){
                        obtenerSiguienteToken()

                        var listaParametros = esListaParametros()

                        if(tokenActual.categoria == Categoria.PARENTESIS_DER){
                            obtenerSiguienteToken()

                            if(tokenActual.categoria == Categoria.LLAVE_IZQ){
                                obtenerSiguienteToken()
                                var listaSentencias = esListaSentencias()
                                if( listaSentencias != null ){
                                    obtenerSiguienteToken()

                                    if(tokenActual.categoria == Categoria.LLAVE_DER){
                                        return Funcion(nombreFuncion, tipoRetorno, listaParametros, listaSentencias)
                                    }else{
                                        reportarError("Falta llave derecha")
                                    }

                                }else{
                                    reportarError("El bloque de sentencias está vacío")
                                }
                            }else{
                                reportarError("Falta llave izquierda")
                            }
                        }else{
                            reportarError("Falta parentesis derecho")
                        }
                    }else{
                        reportarError("Falta parentesis izquierdo")
                    }
                }else{
                    reportarError("Falta nombre del metodo")
                }

            }else{
                reportarError("Falta el tipo de retorno en el método")
            }
        }

        return null
    }

    /**
     * <TipoRetorno> ::= numZ | numR | chordi | khar | bool |void
     */
    fun estipoRetorno():Token?{

        if(tokenActual.categoria == Categoria.PALABRA_RESERVADA){

            if(tokenActual.lexema == "numZ" || tokenActual.lexema == "numR" || tokenActual.lexema == "chordi" || tokenActual.lexema == "khar" || tokenActual.lexema == "bool" || tokenActual.lexema == "void"){
                return tokenActual
            }
        }
        return null
    }

    /**
     * <TipoRetorno> ::= numZ | numR | chordi | khar | bool
     */
    fun estipoDato():Token?{

        if(tokenActual.categoria == Categoria.PALABRA_RESERVADA){

            if(tokenActual.lexema == "numZ" || tokenActual.lexema == "numR" || tokenActual.lexema == "chordi" || tokenActual.lexema == "khar" || tokenActual.lexema == "bool" ){
                return tokenActual
            }
        }
        return null
    }

    /**
     * <ListaParametros> ::= <Parametro>[","<ListaParametros>]
     */
    fun esListaParametros():ArrayList<Parametro>?{
        var listaParametros = ArrayList<Parametro>()
        var parametro = esParametro()
        while (parametro != null){
            listaParametros.add(parametro)
            if(tokenActual.categoria == Categoria.COMA){
                obtenerSiguienteToken()
                parametro = esParametro()
            }else{
                if(tokenActual.categoria != Categoria.PARENTESIS_IZQ){
                    reportarError("Falta una coma en la lista de parametros")
                }
                break
            }

        }
        return listaParametros
    }

    /**
     * <Parametro> ::= <TipoDato> Identificador
     */
    fun esParametro():Parametro?{

        if(tokenActual.categoria == Categoria.PALABRA_RESERVADA){
            if(tokenActual.lexema == "numZ" || tokenActual.lexema == "numR" || tokenActual.lexema == "chordi" || tokenActual.lexema == "khar" || tokenActual.lexema == "bool"){
                val tipoDato = estipoDato()
                if(tipoDato!=null){
                    obtenerSiguienteToken()
                    if(tokenActual.categoria == Categoria.IDENTIFICADOR){
                        val nombre = tokenActual
                        return Parametro(nombre,tipoDato)
                    }
                }else{
                    reportarError("Falta el tipo de error en el parametro")
                }

            }
        }
        return null
    }

    /**
     * <Sentencia> ::= <Desicion> | <DeclaracionMetodos> | <DesclaracionVariables> | <Asignación> |
    <Impresión> | <Lectura> | <Retorno> | <Invocaciones> | <CicloMientras> | <CicloFor> |
    <Incremento> | <Decremento>
     */
    fun esSentencia(){

    }

    /**
     * <ListaSentencias> ::= <Sentencia>[<ListaSentencias>]
     */
   fun esListaSentencias():ArrayList<Sentencia>?{
        /**var listaSentencias = ArrayList<Sentencia>()
        var sentencia =

        while ()*/
       return null
    }

}