<template>
  <div class="game-mid-box">
    <div class="create-room-btn" @click="createRoom()" >
      방만들기
    </div>
    <div  class="enter-room-btn">
      입장하기
    </div>
    <div class="game-guide-btn">
      게임 가이드
    </div>
  </div>
</template>

<script>
import { useAuthStore } from '@/store/authStore';
import { useGameStore } from '@/store/gameStore';
import router from '@/router';

export default {
    name: 'GameMidPage',
    data(){
      return{
        gameData: {},
        createFlag : false,
      }
    },
    props:{
      roomList : Object,
    },
    watch:{
      roomList(nv, ov){
        if (nv.length <=6){
          this.createFlag = true
        }else{
          this.createFlag = false
        }
      }
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
            alert('더이상 방을 생성할 수 없어요.')
          }
        }


    },
    
}
</script>

<style src="./css/GameMidPage.css">

</style>