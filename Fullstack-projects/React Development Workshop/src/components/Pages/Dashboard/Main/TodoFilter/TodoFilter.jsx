import { useRef } from "react";
import { useDispatch } from "react-redux";
import { onFilterToDos } from "../../../../../app/slices/todosSlice";

const TodoFilter = () => {
  const selectRef = useRef();
  const dispatcher = useDispatch();

  const _onFilter = () => {
    dispatcher(onFilterToDos(parseInt(selectRef.current.value)));
  };
  return (
    <div className="g-col-6">
      <div className="input-group">
        <select className="form-control" ref={selectRef} onChange={_onFilter}>
          <option value={0}>Última semana</option>
          <option value={1}>Último mes</option>
          <option selected value={2}>
            Todos
          </option>
        </select>
      </div>
    </div>
  );
};

export default TodoFilter;
