<template>
  <div class="chat-container">

    <div class="chat-content">
      <div  class="vote-title">[투표] {{voteData.voteTitle}}</div>
      <!-- <div v-else class="vote-title">{{ ability.message }}</div> -->

      <div class=" member-list" v-for="(mem, idx) in voteData.voteList" :key=idx>
        <div class="member">
          <input type="radio" :id="`member-${idx}`" 
          name="member" 
          v-model="votedMember" 
          :value="mem.nickname"
          @click="postVote(participant.id,participant.jobId,mem.id)"
          >
          <img :src="mem.imageUrl" alt="">
          <label :for="`member-${idx}`" class="member-name">{{ mem.nickname }} </label>
          <div class="vote-graph"><div :id="idx+'graph'" class="graph"></div></div>
          <div class="vote-count">{{ mem.voteCount }}</div>
        </div>
      </div>
      <div v-if="roomTime == 0" class="vote-hint">
        <p>주어진 시간 내에 투표하지 않으면</p>
        <p>본인으로 투표됩니다.</p>
      </div>   
      </div>
      
  </div>
</template>

<script>
import { useGameStore } from '@/store/gameStore';
export default {
    name: 'RoomVote',
    data(){
      return{
        result : '',
        votedMember: {},
        voteResult :'',
        voteData : {},
      }
    },
    props:{
      isOpen: Number,
      roomData: Object,
      participant:Object,
      mission:Object,
      ability:Object,
      member: Object,
      roomTime:Object
    },
    methods: {
      postVote: async function(id,jobId,targetId){
        const game = useGameStore()
        try{
          let value = await game.postVote(id,jobId,targetId)
          this.voteResult = value.data
          // console.log('투표 성공')
          // console.log(this.voteResult)
          if (this.voteResult == 'mission uncompleted'){
            alert('오늘의 미션을 완료해주세요.')
          }else{
            await this.getvoteRoom(this.participant.roomId)
            this.$nextTick(()=>{
              this.graphShow()
            })
          }
        }catch(err){
          console.log('투표 실패')
        }
      },
      getMyPick: async function(id){
        const game = useGameStore()
        try{
          let value = await game.getMyPick(id)
          this.votedMember = value.data
        }catch(err){
          console.log(err)
      }},
      getvoteRoom: async function(roomId){
        const game = useGameStore()
        try{
          let value = await game.getvoteRoom(roomId)
          this.voteData = value.data
        }catch(err){
          console.log(err)
      }
    },
    graphShow : function(){
      for(let i=0; i<this.voteData.voteList.length;i++){
        const item = document.getElementById(`${i}graph`)
        const voteCount = this.voteData.voteList[i].voteCount
        const per = Math.round(voteCount/this.voteData.voteList.length*100,1)
        item.style.width = `${per}%`
      }
    }
},
async mounted(){
      await this.getMyPick(this.participant.id)
      await this.getvoteRoom(this.participant.roomId)
      this.graphShow()
    }
}
</script>

<style scoped src="./css/RoomVote.css">

</style>