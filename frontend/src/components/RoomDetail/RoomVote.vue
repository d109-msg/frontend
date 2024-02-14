<template>
  <!-- <div class="chat-container"> -->

    <div :class="{'chat-content':!isDarkMode,'chat-content-dark':isDarkMode}">
      <template v-if="participant.jobId != '판사'"> 
        <div class="vote-title">[투표] {{voteData.voteTitle}}</div>
        <div v-if="!roomTime" class="vote-hint">주어진 시간 내에 투표하지 않으면 본인으로 투표됩니다.</div>   
        
        <div  class=" member-list" v-for="(mem, idx) in voteData.voteList" :key=idx>
          <div class="member">
            <input type="radio" :id="`member-${idx}`" 
            name="member" 
            v-model="votedMember" 
            :value="mem.nickname"
            @click="postVote(participant.id,participant.jobId,mem.id)"
            >
            <img :src="mem.imageUrl" alt="">
            <label :for="`member-${idx}`" class="member-name">{{ mem.nickname }} </label>
            <div  class="vote-graph"><div :id="idx+'graph'" class="graph"></div></div>
            <div  class="vote-count">{{ mem.voteCount }}</div>
          </div>
        </div>
      </template>
      <template v-if="participant.jobId == '판사' && ability.status"> 
        <div class="vote-title">[투표] {{voteData.voteTitle}}</div>
        <div  class="vote-hint">주어진 시간 내에 투표하지 않으면 본인으로 투표됩니다.</div>   
        
        <div  class=" member-list" v-for="(mem, idx) in voteData.voteList" :key=idx>
          <div class="member">
            <input type="radio" :id="`member-${idx}`" 
            name="member" 
            v-model="votedMember" 
            :value="mem.nickname"
            @click="postVote(participant.id,participant.jobId,mem.id)"
            >
            <img :src="mem.imageUrl" alt="">
            <label :for="`member-${idx}`" class="member-name">{{ mem.nickname }} </label>
            <div  class="vote-graph"><div :id="idx+'graph'" class="graph"></div></div>
            <div class="vote-count">{{ mem.voteCount }}</div>
          </div>
        </div>
      </template>
        
      </div>
  <!-- </div> -->
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
        judgeResult : '',
        abilityResult:''
      }
    },
    props:{
      isOpen: Number,
      roomData: Object,
      participant:Object,
      mission:Object,
      ability:Object,
      member: Object,
      roomTime:Number,
      aliveMember:Object,
      isDarkMode:Boolean
    },

    methods: {
      // 낮 : 마피아 지목 투표, 저녁 : 살인 투표
      postVote: async function(id,jobId,targetId){
        const game = useGameStore()
        try{
          let value = await game.postVote(id,jobId,targetId)
          this.voteResult = value.data
          console.log('투표 성공')
          console.log(this.voteResult)
          if (this.voteResult == 'you lost your vote to a gangster.'){
            alert('투표권을 건달에게 압수당하여 오늘은 투표할 수 없습니다.')
          }
          else if (this.voteResult == 'mission uncompleted'){
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

      // 내 투표 결과
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
    // 실시간 투표 현황 보여주기
    graphShow : function(){
      if(this.voteData.voteList ==null){
        return
      }
      for(let i=0; i<this.voteData.voteList.length;i++){
        const item = document.getElementById(`${i}graph`)
        if(item == null){
          break
        }
        const voteCount = this.voteData.voteList[i].voteCount
        const per = Math.round(voteCount/this.voteData.voteList.length*100,1)
        item.style.width = `${per}%`
      }
    },

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