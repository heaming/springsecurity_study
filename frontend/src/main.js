import { createApp } from 'vue'
import App from './App.vue'
import router from "@/router";
import store from "@/vuex";
import axios from 'axios';

axios.defaults.withCredentials = true;
const app = createApp(App).use(router).use(store);
app.config.globalProperties.axios = axios;
app.mount('#app');


