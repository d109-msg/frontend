<template>
  <div class="detail-container">
    <div class="day-back" v-if="dayStep">
      <div style="font-size: 30px; color: white; font-weight: bold;">
        낮이 되었습니다.
      </div>
    </div>
    <div class="day-back" v-if="nightStep">
      <div style="font-size: 30px; color: white; font-weight: bold;">
        밤이 되었습니다.
      </div>
    </div>
    <RoomFeed 
    v-if="(step==0 && size=='xs') || size=='lg' || size=='md'"
    :size="size"
    :room-data="roomData"
    :participant="participant"
    :mission="mission"
    :ability="ability"
    :member="member"
    :room-time="roomTime"
    :is-dark-mode="isDarkMode"
    @open-chat="stepUp"
    />
    <div :class="{'chat-container':!isDarkMode,'chat-container-dark':isDarkMode}" v-if="(step==1 && size=='xs') || size=='lg' || size=='md'">
      <div :class="{'chat-title-box':!isDarkMode,'chat-title-box-dark':isDarkMode}" style="display: flex; justify-content: space-between;">
        <div >
          <img v-if="!roomTime" @click="dayFlag()" src="./Img/icon_sun_active.png" alt="" style="cursor: pointer;">
          <img v-else @click="dayFlag()" src="./Img/icon_sun.png" alt="" style="cursor: pointer;">
          <img v-if="roomTime" @click="nightFlag()" src="./Img/icon_moon_active.png" alt="" style="cursor: pointer;">
          <img v-else @click="nightFlag()" src="./Img/icon_moon.png" alt="" style="cursor: pointer;">
        </div>
        <img src="./Img/arrow_down.png" alt="" style="width: 30px; height: 30px; rotate: 90deg; cursor: pointer;"
        @click="step = 0" v-if="size=='xs'"
        >
        <div style="display: flex; flex-direction: row; align-items: center;">
          <img v-if="isOpen==1" src="./Img/icon_chat_active.png" alt="" class="is-chat" @click="isChat" >
          <img v-else src="./Img/icon_chat.png" alt="" class="is-chat" @click="isChat" >
          
          <div v-if="isOpen==2" class="is-guide-active" @click="isGuide"></div>
          <div v-else class="is-guide" @click="isGuide"></div>
          
          <div v-if="isOpen==3" class="is-vote-active" @click="isVote"></div>
          <div v-else class="is-vote" @click="isVote"></div>
          
          <div v-if="isOpen==4" class="is-ability-active" @click="isAbility"></div>
          <div v-else class="is-ability" @click="isAbility"></div>
        </div>
          
    </div>
      <RoomChat v-if="isOpen==1" 
      :room-data="roomData"
      :participant="participant"
      :mission="mission"
      :ability="ability"
      :member="member"
      :room-time="roomTime"
      :alive-member="aliveMember"
      :is-dark-mode="isDarkMode"
      />
      <RoomGuide v-else-if="isOpen==2" :is-dark-mode="isDarkMode"></RoomGuide>
      <RoomVote v-else-if="isOpen==3" 
      :room-data="roomData"
      :participant="participant"
      :mission="mission"
      :ability="ability"
      :member="member"
      :room-time="roomTime"
      :alive-member="aliveMember"
      :is-dark-mode="isDarkMode"
      />
      <JobAbility v-else-if="isOpen==4"
      :room-data="roomData"
      :participant="participant"
      :mission="mission"
      :ability="ability"
      :member="member"
      :room-time="roomTime"
      :alive-member="aliveMember"
      :is-dark-mode="isDarkMode"
      />

    </div>

  </div>
</template>

