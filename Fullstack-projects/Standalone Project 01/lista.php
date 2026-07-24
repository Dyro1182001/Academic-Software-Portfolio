<?php
    header('Content-Type: application/json; charset=utf-8');

    // Conexión a la base de datos
    require 'basedatos.php';

    //  Configuración de paginación

    $perPage = 8; // Número de registros por página
    // Si el número de página no se especifica o es menor que 1, se usa la 1 por defecto
    $page = isset($_GET['page']) ? max(1, intval($_GET['page'])) : 1;

    // Calcula el desplazamiento (OFFSET) según la página actual
    $offset = ($page - 1) * $perPage;

    //  Obtenención total de registros

    // Consulta simple para contar el total de filas en la tabla "usuarios"
    $totalRes = $conn->query("SELECT COUNT(*) AS total FROM usuarios");
    $totalRow = $totalRes->fetch_assoc(); // Resultado como array asociativo
    $total = intval($totalRow['total']);  // Convierte el total a número entero

    // Calcula el número total de páginas (redondeando hacia arriba)
    $totalPages = max(1, (int)ceil($total / $perPage));

    //  Consultar los registros de la página actual

    // Se seleccionan los campos necesarios ordenados del más nuevo al más viejo
    $sql = sprintf(
        "SELECT id, nombre, email, telefono, fecha_registro
        FROM usuarios
        ORDER BY fecha_registro DESC, id DESC
        LIMIT %d OFFSET %d",
        (int)$perPage, (int)$offset
    );

    $result = $conn->query($sql);
    $data = [];

    // Si la consulta devuelve resultados, los recorre fila por fila y los guarda en el array
    if ($result) {
        while ($row = $result->fetch_assoc()) {
            $data[] = $row;
        }
    }

    //  Enviar la respuesta

    // Devuelve la información necesaria al frontend:
    // - ok: estado de éxito
    // - page: número de página actual
    // - per_page: cantidad de registros por página
    // - total: cantidad total de registros
    // - total_pages: número total de páginas
    // - data: registros de la página actual
    echo json_encode([
        "ok" => true,
        "page" => $page,
        "per_page" => $perPage,
        "total" => $total,
        "total_pages" => $totalPages,
        "data" => $data
    ]);
?>
