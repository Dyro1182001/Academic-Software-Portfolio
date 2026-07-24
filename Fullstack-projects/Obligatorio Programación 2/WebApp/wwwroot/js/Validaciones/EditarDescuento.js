document.querySelector("#editarDescuento").addEventListener('submit', validarEdicionDescuento); 

function validarEdicionDescuento(evt) {
    evt.preventDefault();
    let descuento = parseInt(document.querySelector("#Descuento").value);

    if ((descuento <= 100 && descuento >= 0) && descuento != "") {

        this.submit();


    } else {

        document.querySelector("#Mensaje").innerHTML = "El porcentaje ingresado está por fuera de los limites aceptados (0 y 100)";
    }
}