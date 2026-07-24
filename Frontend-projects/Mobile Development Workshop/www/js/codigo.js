Inicio();

function Inicio() {
    // let hayToken = localStorage.getItem('apikey');
    // if(hayToken){
    //     Logout();
    // }
    Eventos();
    ArmarMenu();
    recargarComboDepartamentos();
}

let idUsuarioLogin = "";

function Eventos() {
    // HACERLO CON UN EVENTO DE RUTEO ES MAS EFICIENTE QUE HACERLO CON EVENTO DE CLICK
    ROUTER.addEventListener('ionRouteDidChange', Navegar);
    // CREAMOS EL EVENTO PARA EL BOTON DE GUARDAR
    document.querySelector("#btnRegistrar").addEventListener('click', TomarDatosRegistro);
    document.querySelector("#btnLogin").addEventListener('click', TomarDatosLogin);
    document.querySelector("#selectDep").addEventListener('ionChange', ArmarSelectCiudades);
    document.querySelector("#btnAgregarGasto").addEventListener('click', AgregarGasto);
    document.querySelector("#btnAgregarIngreso").addEventListener('click', AgregarIngreso);
    document.querySelector("#btnFiltrarMovimiento").addEventListener('click', FiltrarMovimientos);
}

// ACA LA FUNCION VA A TOMAR EL OBJETO DE EVENTO QUE SALE DE ARRIBA (EVENT OBJECT)
// ESTA FUNCION SE LE CONOCE COMO UN EVENT HANDLER
function Navegar(evt) {
    OcultarTodo();
    console.log(evt);
    let ruta = evt.detail.to;
    if (ruta == "/") {
        HOME.style.display = "block";
    } else if (ruta == "/login") {
        LOGIN.style.display = "block";
    } else if (ruta == "/registro") {
        REGISTRO.style.display = "block";
    } else if (ruta == "/gastos") {
        GASTOS.style.display = "block";
    } else if (ruta == '/ingresos') {
        INGRESOS.style.display = "block";
    } else if (ruta == '/movimientos') {
        MOVIMIENTOS.style.display = 'block';
        verMovimientos();
    } else if (ruta == '/montos') {
        MONTOS.style.display = "block";
        verMontosTotales();
    } else if (ruta == "/mapa") {
        MAPA.style.display = "block";
        GetPosition();
    }
}

function OcultarTodo() {
    HOME.style.display = "none";
    LOGIN.style.display = "none";
    REGISTRO.style.display = "none";
    GASTOS.style.display = "none";
    INGRESOS.style.display = "none";
    MOVIMIENTOS.style.display = 'none';
    MONTOS.style.display = 'none';
    MAPA.style.display = 'none';

}

function CerrarMenu() {
    // TODAS LAS VECES QUE QUIERA CERRAR EL MENU UTILIZO ESTA FUNCION
    MENU.close();
}

function recargarComboDepartamentos() {
    fetch(URLBASE + 'departamentos.php')
        .then(res => res.json())
        .then(listaDepartamentos => {
            DEPARTAMENTO.innerHTML = `<ion-select-option value="">Seleccione una opción</ion-select-option>`
            for (const departamento of listaDepartamentos.departamentos) {
                DEPARTAMENTO.innerHTML += `<ion-select-option value=${departamento.id}>${departamento.nombre}</ion-select-option>`;
            }
        })
        .catch(error => console.log(error))
}

function ArmarSelectCiudades(evt) {
    fetch(URLBASE + 'ciudades.php?idDepartamento=' + evt.detail.value, {
        method: 'GET',
        headers: {
            'Content-Type': 'application.json',
        },
    })
        .then(res => res.json())
        .then((listaCiudades) => {
            CIUDAD.innerHTML = `<ion-select-option value="">Seleccione una opción</ion-select-option>`
            for (const ciudad of listaCiudades.ciudades) {
                CIUDAD.innerHTML += `<ion-select-option value=${ciudad.id}>${ciudad.nombre}</ion-select-option>`;
            }
        })
        .catch(error => console.log(error))
}

