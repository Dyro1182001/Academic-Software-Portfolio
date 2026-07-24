
function ocultarTodas() {
    document.querySelector("#divInicial").style.display = "none";
    document.querySelector("#divEmpresa").style.display = "none";
    document.querySelector("#divImportador").style.display = "none";
}

function mostrar(id) {
    ocultarTodas();
    document.querySelector("#" + id).style.display = "block";
}

