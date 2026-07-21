import { createSlice } from "@reduxjs/toolkit";
import {
  getFromLocalStorage,
  removeFromLocalStorage,
  setToLocalStorage,
} from "../../utils/storage";

const USER_SESSION_KEY = "userToDoApp";
const userSession = getFromLocalStorage(USER_SESSION_KEY);

const initialState = {
  userLogged: userSession,
};

export const userSlice = createSlice({
  name: "userSlice",
  initialState: initialState,
  reducers: {
    onLogin: (state, action) => {
      state.userLogged = action.payload;
      setToLocalStorage(USER_SESSION_KEY, action.payload);
    },
    onLogout: (state) => {
      state.userLogged = null;
      removeFromLocalStorage(USER_SESSION_KEY);
    },
  },
});

export const { onLogin, onLogout } = userSlice.actions;
export default userSlice.reducer;