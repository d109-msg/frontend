<template>
      <div class="container">
        <div class="main-img">
        <div class="login-box">
            <p class="login-logo"></p>
            <div class="signup-font">Don't have an acount? <span class="signup-font"
                @click="$router.push('/signup')">Sign Up</span></div>
            <div class="email-form">
                <input type="text" class="email-input" required v-model="emailId">
                <p class="email-label">E-mail</p>
            </div>

            <div class="password-form">
                <input type="password" class="password-input" required v-model="emailPassword">
                <p class="password-label">Password</p>
            </div>
           
            <button class="login-btn" @click="login">
                Login
            </button>            
            <div class="find-password-font" @click="FindPassword">Forget your password?</div>

            <div class="or-sign-in-with" >or Sign in with</div>
            <div style="display: flex; justify-content: center;">
                <img src="@/assets/css/loginformImg/google.png" alt="" style="  width: 10%; margin: 2%; margin-top: 3%; cursor: pointer;"
                    @click="socialLogin(0)"
                >
                <img src="@/assets/css/loginformImg/naver.png" alt="" style="width: 10%; margin: 2%; margin-top: 3%; cursor: pointer;"
                    @click="socialLogin(2)">
                <img src="@/assets/css/loginformImg/kakao.png" alt="" style="width: 10%; margin: 2%; margin-top: 3%; cursor: pointer;"
                    @click="socialLogin(1)"
                >
            </div>
        </div>
    </div>
</div>

</template>

<script>
import axios from 'axios'
import dotenv from 'dotenv'
import router from '@/router'
import { useAuthStore } from '@/store/authStore'

dotenv.config()

export default {
    name: "LoginPage",
    data(){
        return{
            emailId : "",
            emailPassword : "",
        }
    },
    methods: {
        login : function(){
            const data = {
                emailId : this.emailId,
                emailPassword : this.emailPassword
            }
            axios.post("http://localhost:8080/user/sign-in",JSON.stringify(data),{
                headers:{"Content-Type": `application/json`}
            }).then((res)=>{
                const access = res.data.accessToken
                const refresh = res.data.refreshToken
                const authStore = useAuthStore()
                authStore.setRefresh(refresh)
                authStore.setAccess(access)
                router.push('/')
            }).catch((err)=>{
                console.log(err)
            })
        },
        
        socialLogin : function(num){
            let server = ''
            if(num == 0){
                server = process.env.VUE_APP_google
            }else if(num == 1){
                server = process.env.VUE_APP_kakao
            }else{
                server = process.env.VUE_APP_naver
            }
            window.location.href = server
            

        },
        FindPassword(){
            router.push('/findpassword')
        }
    }
}
</script>

<style scoped src="../assets/css/loginform.css">
</style>