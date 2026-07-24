let idEmpresa = 1;

class Empresa {
    constructor(nombre, usuario, contraseña) {
        this.id = idEmpresa++
        this.nombre = nombre
        this.usuario = usuario
        this.contraseña = contraseña
    }
}

let idImportador = 1;

class Importador {
    constructor(nombre, usuario, contraseña, foto) {
        this.id = idImportador++
        this.nombre = nombre
        this.usuario = usuario
        this.contraseña = contraseña
        this.foto = foto
    }
}

let idContenido = 1;

class Solicitud {
    constructor(tipo, desc, puerto, contenedores, nombre) {
        this.tipo = tipo
        this.desc = desc
        this.puerto = puerto
        this.contenedores = contenedores
        this.nombre = nombre
        this.numero = idContenido++;
        this.confirmada = false
        this.pendiente = true //activo
        this.cancelada = false
        this.ignorada = false
        this.viaje = null;
    }

    estado() {
        let resp = "Ignorada";
        if (this.pendiente) {
            resp = "Pendiente";
        }
        if (this.cancelada) {
            resp = "Cancelada";
        }
        if (this.confirmada) {
            resp = "Confirmada";
        }
        return resp;
    }

    confirmar(unViaje) {
        this.confirmada = true;
        this.pendiente = false;
        this.cancelada = false;
        this.ignorada = false;
        this.viaje = unViaje;
    }

    cancelar() {
        this.confirmada = false;
        this.pendiente = false;
        this.cancelada = true;
        this.ignorada = false;
    }

    ignorar() {
        this.confirmada = false;
        this.pendiente = false;
        this.cancelada = false;
        this.ignorada = true;
    }
}


let idViaje = 1;

class Viaje {
    constructor(nombreBarco, maxContenedores, lineaDeCarga, fechaLlegada) {
        this.barco = nombreBarco
        this.contenedores = maxContenedores
        this.empresa = lineaDeCarga
        this.fecha = fechaLlegada
        this.solicitudes = [];
        this.confirmed=false;
        this.pending=true;
    }

    agregarSolicitud(unaSolicitud) {
        this.solicitudes.push(unaSolicitud);
        
    }

    confirm(){
        this.confirmed=true;
        this.pending=false;
    }
}