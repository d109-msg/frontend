<template>
    <div class="my-mini"> 
        <div class="mini-title">
            <p class="mini-title-message">My Profile</p>
            <img class="logout" src="./logout.png" v-if="isLogin" @click="logout" @mouseover="toolFlag=true" @mouseleave="toolFlag=false">
            <div class="tool-tip" v-if="toolFlag"><span>Logout</span></div>
        </div>
        <div class="my-mini-content" v-if="isLogin">
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
        <div class="my-mini-content" v-if="!isLogin">
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
            userInfo : {},
            isLogin : false,
            toolFlag : false,
        }
    },
    methods:{
        getUser : async function(){
            const auth = useAuthStore()
            try{
                let value = await auth.getUser()
                this.userInfo = value.data
                // console.log(this.userInfo)

            }catch(err){
                console.log(err)
            }
        },
        logout : function(){
            const auth = useAuthStore()
            auth.logout()
            window.location.reload()
        }
    },
    mounted(){
        const auth = useAuthStore()
        if(auth.getAccess == ""){
            this.isLogin
        } else{
            this.getUser()
            this.isLogin = true
        }

    }
}
</script>

<style scoped src="./MiniProfile.css">

</style>