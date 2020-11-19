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
     * <UnidadDeCompilacion> ::= <ListaFunciones>
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

                            var bloqueSentencias= esBloqueSentencias()

                            if (bloqueSentencias != null){
                                return Funcion(nombreFuncion, tipoRetorno, listaParametros, bloqueSentencias)
                            }
                            else{
                                reportarError( "Faltó el bloque de sentencias en la función" )
                            }
                        }
                        else{
                            reportarError("Falta parentesis derecho")
                        }
                    }
                    else{
                        reportarError("Falta parentesis izquierdo")
                    }
                }
                else{
                    reportarError("Falta nombre del metodo")
                }
            }
            else{
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
            obtenerSiguienteToken()
            if(tokenActual.categoria == Categoria.COMA){
                obtenerSiguienteToken()
                parametro = esParametro()
            }else{
                if(tokenActual.categoria != Categoria.PARENTESIS_DER){
                    reportarError("Falta un parentisis derecho en la lista de parametros")
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

     //   if(tokenActual.categoria == Categoria.PALABRA_RESERVADA){
       //     if(tokenActual.lexema == "numZ" || tokenActual.lexema == "numR" || tokenActual.lexema == "chordi" || tokenActual.lexema == "khar" || tokenActual.lexema == "bool"){
                val tipoDato = estipoDato()
                if(tipoDato!=null){
                    obtenerSiguienteToken()
                    if(tokenActual.categoria == Categoria.IDENTIFICADOR){
                        val nombre = tokenActual
                        return Parametro(nombre,tipoDato)
                    }
                }else{
                    reportarError("Falta el tipo de retorno en el parametro")
                }

           // }
       // }
        return null
    }

    /**
     * <BloqueSentencias> ::= "<" [<ListaSentencias>] ">"
     */
    fun esBloqueSentencias():ArrayList<Sentencia>?{
        if (tokenActual.categoria == Categoria.LLAVE_IZQ) {
            obtenerSiguienteToken()

            var listaSentencias = esListaSentencias()

            if (tokenActual.categoria == Categoria.LLAVE_DER) {
                obtenerSiguienteToken()

                return listaSentencias
            }
            else{
                reportarError( "Falta llave derecha" )
            }
        }
        else{
            reportarError( "Falta llave izquierda" )
        }
        return null
    }

    /**
     * <ListaSentencias> ::= <Sentencia>[<ListaSentencias>]
     */
    fun esListaSentencias():ArrayList<Sentencia>?{
        /**var listaSentencias = ArrayList<Sentencia>()
        var sentencia =

        while ()*/
        return ArrayList()
    }

    /**
     * <Sentencia> ::= <Desicion> | <DeclaracionMetodos> | <DesclaracionVariables> | <Asignación> |
    <Impresión> | <Lectura> | <Retorno> | <Invocaciones> | <CicloMientras> | <CicloFor> |
    <Incremento> | <Decremento>
     */
    fun esSentencia(){

    }

    /**
     * <DeclaracionVariables> ::= <TipoVariable><ListaIdentificadores> <TipoDato> “.”
     */
    fun esDeclaracionVariables():DeclaracionVariable?{
        var listaIdentificadores = ArrayList<Token>()
        if(tokenActual.categoria == Categoria.PALABRA_RESERVADA){
            if(esTipoVariable() != null){
                var tipoVariable = tokenActual
                obtenerSiguienteToken()
                if(tokenActual.categoria == Categoria.IDENTIFICADOR){
                    while (tokenActual.categoria == Categoria.IDENTIFICADOR){
                        var identi = tokenActual
                        listaIdentificadores.add(identi)
                        obtenerSiguienteToken()
                    }
                    if(estipoDato() != null){
                        var tipoDato = tokenActual
                        obtenerSiguienteToken()
                        if(tokenActual.categoria == Categoria.FINDESENTENCIA){
                            return DeclaracionVariable(tipoVariable,listaIdentificadores,tipoDato)
                        }
                    }else{
                        reportarError("Falta especificar el tipo de Dato")
                    }
                }

            }
        }else{
            reportarError("Falta el tipo de variable")
        }
        return null
    }

    /**
     * <TipoVariable> ::= mut | inmut
     */
    fun esTipoVariable():Token?{
        if(tokenActual.categoria == Categoria.PALABRA_RESERVADA){
            if(tokenActual.lexema == "mut" || tokenActual.lexema == "inmut"){
                return tokenActual
            }
        }
        return null
    }

    /**
     * <ListaIdentificadores> ::= Identificador [“,”<ListaIdentificadores>]
     */
    fun esListaIdentificadores():ArrayList<Token>?{
        var listaIdentificadores = ArrayList<Token>()
        while (tokenActual.categoria == Categoria.IDENTIFICADOR){
            var identi = tokenActual
            listaIdentificadores.add(identi)
            obtenerSiguienteToken()
            if(tokenActual.categoria == Categoria.COMA){
                obtenerSiguienteToken()

            }else{
                if(tokenActual.categoria != Categoria.PARENTESIS_DER){
                    reportarError("Falta un parentisis derecho en la lista de parametros")
                }
                break
            }

        }
        return listaIdentificadores
    }

    /**
     * <Asignación> ::= identificador operadorAsignación <Expresión> "."
     */
    fun esAsignacion(){

        if(tokenActual.categoria == Categoria.IDENTIFICADOR){
            var identi = tokenActual
            obtenerSiguienteToken()
            if(tokenActual.categoria == Categoria.OPERADOR_ASIGNACION){
                
            }
        }
    }

    fun esLectura():Lectura?{
        if(tokenActual.categoria == Categoria.PALABRA_RESERVADA){
            if(tokenActual.lexema == "read"){
                obtenerSiguienteToken()
                if(tokenActual.categoria == Categoria.PARENTESIS_IZQ){
                    obtenerSiguienteToken()
                    if(tokenActual.categoria == Categoria.CADENA){
                        var cadena = tokenActual
                        obtenerSiguienteToken()
                        if(tokenActual.categoria == Categoria.PARENTESIS_DER){

                        }
                    }
                }
            }
        }
        return null
    }

    fun esImpresion(){
        if(tokenActual.categoria == Categoria.PALABRA_RESERVADA){
            if (tokenActual.lexema == "println"){
                obtenerSiguienteToken()
                if(tokenActual.categoria == Categoria.PARENTESIS_IZQ){
                    obtenerSiguienteToken()
                    if(tokenActual.categoria == Categoria.IDENTIFICADOR || tokenActual.categoria == Categoria.CADENA ){
                        obtenerSiguienteToken()
                        if(tokenActual.categoria == Categoria.PARENTESIS_DER){

                        }
                    }
                }
            }
        }
    }

}