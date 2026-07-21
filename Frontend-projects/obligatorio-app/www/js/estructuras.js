const URLBASE = "https://dwallet.develotion.com/";

const MENU = document.querySelector("#menu");
const ROUTER = document.querySelector("#ruteo");
const HOME = document.querySelector("#pantalla-home");
const LOGIN = document.querySelector("#pantalla-login");
const REGISTRO = document.querySelector("#pantalla-registro");
const DEPARTAMENTO = document.querySelector("#selectDep");
const CIUDAD = document.querySelector('#selectCity');
const NAV = document.querySelector('ion-nav');
const GASTOS = document.querySelector('#pantalla-gastos');
const INGRESOS = document.querySelector('#pantalla-ingresos');
const MOVIMIENTOS = document.querySelector('#pantalla-movimientos');
const MONTOS = document.querySelector('#pantalla-montos');
const MAPA = document.querySelector("#pantalla-mapa");
// EL ION ROUTER TIENE EVENTOS, DID CHANGE Y WILL CHANGE, ESTOS SE DISPARAN CUANDO CAMBIO Y CUANDO QUIERE CAMBIAR DE DIRECCION

class Usuario {
    constructor(usuario, password, idDepartamento, idCiudad) {
        this.usuario = usuario;
        this.password = password;
        this.idDepartamento = Number(idDepartamento);
        this.idCiudad = Number(idCiudad);
    }
}

class LoginDTO {
    constructor(usuario, password) {
        this.usuario = usuario;
        this.password = password;
    }
}

class Movimiento {
    constructor(idUsuario, concepto, categoria, total, medio, fecha) {
        this.idUsuario = Number(idUsuario);
        this.concepto = concepto;
        this.categoria = Number(categoria);
        this.total = Number(total);
        this.medio = medio;
        this.fecha = fecha;
    }
}

class Boton {
    constructor(idMovimiento) {
        this.idMovimiento = idMovimiento;
    }
}