<script>
import RoomFeed from './RoomFeed.vue';
import RoomChat from './RoomChat.vue';
import RoomGuide from './RoomGuide.vue';
import RoomVote from './RoomVote.vue';
import JobAbility from './JobAbility.vue';
import { useGameStore } from '@/store/gameStore';
import { useChatStore } from '@/store/chatStore';
import mitt from 'mitt';
export default {
  
    name: 'RoomDetailPage',
    data(){
      return{
        isOpen : '1',
        roomData : {},
        participant:{},
        mission:{},
        ability:{},
        member:{},
        roomTime : 0,
        aliveMember:{},
        size : "",
        width : 0,
        height : 0,
        step : 0,
        dayStep : false,
        nightStep : false,
      }
    },
    computed:{
      dayTurn (){
        const chat = useChatStore()
        return chat.getDay
      },
      nightTurn(){
        const chat = useChatStore()
        return chat.getNight
      }
    },
  
    components:{
        RoomFeed,
        RoomChat,
        RoomGuide,
        RoomVote,
        JobAbility
    },
    mounted(){
      this.roomData = JSON.parse(this.$route.params.data)
      // console.log(this.roomData)
      this.startPage()
      this.checkNight()
      this.reactiveSize()
    },
    props:{
      isDarkMode : Boolean,
    },
    methods:{
      stepUp(data){
        this.step = data
      },
      handleResize(event) {
            this.width = window.innerWidth;
            this.height = window.innerHeight;
        },
      reactiveSize : function(){
          const viewportWidth = window.innerWidth
          if (viewportWidth<1070) {
                  this.size =  "xs"
              }
              else if (viewportWidth >= 1070 && viewportWidth < 1440
              ) {
                  this.size = "md"}
              else {this.size = "lg"}
          window.addEventListener('resize', this.handleResize);
      },
      isChat(){
        this.isOpen = 1
      },
      isGuide(){
        this.isOpen = 2
      },
      isVote(){
        this.isOpen = 3
      },
      isAbility(){
        this.isOpen = 4
      },
      checkNight : async function(){
        const game = useGameStore()
        try{
          let value = await game.checkNight()
          this.roomTime = value.data
          console.log(typeof(this.roomTime))
        }catch(err){
          console.log(err)
        }
      },
      nightFlag : async function(){
        const game = useGameStore()
        try{
          let value = await game.nightFlag()
          console.log('밤으로 변경')
          // window.location.reload()
        }catch(err){
          console.log(err)
        }
      },
      dayFlag : async function(){
        const game = useGameStore()
        try{
          let value = await game.dayFlag()
          console.log('아침으로 변경')
          // window.location.reload()

        }catch(err){
          console.log(err)
        }
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
      getAliveMember: async function(roomId){
        const game = useGameStore()
        try{
          let value = await game.getAliveMember(roomId)
          this.aliveMember = value.data
          console.log('살아있는 유저 조회 성공')
          console.log(this.aliveMember)
        }catch(err){
          console.log('살아있는 유저 조회 실패')

          console.log(err)
        }
      },
      startPage : async function(){
        await this.getParticipant(this.roomData.id)
        await this.getMemberList(this.roomData.id)
        await this.getAliveMember(this.roomData.id)
        await this.getMission(this.participant.id)
        await this.getAbility(this.participant.id)
      }
    },
    watch:{
      width(nv,ov){
            if(nv<1070){
                this.size = "xs"
            console.log('사이즈',this.size)

            } else if(nv >= 1070 && nv < 1440){
                this.size = "md"
            console.log('사이즈',this.size)

            }else{
                this.size = "lg"
            console.log('사이즈',this.size)

            }
        },
        async dayTurn(nv,ov){
          if(nv == 1){
            this.emitter.emit('nightChange',false)
            await this.getParticipant(this.roomData.id)
            await this.getMemberList(this.roomData.id)
            await this.getAliveMember(this.roomData.id)
            await this.getMission(this.participant.id)
            await this.getAbility(this.participant.id)
            const chat = useChatStore()
            chat.setDay(0)
            this.dayStep = true
            setTimeout(()=>{
              this.dayStep = false
            },3000)
          }
        },
        async nightTurn(nv,ov){
          if(nv == 1){
            this.emitter.emit('nightChange',true)
            await this.getParticipant(this.roomData.id)
            await this.getMemberList(this.roomData.id)
            await this.getAliveMember(this.roomData.id)
            await this.getMission(this.participant.id)
            await this.getAbility(this.participant.id)
            const chat = useChatStore()
            chat.setNight(0)
            this.nightStep = true
            setTimeout(()=>{
              this.nightStep = false
            },3000)
          }
        }
    },

}
</script>

<style scoped src="./css/RoomDetailPage.css">

</style>