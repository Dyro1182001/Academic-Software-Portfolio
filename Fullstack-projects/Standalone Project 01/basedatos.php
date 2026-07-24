<?php
    $host = "localhost";
    $user = "root";
    $pass = "";
    $db   = "prueba_tecnica_rodrigo_delgado";

    // Conexión a MySQL usando mysqli
    $conn = new mysqli($host, $user, $pass, $db);

    // Verifica si hubo error en la conexión
    // Si ocurre un error, se detiene la ejecución y se muestra el mensaje correspondiente
    if ($conn->connect_error) {
        die("Error de conexión: " . $conn->connect_error);
    }
?>