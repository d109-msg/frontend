<template>
    <div class="my-mini"> 
        <div class="mini-title">
            <p class="mini-title-message">My Profile</p>
            <img class="logout" src="./logout.png" v-if="isLogin" @click="logout" @mouseover="toolFlag=true" @mouseleave="toolFlag=false">
            <div class="tool-tip" v-if="toolFlag"><span>Logout</span></div>
        </div>
        <div class="my-mini-content" v-if="userFlag">
            <img class="mini-image" :src="userInfo.imageUrl">
            <div class="mini-name">{{ userInfo.nickname }}</div>
            <div class="mini-comment">fun, daily, mafia game</div>
            <div class="mini-following-count">
                <span class="following">Following</span>
                <span class="count">22</span>
            </div>
            <div class="mini-following-count">
                <span class="following">Followers</span>
                <span class="count">24</span>
            </div>
        </div>
        <div class="my-mini-content" v-if="!userFlag">
            <img class="mini-image" src="./default.png">
            <div class="mini-name">Anonymous</div>
            <div class="mini-comment">fun, daily, mafia game!!!</div>
            <div class="mini-following-count">
                <span class="following">Following</span>
                <span class="count">0</span>
            </div>
            <div class="mini-following-count">
                <span class="following">Followers</span>
                <span class="count">0</span>
            </div>
        </div>
    </div>
</template>

<script>
import { useAuthStore } from '@/store/authStore'
export default {
    name: 'MiniProfile',
    data(){
        return{
            isLogin : false,
            toolFlag : false,
            user : {},
            userFlag : false,
        }
    },
    methods:{
        
        logout : function(){
            const auth = useAuthStore()
            auth.logout()
            window.location.reload()
        }
    },
    props:{
        userInfo : Object,
    },

    watch:{
        userInfo(nv,ov){
            Object.keys(nv).forEach(item=>{
                if(item == "imageUrl"){
                    this.userFlag = true
                }
            })
            
        }
    },
    mounted(){
        const auth = useAuthStore()
        if(auth.getAccess == ""){
            this.isLogin = false
            //로그인 여부를 getAccess의 토큰 여부로 검색, isLogin을 일종의 플래그로 둠 이를 통해 위의 :src(이미지)에서 비로그인일 시 undefined 오류 방지
        } else{
            this.isLogin = true
            //로그인 여부를 getAccess의 토큰 여부로 검색, isLogin을 일종의 플래그로 둠 이를 통해 위의 :src(이미지)에서 비로그인일 시 undefined 오류 방지
        }

    }
}
</script>

<style scoped src="./MiniProfile.css">

</style>