using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Dominio
{
    public abstract class Usuario
    //Aquí definimos la clase padre Usuario, de la que heredarán Operador y Huesped.
    {
        public int _Id {  get; set; }

        private static int _UltimoId = 1;
        public string _EmailRegistro { get; set; }
        public string _Contrasenia { get; set; }

        public Usuario()
        {
            _Id = _UltimoId;
            _UltimoId++;


        }

        public Usuario(string emailRegistro, string contrasenia)
        {
            _Id = _UltimoId;
            _UltimoId++;
            _EmailRegistro = emailRegistro;
            _Contrasenia = contrasenia;
        }

        public abstract void EsValido(); //Preparamos una clase abstracta que podremos overridear y utilizar en las clases hijas de "Usuario" con el fin de realizar distintos tipos de validaciones.

        public abstract string Rol(); //Preparamos una clase abstracta que podremos overridear y utilizar en las clases hijas de "Usuario" con el fin de identificar sus Roles.

        public abstract int GetEdad(); //Metodo que permite obtener la edad de un huesped desde la clase usuario
    }
}