function TomarDatosRegistro() {
    presentLoading();
    let usuario = document.querySelector("#txtRegistroUser").value;
    let password = document.querySelector("#txtRegistroPassword").value;
    let idDepartamento = document.querySelector("#selectDep").value;
    let idCiudad = document.querySelector("#selectCity").value;

    let user = new Usuario(usuario, password, idDepartamento, idCiudad);

    fetch(`${URLBASE}usuarios.php`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        // ESTO HACE QUE TRANSFORMEMOS DE JAVASCRIPT VALUE A JSON STRING, LE PASO LA VARIABLE USER CREADA EN LA FUNCION
        body: JSON.stringify(user)
    }).then(function (res) {
        if (res.status != 200) {
            throw res;
        }
        console.log(res);
        return res.json();
    })
        .then((data) => {
            loading.dismiss();
            idUsuarioLogin = data.id;
            //document.querySelector("#pRegistroRes").innerHTML = 'Registro exitoso'
            presentAlert("Usuario registrado con exito", " ", "Bienvenido");
            let token = data.apiKey;
            localStorage.setItem('apikey', token);
            ArmarMenu();
            NAV.push('page-home');
            console.log(data);
        })
        .catch(function (e) {
            console.log('El problema fue ' + e);
            document.querySelector('#pRegistroRes').innerHTML = e.Response;
        })
}

function TomarDatosLogin() {
    presentLoading();
    let usuario = document.querySelector('#txtLoginUser').value;
    let password = document.querySelector('#txtLoginPassword').value;

    let dto = new LoginDTO(usuario, password);

    fetch(`${URLBASE}login.php`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(dto)
    }).then(function (res) {
        if (res.status == 200) {
            return res.json();
        } else {
            throw res;
        }
    })
        .then(function (data) {
            idUsuarioLogin = data.id;
            loading.dismiss();
            let token = data.apiKey;
            localStorage.setItem('apikey', token);
            NAV.push('page-home');
            ArmarMenu();
        }).catch(e => {
            console.log('El problema fue ' + e.status);
            document.querySelector('#pLoginRes').innerHTML = 'Estamos en el catch';
        })
}

function ArmarMenu() {
    let hayToken = localStorage.getItem('apikey');
    document.querySelector('#menu-opciones').innerHTML = ``;
    if (hayToken) {
        document.querySelector('#menu-opciones').innerHTML = ` <ion-item href="/" onclick="CerrarMenu()">Home</ion-item>
        <ion-item href="/gastos" onclick="CerrarMenu()">Gastos</ion-item>
        <ion-item id='' href="/ingresos" onclick="CerrarMenu()">Ingresos</ion-item>
        <ion-item href="/movimientos" onclick="CerrarMenu()">Movimientos</ion-item>
        <ion-item href="/montos" onclick="CerrarMenu()">Montos</ion-item>
        <ion-item href="/mapa" onclick="CerrarMenu()">Cajeros Cercanos</ion-item>
        <ion-item href="/" onclick="Logout()">Cerrar sesion</ion-item>`;
        recargarComboRubros();

    } else {
        document.querySelector('#menu-opciones').innerHTML = `<ion-item href="/" onclick="CerrarMenu()">Home</ion-item>
        <ion-item href="/login" onclick="CerrarMenu()">Login</ion-item>
        <ion-item href="/registro" onclick="CerrarMenu()">Registro</ion-item>`
    }
}

function Logout() {
    localStorage.clear();
    CerrarMenu();
    NAV.push('page-home')
    ArmarMenu();
}

const loading = document.createElement('ion-loading');

function presentLoading() {

    loading.message = 'Cargando',
        loading.duration = 0;

    document.body.appendChild(loading);
    loading.present();

    const { role, data } = loading.onDidDismiss();

    console.log('Loading dismissed!');
}

