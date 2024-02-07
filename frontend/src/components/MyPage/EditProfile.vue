<template>
      <div class="edit-profile-back">
      <div class="edit-profile-box">
        <div class="edit-profile-nav" >
          <p>회원정보 수정</p>
          <img class="close-edit-box" @click="closeEdit" src="./Img/icon_close.png" >
        </div>
        <hr>
        <div class="edit-profile-content-box">
          <div style="display: flex; flex-direction: column; justify-content: center; align-items: center;">
            <img  class="edit-profile-img"  alt="" :src="userPhoto">
            <div style="display: flex;">
                <input  class="choice-profile-img-btn" type="File" id="userPhoto">
                <label for="userPhoto" class="edit-profile-img-btn">선택</label> 
                <button class="edit-profile-img-btn" @click="updatePhoto">변경</button>
            </div>
          </div>
            <div class="edit-profile-content">
            <div>
              <p>이메일</p>
              <div>{{ userEmail}}</div>
            </div>
            <hr>
            <div>  
              <p>닉네임</p>
              <div style="display: flex; justify-content: space-between;">
                <input class="input-style" type="text" v-model="userNickname" required >
                <button class="edit-btn" @click="updateNickname" >수정</button>
              </div>

            </div>
            <hr>
            <div>
              <p>소개글</p>
              <div style="display: flex; justify-content: space-between;">
                <input class="input-style" type="text" v-model="userIntro" placeholder="소개글을 입력해주세요." required>
                <button class="edit-btn" @click="updateIntro">수정</button>
              </div>
            </div>
            <hr>
            <div class="btn-box">
              <button class="withdraw-btn" @click="deleteUser" >회원탈퇴</button>
              <button @click="$router.push('/change-password')" class="password-btn">비밀번호 변경</button>
            </div>
          </div>
        </div>
      </div>
    </div>
</template>

<script>
import { useAuthStore } from '@/store/authStore'
import base64toFile from '../ImageEdit/base64ToFile'
export default {
    name: 'EditProfile',
    data(){
        return{
            userEmail: this.userInfo.emailId,
            userNickname: this.userInfo.nickname,
            userIntro: this.userInfo.bio,
            userPhoto :this.userInfo.imageUrl,
            photoFile : {}
        }
    },
    props:{
        isEdit:Number,
        userInfo:Object,
    },
    mounted(){
        const fileImage = document.getElementById('userPhoto')
        fileImage.addEventListener('change',()=>{
            const file = fileImage.files
            if(file.length >0){
              this.userPhoto = URL.createObjectURL(file[0])
              this.photoFile = file[0]
            }
        })
    },
    watch:{
        userPhoto(){
            console.log(this.userPhoto)
        }
    },
    methods: {

        closeEdit : function(){
            this.$emit('closeEdit')
        },
        async updatePhoto(){
            const auth = useAuthStore()
            try{
                // let data = base64toFile(this.userPhoto,'temp.png')
                let image = await auth.editPhoto(this.photoFile)
                window.location.reload()
            } catch(err){
                console.log(err)
            }
        } ,
        async updateNickname(){
            const auth = useAuthStore()
            try{
                let value = await auth.editNickname(this.userNickname)
                console.log(value)
                window.location.reload()
            }catch(err){
                console.log(err)
            }
        } ,
        async updateIntro(){
          const auth = useAuthStore()
            try{
                let value = await auth.editIntro(this.userIntro)
                console.log(value)
                window.location.reload()
            }catch(err){
                console.log(err)
            }
        },
        deleteUser(){

        }
    },
}
</script>

<style scoped src="./css/EditProfile.css">

</style>