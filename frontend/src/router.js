import { createRouter, createWebHistory } from "vue-router";
import HomePage from './components/HomePage/HomePage.vue'
import Signup from './components/Signup.vue'
import Login from './components/LoginPage/Login.vue'
import FindPassword from './components/FindPasswordPage/FindPassword.vue'
import MyPage from './components/MyPage/MyPage.vue'
import MessagePage from './components/MessagePage/MessagePage.vue'
import SocialSign from './components/SocialSign.vue'
import Mainpage from './components/MainPage/MainPage.vue'
import GamePage from './components/GamePage/GamePage.vue'
import ChangePassword from './components/ChangePassword/ChangePassword.vue'
import RoomDetailPage from "./components/RoomDetail/RoomDetailPage.vue"
import UserPage from "./components/UserPage/UserPage.vue"
import GameGuide from "./components/GamePage/GameGuide.vue"
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
                name: "message",
                path: "/message",
                component: MessagePage,
                beforeEnter: (to, from, next) => {
                    const auth = useAuthStore()
                    if(auth.getAccess==""){
                        alert('로그인이 필요한 페이지입니다.')
                        return next(from.fullPath)
                    }else{
                        return next()
                    }
                }
            },
            {
                name: "game",
                path: "/game",
                component: GamePage,
                beforeEnter: (to, from, next) => {
                    const auth = useAuthStore()
                    if(auth.getAccess==""){
                        alert('로그인이 필요한 페이지입니다.')
                        return next(from.fullPath)
                    }else{
                        return next()
                    }
                }
            },
            {   name: "mypage",
                path: "/mypage",
                component : MyPage,
                beforeEnter: (to, from, next) => {
                    const auth = useAuthStore()
                    if(auth.getAccess==""){
                        alert('로그인이 필요한 페이지입니다.')
                        return next(from.fullPath)
                    }else{
                        return next()
                    }
                }
            },
            {
                name: "room",
                path: "/game/room/:data",
                component: RoomDetailPage,
            },
            {   
                name: "guide",
                path: "/game/guide",
                component: GameGuide
            },
            {
                name: "userpage",
                path:"/user/:id",
                component : UserPage,
                beforeEnter: (to, from, next) => {
                    const auth = useAuthStore()
                    if(auth.getAccess==""){
                        alert('로그인이 필요한 페이지입니다.')
                        return next(from.fullPath)
                    }else{
                        return next()
                    }
                }
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
        path : "/sign-in/callback/:id", //소셜로그인
        component : SocialSign,
    },

    {
        path: "/change-password",
        component: ChangePassword,
        beforeEnter: (to, from, next) => {
            const auth = useAuthStore()
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