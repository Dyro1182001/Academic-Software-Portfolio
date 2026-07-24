window.addEventListener("load", onLoad);

function onLoad() {
    doc("btnLogOff1").addEventListener("click", onLogOff);
    doc("btnLogOff2").addEventListener("click", onLogOff);
    doc("btnLogin").addEventListener("click", onLogin);
    doc("btnRegister").addEventListener("click", onRegister);
    doc("btnRegistrarSolicitud").addEventListener("click", onRegistrarSolicitud);
    doc("btnSearch").addEventListener("click", onSearch);
    doc("btnShowEstadisticas").addEventListener("click", onMostrarEstadisticas);
    doc("btnMostrarSolicitudes").addEventListener("click", TablaSolicitudes);
    doc("btnRegistrarViaje").addEventListener("click", onRegistrarViaje);
    doc("btnShowPendientes").addEventListener("click", onMostrarPendientes);
    doc("btnCheckManifiesto").addEventListener("click", onCheckManifiesto);
    doc("btnShowCanceladas").addEventListener("click", onMostrarCanceladas);
    doc("btnCheckDanger").addEventListener("click", onCheckDanger);

    cargarViajesSelect()
    mostrar("divInicial");

    //tienen que estar todos los DIVs definidos, de haber uno que sea nulo se rompe todo
}

let arrayEmpresas = [
    new Empresa("Rodrigo", "RD299328", "123"),
    new Empresa("Tomas", "TA303898", "456"),
    new Empresa("Jorge", "JO775022", "789"),
    new Empresa("Pepe", "PT099755", "910"),
]

let arrayImportadores = [
    new Importador("Albert", "Bon Jovi", "Albert1395"),
    new Importador("Rodolfo", "Speedwagon", "Elrodo1798"),
    new Importador("Juan", "Jojo", "Mondongo17"),
    new Importador("Roberto", "SP17", "Elplatino4"),
    new Importador("Nelson", "Zeppeli", "NZeppeli10"),
]

let sol1 = new Solicitud("Carga General", "4 toneladas de cuero", "Puerto A", 12, "RD299328");
let sol2 = new Solicitud("Refrigerado", "7 toneladas de frutas", "Puerto B", 21, "TA303898");
let sol3 = new Solicitud("Carga Peligrosa", "1 tonelada de explosivos", "Puerto B", 9, "JO775022");
let sol4 = new Solicitud("Carga General", "3 toneladas de acero", "Puerto A", 14, "PT099755");
let sol5 = new Solicitud("Refrigerado", "17 toneladas de carne bovina", "Puerto C", 41, "PT099755");

sol2.cancelar();
sol5.cancelar();

let arraySolicitudes = [
    sol1,
    sol2,
    sol3,
    sol4,
    sol5,
]

let arrayViajes = [
    new Viaje("Legacy", 50, "RD299328", "2023-07-16"),
    new Viaje("Enterprise", 38, "TA303898", "2023-04-20"),
    new Viaje("Javelin", 77, "TA303898", "2022-12-31"),
    new Viaje("San Diego", 69, "PT099755", "2022-12-24"),
]

function doc(id) { // Varible que nos permitirá agilizar los procesos de asignación.
    return document.querySelector("#" + id);
}

function onLogOff() { /* Permite simular un cierre de sesión sin tener que recargar la página y perder los datos que se hayan cargado, 
                        limpiando todos los campos de texto para que no haya que borrar nada luego. */
    let formLogin = doc("formLogin");
    let formRegistroUsuario = doc("formRegistroUsuario");
    let formRegistroSolicitudes = doc("formRegistroSolicitudes");
    let formConsulta = doc("formConsulta");
    let formRegistroViajes = doc("formRegistroViajes");

    let respuesta = confirm("¿Está seguro que desea cerrar sesión?")

    if (respuesta) {
        formLogin.reset();
        formRegistroUsuario.reset();
        formRegistroSolicitudes.reset();
        formConsulta.reset();
        formRegistroViajes.reset();

        mostrar("divInicial");
    }

}

