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
                        <input type="text" class="email-input" required v-model="emailId">
                        <p class="email-label">E-mail</p>
                        <p class="email-warn">대소문자를 구분하여 입력해주세요.</p>
                    </div>
                    
                    <button class="next-btn" @click="step++">
                        Next
                    </button>            
                </div>
            </div>

            <div  v-if="step==1">
                <div  class="find-password-form">
                    <p class="find-password-title">임시 비밀번호를 알려드립니다.</p>
                    <div class="find-password-content-box">
                        <p class="find-password-content">
                            입력하신 이메일이 <span></span>이 맞으신가요?
                        </p>
                        <p class="email-warn">
                            회원님 본인이 요청한 경우 아래버튼을 눌러 비밀번호를 새로 설정할 수 있습니다.
                        </p>
                    </div>

                    
                    <button class="next-btn" @click="resetPassword">
                        비밀번호 재설정
                    </button>                
                </div>
            </div>
            

            <div  v-if="step==2">
                <div  class="find-password-form">
                    <p class="find-password-title">비밀번호 재설정</p>
                    <div class="find-password-content-box">
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
                    </div>
                </div>
            </div> 
        </div>
    </div>

</template>

<script>
import axios from 'axios'
export default {
    name : 'FindPassword',
    data(){
        return{
            step:0,
        }
    },
    methods:{
        resetPassword(){

        }
    }
}
</script>

<style scoped src="./FindPassword.css">

</style>