function recargarComboRubros() {

    fetch(URLBASE + 'rubros.php', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'apikey': localStorage.getItem('apikey'),
        },
    })
        .then(res => res.json())
        .then(listaGastosRubros => {
            console.log(listaGastosRubros);
            document.querySelector('#selectGastoRubro').innerHTML = `<ion-select-option value="">Seleccione una opcion</ion-select-option>`
            document.querySelector('#selectIngresoRubro').innerHTML = `<ion-select-option value="">Seleccione una opcion</ion-select-option>`
            for (const rubro of listaGastosRubros.rubros) {
                if (rubro.tipo == 'gasto') {
                    document.querySelector('#selectGastoRubro').innerHTML += `<ion-select-option value=${rubro.id}>${rubro.nombre}</ion-select-option>`;

                } else if (rubro.tipo == 'ingreso') {
                    document.querySelector('#selectIngresoRubro').innerHTML += `<ion-select-option value=${rubro.id}>${rubro.nombre}</ion-select-option>`;
                }
            }
        })
        .catch(error => console.log(error))
}

function AgregarGasto() {
    presentLoading();
    let idUsuario = Number(idUsuarioLogin);
    let concepto = document.querySelector('#txtDescGasto').value;
    let categoria = document.querySelector('#selectGastoRubro').value;
    let total = document.querySelector('#txtTotalGasto').value;
    let medio = document.querySelector('#selectGastoMedio').value;
    let fecha = document.querySelector('#GastoFecha').value;

    let nuevoGasto = new Movimiento(idUsuario, concepto, categoria, total, medio, fecha);

    fetch(URLBASE + 'movimientos.php', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'apikey': localStorage.getItem('apikey'),
        },
        body: JSON.stringify(nuevoGasto)
    }).then(function (res) {
        if (res.status != 200) {
            throw res;
        }
        console.log(res);
        return res.json();
    })
        .then((data) => {
            loading.dismiss();
            presentAlert("Gasto confirmado", " ", "Su gasto se ha registrado con éxito")
            //document.querySelector("#pGastoRes").innerHTML = 'Gasto confirmado'
            // console.log(nuevoGasto.fecha);
        })
        .catch(function (e) {
            console.log('El problema fue ' + e);
            document.querySelector('#pGastoRes').innerHTML = e.Response;
        })
}

function AgregarIngreso() {
    presentLoading();
    let idUsuario = Number(idUsuarioLogin);
    let concepto = document.querySelector('#txtDescIngreso').value;
    let categoria = document.querySelector('#selectIngresoRubro').value;
    let total = document.querySelector('#txtTotalIngreso').value;
    let medio = document.querySelector('#selectIngresoMedio').value;
    let fecha = document.querySelector('#IngresoFecha').value;

    let nuevoIngreso = new Movimiento(idUsuario, concepto, categoria, total, medio, fecha);

    fetch(URLBASE + 'movimientos.php', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'apikey': localStorage.getItem('apikey'),
        },
        body: JSON.stringify(nuevoIngreso)
    }).then(function (res) {
        if (res.status != 200) {
            throw res;
        }
        console.log(res);
        return res.json();
    })
        .then((data) => {
            loading.dismiss();
            presentAlert("Ingreso confirmado", " ", "Su ingreso se ha registrado con éxito")
            //document.querySelector("#pIngresoRes").innerHTML = 'Ingreso confirmado'
            console.log(nuevoIngreso.fecha);
        })
        .catch(function (e) {
            console.log('El problema fue ' + e);
            document.querySelector('#pIngresoRes').innerHTML = e.Response;
        })
}

