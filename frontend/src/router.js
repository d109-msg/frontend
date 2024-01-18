import { createRouter, createWebHistory } from "vue-router";
import HomePage from './components/HomePage.vue'
import Signup from './components/Signup.vue'
import Login from './components/Login.vue'
import MyPage from './components/MyPage/MyPage.vue'
import CategoryPage from './components/CategoryPage/CategoryPage.vue'
import MessagePage from './components/MessagePage/MessagePage.vue'
import TodayPage from './components/TodayPage/TodayPage.vue'
import SocialSign from './components/SocialSign.vue'


const routes = [
    {
        path :"/",   // 문자열 ""로 써야함 주의
        component : HomePage,
    },

    {
        path: "/login",
        component : Login,
    },
    
    {
        path: "/signup",
        component : Signup,
    },

    {
        path: "/MyPage",
        component : MyPage,
    },

    {
        path: "/CategoryPage",
        component : CategoryPage,
    },

    {
        path: "/MessagePage",
        component : MessagePage,
    },
    
    {
        path: "/TodayPage",
        component : TodayPage,
    },

    {
        path : "/sign-in/callback/:id", //소셜로그인
        component : SocialSign,
    },
]

const router = createRouter({
    history : createWebHistory(),
    routes

})

export default router