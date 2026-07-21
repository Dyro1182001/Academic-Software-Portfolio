import { configureStore } from "@reduxjs/toolkit";
import userSlice from "./slices/userSlice";
import todosSlice from "./slices/todosSlice";
import paisesSlice from "./slices/paisesSlice";
export const store = configureStore({
  reducer: {
    userSlice: userSlice,
    todosSlice: todosSlice,
    paisesSlice: paisesSlice,
  },
});