function onLogin() { // Permite tanto a los usuarios “importador” como a los usuarios "empresa" ingresar en la aplicación.
    let user = doc("inputUserLogin").value;
    let pass = doc("inputPasswordLogin").value;

    if (loginValidoEmp(user, pass)) {
        mostrar("divEmpresa")
    } else if (loginValidoImp(user, pass)) {
        mostrar("divImportador")
    } else {
        alert("Datos de usuario incorrectos")
    }

}

function loginValidoEmp(user, password) { /* Valida el login del usuario "empresa" 
                                            en caso de que los datos hayan sido correctamente registrados previamente.*/
    let loginCorrecto = false

    for (let i = 0; i < arrayEmpresas.length && !loginCorrecto; i++) {
        let empresa = arrayEmpresas[i]
        if (user.toLowerCase() === empresa.usuario.toLowerCase() && password === empresa.contraseña) {
            loginCorrecto = true
        }
    }

    return loginCorrecto
}

function loginValidoImp(user, password) { /*Valida el login del usuario "importador" 
                                            en caso de que los datos hayan sido registrados correctamente con anterioridad.*/
    let loginCorrecto = false
    for (let i = 0; i < arrayImportadores.length && !loginCorrecto; i++) {
        let importador = arrayImportadores[i]
        if (user.toLowerCase() === importador.usuario.toLowerCase() && password === importador.contraseña) {
            loginCorrecto = true
        }
    }

    return loginCorrecto
}

function onRegister() { /* Permite el registro del usuario "importador", 
                        no sin antes asegurarse de que los datos sean correctos y no estén repetidos con otros usuarios importadores.*/
    let formRegistroUsuario = doc("formRegistroUsuario");
    let nombre = doc("inputNombreRegistro").value
    let user = doc("inputUserRegistro").value
    let pass = doc("inputPasswordRegistro").value
    let foto = doc("inputFotoRegistro")

    if (estaRepetidoUsuario(user)) {
        alert("El usuario ya está registrado")
    } else if (passEsValida(pass) === false) {
        alert("El formato de la contraseña es incorrecto")
    } else if (user === "") {
        alert("Por favor ingrese un usuario")
    } else if(foto===null){
        alert("Por favor ingrese una foto")
    }else{
        let imp = new Importador(nombre, user, pass, foto)
        arrayImportadores.push(imp);
        alert("Usuario importador registrado correctamente")
        formRegistroUsuario.reset();
    }

}

function estaRepetidoUsuario(user) { /* Función auxiliar que nos permite saber si efectivamente hay usuarios repetidos dentro de los importadores. 
                                        Retorna true o false.*/
    let repetido = false

    for (let i = 0; i < arrayEmpresas.length && !repetido; i++) {
        let emp = arrayEmpresas[i]
        if (emp.usuario.toLowerCase() === user.toLowerCase()) {
            repetido = true
        }
    }

    for (let i = 0; i < arrayImportadores.length && !repetido; i++) {
        let imp = arrayImportadores[i]
        if (imp.usuario.toLowerCase() === user.toLowerCase()) {
            repetido = true
        }
    }

    return repetido;
}

function passEsValida(password) { // Función auxiliar que valida los criterios que debe tener una contraseña. Retorna true o false.
    let esValida = false
    let contieneMayus = false
    let contieneMinus = false
    let contieneNumero = false

    for (let i = 0; i < password.length - 1; i++) {
        if (password.charCodeAt(i) >= 65 && password.charCodeAt(i) <= 90) {
            contieneMayus = true
            break;
        }
    }

    for (let i = 0; i < password.length - 1; i++) {
        if (password.charCodeAt(i) >= 97 && password.charCodeAt(i) <= 122) {
            contieneMinus = true
            break;
        }
    }

    for (let i = 0; i < password.length - 1; i++) {
        if (password.charCodeAt(i) >= 48 && password.charCodeAt(i) <= 57) {
            contieneNumero = true
            break;
        }
    }

    if (password.length >= 5 && contieneMayus && contieneMinus && contieneNumero) {
        esValida = true
    }

    return esValida;
}


