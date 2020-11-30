package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Categoria
import co.edu.uniquindio.compiladores.lexico.Token

class AnalizadorSintactico(var listaTokens:ArrayList<Token>) {

    var posicionActual = 0
    var tokenActual = listaTokens[0]
    var listaErrores = ArrayList<Error>()

    fun obtenerSiguienteToken() {

        posicionActual++
        if (posicionActual < listaTokens.size) {
            tokenActual = listaTokens[posicionActual]
        }
    }

    fun obtenerAnteriorToken() {

        posicionActual--
        if (posicionActual < listaTokens.size) {
            tokenActual = listaTokens[posicionActual]
        }
    }

    fun reportarError(mensaje: String) {
        listaErrores.add(Error(mensaje, tokenActual.fila, tokenActual.columna))
    }

    /**
     * <UnidadDeCompilacion> ::=  <ListaFunciones>
     */
    fun esUnidadDeCompilacion(): UnidadDeCompilacion? {
        val listaFunciones: ArrayList<Funcion> = esListaFunciones()
       // val listaVariblesGlobales: ArrayList<DeclaracionVariable> = esListaVaribalesGlobales()
        return if (listaFunciones.size > 0) {
            UnidadDeCompilacion(listaFunciones)
        } else null
    }

    /**
     * <ListaFunciones> ::= <Funcion> [<ListaFunciones>]
     */
    fun esListaFunciones(): ArrayList<Funcion> {
        var listaFunciones = ArrayList<Funcion>()
        var funcion = esFuncion()

        while (funcion != null) {
            listaFunciones.add(funcion)
            funcion = esFuncion()
        }
        return listaFunciones
    }

