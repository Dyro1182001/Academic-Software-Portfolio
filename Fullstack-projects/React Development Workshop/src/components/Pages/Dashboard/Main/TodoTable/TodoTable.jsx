import { useSelector } from "react-redux";
import "./TodoTable.css";
import TodoTableRow from "./TodoTableRow";

const Table = () => {
  const filteredToDos = useSelector((store) => store.todosSlice.filteredToDos);

  const alimentos = useSelector((store) => store.todosSlice.alimentos);
  const arrayConFoto = filteredToDos.map((e) => ({
    ...e,
    idImagen: 0,
    nombre: ""
  }));

  arrayConFoto.forEach(a => {
    alimentos.forEach(b => {
    if(a.idAlimento === b.id){
      a.idImagen = b.imagen;
      a.nombre = b.nombre;
    }
  });
  });

  return (
    <table className="table table-hover">
      <thead>
        <tr>
          <th scope="col"></th>
          <th scope="col">IdAlimento</th>
          <th scope="col">Cantidad</th>
          <th scope="col">Fecha</th>
          <th scope="col">Delete</th>
        </tr>
      </thead>
      <tbody>
        {arrayConFoto.map((toDo) => (
          <TodoTableRow todo={toDo} key={toDo.id} />
        ))}
      </tbody>
    </table>
  );
};
export default Table;