function onRegistrarSolicitud() { /* Permite el ingreso y registro para las solicitudes de carga, 
                                    además de asegurarse que los datos hayan sido correctamente ingresados.*/
    let formRegistroSolicitudes = doc("formRegistroSolicitudes");
    let tipo = doc("selectMerchType").value;
    let descripcion = doc("inputDescripcion").value;
    let puerto = doc("selectPuerto").value;
    let contenedores = doc("inputContenedores").value;
    let nombre = doc("selectNombreEmpresa").value;

    if (tipo === "" || descripcion === "" || puerto === "" || contenedores === "" || nombre === "") {
        alert("Debe ingresar los datos correspondientes")
    } else {
        let solicitud = new Solicitud(tipo, descripcion, puerto, contenedores, nombre)
        arraySolicitudes.push(solicitud)
        alert("Viaje registrado con éxito")
        formRegistroSolicitudes.reset();
    }


}

function TablaSolicitudes() { /* Permite la creación de una tabla que detalla todas las solicitudes
     que han sido almacenadas hasta el momento en el array "arraySolicitudes".*/
    // Nos permite comenzar a crear los botones "Cancelar" también presentes en la tabla para comenzar a agregar funcionalidad a la misma.
    let textoDeTabla =
        `<tr>
    <th>Tipo</th>
    <th>Descripción</th>
    <th>Puerto</th>
    <th>Contenedores</th>
    <th>Nombre</th>
    <th>Estado</th>
    </tr>`;
    for (let i in arraySolicitudes) {
        let valorI = arraySolicitudes[i];
        if (valorI.pendiente) {
            textoDeTabla +=
                `<tr>
                <th>${valorI.tipo}</th>   
                <th>${valorI.desc}</th>   
                <th>${valorI.puerto}</th>   
                <th>${valorI.contenedores}</th>  
                <th>${valorI.nombre}</th>  
                <th>${valorI.estado()}</th>  
                <th><input type="button" value="Cancelar solicitud" class="claseBotonCancelar" id="${valorI.numero + "-botonCancelar"}"></th>
            </tr>`;
        }

    }
    doc("tablaSolicitudes").innerHTML = textoDeTabla;
    let arrayBotonesCancelar = document.querySelectorAll(".claseBotonCancelar");
    for (let i = 0; i < arrayBotonesCancelar.length; i++) {
        let boton = arrayBotonesCancelar[i];
        boton.addEventListener("click", cancelarSolicitud);
    }
}

function cancelarSolicitud() { /* Función auxiliar que permite cancelar solicitudes presentes en la tabla a través de un botón
                                     y le aplica funcionalidad al mismo.*/
    let numeroSolicitud = parseInt(this.id);
    let pos = -1;
    for (let i = 0; i < arraySolicitudes.length && pos == -1; i++) {
        let valorI = arraySolicitudes[i];
        if (valorI.numero === numeroSolicitud) {
            pos = i;
        }
    }
    let respuesta = confirm("¿Está seguro que desea cancelar la orden de " + arraySolicitudes[pos].desc + "?");
    if (respuesta) {
        arraySolicitudes[pos].cancelar()
        TablaSolicitudes();
        selectSolicitudesCanceladas()
    }
}

function onSearch() { /* Función que permite realizar búsquedas para conocer a través del ingreso parcial o completo de un texto 
                            si éste está presente en la descripción de una solicitud.*/
    let buscar = doc("inputSearch").value;
    let pMessage = doc("pMessageBuscador");
    let indice = obtenerIndiceDeSolicitud(buscar);

    if (buscar === "") {
        alert("Debe ingresar el texto que desea buscar")
    } else if (indice != -1) {
        pMessage.innerHTML = `La descripción ingresada coincide con la siguiente solicitud: ${"<br>"} ${"<br>"} Tipo: ${arraySolicitudes[indice].tipo} ${"<br>"} ${"<br>"} Desc: ${arraySolicitudes[indice].desc} ${"<br>"} ${"<br>"} Puerto: ${arraySolicitudes[indice].puerto} ${"<br>"} ${"<br>"} Contenedores: ${arraySolicitudes[indice].contenedores} ${"<br>"} ${"<br>"} Nombre de empresa: ${arraySolicitudes[indice].nombre} ${"<br>"} ${"<br>"}`;
    } else {
        alert("La descripción ingresada no se encuentra en el listado")
    }
}

