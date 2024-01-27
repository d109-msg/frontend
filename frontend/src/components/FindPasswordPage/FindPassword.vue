<template>
    <div class="container">
        <div class="main-img">

            <div  v-if="step==0">
                <div  class="find-password-form">
                    <p class="find-password-title">비밀번호 찾기</p>
                    <div class="find-password-content-box">
                        <p class="find-password-content">
                            가입한 이메일 주소를 입력해주세요.
                        </p>
                        <p class="find-password-content">
                            비밀번호 재설정을 위한 임시 비밀번호를 보내드리겠습니다.
                        </p>
                    </div>
                    <div class="email-form">
                        <input type="text" class="email-input" required v-model="email">
                        <p class="email-label">E-mail</p>
                        <p class="email-warn" v-if="emailCheck">올바른 형식의 이메일을 기입해주세요.</p>
                    </div>
                    
                    <button class="next-btn" @click="next()">
                        Next
                    </button>            
                </div>
            </div>

            <div  v-if="step==1">
                <div  class="find-password-form">
                    <p class="find-password-title2">
                        발송된 <span>임시 비밀번호</span>를 확인 후, 새로운 비밀번호를 설정해주세요.</p>
                    <div class="find-password-content-box">
                        <div class="temp-password-form">
                           <input class="temp-password-input" required v-model="tempPassword">
                            <p class="temp-password-label">Password</p>
                        </div>
                        <div class="password-form">
                           <input type="password" class="password-input" required v-model="password">
                            <p class="password-label">New Password</p>
                            <p class="password-warn" v-if="passwordCheck">특수문자를 포함한 비밀번호(8~15 글자)를 입력해주세요.</p>
                        </div>

                        <div class="validation-form">
                            <input type="password" class="validation-input" required v-model="validation">
                            <p class="validation-label">Confirm New Password</p>
                            <p class="validation-warn" v-if="validationCheck">비밀번호가 일치하지 않습니다.</p>
                        </div>
                    </div>

                    
                    <button class="next-btn" @click="resetPassword">
                        비밀번호 재설정
                    </button>                
                </div>
            </div>
            

        </div>
    </div>

</template>

<script>
import axios from 'axios'
import router from '@/router'
export default {
    name : 'FindPassword',
    data(){
        return{
            step:0,
            email : "",
            emailCheck : true,
            tempPassword : "",
            tempPasswordCheck : false,
            password : "",
            passwordCheck : false,
            validation : "",
            validationCheck : false,
        }
    },
    methods:{
        next(){
            if (this.emailCheck == false){
                const emailInput = document.querySelector(".email-input")
                const emailData = {
                    "emailId": this.email
                }
                axios.post("http://localhost:8080/user/password/reset", JSON.stringify(emailData),
                {
                    headers : {"Content-Type": `application/json`},
                }
                ).then(res=>{
                    console.log(res)
                    this.step++
                }).catch(err=>{
                    console.log(err)
                    emailInput.focus()
                })
            }else{
                emailInput.focus()
            }
        },
        resetPassword(){
            const tempPasswordInput = document.querySelector(".temp-password-input")
            const passwordData = {
                "emailId": this.email,
                "emailPassword": this.tempPassword
            }

            axios.post("http://localhost:8080/user/sign-in",JSON.stringify(passwordData),
            {
                    headers : {"Content-Type": `application/json`},
            }).then(res=>{
                console.log(res.data)
                const access = res.data.accessToken
                const refresh = res.data.refreshToken
                store.commit('setAccessToken',access)
                store.commit('setRefreshToken',refresh)
                const newPasswordInput = document.querySelector(".password-input")
                const newPasswordData = {
                    "oldEmailPassword": this.tempPassword,
                    "newEmailPassword": this.password
                    
                }
                console.log(res)
                axios.patch("http://localhost:8080/user/password",JSON.stringify(newPasswordData),
                    {
                        headers : {"Content-Type": `application/json`,
                                    Authorization : `Bearer ${access}`,
                    },
                    }
                ).then(res=>{
                    console.log(res)
                    this.step == 0
                    router.push('/')
                }).catch(err=>{
                    console.log(err)    
                })
            }).catch(err=>{
                console.log(err)
                tempPasswordInput.focus()
            })
        },
        checkEmail : function(){
            const valid = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
            if(!valid.test(this.email)|| !this.email) {
                this.emailCheck = true
                return
            }
            this.emailCheck = false
        },
        checkTempPassword :function(){

        },
        checkPassword : function(){
            const valid = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,15}$/; 
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
    },
    watch:{
        email(){
            this.checkEmail()
        },

        tempPassword(){
            
        },
        password(){
            this.checkValidation()
            this.checkPassword()
        },
        
        validation(){
            this.checkValidation()
        },
    }
}
</script>

<style scoped src="./FindPassword.css">

</style>