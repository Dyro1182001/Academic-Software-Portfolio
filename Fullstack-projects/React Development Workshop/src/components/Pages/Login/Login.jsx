import logo from "./to-do-icon.png";
import "./Login.css";
import LoginForm from "./LoginForm";
import { useSelector } from "react-redux";
import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { Link } from "react-router-dom";


const Login = () => {
  const userLogged = useSelector((store) => store.userSlice.userLogged);
  const navigateTo = useNavigate();

  useEffect(() => {
    if (userLogged) {
      navigateTo("/dashboard/todo-list");
    }
  }, []);

  return (
    <>
      <section className="d-flex flex-md justify-content-center login">
        <div className="card">
          <img src={logo} width="70" height="70" alt="Logo" />
          <h3>Login</h3>
          <section className="card-body">
            <LoginForm />
            <br />
            <Link to="/signup">No tienes cuenta?</Link> 
          </section>
        </div>
      </section>
    </>
  );
};

export default Login;