    /**
     * <Desicion> ::= se <Expresiones> <BloqueSentencias> [also <BlouqeSentencias>  ]
     */
    fun esDesicion(): Decision? {
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "se") {
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.PARENTESIS_IZQ) {
                obtenerSiguienteToken()
                val expresion = esExpresionLogica()
                if (expresion != null) {
                    if (tokenActual.categoria == Categoria.PARENTESIS_DER) {
                        obtenerSiguienteToken()
                        val listaSentencias = esBloqueSentencias()
                        if (listaSentencias != null) {
                            if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "altro") {
                                obtenerSiguienteToken()
                                val listaSentenciasAltro = esBloqueSentencias()
                                if (listaSentenciasAltro != null) {
                                    return Decision(expresion, listaSentencias, listaSentenciasAltro)
                                } else {
                                    reportarError("Falta bloque de sentencias")
                                }

                            } else {
                                return Decision(expresion, listaSentencias, null)
                            }
                        } else {
                            reportarError("Falta bloque de sentencias")
                        }
                    } else {
                        reportarError("Falta parentesis derecho")
                    }
                }
            } else {
                reportarError("Falta parentesis izquierdo")
            }
        }

        return null
    }


    /**
     * fun <TipoRetorno> identificador "("[<ListaParametros>]")" <BloqueSentencias>
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
     * <TipoDato> ::= numZ | numR | chordi | khar | bool
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
     * <Sentencia> ::= <Desicion> | <DeclaracionMetodos> | <DesclaracionVariables> | <Asignación> |
    <Impresión> | <Lectura> | <Retorno> | <Invocaciones> | <CicloMientras> | <CicloFor> |
    <Incremento> | <Decremento>
     */
    fun esSentencia():Sentencia?{

        var s:Sentencia? = esDesicion()
        if(s != null) return s
        s = esDeclaracionVariables()
        if(s != null) return s
        s = esAsignacion()
        if(s != null) return s
        s = esImpresion()
        if(s != null) return s
        s = esLectura()
        if(s != null) return s
        s = esRetorno()
        if(s != null) return s
        s = esInvocacion()
        if(s != null) return s
        s = esCicloMientras()
        if(s != null) return s
        s = esIncremento()
        if(s != null) return s
        s = esDecremento()
        if(s != null) return s
        s = esCicloPer()
        if(s != null) return s
        s = esArreglo()
        return s

    }

    /**
     * <ListaSentencias> ::= <Sentencia>[<ListaSentencias>]
     */
    fun esListaSentencias():ArrayList<Sentencia>?{

        var listaSentencia = ArrayList<Sentencia>()
        var sentencia = esSentencia()

        while (sentencia!=null){
            listaSentencia.add(sentencia)
            sentencia = esSentencia()
        }
       return listaSentencia


    return null
    }

    /**
     * <Expresion> ::= <ExpresionLogica> |<ExpresionRelacional> |<ExpresionAritmetica> | <ExpresionCadena>
     */
    fun esExpresion():Expresion?{
        var e:Expresion? = esExpresionAritmetica()
        if(e != null){
            return e
        }
        e = esExpresionLogica()
        if(e != null) {
            return e
        }
        e = esExpresionRelacional()
        if(e != null) {
            return e
        }

        e = esExpresionCadena()
        return e
    }

    /**
     * <ExpresionAritmetica> ::= "("<ExpresionAritmetica>")"[operadorAritmetico<ExpreisonAritmetica>] | <valorNumerico>[operadorAritmetico<expreisonAritmetica>]
     */
    fun esExpresionAritmetica():ExpresionAritmetica?{
        if (tokenActual.categoria == Categoria.PARENTESIS_IZQ){
            obtenerSiguienteToken()
            val expresion1 = esExpresionAritmetica()
            if(expresion1 != null){

                if(tokenActual.categoria == Categoria.PARENTESIS_DER){
                    obtenerSiguienteToken()
                    if(tokenActual.categoria == Categoria.OPERADOR_ARITMETICO){
                        val operador = tokenActual
                        obtenerSiguienteToken()
                        val expresion2 = esExpresionAritmetica()
                        if(expresion2 != null){
                            return ExpresionAritmetica(expresion1,operador,expresion2)
                        }
                    }else{
                        return ExpresionAritmetica(expresion1)
                    }
                }
            }
        }else{
            val valor = esValorNumerico()
            if(valor != null){
                obtenerSiguienteToken()
                if(tokenActual.categoria == Categoria.OPERADOR_ARITMETICO){
                    val operador = tokenActual
                    obtenerSiguienteToken()
                    val expresion = esExpresionAritmetica()
                    if(expresion != null){
                        return ExpresionAritmetica(valor,operador,expresion)
                    }
                }else{
                    return ExpresionAritmetica(valor)
                }
            }
        }
        return null
    }

    /**
     * <ExpresionLogica> ::= <ExpresionLogica>[operadorLogico <ExpresionLogica>]|
     * <valoirLogico> [operadorLogico <ExpresionLogica>]| <ExpresionRelacionla>[operadorLogico <ExpresionLogica>]
     */
    fun esExpresionLogica():ExpresionLogica?{

        val expR = esExpresionRelacional()
        if (expR != null) {
            if (tokenActual.categoria == Categoria.OPERADOR_LOGICO) {
                val operadorL = tokenActual
                obtenerSiguienteToken()
                val expL2 = esExpresionLogica()
                if (expL2 != null) {
                    return ExpresionLogica(expR, operadorL, expL2)
                }
            } else {
                return ExpresionLogica(expR)
            }
        } else {
            if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && (tokenActual.lexema == "True" || tokenActual.lexema == "False")){
                val valorLogico = tokenActual
                obtenerSiguienteToken()
                if (tokenActual.categoria == Categoria.OPERADOR_LOGICO) {
                    val operadorL = tokenActual
                    obtenerSiguienteToken()
                    val expL2 = esExpresionLogica()
                    if (expL2 != null) {
                        return ExpresionLogica(valorLogico, operadorL, expL2)
                    }
                } else {
                    return ExpresionLogica(valorLogico)
                }
            }
        }

        return null
    }

    /**
     * <ExpresiónRelacional> ::= <ExpresionAritmetica> operadorRelacional <ExpresionAritmetica> | <ExpresionCadena> operadorRelacional <Expresioncadena>
     */
    fun esExpresionRelacional():ExpresionRelacional?{
        var exp1= esExpresionAritmetica()
        if(exp1 != null){
            if(tokenActual.categoria == Categoria.OPERADORES_RELACIONALES){
                val operadorRelacional = tokenActual
                obtenerSiguienteToken()
                val exp2 = esExpresionAritmetica()
                if(exp2 != null){
                    return ExpresionRelacional(exp1,operadorRelacional,exp2)
                }
            }
        }
        val expC1 = esExpresionCadena()
        if(expC1 != null){
            if(tokenActual.categoria == Categoria.OPERADORES_RELACIONALES && (tokenActual.lexema == "¬:" || tokenActual.lexema == "::")){
                val operadorRelacional = tokenActual
                obtenerSiguienteToken()
                val expC2 = esExpresionCadena()
                if(expC2 != null){
                    return ExpresionRelacional(expC1,operadorRelacional,expC2)
                }
            }
        }

        return null
    }
    /**
     *  <ExpreisonCadena> ::= [Identificador “+”] <ExpresionCadena> [“+”Identifiacdor] | [cadena“+”] <ExpresionCadena> [“+”cadena] | Cadena
     */
    fun esExpresionCadena():ExpresionCadena?{

        if(tokenActual.categoria == Categoria.IDENTIFICADOR){
            val identificador1 = tokenActual
            obtenerSiguienteToken()
            if(tokenActual.categoria == Categoria.OPERADOR_ARITMETICO && tokenActual.lexema == "+"){
                obtenerSiguienteToken()
                var expC=esExpresionCadena()
                if(expC != null) {
                    if(tokenActual.categoria == Categoria.OPERADOR_ARITMETICO && tokenActual.lexema == "+") {
                        obtenerSiguienteToken()
                        if (tokenActual.categoria == Categoria.IDENTIFICADOR) {
                            val identificador2 = tokenActual
                            return ExpresionCadena(identificador1, expC, identificador2)
                        }else{
                            reportarError("Falta concatenar una expresion")
                        }
                    }else {
                        return ExpresionCadena(identificador1, expC)
                    }
                }else{
                    reportarError("Falta una expresion")
                }
            }else{

                return ExpresionCadena(identificador1)
            }
        }else if(tokenActual.categoria == Categoria.CADENA) {  //cadena + a
            val cadena1 = tokenActual
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.OPERADOR_ARITMETICO && tokenActual.lexema == "+") {
                obtenerSiguienteToken()
                //  var aux = tokenActual
                val expC = esExpresionCadena()
                if (expC != null) {
                    if (tokenActual.categoria == Categoria.OPERADOR_ARITMETICO && tokenActual.lexema == "+") {
                        obtenerSiguienteToken()
                        if (tokenActual.categoria == Categoria.CADENA) {
                            val cadena2 = tokenActual
                            return ExpresionCadena(cadena1, expC, cadena2)
                        } else {
                            reportarError("Falta concatenar una expresion")
                        }
                    }else {
                        return ExpresionCadena(cadena1,expC)
                    }
                }
                if(tokenActual.categoria == Categoria.IDENTIFICADOR){
                    return ExpresionCadena(cadena1,tokenActual)
                }
            }else{
                return ExpresionCadena(cadena1)
            }
        }
        return null
    }

    fun esValorNumerico():ValorNumerico?{
        var signo:Token?=null
        if(tokenActual.categoria == Categoria.OPERADOR_ARITMETICO && (tokenActual.lexema == "+" || tokenActual.lexema == "-")){
            signo = tokenActual
            obtenerSiguienteToken()
        }
        if(tokenActual.categoria == Categoria.ENTERO || tokenActual.categoria == Categoria.DECIMAL || tokenActual.categoria == Categoria.IDENTIFICADOR && tokenActual.categoria != Categoria.CADENA){
            val valor = tokenActual
            return ValorNumerico(signo, valor)
        }
        return null
    }

    //-------------------------------------------------------------------------------------------

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
                        listaIdentificadores = esListaIdentificadores()
                        //listaIdentificadores.add(identi)

                    }
                    if(estipoDato() != null){
                        var tipoDato = tokenActual
                        obtenerSiguienteToken()
                        if(tokenActual.categoria == Categoria.FINDESENTENCIA){
                            obtenerSiguienteToken()
                            return DeclaracionVariable(tipoVariable,listaIdentificadores,tipoDato)
                        }
                    }else{
                        reportarError("Falta especificar el tipo de Dato")
                    }
                }

            }
        }
        return null
    }



    /**
     * <ListaIdentificadores> ::= Identificador [“,”<ListaIdentificadores>]
     */
    fun esListaIdentificadores():ArrayList<Token>{
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
    fun esAsignacion():Asignacion?{

        if(tokenActual.categoria == Categoria.IDENTIFICADOR){
            var identi = tokenActual
            obtenerSiguienteToken()
            if(tokenActual.categoria == Categoria.OPERADOR_ASIGNACION){
                obtenerSiguienteToken()
                var exp = esExpresion()
                if(exp != null){

                    if(tokenActual.categoria == Categoria.FINDESENTENCIA){
                        obtenerSiguienteToken()
                        return Asignacion(identi,exp)
                    }else{
                        reportarError("Falta simbolo de fin de sentencia")
                    }
                }
            }
        }
        return null
    }

    /**
     * <Impresion> ::= println ”(“ [Identificador] | [Cadena] | [<ExpresionAritmetica>] |
    [<ExpresionRelacional>]”)”
     */
    fun esImpresion():Impresion?{
        if(tokenActual.categoria == Categoria.PALABRA_RESERVADA){
            if (tokenActual.lexema == "println"){
                obtenerSiguienteToken()
                if(tokenActual.categoria == Categoria.PARENTESIS_IZQ){
                    obtenerSiguienteToken()
                    var exp1 = esExpresionAritmetica()
                    var exp2 = esExpresionRelacional()
                    obtenerAnteriorToken()
                    if(tokenActual.categoria == Categoria.IDENTIFICADOR || tokenActual.categoria == Categoria.CADENA || exp1 != null || exp2 != null ){
                        var cadena = tokenActual
                        obtenerSiguienteToken()
                        if(tokenActual.categoria == Categoria.PARENTESIS_DER){
                            if(exp1 != null){
                                obtenerSiguienteToken()
                                return Impresion(exp1)
                            }
                            if(exp2 != null){
                                obtenerSiguienteToken()
                                return Impresion(exp2)
                            }
                            obtenerSiguienteToken()
                            return Impresion(cadena)
                        }
                    }
                }
            }
        }
        return null
    }

    /**
     * <Retorno> ::= return <ExpresionArimetica> | return <ExpresionRelacional> | return Identificador
     */
    fun esRetorno():Retorno?{
        if(tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "return"){
            obtenerSiguienteToken()

            var expAritmetica = esExpresionAritmetica()
            var expRelacional = esExpresionRelacional()
            obtenerAnteriorToken()

            if (expAritmetica != null){
                obtenerSiguienteToken()
                return Retorno(expAritmetica)
            }
            if(expRelacional != null){
                obtenerSiguienteToken()
                return Retorno(expRelacional)
            }
            if(tokenActual.categoria == Categoria.IDENTIFICADOR){
                return Retorno(tokenActual)
            }

        }
        return null
    }

    /**
     * <Lectura> ::= read “(“ Cadena ”)”
     */
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
                            obtenerSiguienteToken()
                            return Lectura(cadena)
                        }else{
                            reportarError("Falta parentesis derecho")
                        }
                    }
                }else{
                    reportarError("Falta parentesis izquierdo")
                }
            }
        }
        return null
    }


    // ---------------------------------------------------------------------------------------------------------

    /**
     * <ListaInvocaciones> ::= <Invocacion> [<ListaInvocaciones>]
     */
    fun esListaInvocaciones(): ArrayList<Invocacion> {
        var listaInvocaciones = ArrayList<Invocacion>()
        var invocacion = esInvocacion()

        while (invocacion != null) {
            listaInvocaciones.add(invocacion)
            invocacion = esInvocacion()
        }
        return listaInvocaciones
    }

    /**
     * <Invocaciones> ::= nombreMetodo”(“<ListaArgumentos>”)”
     */
    fun esInvocacion(): Invocacion? {
        if (tokenActual.categoria == Categoria.METODO) {
            var nombreMetodo = tokenActual
            obtenerSiguienteToken()

            if (tokenActual.categoria == Categoria.PARENTESIS_IZQ) {
                obtenerSiguienteToken()
                var listaArgumentos = esListaArgumentos()

                if (tokenActual.categoria == Categoria.PARENTESIS_DER) {
                    if (listaArgumentos != null) {
                        obtenerSiguienteToken()
                        return Invocacion(nombreMetodo, listaArgumentos)

                    } else {
                        reportarError("Falta la invocacion")
                    }
                } else {
                    reportarError("Falta un parentisis derecho en la lista de parametros")
                }
            } else {
                reportarError("Falta parentisis izquierdo")
            }
        }
        return null
    }

    /**
     * <ListaArgumentos> ::= <Argumento>[","< ListaArgumentos>]
     */
    fun esListaArgumentos(): ArrayList<Argumento> {

        var listaArgumentos = ArrayList<Argumento>()
        var argumento = esArgumento()

        while (argumento != null) {
            listaArgumentos.add(argumento)
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.COMA) {
                obtenerSiguienteToken()
                argumento = esArgumento()
            } else {
                break
            }
        }
        return listaArgumentos
    }


    /**
     * <Argumento> ::= Identificador
     */
    fun esArgumento(): Argumento? {

        if (tokenActual.categoria == Categoria.IDENTIFICADOR) {
            val nombre = tokenActual
            return Argumento(nombre)
        } else {
            reportarError("Falta el nombre del Argumento")
        }
        return null
    }


    /**
     * <CicloMientras> ::=mentre "("<ExpresionLogica>")" <BloqueSentencias>
     */
    fun esCicloMientras(): CicloMientras? {
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "mentre") {
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.PARENTESIS_IZQ) {
                obtenerSiguienteToken()

                val expresionLogica = esExpresionLogica()

                if (expresionLogica != null) {
                    if (tokenActual.categoria == Categoria.PARENTESIS_DER) {
                        obtenerSiguienteToken()

                        val bloqueSentencias = esBloqueSentencias()

                        if (bloqueSentencias != null) {
                            obtenerSiguienteToken()
                            return CicloMientras(expresionLogica, bloqueSentencias)
                        } else {
                            reportarError("Faltó el bloque de sentencias en el ciclo mientras")
                        }
                    } else {
                        reportarError("Falta parentesis derecho")
                    }
                } else {
                    reportarError("Falta la expresion logica")
                }
            } else {
                reportarError("Falta parentesis izquierdo")
            }
        }
        return null
    }


    /**
     *  <Arreglo> ::= array ”[“ TipoDato “]” identificador: [“<” <ListaArgumentos> “>”].
     */
    fun esArreglo(): Arreglo? {

        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "array") {
            obtenerSiguienteToken()

            if (tokenActual.categoria == Categoria.CORCHETE_IZQ) {
                obtenerSiguienteToken()
                val tipoDato = estipoDato()
                obtenerSiguienteToken()
                if (tokenActual.categoria == Categoria.CORCHETE_DER) {

                    if (tipoDato != null) {
                        obtenerSiguienteToken()

                        if (tokenActual.categoria == Categoria.IDENTIFICADOR) {
                            val nombreArreglo = tokenActual
                            obtenerSiguienteToken()
                            var listaExpresiones = ArrayList<Argumento>()

                            if (tokenActual.categoria == Categoria.OPERADOR_ASIGNACION && tokenActual.lexema == ":") {
                                obtenerSiguienteToken()
                                if (tokenActual.categoria == Categoria.LLAVE_IZQ) {
                                    obtenerSiguienteToken()
                                    listaExpresiones = esListaArgumentos()
                                    obtenerSiguienteToken()
                                    if (tokenActual.categoria == Categoria.LLAVE_DER) {
                                        obtenerSiguienteToken()
                                    } else {
                                        reportarError("Falta la llave derecha en el arreglo")
                                    }
                                } else {
                                    reportarError("Falta la llave izquierda en el arreglo")
                                }
                            }
                            if (tokenActual.categoria == Categoria.FINDESENTENCIA) {
                                obtenerSiguienteToken()
                                return Arreglo(nombreArreglo, tipoDato,listaExpresiones)
                            }else {
                                reportarError("Falta el fin de sentencia")
                            }
                        } else {
                            reportarError("Falta definir el nombre del arreglo")
                        }
                    } else {
                        reportarError("Falta tipo de dato del arreglo")
                    }
                } else {
                    reportarError("Falta corchete derecho")
                }
            } else {
                reportarError("Falta corchete izquierdo")
            }
        }

        return null
    }


    /**
     * <Incremento> ::= Identificador"++"
     */
    fun esIncremento(): Incremento? {
        if(tokenActual.categoria == Categoria.OPERADOR_INCREMENTO){
            obtenerAnteriorToken()
        }


        if (tokenActual.categoria == Categoria.IDENTIFICADOR) {

            val nombre = tokenActual

            if (nombre!=null)
            {
                obtenerSiguienteToken()
                if (tokenActual.categoria == Categoria.OPERADOR_INCREMENTO) {
                    obtenerSiguienteToken()
                    obtenerSiguienteToken()
                    return Incremento(nombre)
                }else {
                    obtenerAnteriorToken()
                }
            }else{
                reportarError("Falta nombre de identificador")
            }
        }

        return null
    }


    /**
     * <Decremento> ::= Identificador"--"
     */
    fun esDecremento(): Decremento? {
        if (tokenActual.categoria == Categoria.OPERADOR_DECREMENTO) {
            obtenerAnteriorToken()
        }


        if (tokenActual.categoria == Categoria.IDENTIFICADOR) {

            val nombre = tokenActual

            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.OPERADOR_DECREMENTO) {
                obtenerSiguienteToken()
                obtenerSiguienteToken()
                return Decremento(nombre)
            }
        }

        return null
    }

    /**
     * <CicloPer> ::= "per" "(" identificador "in" identificador ")" <BloqueSentencias> | "per" "(" identificador "in" cadena ")" <BloqueSentencias>
     */
    fun esCicloPer():CicloPer?{

        if(tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "per" ){
            obtenerSiguienteToken()
            if(tokenActual.categoria == Categoria.PARENTESIS_IZQ){
                obtenerSiguienteToken()
                if(tokenActual.categoria == Categoria.IDENTIFICADOR){
                    val variableControl = tokenActual
                    obtenerSiguienteToken()
                    if(tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "in"){
                        obtenerSiguienteToken()
                        if(tokenActual.categoria == Categoria.IDENTIFICADOR || tokenActual.categoria == Categoria.CADENA){
                            val indice = tokenActual
                            obtenerSiguienteToken()
                            if(tokenActual.categoria == Categoria.PARENTESIS_DER){
                                obtenerSiguienteToken()
                                val bloqueSentencias = esBloqueSentencias()
                                if(bloqueSentencias != null){
                                    return CicloPer(variableControl,indice,bloqueSentencias)
                                }else{
                                    reportarError("Falta bloque de sentencias")
                                }
                            }
                        }
                    }
                }
            }
        }

        return null
    }



}