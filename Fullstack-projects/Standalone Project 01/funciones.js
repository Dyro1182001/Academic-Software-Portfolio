$(function () {

    //  Referencias a elementos del html
    const $form = $("#formulario");
    const $msg = $("#mensaje");
    const $list = $("#listado");
    const $pager = $("#paginador");
    const $meta = $("#metaListado");

    // Página actual del paginador
    let currentPage = 1;

    // Validación de campos
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    const nombreRegex = /^[a-zA-ZÀ-ÿ\s]{2,60}$/;
    const telRegex = /^[0-9()+\-\s]{6,20}$/;

    //  Función para renderizar un registro
    function renderizarCarta(u) {
        // Si el teléfono está vacío, muestra un guion largo
        const tel = (u.telefono && u.telefono.trim() !== "") ? u.telefono : "—";

        // Retorna el HTML de una "tarjeta" con los datos del usuario
        return `
      <div class="card-item">
        <div class="card-line mb-2">
          <div class="card-kv"><b>Nombre:</b><span>${escapeHtml(u.nombre)}</span></div>
          <div class="card-kv"><b>Email:</b><span>${escapeHtml(u.email)}</span></div>
          <div class="card-kv"><b>Teléfono:</b><span>${escapeHtml(tel)}</span></div>
        </div>
        <span class="badge-date">Ingresado: ${formatoFecha(u.fecha_registro)}</span>
      </div>
    `;
    }

    //  Renderizado completo de la lista
    function renderizarLista(data) {
        // Genera el HTML de las tarjetas y las inserta en el contenedor
        $list.html(data.data.map(renderizarCarta).join(''));

        // Muestra información de página actual y total de registros
        $meta.text(`Mostrando página ${data.page} de ${data.total_pages} (${data.total} registros)`);

        // Genera el paginador
        renderizarPaginador(data.page, data.total_pages);
    }

    //  Generador de botones del paginador
    function renderizarPaginador(page, totalPages) {
        // Función auxiliar para crear cada botón
        const btn = (p, label = p, disabled = false, active = false) =>
            `<button class="pager-btn ${active ? 'active' : ''}" data-page="${p}" ${disabled ? 'disabled' : ''}>${label}</button>`;

        const items = [];
        items.push(btn(1, '« Primero', page === 1));
        items.push(btn(Math.max(1, page - 1), '‹ Anterior', page === 1));

        // Rango central (página actual +- 1)
        const start = Math.max(1, page - 1);
        const end = Math.min(totalPages, page + 1);
        for (let p = start; p <= end; p++) items.push(btn(p, String(p), false, p === page));

        items.push(btn(Math.min(totalPages, page + 1), 'Siguiente ›', page === totalPages));
        items.push(btn(totalPages, 'Último »', page === totalPages));

        // Inserta el HTML en el contenedor del paginador
        $pager.html(items.join(''));
    }

    //  Evento de click en botones del paginador
    $pager.on('click', '.pager-btn', function () {
        const p = Number($(this).data('page'));
        if (!isNaN(p)) cargarPaginaRegistro(p);
    });

    //  Carga de registros de una página específica (por AJAX)
    function cargarPaginaRegistro(p = 1) {
        $.getJSON('lista.php', { page: p })
            .done(data => {
                if (data.ok) { currentPage = data.page; renderizarLista(data); }
            })
            .fail(() => { mostrarMensaje('Error al cargar el listado.', 'danger'); });
    }

    //  Evento de envío del formulario
    $form.on('submit', function (e) {
        e.preventDefault();

        const nombre = $("#nombre").val().trim();
        const email = $("#email").val().trim();
        const tel = $("#telefono").val().trim();

        // Validaciones usando expresiones regulares
        if (!nombreRegex.test(nombre)) return mostrarMensaje('Nombre inválido (2–60 letras).', 'danger');
        if (!emailRegex.test(email)) return mostrarMensaje('Email inválido.', 'danger');
        if (tel && !telRegex.test(tel)) return mostrarMensaje('Teléfono inválido.', 'danger');

        // Envío de datos al servidor vía AJAX
        $.ajax({
            url: 'insertar.php',
            type: 'POST',
            data: { nombre, email, telefono: tel },
            dataType: 'json'
        })
            .done(res => {
                // Si hay error de validación o duplicado
                if (!res.ok) return mostrarMensaje(res.msg || 'Error de validación.', 'warning');

                // Si se guardó correctamente:
                // Recarga la primera página (donde aparecen los más recientes)
                cargarPaginaRegistro(1);

                // Limpia el formulario
                $form[0].reset();
                mostrarMensaje('Guardado correctamente.', 'success');
            })
            .fail(() => mostrarMensaje('Error al enviar los datos.', 'danger'));
    });

    //  Ayudas visuales y de formato

    // Muestra mensajes temporales (alertas Bootstrap)
    function mostrarMensaje(text, type = 'primary') {
        $msg.html(`<div class="alert alert-${type} py-2 my-2" role="alert">${escapeHtml(text)}</div>`);
        setTimeout(() => $msg.empty(), 3500);
    }

    // Escapa caracteres HTML peligrosos (previene inyección en html)
    function escapeHtml(str) {
        return String(str)
            .replaceAll('&', '&amp;').replaceAll('<', '&lt;')
            .replaceAll('>', '&gt;').replaceAll('"', '&quot;')
            .replaceAll("'", '&#039;');
    }

    // Da formato legible a la fecha de registro (YYYY-MM-DD HH:MM:SS -> DD/MM/YYYY HH:MM)
    function formatoFecha(dt) {
        if (!dt) return '—';
        const [d, t] = dt.split(' ');
        const [Y, M, D] = d.split('-');
        return `${D}/${M}/${Y} ${t?.slice(0, 5) ?? ''}`;
    }

    //  Inicialización
    // Carga inicial de la primera página de registros al abrir el sitio
    cargarPaginaRegistro(1);
});
