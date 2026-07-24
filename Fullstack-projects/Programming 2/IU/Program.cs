using Dominio;
using System.Linq.Expressions;

namespace IU
{
    internal class Program
    {
        static void Main(string[] args)
        {
            //Cargamos el sistema para poder comenzar a trabajar con todos los datos y métodos correspondientes.
            Sistema s = Sistema.GetInstancia();

            //Empezamos a formular una estructura que nos permita crear y desplegar un menú interactivo para el Usuario.
            int op = -1;

            while (op != 0)
            {

                armarMenu();
                op = int.Parse(Console.ReadLine());

                switch (op)
                { 
                    case 1:
                        Console.Clear();
                        Console.WriteLine("Listado de Actividades");
                        Console.WriteLine(" ");

                        foreach (Actividad a in s.GetActividades())
                        {
                            Console.WriteLine(a.ToString());
                            Console.WriteLine(" ");
                        }
                        break;


                    case 2:
                        Console.Clear();
                        Console.WriteLine("Listado de Proveedores");
                        Console.WriteLine(" ");

                        foreach (Proveedor p in s.GetProveedores())
                        {
                            Console.WriteLine(p.ToString());
                            Console.WriteLine(" ");
                        }
                        break;

                    case 3:
                        Console.Clear();
                        Console.WriteLine("Listado de Actividades entre 2 fechas y mayor a un costo dado");
                        Console.WriteLine(" ");

                        DateTime aux = new DateTime();

                        Console.WriteLine("Ingrese el año de la primera fecha");
                        int añoFecha1 = int.Parse(Console.ReadLine());
                        Console.WriteLine("Ingrese el mes de la primera fecha");
                        int mesFecha1 = int.Parse(Console.ReadLine());
                        Console.WriteLine("Ingrese el dia de la primera fecha");
                        int diaFecha1 = int.Parse(Console.ReadLine());

                        Console.WriteLine("Ingrese el año de la segunda fecha");
                        int añoFecha2 = int.Parse(Console.ReadLine());
                        Console.WriteLine("Ingrese el mes de la segunda fecha");
                        int mesFecha2 = int.Parse(Console.ReadLine());
                        Console.WriteLine("Ingrese el dia de la segunda fecha");
                        int diaFecha2 = int.Parse(Console.ReadLine());

                        Console.WriteLine("Ingrese el costo a comparar");
                        double costo = double.Parse(Console.ReadLine());

                        DateTime fecha1 = new DateTime(añoFecha1, mesFecha1, diaFecha1);
                        DateTime fecha2 = new DateTime(añoFecha2, mesFecha2, diaFecha2);

                        if (fecha1 > fecha2)
                        {
                            aux = fecha2;
                            fecha2 = fecha1;
                            fecha1 = aux;
                        }

                        foreach (Actividad a in s.GetActividadesRangoFechasCosto(fecha1, fecha2, costo))
                        {
                            Console.WriteLine(a.ToString());
                            Console.WriteLine(" ");
                        }
                        break;

                    case 4:
                        Console.Clear();
                        Console.WriteLine("Elegir el Proveedor del cual se desea modificar el valor de promoción");
                        Console.WriteLine(" ");
                        foreach (Proveedor p in s.GetProveedores())
                        {
                            Console.WriteLine(p.ToString());
                            Console.WriteLine(" ");
                        }
                        Console.WriteLine("Ingrese el ID del proveedor cuyo valor de promoción desea modificar");
                        int id = int.Parse(Console.ReadLine());
                        Console.WriteLine("Ingrese el nuevo valor de promoción");
                        int nuevoValorPromo = int.Parse(Console.ReadLine());

                        bool exitoCambiar = s.CambiarValorPromoProveedor(id, nuevoValorPromo);

                        //USAR EL COMPARETO PARA BUSCAR EL PROVEEDOR MEDIANTE SU ID

                        break;

                    case 5:
                        Console.Clear();
                        Console.WriteLine("Alta de Huespedes");
                        Console.WriteLine(" ");

                        Console.WriteLine("Ingrese el correo electrónico");
                        string email = Console.ReadLine();
                        Console.WriteLine("Ingrese la contraseña");
                        string password = Console.ReadLine();
                        Console.WriteLine("Ingrese el tipo de documento de identidad");

                        seleccionTipoDocumento();

                        string tipoDocumento = "";

                        op = int.Parse(Console.ReadLine());

                        switch (op)
                        {
                            case 1:
                                tipoDocumento = "CI";
                                break;
                            case 2:
                                tipoDocumento = "Pasaporte";
                                break;
                            case 3:
                                tipoDocumento = "Otros";
                                break;
                        }
                        Console.WriteLine("Ingrese el número de documento"); //Vamos registrando poco a poco los datos del Usuario.
                        string numDocumento = Console.ReadLine();
                        while (s.MatchTipoNumDocumento(tipoDocumento, numDocumento))
                        {
                            Console.WriteLine("Ya hay un huesped con ese tipo y numero de documento");
                            Console.WriteLine("Ingrese el número de documento");
                            numDocumento = Console.ReadLine();
                            s.MatchTipoNumDocumento(tipoDocumento, numDocumento);
                        }

                        Console.WriteLine("Ingrese el nombre");
                        string nombre = Console.ReadLine();
                        Console.WriteLine("Ingrese el apellido");
                        string apellido = Console.ReadLine();
                        Console.WriteLine("Ingrese la habitación");
                        string habitacion = Console.ReadLine();
                        Console.WriteLine("Ingrese el año de nacimiento");
                        int anioNacimiento = int.Parse(Console.ReadLine());
                        Console.WriteLine("Ingrese el mes de nacimiento");
                        int mesNacimiento = int.Parse(Console.ReadLine());
                        Console.WriteLine("Ingrese el dia de nacimiento");
                        int diaNacimiento = int.Parse(Console.ReadLine());

                        DateTime fechaNacimiento = new DateTime(anioNacimiento, mesNacimiento, diaNacimiento);

                        Huesped h1 = new Huesped(email, password, tipoDocumento, numDocumento, nombre, apellido, habitacion, fechaNacimiento, 2);
                        try
                        {
                            s.AltaHuesped(h1);
                            Console.WriteLine("Huesped registrado correctamente");
                        }
                        catch(Exception e)
                        {
                            Console.WriteLine(e.Message);
                        }
                        //No reescribir nada (salvo el ReadKey) pasando el break
                        //Es donde termina el menú desplegable
                        break;

                }
                Console.ReadKey();
            }


        }
        private static void armarMenu() //Función que nos permitirá desplegar la lista de selección.
        {
            Console.Clear();
            Console.WriteLine("1 - Listar todas las Actividades.");
            Console.WriteLine("2 - Listar todos los proveedores.");
            Console.WriteLine("3 - Listar Actividades mayores a un costo determinado y entre 2 fechas.");
            Console.WriteLine("4 - Establecer el valor de promoción de un Proveedor.");
            Console.WriteLine("5 - Alta de Huespedes.");
        }

        private static void seleccionTipoDocumento() //Función que permite al usuario seleccionar su tipo de documento entre 3 opciones.
        {
            Console.WriteLine("1 - CI");
            Console.WriteLine("2 - Pasaporte");
            Console.WriteLine("3 - Otros");
        }
    }
}