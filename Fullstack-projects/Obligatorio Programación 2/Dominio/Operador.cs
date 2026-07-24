using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Dominio
{
    public class Operador : Usuario
    //Clase hija "Operador", que toma herencia de Usuario y en donde comenzaremos a implementar sistemas de validación correspondientes.
    {
        public Operador(string emailRegistro, string contrasenia) : base(emailRegistro, contrasenia)
        {

        }

        public override void EsValido() //Validaciones correspondientes con excepciones acordes a cada caso planteado.
        {
            if (_EmailRegistro.IndexOf("@") <= 0 || _EmailRegistro.IndexOf("@") == _EmailRegistro.Length - 1)
            {
                throw new Exception("Email no valido");
            }

            if (_Contrasenia.Length < 8)
            {
                throw new Exception("Contraseña no valida");
            }
        }
        public override string Rol() //Preparamos una clase abstracta que podremos overridear y utilizar en las clases hijas de "Usuario" con el fin de identificar sus Roles.
        {
            return "Operador";
        }

        public override int GetEdad()
        {
            return 0;
        }
    }
}
