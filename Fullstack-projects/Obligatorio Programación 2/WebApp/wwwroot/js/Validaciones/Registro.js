//serie de validaciones generales realizadas en javascript para loscampos de formularios del registro

document.querySelector("#registro").addEventListener('submit', validarDatosRegistro); 


function validarDatosRegistro(evt) {
    evt.preventDefault();

    let numDoc = document.querySelector("#numDoc").value;
    let nombreHuesped = document.querySelector("#nombreHuesped").value;
    let apellidoHuesped = document.querySelector("#apellidoHuesped").value;
    let habitacion = document.querySelector("#habitacion").value;
    let fechaNacimiento = document.querySelector("#fechaNacimiento").value;
    let emailRegistro = document.querySelector("#emailRegistro").value;
    let contra = document.querySelector("#contra").value;

    if (numDoc != "" && nombreHuesped != "" && apellidoHuesped != "" && habitacion != "" && fechaNacimiento != "" && emailRegistro != "" && contra != "") {

        this.submit();


    } else {

        document.querySelector("#Mensaje").innerHTML = "Los datos no pueden ser vacios";
    }
}