function verMovimientos() {
    fetch(URLBASE + 'movimientos.php?idUsuario=' + idUsuarioLogin, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'apikey': localStorage.getItem('apikey'),
        }
    }).then(function (res) {
        if (res.status != 200) {
            throw res;
        }
        console.log(res);
        return res.json();
    })
        .then((listaMovimientos) => {
            console.log('hola');
            document.querySelector('#tablaMovimientos').innerHTML = `
        <ion-row>
        <ion-col size="auto">Descripcion</ion-col>
        <ion-col size="auto">Categoria</ion-col>
        <ion-col size="auto">Total</ion-col>
        <ion-col size="auto">Medio</ion-col>
        <ion-col size="auto">Fecha</ion-col>
        <ion-col size="auto">Eliminar</ion-col>
        </ion-row>`
            console.log(listaMovimientos.movimientos);
            for (const movimiento of listaMovimientos.movimientos) {
                if (movimiento.categoria <= 6) {
                    document.querySelector('#tablaMovimientos').innerHTML += `<ion-row>
                <ion-col size="auto">${movimiento.concepto}</ion-col>
                <ion-col size="auto">Gasto</ion-col>
                <ion-col size="auto">${movimiento.total}</ion-col>
                <ion-col size="auto">${movimiento.medio}</ion-col>
                <ion-col size="auto">${movimiento.fecha}</ion-col>
                <ion-col size="auto"><ion-button class='btnEliminar' id="${movimiento.id}" color="danger" shape="round" size="small">Eliminar</ion-button></ion-col>
                </ion-row>`
                } else {
                    document.querySelector('#tablaMovimientos').innerHTML += `<ion-row>
                <ion-col size="auto">${movimiento.concepto}</ion-col>
                <ion-col size="auto">Ingreso</ion-col>
                <ion-col size="auto">${movimiento.total}</ion-col>
                <ion-col size="auto">${movimiento.medio}</ion-col>
                <ion-col size="auto">${movimiento.fecha}</ion-col>
                <ion-col size="auto"><ion-button class='btnEliminar' id="${movimiento.id}" color="danger" shape="round" size="small">Eliminar</ion-button></ion-col>
                </ion-row>`
                }
            }

            let botones = document.querySelectorAll(".btnEliminar");
            for (let b of botones) {
                b.addEventListener('click', eliminarMovimiento);
            }
        })
        .catch(function (e) {
            console.log('El problema fue ' + e);
            //document.querySelector('#pGastoRes').innerHTML = e.Response;
        })
}

function eliminarMovimiento() {
    presentLoading();
    let idMovimiento = new Boton(Number(this.getAttribute('id')));
    fetch(URLBASE + 'movimientos.php', {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
            'apikey': localStorage.getItem('apikey'),
        },
        body: JSON.stringify(idMovimiento)
    }).then(function (res) {
        if (res.status != 200) {
            throw res;
        }
        console.log(res);
        return res.json();
    })
        .then((data) => {
            presentAlert('¡Movimiento eliminado!', " ", "El movimiento ha sido eliminado con exito.");
            FiltrarMovimientos();
            loading.dismiss();
        })
        .catch(function (e) {
            console.log('El problema fue ' + e);
            //document.querySelector('#pGastoRes').innerHTML = e.Response;
        })
}

