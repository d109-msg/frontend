<template>
    <div class="my-mini"> 
        <div class="mini-title">
            <p class="mini-title-message">My Profile</p>
        </div>
        <div class="my-mini-content">
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
    </div>
</template>

<script>
import { useAuthStore } from '@/store/authStore'
export default {
    name: 'MiniProfile',
    data(){
        return{
            userInfo : {},

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
        }
    },
    mounted(){
        console.log(this.userInfo)
        this.getUser()

    }
}
</script>

<style scoped src="./MiniProfile.css">

</style>