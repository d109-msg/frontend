<template>
  <div>
    <div class="banner">
        <div class="search-box">
            <input class="search-bar" type="text" value="초대코드 입력">
            <div class="search-btn">입장</div>
        </div>
    </div>
    <GameMidPageVue></GameMidPageVue>
    <div class="game-bot-box">
        <GameRoomPageVue class="game-room-box"></GameRoomPageVue>
        <MiniProfile :userInfo="userInfo"/>

    </div>
  </div>
</template>

<script>
import GameMidPageVue from './GameMidPage.vue'
import GameRoomPageVue from './GameRoomPage.vue'
import MiniProfile from '../MiniProfile/MiniProfile.vue'
import { useAuthStore } from '@/store/authStore'

export default {
    name: "GamePage",
    data(){
        return{
            prevScrollY : '',
            userInfo : {},
        }
    },
    methods:{
        getUser : async function(){
            const auth = useAuthStore()
            if(auth.getAccess!=""){
                let value = await auth.getUser()
                this.userInfo = value.data
            }
        }
    },
    components:{
        GameMidPageVue,
        GameRoomPageVue,
        MiniProfile,
    },
    mounted(){
        this.getUser()
        this.emitter.emit('pageChange',1)
        let banner = document.querySelector('.banner')
        this.prevScrollY = window.scrollY
        window.addEventListener('scroll',()=>{
            let nowScrollY = window.scrollY
            if(this.prevScrollY < nowScrollY){  
                banner.classList.remove('banner-up-event')
                banner.classList.add('banner-down-event')
            }else{
                banner.classList.add('banner-up-event')
                banner.classList.remove('banner-down-event')
            }
            this.prevScrollY = nowScrollY

        })
    },
}
</script>

<style scoped src="./css/GamePage.css">

</style>