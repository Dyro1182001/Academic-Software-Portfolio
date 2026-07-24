namespace Dominio
{
    public abstract class Actividad : IComparable<Actividad>
    //Aquí definimos la clase padre Actividad, de la que heredarán ActividadHostal y ActividadTercerizada.
    {
        private static int _UltimoId = 1;

        public int _Id { get; set; }
        public string _NombreActividad { get; set; }
        public string _Descripcion { get; set; }
        public DateTime _FechaActividad { get; set; }
        public int _CantMaxPersonas { get; set; }
        public int _EdadMinima { get; set; }
        public double _Costo { get; set; }




        public Actividad()
        {
            _Id = _UltimoId;
            _UltimoId++;
        }

        public Actividad(string nombreActividad, string descripcion, DateTime fechaActividad, int cantMaxPersonas, int edadMinima, double costo)
        {
            _Id = _UltimoId;
            _UltimoId++;
            _NombreActividad = nombreActividad;
            _Descripcion = descripcion;
            _FechaActividad = fechaActividad;
            _CantMaxPersonas = cantMaxPersonas;
            _EdadMinima = edadMinima;
            _Costo = costo;
        }

        public abstract void EsValido(); //Preparamos clases abstractas que podremos overridear y utilizar en las clases hijas de "Actividad".

        public override string ToString() //Método ToString() para asegurarnos de que al ser llamadas, las actvidades devuelvan los datos que se piden.
        {
            return $"Id: {_Id}, Nombre: {_NombreActividad}, Descripción: {_Descripcion}, Fecha: {_FechaActividad}, Cantidad Máxima de Personas: {_CantMaxPersonas}, Edad Mínima: {_EdadMinima}, Costo: U$S {_Costo}";
        }

        public int CompareTo(Actividad? other)
        //Método que nos permite ordenar la lista Actividades por orden de costo decreciente.
        {
            if (_Costo.CompareTo(other._Costo) > 0)
            {
                return -1;
            }
            else if (_Costo.CompareTo(other._Costo) < 0)
            {
                return 1;
            }
            else
            {
                return 0;
            }
        }

        public abstract string Lugar();
        public abstract string Proveedor();

        public abstract double CalcularPrecio(double CostoATrabajar, Huesped H);
    }
}