function obtenerIndiceDeSolicitud(desc) { /* Función auxiliar que devuelve un valor necesario a través de la variable "index" 
                                            para la búsqueda planteada anteriormente. */
    let index = -1;

    for (let i in arraySolicitudes) {
        let valorI = arraySolicitudes[i];
        let valorISegundo = valorI.desc.toLowerCase();
        if (valorISegundo.includes(desc.toLowerCase()) || valorISegundo.includes(desc.toUpperCase())) {
            index = i;
            break
        }
    }
    return index;
}

function onMostrarEstadisticas() { //Funcion que calcula a partir de los viajes y solicitudes ya registradas diferentes estadísticas y las muestra.
    let pMessage1 = doc("tablaEstadisticas");
    let pMessage3 = doc("viajesEstadisticas");

    let total = 0
    let totalCanceladas = 0

    for (let i in arraySolicitudes) {
        total = total + 1;
        if (arraySolicitudes[i].estado() === "Cancelada") {
            totalCanceladas = totalCanceladas + 1;
        }
    }

    let porcentaje = (totalCanceladas * 100) / total

    pMessage1.innerHTML = "El " + porcentaje + "% de las solicitudes fueron canceladas";

    arrayViajes.sort(comparaFechaCreciente) //Metódo para ordenar de forma creciente los viajes registrados a partir de sus fechas.

    generarTabla();
    let viajesTotal = 0
    let totalRD = 0
    let totalTA = 0
    let totalPT = 0
    let totalJO = 0
    for (let i in arrayViajes) {
        viajesTotal = viajesTotal + 1
        if (arrayViajes[i].empresa === "RD299328") {
            totalRD = totalRD + 1
        }
        if (arrayViajes[i].empresa === "TA303898") {
            totalTA = totalTA + 1
        }
        if (arrayViajes[i].empresa === "PT099755") {
            totalPT = totalPT + 1
        }
        if (arrayViajes[i].empresa === "JO775022") {
            totalJO = totalJO + 1
        }
    }

    let porcentajeRD = (totalRD * 100) / viajesTotal
    let porcentajeTA = (totalTA * 100) / viajesTotal
    let porcentajeJO = (totalPT * 100) / viajesTotal
    let porcentajePT = (totalJO * 100) / viajesTotal

    let textoAMostrar = ""
    if (porcentajeRD != 0) {
        textoAMostrar += porcentajeRD + "% de los viajes con RD299328" + "<br>"
    }
    if (porcentajeTA != 0) {
        textoAMostrar += porcentajeTA + "% de los viajes con TA303898" + "<br>"
    }
    if (porcentajeJO != 0) {
        textoAMostrar += porcentajeJO + "% de los viajes con JO775022" + "<br>"
    }
    if (porcentajePT != 0) {
        textoAMostrar += porcentajePT + "% de los viajes con PT099755" + "<br>"
    }
    pMessage3.innerHTML = textoAMostrar
}

function generarTabla() { //Funcion auxuliar que genera la tabla de estadísticas de la función anterior.
    let pMessage2 = doc("calendarioEstadisticas");
    let textoDeTabla =
        `<tr>
        <th>Fecha de llegada</th>
        <th>Buque</th>
        <th>Contenedores</th>
        <th>Empresa</th>

    </tr>`;
    for (let i in arrayViajes) {
        let valorI = arrayViajes[i];
        textoDeTabla +=
            `<tr>
            <th>${valorI.fecha}</th>
            <th>${valorI.barco}</th>   
            <th>${valorI.contenedores}</th>   
            <th>${valorI.empresa}</th>   
        </tr>`;
    }

    pMessage2.innerHTML = textoDeTabla
}

function comparaFechaCreciente(solicitud1, solicitud2) { /*Función que dada 2 strings de fechas distintas, indica si una fecha 
                                                            viene antes o despues que la otra. Cabe aclarar que no las ordena. */
    return solicitud1.fecha.localeCompare(solicitud2.fecha);
}


