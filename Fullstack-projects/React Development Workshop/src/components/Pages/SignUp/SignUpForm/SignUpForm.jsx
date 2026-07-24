import { useRef, useState } from "react";
import Button from "../../../UI/Button/Button";
import { signUp } from "../../../../services/api";
import Alert from "../../../UI/Alert/Alert";
import { onLogin } from "../../../../app/slices/userSlice";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";


const SignUpForm = () => {
  const inputUserNameRef = useRef();
  const inputPassRef = useRef();
  const inputPaisRef = useRef();
  const inputCaloriasRef = useRef();

  const [message, setMessage] = useState("");
  const [messageColor, setMessageColor] = useState("danger");
  const [btnDisabled, setBtnDisbled] = useState(true);

  const dispatcher = useDispatch();
  const navigateTo = useNavigate();

  const onHandleSignUp = (e) => {
    e.preventDefault();
    const inputName = inputUserNameRef.current.value;
    const inputPass = inputPassRef.current.value;
    const inputCalories = inputCaloriasRef.current.value;
    const inputPais = inputPaisRef.current.value;
    if (inputName === "" || inputPass === "" || inputCalories <= 0) {
      alert("Por favor completar los campos"); 
    } else {
      signUp(inputName, inputPass, inputPais, inputCalories)
        .then((res) => {
          setMessage("Registro correcto");
          setMessageColor("success");

          setTimeout(() => {
            dispatcher(onLogin(res));
            navigateTo("/dashboard/todo-list");
          }, 1000);
        })
        .catch((e) => {
          setMessage(e.message);
          setMessageColor("danger");
        });
    }
  };

  const validateForm = () => {
    const inputName = inputUserNameRef.current.value;
    const inputPass = inputPassRef.current.value;
    const inputCalorias = inputCaloriasRef.current.value;
    if (inputName !== "" && inputPass !== "" && inputCalorias > 0) {
      setBtnDisbled(false);
    } else {
      setBtnDisbled(true);
    }
  };

  return (
    <>
      <form>
        {message !== "" ? (
          <Alert classColor={messageColor} message={message} />
        ) : (
          ""
        )}

        <label>Username</label>
        <br />
        <input 
          className="form-control" 
          type="text"
          ref={inputUserNameRef}
          onChange={validateForm} 
        />
        <br />
        <label>Password</label>
        <br />
        <input 
          className="form-control" 
          type="password"
          ref={inputPassRef}
          onChange={validateForm} 
        />
        <br />
        <label>Pais</label>
        <br />
        <select name="paises" id="paises" className="form-control" ref={inputPaisRef}>
          <option value="11">Argentina</option>
          <option value="27">Bolivia</option>
          <option value="31">Brazil</option>
          <option value="44">Chile</option>
          <option value="48">Colombia</option>
          <option value="64">Ecuador</option>
          <option value="172">Paraguay</option>
          <option value="173">Peru</option>
          <option value="235">Uruguay</option>
          <option value="239">Venezuela</option>
        </select>
        <br />
        <label>Calorías Diarias</label>
        <br />
        <input 
          className="form-control" 
          type="number"
          ref={inputCaloriasRef}
          onChange={validateForm} 
        />
        <br />
        <Button 
          cta={"Sign Up"} 
          classColor={"btn-primary"}
          onHandleClick={onHandleSignUp}
          disabled={btnDisabled} 
        />
      </form>
    </>
  );
};

export default SignUpForm;
