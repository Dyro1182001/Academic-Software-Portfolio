const login = (userName, password) => {
  return fetch("https://calcount.develotion.com/login.php", {
    method: "POST",
    headers: {
      "Content-type": "application/json",
    },
    body: JSON.stringify({
      usuario: userName,
      password: password,
    }),
  })
    .then((response) => {
      if (response.status === 200) {
        return response.json();
      } else {
        return Promise.reject({
          message: "Ha ocurrido un error",
        });
      }
    })
    .catch((e) =>
      Promise.reject({
        message: "Ha ocurrido un error",
      })
    );
};

const fakeLogin = (userName, password) => {
  return Promise.resolve({
    codigo: 200,
    apiKey: "074c115059a0bb76ff71b12da3d5a8e4",
    id: 37,
    caloriasDiarias: 2000,
  });
};

const signUp = (userName, password, idPais, caloriasDiarias) => {
  return fetch("https://calcount.develotion.com/usuarios.php", {
    method: "POST",
    headers: {
      "Content-type": "application/json",
    },
    body: JSON.stringify({
      usuario: userName,
      password: password,
      idPais: idPais,
      caloriasDiarias: caloriasDiarias,
    }),
  })
    .then((response) => {
      if (response.status === 200) {
        return response.json();
      } else {
        return Promise.reject({
          message: "Ha ocurrido un error",
        });
      }
    })
    .catch((e) =>
      Promise.reject({
        message: "Ha ocurrido un error",
      })
    );
};


const getUsuariosPais = (apiKey, idUser) => {
  return fetch("https://calcount.develotion.com/usuariosPorPais.php", {
    method: "GET",
    headers: {
      "Content-type": "application/json",
      "apikey": apiKey,
      "iduser": idUser
    },
  })
    .then((response) => {
      if (response.status === 200) {
        return response.json();
      } else {
        return Promise.reject({
          message: "Ha ocurrido un error",
        });
      }
    })
    .catch((e) =>
      Promise.reject({
        message: "Ha ocurrido un error",
      })
    );
};


const getTodos = (userId) => {
  // return fetch(`https://jsonplaceholder.typicode.com/todos?userId=${userId}`)
  return fetch(`https://jsonplaceholder.typicode.com/todos?userId=1`)
    .then((response) => {
      if (response.status === 200) {
        return response.json();
      } else {
        return Promise.reject({
          message: "Ha ocurrido un error",
        });
      }
    })
    .catch((e) =>
      Promise.reject({
        message: "Ha ocurrido un error",
      })
    );
};

const registroAlimento = (idAlimento, idUsuario, cantidad, fecha, apiKey) => {
  return fetch("https://calcount.develotion.com/registros.php", {
    method: "POST",
    headers: {
      "Content-type": "application/json",
      "apikey": apiKey,
      "iduser": idUsuario
    },
    body: JSON.stringify({
      idAlimento: idAlimento,
      idUsuario: idUsuario,
      cantidad: cantidad,
      fecha: fecha,
    }),
  })
    .then((response) => {
      if (response.status === 200) {
        return response.json();
      } else {
        return Promise.reject({
          message: "Ha ocurrido un error",
        });
      }
    })
    .catch((e) =>
      Promise.reject({
        message: "Ha ocurrido un error",
      })
    );
};

const getAlimentos = (apiKey, idUser) => {
  return fetch("https://calcount.develotion.com/alimentos.php", {
    method: "GET",
    headers: {
      "Content-type": "application/json",
      "apikey": apiKey,
      "iduser": idUser
    },
  })
    .then((response) => {
      if (response.status === 200) {
        return response.json();
      } else {
        return Promise.reject({
          message: "Ha ocurrido un error",
        });
      }
    })
    .catch((e) =>
      Promise.reject({
        message: "Ha ocurrido un error",
      })
    );
};

const getAlimentosUsuario = (apiKey, idUser) => {
  return fetch(`https://calcount.develotion.com/registros.php?idUsuario=${idUser}`, {
    method: "GET",
    headers: {
      "Content-type": "application/json",
      "apikey": apiKey,
      "iduser": idUser
    },
  })
    .then((response) => {
      if (response.status === 200) {
        return response.json();
      } else {
        return Promise.reject({
          message: "Ha ocurrido un error",
        });
      }
    })
    .catch((e) =>
      Promise.reject({
        message: "Ha ocurrido un error",
      })
    );
};

const getPaises = () => {
  return fetch(`https://calcount.develotion.com/paises.php`, {
    method: "GET",
    headers: {
      "Content-type": "application/json",
    },
  })
    .then((response) => {
      if (response.status === 200) {
        return response.json();
      } else {
        return Promise.reject({
          message: "Ha ocurrido un error",
        });
      }
    })
    .catch((e) =>
      Promise.reject({
        message: "Ha ocurrido un error",
      })
    );
};

const getPaisesUsuario = (apiKey, idUser) => {
  return fetch(`https://calcount.develotion.com/usuariosPorPais.php`, {
    method: "GET",
    headers: {
      "Content-type": "application/json",
      "apikey": apiKey,
      "iduser": idUser
    },
  })
    .then((response) => {
      if (response.status === 200) {
        return response.json();
      } else {
        return Promise.reject({
          message: "Ha ocurrido un error",
        });
      }
    })
    .catch((e) =>
      Promise.reject({
        message: "Ha ocurrido un error",
      })
    );
};

const eliminarAlimentoUsuario = (apiKey, idUser, idRegistro) => {
  return fetch(`https://calcount.develotion.com/registros.php?idRegistro=${idRegistro}`, {
    method: "DELETE",
    headers: {
      "Content-type": "application/json",
      "apikey": apiKey,
      "iduser": idUser
    },
  })
    .then((response) => {
      if (response.status === 200) {
        return response.json();
      } else {
        return Promise.reject({
          message: "Ha ocurrido un error",
        });
      }
    })
    .catch((e) =>
      Promise.reject({
        message: "Ha ocurrido un error",
      })
    );
};


//https://calcount.develotion.com/paises.php
const saveToDo = (toDo) => {
  return Promise.resolve({
    codigo: 200,
    id: Math.floor(Math.random() * 10) * 10,
  });
};

export { login, getTodos, saveToDo, fakeLogin, signUp, getUsuariosPais, registroAlimento, getAlimentos, getAlimentosUsuario, getPaises, getPaisesUsuario, eliminarAlimentoUsuario };
