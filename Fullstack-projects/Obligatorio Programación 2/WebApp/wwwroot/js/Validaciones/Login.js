document.querySelector("#login").addEventListener('submit', validarDatosLogin);

function validarDatosLogin(evt) {
    evt.preventDefault();

    let email = document.querySelector("#Email").value;
    let password = document.querySelector("#Password").value;

    if (email != "" && password != "") {

        this.submit();


    } else {

        document.querySelector("#Mensaje").innerHTML = "Los datos no pueden ser vacios";
    }
}