<template>
  <div>
    <div class="game-banner-box">
        <div class="game-banner">
            <div class="search-box">
                <input class="search-bar" type="text" placeholder="초대코드 입력" >
                <div class="search-btn">입장</div>
            </div>
        </div>
    </div>
        <GameMidPageVue></GameMidPageVue>
        <div class="game-bot-box">
            <GameRoomPageVue class="game-room-box"></GameRoomPageVue>
            <MiniProfile v-if="size == 'lg'" :userInfo="userInfo"/>
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
            width: 0,
            height: 0,
            size : 'lg'
        }
    },
    components:{
        GameMidPageVue,
        GameRoomPageVue,
        MiniProfile,
    },
    methods:{
        getUser : async function(){
            const auth = useAuthStore()
            if(auth.getAccess!=""){
                try{
                    await auth.useRefresh()
                    // refresh 사용해 access 갱신 이를 통해 밑의 getUser메서드 가능
                    // 잘못될 시 authStore의 logout function 작동 -> 세션에 저장된 정보들 다 비워줌
                    let value = await auth.getUser()
                    this.userInfo = value.data
                } catch(err){
                    await auth.logout()
                }
            } // 어세스 토큰이 존재 할 시 이를 통해 getUser 미니 프로필 컴포넌트에 해당 객체값 props로 전해줌
        },

        startPage : async function(){
            await this.getUser
            this.emitter.emit('pageChange',1)
        },
        handleResize(event) {
            this.width = window.innerWidth;
            this.height = window.innerHeight;
        },
        reactiveSize : function(){
            const viewportWidth = window.innerWidth
            if (viewportWidth<860) {
                    this.size =  "xs"
                }
                else if (viewportWidth >= 860 && viewportWidth < 1200) {
                    this.size = "md"}
                else {this.size = "lg"}
            window.addEventListener('resize', this.handleResize);
        },
    },
    beforeDestroy() {
        // console.log("beforeDestroy...");
        window.removeEventListener('resize', this.handleResize);
    },
    mounted(){
        this.startPage()
        this.reactiveSize()

    },
    watch:{
      width(){
            if (this.width<440) {
                this.size =  "xs"
                // console.log(this.size)
            }
            else if (this.width >= 440 && this.width < 1100) {
                this.size = "md"
                // console.log(this.size)
            }
            else {
                this.size = "lg"
                // console.log(this.size)
        }
        },
    }
}
</script>

<style scoped src="./css/GamePage.css">

</style>