using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Dominio
{
    public class Huesped : Usuario
    //Clase hija "Huesped", que toma herencia de Usuario y en donde comenzaremos a implementar sistemas de validación correspondientes.
    {
        public string _TipoDocumento { get; set; }
        public string _NumDocumento { get; set; }
        public string _NombreHuesped { get; set; }
        public string _ApellidoHuesped { get; set; }
        public string _Habitacion { get; set; }
        public DateTime _FechaNacimiento { get; set; }
        public int _ValorNumerico { get; set; }

        public Huesped(string emailRegistro, string contrasenia, string tipoDocumento, string numDocumento, string nombreHuesped, string apellidoHuesped, string habitacion, DateTime fechaNacimiento, int valorNumerico) : base(emailRegistro, contrasenia)
        {
            _TipoDocumento = tipoDocumento;
            _NumDocumento = numDocumento;
            _NombreHuesped = nombreHuesped;
            _ApellidoHuesped = apellidoHuesped;
            _Habitacion = habitacion;
            _FechaNacimiento = fechaNacimiento;
            _ValorNumerico = valorNumerico;
        }
        public Huesped()
        {

        }

        public bool CedulaEsValida(string cedula) //Algoritmo de método verificador de cédula en Uruguay.
        {
            bool ret = false;

            int[] factores = { 2, 9, 8, 7, 6, 3, 4 };

            int sumaDigitosCI = 0;

            if (cedula.Length == 8)
            {
                int digitoVerificador = 0;

                string test = "";

                bool encontrado = false;

                for (int i = 0; i < cedula.Length - 1; i++)
                {
                    sumaDigitosCI += factores[i] * int.Parse(cedula[i].ToString());
                }

                for (int p = sumaDigitosCI; encontrado; p++)
                {
                    test = p.ToString();
                    if (test[test.Length - 1] == 0)
                    {
                        digitoVerificador = sumaDigitosCI - p;
                        encontrado = true;
                    }
                }

                if (int.Parse(cedula[7].ToString()) != digitoVerificador)
                {
                    ret = false;

                }
            }
            else
            {
                ret = false;
            }

            return true;
        }


        public override void EsValido() //Validaciones correspondientes con excepciones acordes a cada caso planteado.
        {
            if (_EmailRegistro.IndexOf("@") <= 0 || _EmailRegistro.IndexOf("@") == _EmailRegistro.Length - 1 || String.IsNullOrEmpty(_EmailRegistro))
            {
                throw new Exception("Email no valido");
            }

            if (_Contrasenia.Length < 8)
            {
                throw new Exception("Contraseña no valida");
            }

            if (String.IsNullOrEmpty(_Habitacion))
            {
                throw new Exception("Habitacion no valida");
            }

            if (String.IsNullOrEmpty(_NombreHuesped))
            {
                throw new Exception("Nombre no valido");
            }

            if (String.IsNullOrEmpty(_ApellidoHuesped))
            {
                throw new Exception("Apellido no valido");
            }

            if (_ValorNumerico < 1 || _ValorNumerico > 4)
            {
                throw new Exception("Valor numerico no valido");
            }
            if (_TipoDocumento == "CI")
            {
                if (_NumDocumento.Length<8)
                {
                    throw new Exception ("Número de documento no válido");
                }
            }


            //falta validar el tipo de documento y el numero del mismo.
            //tambien que no pueden haber 2 personas con un mismo tipo y numero de documento.

        }
        public override string Rol() //Preparamos una clase abstracta que podremos overridear y utilizar en las clases hijas de "Usuario" con el fin de identificar sus Roles.
        {
            return "Huesped";
        }

        public override int GetEdad()
        {
            int edad = DateTime.Now.Year - _FechaNacimiento.Year;
            return edad;
        }
    }
}