function FiltrarMovimientos() {
    let filtro = document.querySelector("#selectFiltro").value;
    presentLoading();

    fetch(URLBASE + 'movimientos.php?idUsuario=' + idUsuarioLogin, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'apikey': localStorage.getItem('apikey'),
        }
    }).then(function (res) {
        if (res.status != 200) {
            throw res;
        }
        console.log(res);
        return res.json();
    })
        .then((listaMovimientos) => {
            console.log('hola');
            document.querySelector('#tablaMovimientos').innerHTML = `
        <ion-row>
        <ion-col size="auto">Descripcion</ion-col>
        <ion-col size="auto">Categoria</ion-col>
        <ion-col size="auto">Total</ion-col>
        <ion-col size="auto">Medio</ion-col>
        <ion-col size="auto">Fecha</ion-col>
        <ion-col size="auto">Eliminar</ion-col>
        </ion-row>`
            console.log(listaMovimientos.movimientos);
            if (filtro == "NoFiltro") {
                for (const movimiento of listaMovimientos.movimientos) {
                    if (movimiento.categoria <= 6) {
                        document.querySelector('#tablaMovimientos').innerHTML += `<ion-row>
                    <ion-col size="auto">${movimiento.concepto}</ion-col>
                    <ion-col size="auto">Gasto</ion-col>
                    <ion-col size="auto">${movimiento.total}</ion-col>
                    <ion-col size="auto">${movimiento.medio}</ion-col>
                    <ion-col size="auto">${movimiento.fecha}</ion-col>
                    <ion-col size="auto"><ion-button class='btnEliminar' id="${movimiento.id}" color="danger" shape="round" size="small">Eliminar</ion-button></ion-col>
                    </ion-row>`
                        //document.querySelector(`#${movimiento.id}`).addEventListener('click', eliminarMovimiento);
                    } else {
                        document.querySelector('#tablaMovimientos').innerHTML += `<ion-row>
                    <ion-col size="auto">${movimiento.concepto}</ion-col>
                    <ion-col size="auto">Ingreso</ion-col>
                    <ion-col size="auto">${movimiento.total}</ion-col>
                    <ion-col size="auto">${movimiento.medio}</ion-col>
                    <ion-col size="auto">${movimiento.fecha}</ion-col>
                    <ion-col size="auto"><ion-button class='btnEliminar' id="${movimiento.id}" color="danger" shape="round" size="small">Eliminar</ion-button></ion-col>
                    </ion-row>`
                        //document.querySelector(`#${movimiento.id}`).addEventListener('click', eliminarMovimiento);
                    }

                }
            } else if (filtro == "Gasto") {
                for (const movimiento of listaMovimientos.movimientos) {
                    if (movimiento.categoria <= 6) {
                        document.querySelector('#tablaMovimientos').innerHTML += `<ion-row>
                    <ion-col size="auto">${movimiento.concepto}</ion-col>
                    <ion-col size="auto">Gasto</ion-col>
                    <ion-col size="auto">${movimiento.total}</ion-col>
                    <ion-col size="auto">${movimiento.medio}</ion-col>
                    <ion-col size="auto">${movimiento.fecha}</ion-col>
                    <ion-col size="auto"><ion-button class='btnEliminar' id="${movimiento.id}" color="danger" shape="round" size="small">Eliminar</ion-button></ion-col>
                    </ion-row>`
                        //document.querySelector(`#${movimiento.id}`).addEventListener('click', eliminarMovimiento);
                    }
                }
            } else if (filtro == "Ingreso") {
                for (const movimiento of listaMovimientos.movimientos) {
                    if (movimiento.categoria >= 7) {
                        document.querySelector('#tablaMovimientos').innerHTML += `<ion-row>
                    <ion-col size="auto">${movimiento.concepto}</ion-col>
                    <ion-col size="auto">Ingreso</ion-col>
                    <ion-col size="auto">${movimiento.total}</ion-col>
                    <ion-col size="auto">${movimiento.medio}</ion-col>
                    <ion-col size="auto">${movimiento.fecha}</ion-col>
                    <ion-col size="auto"><ion-button class='btnEliminar' id="${movimiento.id}" color="danger" shape="round" size="small">Eliminar</ion-button></ion-col>
                    </ion-row>`
                        //document.querySelector(`#${movimiento.id}`).addEventListener('click', eliminarMovimiento);
                    }
                }

            }

            let botones = document.querySelectorAll(".btnEliminar");
            for (let b of botones) {
                b.addEventListener('click', eliminarMovimiento);
            }
            loading.dismiss();
        })
        .catch(function (e) {
            console.log('El problema fue ' + e);
            //document.querySelector('#pGastoRes').innerHTML = e.Response;
        })
}

function verMontosTotales() {
    presentLoading();
    fetch(URLBASE + 'movimientos.php?idUsuario=' + idUsuarioLogin, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'apikey': localStorage.getItem('apikey'),
        }
    }).then(function (res) {
        if (res.status != 200) {
            throw res;
        }
        console.log(res);
        return res.json();
    })
        .then((listaMovimientos) => {
            console.log('hola');
            console.log(listaMovimientos.movimientos);
            document.querySelector('#pMontosIngresosRes').innerHTML = ``
            document.querySelector('#pMontosGastosRes').innerHTML = ``
            document.querySelector('#pMontosSaldoRes').innerHTML = ``
            let ingresosTotales = 0;
            for (const movimiento of listaMovimientos.movimientos) {
                if (movimiento.categoria >= 7) {
                    ingresosTotales += movimiento.total;
                }
            }
            document.querySelector("#pMontosIngresosRes").innerHTML = `Sus ingresos totales son: $${ingresosTotales}.`;
            let gastosTotales = 0;
            for (const movimiento of listaMovimientos.movimientos) {
                if (movimiento.categoria <= 6) {
                    gastosTotales += movimiento.total;
                }
            }
            document.querySelector("#pMontosGastosRes").innerHTML = `Sus gastos totales son: $${gastosTotales}.`;
            let saldoTotal = ingresosTotales - gastosTotales
            document.querySelector("#pMontosSaldoRes").innerHTML = `Su saldo total es de: $${saldoTotal}.`;
            loading.dismiss();
        })
        .catch(function (e) {
            console.log('El problema fue ' + e);
            //document.querySelector('#pGastoRes').innerHTML = e.Response;
        })
}

