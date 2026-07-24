<?php
    // Respuesta del servidor en JSON para peticiones AJAX
    header('Content-Type: application/json; charset=utf-8');

    // Conexión a la base de datos
    require 'basedatos.php';

    // Datos del formulario
    // trim() para eliminar espacios innecesarios
    $nombre   = isset($_POST['nombre'])   ? trim($_POST['nombre'])   : '';
    $email    = isset($_POST['email'])    ? trim($_POST['email'])    : '';
    $telefono = isset($_POST['telefono']) ? trim($_POST['telefono']) : '';

    // Verificación de que no queden vacios los campos obligatorios
    if ($nombre === '' || $email === '') {
        echo json_encode(["ok" => false, "msg" => "Nombre y Email son obligatorios."]); exit;
    }

    // Validación formato nombre (solo letras, espacios y acentos, entre 2 y 60 caracteres)
    if (!preg_match('/^[a-zA-ZÀ-ÿ\s]{2,60}$/u', $nombre)) {
        echo json_encode(["ok" => false, "msg" => "Nombre inválido."]); exit;
    }

    // Validación formato email
    if (!filter_var($email, FILTER_VALIDATE_EMAIL)) {
        echo json_encode(["ok" => false, "msg" => "Email inválido."]); exit;
    }

    // Validación formato teléfono (si se completo)
    if ($telefono !== '' && !preg_match('/^[0-9()+\-\s]{6,20}$/', $telefono)) {
        echo json_encode(["ok" => false, "msg" => "Teléfono inválido."]); exit;
    }

    // Si el teléfono está vacío, se normaliza como NULL
    // Esto evita errores con la restricción UNIQUE en la base de datos
    $telefono = ($telefono === '') ? null : $telefono;

    //  Verificación de duplicados
    // Comprobación de si existe ya el mismo email o teléfono en la base de datos
    if ($telefono !== null) {
        $stmt = $conn->prepare("SELECT id FROM usuarios WHERE email = ? OR telefono = ?");
        $stmt->bind_param("ss", $email, $telefono);
    } else {
        $stmt = $conn->prepare("SELECT id FROM usuarios WHERE email = ?");
        $stmt->bind_param("s", $email);
    }

    $stmt->execute();
    $res = $stmt->get_result();

    if ($res->num_rows > 0) {
        echo json_encode(["ok" => false, "msg" => "El correo o teléfono ya están registrados."]); exit;
    }
    $stmt->close();

    //  Inserción del nuevo registro
    $stmt = $conn->prepare("INSERT INTO usuarios (nombre, email, telefono) VALUES (?, ?, ?)");
    $stmt->bind_param("sss", $nombre, $email, $telefono);

    // Si ocurre un error al ejecutar la inserción, devuelve un mensaje de error JSON
    if (!$stmt->execute()) {
        echo json_encode(["ok" => false, "msg" => "Error al guardar los datos."]); exit;
    }

    $newId = $stmt->insert_id;
    $stmt->close();

    //  Recuperación del registro recién insertado
    $stmt = $conn->prepare("SELECT id, nombre, email, telefono, fecha_registro FROM usuarios WHERE id = ?");
    $stmt->bind_param("i", $newId);
    $stmt->execute();
    $item = $stmt->get_result()->fetch_assoc();
    $stmt->close();

    //  Respuesta final
    echo json_encode(["ok" => true, "msg" => "Datos guardados correctamente.", "item" => $item]);
?>
