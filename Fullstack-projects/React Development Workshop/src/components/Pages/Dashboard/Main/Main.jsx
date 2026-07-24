import TodoTable from "./TodoTable";
import TodoFilter from "./TodoFilter";
import TodoForm from "./TodoForm";

const Main = () => {
  return (
    <main>
      <div className="grid">
        <h5>Registro</h5>
        <TodoForm />
        <br />
        <h5>Filtros</h5>
        <TodoFilter />
      </div>
      <br />
      <h5>Listado</h5>
      <TodoTable />
    </main>
  );
};

export default Main;
