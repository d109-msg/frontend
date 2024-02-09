<template>
    <div class="container">
      <div class="main-img">
      <div class="login-box">
          <p class="login-title">WELCOME!!</p>
          <div class="login-font">Already have an acount? <span style="cursor: pointer;"
              @click="$router.push('/login')"
              >Log In</span></div>

          <div class="name-form">
              <input type="text" class="name-input" required v-model="name">
              <p class="name-label">nickname</p>
              <p class="name-warn" v-if="nameCheck">올바른 형식의 닉네임을 기입해주세요.</p>
          </div>
          <div class="email-form">
              <input type="text" class="email-input" required v-model="email">
              <p class="email-label">E-mail</p>
              <p class="email-warn" v-if="emailCheck">올바른 형식의 이메일을 기입해주세요.</p>
          </div>
          <div class="password-form">
              <input type="password" class="password-input" required v-model="password">
              <p class="password-label">Password</p>
              <p class="password-warn" v-if="passwordCheck">특수문자를 포함한 비밀번호(8~15 글자)를 입력해주세요.</p>
          </div>
          <div class="validation-form">
              <input type="password" class="validation-input" required v-model="validation">
              <p class="validation-label">Confirm Password</p>
              <p class="validation-warn" v-if="validationCheck">비밀번호가 일치하지 않습니다.</p>
          </div>
          <button class="login-btn" @click="signUp">
              Join
          </button>

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

          <div style="display: flex; justify-content: center;">
          
          </div>
      </div>
    </div>
</div>

</template>

<script>
import axios from 'axios'
import router from '@/router'
import { useAuthStore } from '@/store/authStore'


export default {
    name : "SignupPage",
    data(){
        return{
            server : "",
            name : "",
            nameCheck : false,
            email : "",
            emailCheck : false,
            password : "",
            passwordCheck : false,
            validation : "",
            validationCheck : false,
        }
    },
    methods : {
        checkName : function(){
            console.log(this.nameCheck)
            const valid = /^[a-zA-Z0-9ㄱ-힣 ]{1,20}$/
            if(!valid.test(this.name)|| !this.name){
                this.nameCheck = true
                return
            }
            this.nameCheck = false
        },


        checkEmail : function(){
            const valid = /^[a-zA-Z0-9+-\_.]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$/;
            if(!valid.test(this.email)|| !this.email) {
                this.emailCheck = true
                return
            }
            this.emailCheck = false
            },


        checkPassword : function(){
            const valid = /^(?=.[A-Za-z])(?=.\\d)(?=.[@$!%#?&])[A-Za-z\\d@$!%*#?&]{8,20}$/; 
            if(!valid.test(this.password)|| !this.password){
                this.passwordCheck = true
                return
            }
            this.passwordCheck = false
        },
        checkValidation : function(){
            if(this.validation === this.password){
                this.validationCheck = false
                return
            }
            else{
                this.validationCheck = true
                return
            }
        },

        signUp : async function(){
            if(!this.nameCheck && !this.emailCheck && !this.passwordCheck && !this.validationCheck && this.name!="" && this.email != "" && this.password != "" && this.validation != ""){
                try{
                    const auth = useAuthStore()
                    await auth.signUp(this.name, this.email, this.password)
                    router.push('/login')
                    alert(`${this.name}님 회원가입에 축하드립니다.`)
                } catch(err){
                    alert('예기치 못한 오류가 발생했습니다.')
                }
            }else{
                if(this.nameCheck){this.name = ""}
                if(this.emailCheck){this.email = ""}
                if(this.passwordCheck){this.password = ""}
                if(this.validationCheck){this.validation = ""}
            }
        }

    },

    watch:{

        name(){
            this.checkName()
        },


        email(){
            this.checkEmail()
        },

        password(){
            this.checkValidation()
            this.checkPassword()
        },
        
        validation(){
            this.checkValidation()
        },
    },
}
</script>

<style scoped src="../assets/css/Signupform.css">

</style>