function onRegistrarViaje() { /* Permite el ingreso y registro de viajes a los que se le asignarán solicitudes de carga, 
                                además de asegurarse que los datos hayan sido ingresados correctamente. */
    let formRegistroViajes = doc("formRegistroViajes");
    let barco = doc("inputShipName").value;
    let contenedores = Number(doc("inputMaxContenedores").value)
    let empresa = doc("selectLineaCarga").value
    let fecha = doc("inputFecha").value

    if (barco === "" || contenedores === "" || empresa === "" || fecha === "") {
        alert("Debe ingresar los datos correspondientes")
    } else {
        let viaje = new Viaje(barco, contenedores, empresa, fecha)
        arrayViajes.push(viaje)
        alert("Viaje registrado con éxito")
        cargarViajesSelect();
        formRegistroViajes.reset();
    }
}

function cargarViajesSelect() { /* Función aulixiar que inyecta todos los viajes en el HTML a través de un "select" con el que el usuario puede interactuar 
                                para asignar viajes posteriormente. */
    let texto = "";
    for (let i in arrayViajes) {
        let valorI = arrayViajes[i];
        texto += `<option value="${i}">${valorI.barco}  Capacidad: ${valorI.contenedores} contenedores</option>`
    }
    doc("selectViajes").innerHTML = texto
}

function onMostrarPendientes() { // Funcion que muestra los viajes pendientes para poder asignarles un viaje.
    let textoDeTabla =
        `<tr>
    <th>Tipo</th>
    <th>Descripción</th>
    <th>Puerto</th>
    <th>Contenedores</th>
    <th>Nombre</th>
    <th>Estado</th>
    </tr>`;
    for (let i in arraySolicitudes) {
        let valorI = arraySolicitudes[i];
        if (valorI.pendiente) {
            textoDeTabla +=
                `<tr>
                <th>${valorI.tipo}</th>   
                <th>${valorI.desc}</th>   
                <th>${valorI.puerto}</th>   
                <th>${valorI.contenedores}</th>  
                <th>${valorI.nombre}</th>  
                <th>${valorI.estado()}</th>  
                <th><input type="button" value="Asignar viaje" class="claseBotonAsignar" id="${valorI.numero + "-botonAsignar"}"></th>
            </tr>`;
        }

    }
    doc("tablaPendientes").innerHTML = textoDeTabla;
    let arrayBotonesAsignar = document.querySelectorAll(".claseBotonAsignar");
    for (let i = 0; i < arrayBotonesAsignar.length; i++) {
        let boton = arrayBotonesAsignar[i];
        boton.addEventListener("click", asignarViaje);
    }
}

function buscarObj(lista, nombreProp, valorProp) { // Función auxiliar que permite enlazar y comprobar datos entre objetos.
    let respuesta = null
    for (let i = 0; i < lista.length; i++) {
        let objActual = lista[i]
        if (objActual[nombreProp] === valorProp) {
            respuesta = objActual
        }
    }
    return respuesta
}

function asignarViaje() { // Función auxiliar que permite asignar viajes a solicitudes de carga pendientes.
    let indiceViaje = doc("selectViajes").value
    let viaje = arrayViajes[indiceViaje];
    let numeroSolicitud = parseInt(this.id)
    let solicitud = buscarObj(arraySolicitudes, "numero", numeroSolicitud);

    if (viaje.contenedores >= solicitud.contenedores) {
        solicitud.confirmar(viaje)
        viaje.agregarSolicitud(solicitud);
        alert("Viaje asignado correctamente")
        onMostrarPendientes()
        cargarViajesConfirmados()
        cargarViajesInspeccionables()
        viaje.contenedores=viaje.contenedores-solicitud.contenedores//Permite hacer un rollover de carga
        viaje.confirm();
    } else {
        alert("El viaje seleccionado no cuenta con suficientes contenedores disponibles, por favor seleccione otro")
    }

}

function cargarViajesConfirmados() {
    let texto = "";
    for (let i in arrayViajes) {
        let valorI = arrayViajes[i];
            texto += `<option value="${i}">${valorI.barco}  ${valorI.fecha}</option>`
    }
    doc("selectViajesEnCurso").innerHTML = texto
}

