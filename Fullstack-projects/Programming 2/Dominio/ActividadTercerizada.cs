using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Dominio
{
    public class ActividadTercerizada : Actividad
    //Clase hija "ActividadTercerizada", que toma herencia de Actividad y en donde comenzaremos a implementar sistemas de validación correspondientes.
    {
        public Proveedor _Proveedor { get; set; }
        public bool _Confirmada { get; set; }
        public DateTime _FechaConfirm { get; set; }

        //se podria definir _Fecha como null y con un metodo asignarle un valor.
        //no lo aplicamos pues la letra no pide validacion de fecha.

        public ActividadTercerizada(string nombreActividad, string descripcion, DateTime fechaActividad, int cantMaxPersonas, int edadMinima, double costo, Proveedor proveedor, bool confirmada, DateTime fechaConfirm) : base(nombreActividad, descripcion, fechaActividad, cantMaxPersonas, edadMinima, costo)
        {
            _Proveedor = proveedor;
            _Confirmada = confirmada;
            _FechaConfirm = fechaConfirm;

        }


        public override void EsValido() //Validaciones correspondientes con excepciones acordes a cada caso planteado.
        {
            if (String.IsNullOrEmpty(_NombreActividad) || _NombreActividad.Length > 25)
            {
                throw new Exception("Nombre no valido");
            }

            if (String.IsNullOrEmpty(_Descripcion))
            {
                throw new Exception("Descripcion no valida");
            }

            if (_FechaActividad < DateTime.Now)
            {
                throw new Exception("Fecha no valida");
            }
        }


        public override string Proveedor()
        //Preparamos una clase abstracta que podremos overridear y utilizar en las clases hijas de "Actividad" con el fin de identificar el Proveedor de la Actividad.
        {
            return _Proveedor._NombreProveedor;
        }

        public override string Lugar()
        //Preparamos una clase abstracta que podremos overridear y utilizar en las clases hijas de "Actividad" con el fin de identificar el Lugar donde se desarrolla la Actividad.
        {
            return "Fuera del hostal";
        }

        public override double CalcularPrecio(double CostoATrabajar, Huesped H)
        //Preparamos una clase abstracta que podremos overridear y utilizar en las clases hijas de "Actividad" con el fin de calcular el precio de una Actividad en base a un Costo y un Huesped con un determinado nivel de fidelización/valor numérico.
        {
            if (_Confirmada == true) //dudas con respecto a si deberíamos utilizar "true" o una string con un nombre propio
            {
                CostoATrabajar =_Costo - (_Costo * _Proveedor._Descuento) / 100;
            }

            return CostoATrabajar;
        }
    }
}
