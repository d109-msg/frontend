<template>
  <div class="detail-container">
    <RoomFeed 
    :room-data="roomData"
    :participant="participant"
    :mission="mission"
    :ability="ability"
    :member="member"
       />
    <div class="chat-container">
      <div class="chat-title-box">
        <div class="is-chat" @click="isChat" ></div>
        <div class="is-guide" @click="isGuide"></div>
        <div class="is-vote" @click="isVote"></div>
    </div>
      <RoomChat v-if="isOpen==1" 
      :room-data="roomData"
      :participant="participant"
      :mission="mission"
      :ability="ability"
      :member="member"

      ></RoomChat>
      <RoomGuide v-else-if="isOpen==2" ></RoomGuide>
      <RoomVote v-else-if="isOpen==3" 
      :room-data="roomData"
      :participant="participant"
      :mission="mission"
      :ability="ability"
      :member="member"
      ></RoomVote>
    </div>

  </div>
</template>

<script>
import RoomFeed from './RoomFeed.vue';
import RoomChat from './RoomChat.vue';
import RoomGuide from './RoomGuide.vue';
import RoomVote from './RoomVote.vue';
import { useAuthStore } from '@/store/authStore';
import { useGameStore } from '@/store/gameStore';
export default {
  
    name: 'RoomDetailPage',
    data(){
      return{
        isOpen : '1',
        roomData : {},
        participant:{},
        mission:{},
        ability:{},
        member:{}
        
      }
    },
    components:{
        RoomFeed,
        RoomChat,
        RoomGuide,
        RoomVote
    },
    mounted(){
      this.roomData = JSON.parse(this.$route.params.data)
      console.log(this.roomData)
      this.startPage()
    },
    watch:{
      $route(to,from){}
    },
    methods:{
      isChat(){
        this.isOpen = 1
        console.log(this.isOpen)

      },
      isGuide(){
        this.isOpen = 2
        console.log(this.isOpen)

      },
      isVote(){
        this.isOpen = 3
        console.log(this.isOpen)

      },
      getParticipant: async function(roomId){
        const game = useGameStore()
        try{
          let value = await game.getParticipant(roomId)
          this.participant = value.data
          console.log('participant 조회 성공')
          console.log(this.participant)
        }catch(err){
          console.log('participant 조회 실패')
          console.log(err)
        }
      },
      getMemberList: async function(roomId){
        const game = useGameStore()
        try{
          let value = await game.getMemberList(roomId)
          this.member = value.data
          console.log('멤버 조회 성공')
          console.log(this.member)
        }catch(err){
          console.log('멤버 조회 실패')
          console.log(err)
        }
      },
      getMission : async function(participantId){
        const game = useGameStore()
        try{
          let value = await game.getMission(participantId)
          this.mission = value.data
          console.log('직업 조회 성공')
          console.log(this.mission)
        }catch(err){
          console.log('직업 조회 실패')
          console.log(err)
        }
      },
      getAbility : async function(participantId){
        const game = useGameStore()
        try{
          let value = await game.getAbility(participantId)
          this.ability = value.data
          console.log('능력 조회 성공')
          console.log(this.ability)
        }catch(err){
          console.log('능력 조회 실패')
          console.log(err)
        }
      },
      startPage : async function(){
        await this.getParticipant(this.roomData.id)
        await this.getMemberList(this.roomData.id)
        await this.getMission(this.participant.id)
        await this.getAbility(this.participant.id)
      }
    }
}
</script>

<style scoped src="./css/RoomDetailPage.css">

</style>