function onCheckManifiesto() {//Permite ver el manifiesto de carga de los viajes
    doc("tablaManifiesto").innerHTML
    let textoDeTabla =
        `<tr>
    <th>Tipo</th>
    <th>Descripción</th>
    <th>Puerto</th>
    <th>Contenedores</th>
    <th>Nombre</th>
    </tr>`;
    for (let i in arraySolicitudes) {
        let valorI = arraySolicitudes[i];
        if (valorI.confirmada) {
            textoDeTabla +=
                `<tr>
                <th>${valorI.tipo}</th>   
                <th>${valorI.desc}</th>   
                <th>${valorI.puerto}</th>   
                <th>${valorI.contenedores}</th>  
                <th>${valorI.nombre}</th>  
            </tr>`;
        }

    }
    doc("tablaManifiesto").innerHTML = textoDeTabla;
}

/*function selectSolicitudesCanceladas() { 
    let texto = "";
    for (let i in arraySolicitudes) {
        let valorI = arraySolicitudes[i];
        if(valorI.cancelada===true){
            texto += `<option value="${i}">Solicitud ${valorI.numero}: ${valorI.desc}</option>`
        }
    }
    doc("selectIgnorados").innerHTML = texto
}*/


function onMostrarCanceladas() {//Muestra todas las solicitudes que fueron canceladas para asi poder ignorarlas

    let textoDeTabla =
        `<tr>
    <th>Tipo</th>
    <th>Descripción</th>
    <th>Puerto</th>
    <th>Contenedores</th>
    <th>Nombre</th>
    <th>Estado</th>
    </tr>`;
    for (let i in arraySolicitudes) {
        let valorI = arraySolicitudes[i];
        if (valorI.cancelada) {
            textoDeTabla +=
                `<tr>
                <th>${valorI.tipo}</th>   
                <th>${valorI.desc}</th>   
                <th>${valorI.puerto}</th>   
                <th>${valorI.contenedores}</th>  
                <th>${valorI.nombre}</th>  
                <th>${valorI.estado()}</th>  
                <th><input type="button" value="Ignorar solicitud" class="claseBotonIgnorar" id="${valorI.numero + "-botonIgnorar"}"></th>
            </tr>`;
        }

    }
    doc("tablaCanceladas").innerHTML = textoDeTabla;
    let arrayBotonesIgnorar = document.querySelectorAll(".claseBotonIgnorar");
    for (let i = 0; i < arrayBotonesIgnorar.length; i++) {
        let boton = arrayBotonesIgnorar[i];
        boton.addEventListener("click", ignorarSolicitud);
    }
}

function ignorarSolicitud() {//Permite ignorar una solicitud previamente cancelada para asi re-habilitar al usuario importador
    let numeroSolicitud = parseInt(this.id);
    let pos = -1;
    for (let i = 0; i < arraySolicitudes.length && pos == -1; i++) {
        let valorI = arraySolicitudes[i];
        if (valorI.numero === numeroSolicitud) {
            pos = i;
        }
    }
    let respuesta = confirm("¿Está seguro que desea ignorar la solicitud cancelada número " + arraySolicitudes[pos].numero + "?");
    if (respuesta) {
        arraySolicitudes[pos].ignorar()
        onMostrarCanceladas();
    }
}

function cargarViajesInspeccionables() {
    let texto = "";
    for (let i in arrayViajes) {
        let valorI = arrayViajes[i];
        texto += `<option value="${i}">${valorI.barco}  ${valorI.fecha}</option>`
    }
    doc("selectViajeAInspeccionar").innerHTML = texto
}

function onCheckDanger() {//Permite ver las solicitudes que llevan carga peligrosa en cada viaje
    let textoDeTabla =
        `<tr>
    <th>Tipo</th>
    <th>Descripción</th>
    <th>Puerto</th>
    <th>Contenedores</th>
    <th>Nombre</th>
    </tr>`;
    for (let i in arraySolicitudes) {
        let valorI = arraySolicitudes[i];
        if (valorI.confirmada && valorI.tipo==="Carga Peligrosa") {
            textoDeTabla +=
                `<tr>
                <th>${valorI.tipo}</th>   
                <th>${valorI.desc}</th>   
                <th>${valorI.puerto}</th>   
                <th>${valorI.contenedores}</th>  
                <th>${valorI.nombre}</th>  
            </tr>`;
        }

    }
    doc("tablaCargaPeligrosa").innerHTML = textoDeTabla;
}
