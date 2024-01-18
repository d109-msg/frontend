<template>
      <div class="container">
        <div class="main-img"></div>
        <div class="login-box">
            <p class="login-title">HONEY COMB</p>
            <div class="email-form">
                <input type="text" class="email-input" required v-model="emailId">
                <p class="email-label">이메일</p>
                <img src="@/assets/css/loginformImg/email.png" alt="" class="email-img">
            </div>
            <div class="password-form">
                <input type="password" class="password-input" required v-model="emailPassword">
                <p class="password-label">비밀번호</p>
                <img src="@/assets/css/loginformImg/password.png" alt="" class="password-img">
            </div>
            <button class="login-btn" @click="login">
                Login
            </button>
            <div style="margin-top: 5%; font-size: 0.8rem; color: rgba(0,0,0,0.6);">
                <span style="cursor: pointer;">아이디 찾기</span>
                <span> | </span>
                <span style="cursor: pointer;">비밀번호 찾기</span>
                <span > | </span>
                <span style="cursor: pointer;"
                @click="$router.push('/signup')"
                >회원가입</span>

            </div>
            <div style="margin-top: 7%; font-size: 0.9rem; color: rgba(0,0,0,0.6);">or Sign in with</div>
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

</template>

<script>
import axios from 'axios'
import dotenv from 'dotenv'
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
                console.log(res,"성공")
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
            

        }
    }
}
</script>

<style scoped src="../assets/css/loginform.css">
</style>