// ACA VOY A PROBAR

function presentAlert(header, subHeader, msg) {
    const alert = document.createElement('ion-alert');

    alert.header = header
    alert.subHeader = subHeader;
    alert.message = msg;
    alert.buttons = ['OK'];

    document.body.appendChild(alert);
    alert.present();
}

let MiLat = null
let MiLong = null

function GetPosition() {
    if (navigator.geolocation) {
        presentLoading();
        navigator.geolocation.getCurrentPosition(MiUbicacion);
    } else {
        presentAlert("Error", " ", "Su dispositivo no soporta el GPS");
    }
}

function MiUbicacion(position) {
    MiLat = position.coords.latitude;
    MiLong = position.coords.longitude;
    CrearMapa();
}

function CrearMapa() {
    loading.dismiss();
    //Crear Mapa
    var map = L.map("map").setView([MiLat, MiLong], 15);
    L.tileLayer("https://tile.openstreetmap.org/{z}/{x}/{y}.png", {
        maxZoom: 19,

        attribution:
            '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors',
    }).addTo(map);

    var redIcon = new L.Icon({
        iconUrl:
            "https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-red.png",
        shadowUrl:
            "https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png",
        iconSize: [25, 41],
        iconAnchor: [12, 41],
        popupAnchor: [1, -34],
        shadowSize: [41, 41],
    });

    let ubicacionUser = L.marker([MiLat, MiLong], { icon: redIcon }).addTo(map);
    ubicacionUser.bindPopup("<strong>Usted esta</strong>");

    fetch(URLBASE + 'cajeros.php', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',

        }
    }).then(function (res) {
        if (res.status != 200) {
            throw res;
        }
        console.log(res);
        return res.json();
    })
        .then((listaCajeros) => {
            console.log(listaCajeros)
            for (const cajero of listaCajeros.cajeros) {
                var greenIcon = new L.Icon({
                    iconUrl: 'https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-2x-green.png',
                    shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png',
                    iconSize: [25, 41],
                    iconAnchor: [12, 41],
                    popupAnchor: [1, -34],
                    shadowSize: [41, 41]
                });
                let marcadorCajero = L.marker([cajero.latitud, cajero.longitud], { icon: greenIcon }).addTo(map);
                if (cajero.depositos == 1 && cajero.disponible == 1) {
                    marcadorCajero.bindPopup("<span>Depósitos: SI</span><br><span>Disponible: SI</span>").addTo(map);
                } else if (cajero.depositos == 1 && cajero.disponible == 0) {
                    marcadorCajero.bindPopup("<span>Depósitos: SI</span><br><span>Disponible: NO</span>").addTo(map);
                } else if (cajero.depositos == 0 && cajero.disponible == 1) {
                    marcadorCajero.bindPopup("<span>Depósitos: NO</span><br><span>Disponible: SI</span>").addTo(map);
                } else if (cajero.depositos == 0 && cajero.disponible == 0) {
                    marcadorCajero.bindPopup("<span>Depósitos: NO</span><br><span>Disponible: NO</span>").addTo(map);
                }
            }
        })
        .catch(function (e) {
            console.log('El problema fue ' + e);
            //document.querySelector('#pGastoRes').innerHTML = e.Response;
        })
}

function Share() {
    Capacitor.Plugins.Share.share({
        title: `Enviar`,
        text: `Descargala!`,
        url: 'https://www.notengourl.com',
        dialogTitle: 'Gracias!',
    })
}

