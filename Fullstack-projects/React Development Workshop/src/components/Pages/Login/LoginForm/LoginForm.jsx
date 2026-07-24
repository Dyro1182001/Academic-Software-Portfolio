import { useRef, useState } from "react";
import Button from "../../../UI/Button/Button";
import { login } from "../../../../services/api";
import Alert from "../../../UI/Alert/Alert";
import { onLogin } from "../../../../app/slices/userSlice";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";

const LoginForm = () => {
  const inputUserNameRef = useRef();
  const inputPassRef = useRef();
  const [message, setMessage] = useState("");
  const [messageColor, setMessageColor] = useState("danger");
  const [btnDisabled, setBtnDisbled] = useState(true);

  const dispatcher = useDispatch();
  const navigateTo = useNavigate();

  const onHandleLogin = (e) => {
    e.preventDefault();
    const inputName = inputUserNameRef.current.value;
    const inputPass = inputPassRef.current.value;
    if (inputName == "" || inputPass == "") {
      alert("Por favor completar los campos"); // TODO
    } else {
      login(inputName, inputPass)
        .then((res) => {
          setMessage("Inicio de sesion correcto");
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
    if (inputName != "" && inputPass != "") {
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
        <br />
        <Button
          cta={"Sign in"}
          classColor={"btn-primary"}
          onHandleClick={onHandleLogin}
          disabled={btnDisabled}
        />
      </form>
    </>
  );
};

export default LoginForm;
