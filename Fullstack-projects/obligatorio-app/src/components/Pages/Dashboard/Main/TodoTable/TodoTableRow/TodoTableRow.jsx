import Button from "../../../../../UI/Button/Button";
import { useDispatch, useSelector } from "react-redux";
import { eliminarAlimentoUsuario } from "../../../../../../services/api";
import { onLoadToDos } from "../../../../../../app/slices/todosSlice";

//import Alert from "../../../../../UI/Alert";

const TodoTableRow = ({ todo }) => {
  const userLogged = useSelector((store) => store.userSlice.userLogged);
  const toDos = useSelector((store) => store.todosSlice.toDos);
  const dispatcher = useDispatch();


  const onHandleDelete = (id) => e =>{
    e.preventDefault();

    eliminarAlimentoUsuario(userLogged.apiKey, userLogged.id, id)
    .then((res) => {
      if(res.codigo===200){
        const toDosFinal = toDos.filter(x => x.id!==id);
        dispatcher(onLoadToDos(toDosFinal));
        //alert(`${res.mensaje}`);
      }
    })

    eliminarAlimentoUsuario(userLogged.apiKey, userLogged.id, id);
  }

  return (
    <tr>
      <th scope="row"><img
                                src={`https://calcount.develotion.com/imgs/${todo.idImagen}.png`}
                                alt={"Imagen"}
                                style={{ width: "50px", height: "50px", marginRight: "10px" }}
                            /></th>
      <td>{todo.idAlimento}</td>
      <td>{todo.cantidad}</td>
      <td>{todo.fecha}</td>
      <td>
        <Button cta={"Delete"} classColor="btn-danger" onHandleClick={onHandleDelete(todo.id)} />
      </td>
    </tr>
  );
};

export default TodoTableRow;
