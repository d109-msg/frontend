import { createRouter, createWebHistory } from "vue-router";
import HomePage from './components/HomePage/HomePage.vue'
import Signup from './components/Signup.vue'
import Login from './components/Login.vue'
import FindPassword from './components/FindPasswordPage/FindPassword.vue'
import MyPage from './components/MyPage/MyPage.vue'
import CategoryPage from './components/CategoryPage/CategoryPage.vue'
import MessagePage from './components/MessagePage/MessagePage.vue'
import TodayPage from './components/TodayPage/TodayPage.vue'
import SocialSign from './components/SocialSign.vue'
import Mainpage from './components/MainPage/MainPage.vue'
import GamePage from './components/GamePage/GamePage.vue'
import ChangePassword from './components/ChangePassword/ChangePassword.vue'
import RoomDetailPage from "./components/GamePage/RoomDetailPage.vue"
import { useAuthStore } from "./store/authStore";


const routes = [
    {
        path: "/",   // 문자열 ""로 써야함 주의
        component : HomePage,
        name: 'HomePage',
        children:[
            {   
                name: "main",
                path: "",
                component: Mainpage
            },
            {   
                name: "messgage",
                path: "/message",
                component: MessagePage
            },
            {
                name: "game",
                path: "/game",
                component: GamePage,
            },
            {   name: "mypage",
                path: "/mypage",
                component : MyPage,
            },
            {
                name: "room",
                path: "/game/:roomId",
                component: RoomDetailPage,
            }


            
        ]
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
        path: "/findpassword",
        component : FindPassword,
    },

    {
        path: "/CategoryPage",
        component : CategoryPage,
    },

    {
        path: "/TodayPage",
        component : TodayPage,
    },

    {
        path : "/sign-in/callback/:id", //소셜로그인
        component : SocialSign,
    },

    {
        path: "/change-password",
        component: ChangePassword,
        beforeEnter: (to, from, next) => {
            const auth = useAuthStore()
            console.log(auth.getAccess)
            if(auth.getAccess==""){
                alert('로그인이 필요한 페이지입니다.')
                next('/login')
            }else{
                return next()
            }
        }
    },

    { //없는 URL 매핑시키는 로직 맨 마지막에 넣으면 됩니다.
        name: "NotFoundPage",
        path: "/:notFound(.*)*",
        redirect: '/'
    }
]

const router = createRouter({
    history : createWebHistory(),
    routes

})

export default router