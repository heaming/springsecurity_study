import { createRouter, createWebHistory } from 'vue-router'
import Home from "@/components/Home";
import Login from "@/components/Login";
import JoinForm from "@/components/JoinForm";

const routes = [
    {
        path: '/',
        name: 'home',
        component: Home
    },
    {
        path: '/login',
        name: 'login',
        component: Login
    },
    {
        path: '/joinForm',
        name: 'joinForm',
        component: JoinForm,
    }
];

const router = createRouter({
    history: createWebHistory(""),
    routes,
})

export default router;