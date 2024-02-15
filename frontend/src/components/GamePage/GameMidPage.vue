<template>
  <div :class="{'game-mid-box':!isDarkMode,'game-mid-box-dark':isDarkMode}">
    <div class="create-room-btn" @click="createRoom()" >
      방만들기
    </div>

    <div v-if="randomData == false"  class="enter-room-btn" @click="randomRoom()">
      입장하기
    </div>
    <div v-else  class="enter-room-btn" @click="randomRoom()">
      취소하기
    </div>

    <div class="game-guide-btn" @click="gameguide()">
      게임 가이드
    </div>
  </div>
</template>

<script>
import { useAuthStore } from '@/store/authStore';
import { useGameStore } from '@/store/gameStore';
import router from '@/router';
import { toast} from 'vue3-toastify'
import "vue3-toastify/dist/index.css"

export default {
    name: 'GameMidPage',
    data(){
      return{
        gameData: {},
        createFlag : false,
        randomData :false,
      }
    },
    props:{
      roomList : Object,
      isDarkMode : Boolean
    },
    watch:{
      roomList(nv, ov){
        if (nv.length <=6){
          this.createFlag = true
        }else{
          this.createFlag = false
        }
      },
      randomData(){
        console.log(this.randomData)
      }
    },
    mounted(){
      this.checkRandomFlag()
      
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
                    console.log(this.userInfo)
                } catch(err){
                    await auth.logout()
                }
            } // 어세스 토큰이 존재 할 시 이를 통해 getUser 미니 프로필 컴포넌트에 해당 객체값 props로 전해줌
        },
        createRoom: async function(){
          const game = useGameStore()
          if (this.roomList.length <6 ){
            try{
              let value = await game.createRoom()
              this.gameData = value.data
              
              router.push({
                name:'room',
                params: {
                  data: JSON.stringify(this.gameData),
                  
                }
                
              })
              
              // console.log(this.gameData)
            }catch(err){
              console.log(err)
            }finally{
              this.createFlag = false
            }
          }else{
            toast('더이상 방을 생성할 수 없어요.',{
                    theme : "auto",
                    "type": "warning",
                    "pauseOnHover": false,
                    "position": "top-center",
                    "transition": "slide",
                    "autoClose": 1000,
                })
          }
        },
        checkRandomFlag: async function(){
          const game = useGameStore()

          try{
            let value = await game.randomFlag()
            this.randomData = value.data
            console.log(this.randomData)
          }catch(err){
            console.log(err)
          }
        },
        randomRoom: async function(){
          const game = useGameStore()
            if (this.randomData === false){
              console.log('입장하기 가능')
              await game.enterRandomRoom()
              this.randomData = true
            }else{
              console.log('입장하기 불가')
              await game.exitRandomRoom()
              this.randomData = false
            }

        },
        gameguide(){
          router.push({name: 'guide'})
        }


    },
    
}
</script>

<style scoped src="./css/GameMidPage.css">

</style>