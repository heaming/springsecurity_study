import { createStore } from "vuex";

const state = {
    loggedIn: false,
}

const mutation = {
    loginSuccess(state) {
        state.loggedIn = true;
    },
    logoutSuccess(state) {
        state.loggedIn = false;
    }
}


const store = createStore({
    state: state,
    mutations: mutation,
})

export default store;