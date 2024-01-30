<template>
    <div class="container">
        <div class="main-img">
            <div>
                <div  class="find-password-form">
                    <p class="find-password-title2">
                        새로운 비밀번호를 설정해주세요.</p>
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
import { useAuthStore } from '@/store/authStore'
import LoadingSpinner from '../LoadingSpinner/LoadingSpinner.vue'
export default {
    name : 'FindPassword',
    data(){
        return{
            tempPassword : "",
            tempPasswordCheck : false,
            password : "",
            passwordCheck : false,
            validation : "",
            validationCheck : false,
            spinFlag : false,
        }
    },
    methods:{
        async resetPassword(){
            try{
                const auth = useAuthStore()
                await auth.changePassword(this.tempPassword,this.password)
                router.push('/')
            } catch(error){
                console.log(error)
                // alert('로그인 세션이 만료되었습니다.')
                // router.push('/login')
            }
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
        password(){
            this.checkValidation()
            this.checkPassword()
        },
        validation(){
            this.checkValidation()
        },
    },
    components: {
        LoadingSpinner,
    }
}
</script>

<style scoped src="./FindPassword.css">

</style>