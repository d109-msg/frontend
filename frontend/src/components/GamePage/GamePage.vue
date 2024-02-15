<template>
  <div :class="{'game-page':!isDarkMode,'game-page-dark':isDarkMode}">
    <div class="game-banner-box">
        <div class="game-banner">
            <div class="search-box">
                <input :class="{'search-bar':!isDarkMode,'search-bar-dark':isDarkMode}" type="text" v-model="inviteCode" placeholder="초대코드 입력" >
                <div :class="{'search-btn':!isDarkMode,'search-btn-dark':isDarkMode}" @click="enterInviteRoom()">입장</div>
            </div>
        </div>
    </div>
        <GameMidPageVue :room-list="roomList" :is-dark-mode="isDarkMode" ></GameMidPageVue>
        <div :class="{'game-bot-box':!isDarkMode, 'game-bot-box-dark':isDarkMode}">
            <GameRoomPageVue :class="{'game-room-box':!isDarkMode, 'game-room-box-dark':isDarkMode}" :room-list="roomList" :is-dark-mode="isDarkMode"></GameRoomPageVue>
            <MiniProfile v-if="size == 'lg'" :userInfo="userInfo" :is-dark-mode="isDarkMode" />
        </div>
  </div>
</template>

<script>
import GameMidPageVue from './GameMidPage.vue'
import GameRoomPageVue from './GameRoomPage.vue'
import MiniProfile from '../MiniProfile/MiniProfile.vue'
import { useAuthStore } from '@/store/authStore'
import { useGameStore } from '@/store/gameStore';
import { toast} from 'vue3-toastify'
import "vue3-toastify/dist/index.css"

import router from '@/router'
import { useChatStore } from '@/store/chatStore'

export default {
    name: "GamePage",
    data(){
        return{
            prevScrollY : '',
            userInfo : {},
            width: 0,
            height: 0,
            size : 'lg',
            inviteCode: "",
            roomList :{},
            inviteRoom:{}
        }
    },
    computed : {
        gameReset(){
            const chat = useChatStore()
            return  chat.getGameReset
        }
    },
    props:{
        isDarkMode : Boolean
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
                    // console.log(this.userInfo)
                } catch(err){
                    await auth.logout()
                }
            } // 어세스 토큰이 존재 할 시 이를 통해 getUser 미니 프로필 컴포넌트에 해당 객체값 props로 전해줌
        },
        getRoomList: async function(){
            const game = useGameStore()
            try{
              let value = await game.getRoomList()
              this.roomList = value.data
              console.log(this.roomList)
            }catch(err){
              console.log(err)
            }
        },
        startPage : async function(){
            await this.getUser()
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
        enterInviteRoom : async function(){
            const game = useGameStore()
            try{
                let value = await game.enterInviteRoom(this.inviteCode)
                console.log(value)
                if (value.status == 200){
                    this.inviteRoom = value.data
                    router.push({
                        name:'room',
                        params: {
                            data: JSON.stringify(this.inviteRoom),
                        }
                    })
                }else if (value.status == 204){
                    toast('존재하지 않는 방입니다.',{
                    theme : "auto",
                    "type": "error",
                    "pauseOnHover": false,
                    "position": "top-center",
                    "transition": "slide",
                    "autoClose": 1000,
                })
                }


            }catch(err){
                console.log(err.response.status)
                if(err.response.status == 409){
                    toast('이미 참여 중인 게임방입니다.',{
                    theme : "auto",
                    "type": "error",
                    "pauseOnHover": false,
                    "position": "top-center",
                    "transition": "slide",
                    "autoClose": 1000,
                })

                }else if(err.response.status == 403)
                toast('인원이 다 찬 방입니다.',{
                    theme : "auto",
                    "type": "error",
                    "pauseOnHover": false,
                    "position": "top-center",
                    "transition": "slide",
                    "autoClose": 1000,
                })
            }

        }
    },
    beforeDestroy() {
        // console.log("beforeDestroy...");
        window.removeEventListener('resize', this.handleResize);
    },
    mounted(){
        this.startPage()
        this.reactiveSize()
        this.getRoomList()


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
        gameReset(nv,ov){
            if(nv == 1){
                this.getRoomList()
                const chat = useChatStore()
                chat.setGameReset(0)
            }
        }
    }
}
</script>

<style scoped src="./css/GamePage.css">

</style>