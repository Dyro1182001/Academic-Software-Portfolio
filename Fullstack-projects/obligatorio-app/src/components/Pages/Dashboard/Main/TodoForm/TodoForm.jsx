import { useRef, useEffect, useState } from "react";
import Button from "../../../../UI/Button/Button";
import Alert from "../../../../UI/Alert";
import { registroAlimento } from "../../../../../services/api";
import { getAlimentos } from "../../../../../services/api";
import { onAddToDo } from "../../../../../app/slices/todosSlice";
import { useDispatch, useSelector } from "react-redux";

const TodoForm = () => {

  const inputAlimentoRef = useRef();
  const inputCantidadRef = useRef();
  const inputFechaRef = useRef();

  const [message, setMessage] = useState("");
  const [messageColor, setMessageColor] = useState("danger");
  const [btnDisabled, setBtnDisbled] = useState(true);

  const dispatcher = useDispatch();

  const userLogged = useSelector((store) => store.userSlice.userLogged);
  const alimentos = useSelector((store) => store.todosSlice.alimentos);

  
  const [data, setData] = useState(null);
  useEffect(() => {
    getAlimentos(userLogged.apiKey, userLogged.id)
      .then((data) => {
        setData(data.alimentos);})
  }, [userLogged.apiKey, userLogged.id]);

  const onHandleRegistro = (e) => {
    e.preventDefault();
    const inputAlimento = inputAlimentoRef.current.value;
    const idUsuario = userLogged.id;
    const inputCantidad = inputCantidadRef.current.value;
    const inputFecha = inputFechaRef.current.value;
    const apiKey = userLogged.apiKey;
    

    const agregarIdFoto = (id) => {
      let x = 0;
    
      alimentos.forEach((e) => {
        console.log(id);
        console.log(e.id)
        if (e.id === id) {
          x = e.id;
          return x;
        }
      });
    
      return x;
    };

    if (inputAlimento <= 0 || inputCantidad <= 0) {
      alert("Por favor completar los campos"); 
    } else {
      const newTodo = {
            userId: idUsuario,
            cantidad: inputCantidad,
            fecha: inputFecha,
            idAlimento: inputAlimento,
      };
      registroAlimento(inputAlimento, idUsuario, inputCantidad, inputFecha, apiKey)
        .then((res) => {
          setMessage("Registro correcto");
          setMessageColor("success");
          setTimeout(() => {
            newTodo.id = res.idRegistro;
            newTodo.idImagen = agregarIdFoto(parseInt(inputAlimento));
            dispatcher(onAddToDo(newTodo));
          }, 1000);
        })
        .catch((e) => {
          setMessage(e.message);
          setMessageColor("danger");
        });
    }
  };

  const validateForm = () => {
    const fechaHoy = new Date();
    const inputAlimento = inputAlimentoRef.current.value;
    const inputFecha = new Date(inputFechaRef.current.value);
    const inputCantidad = inputCantidadRef.current.value;

    if (inputAlimento > 0 && inputCantidad > 0 && fechaHoy > inputFecha) {
      setBtnDisbled(false);
    } else {
      setBtnDisbled(true);
    }
  };


  return (
    <div className="g-col-6">
      <form className="row">
        
        <div className="col">
          <select name="alimentos" id="alimentos"  className="form-control" ref={inputAlimentoRef}>
          {data?.map(element => (<option key={element.id} value={element.id}>{element.nombre} ---- Porcion: {element.porcion}</option>
          ))}
          </select>
          <br />
          <input 
            className="form-control" 
            type="number"
            placeholder="Cantidad consumida.."
            ref={inputCantidadRef}
            onChange={validateForm} 
          />
          <br />
          <input 
            className="form-control" 
            type="date"
            ref={inputFechaRef}
            onChange={validateForm} 
          />
          <br />
          <Button 
          cta={"Registrar"} 
          classColor={"btn-primary"}
          disabled={btnDisabled} 
          onHandleClick={onHandleRegistro}
        />

        {message !== "" ? (
          <Alert classColor={messageColor} message={message} />
        ) : (
          ""
        )}
        </div>
      </form>
    </div>
  );
};

export default TodoForm;
