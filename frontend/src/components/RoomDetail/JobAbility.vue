<template>
  <div class="chat-container">
    <div class="ability-content">
        <div>{{ ability.message }}</div>
        <!-- <div>{{ ability }}</div> -->
        
        <!-- 지목대상이 필요한 능력이라면 -->
        <template v-if="ability.flagTarget"  >
            <div class="member-list" v-for="(mem, idx) in aliveMember" :key="idx">
                <div class="member">
                    <input type="radio" :id="`member-${idx}`" 
                    name="member" 
                    v-model="votedMember" 
                    :value="mem.nickname"
                    @click="useAbility(participant.id,mem.id)"
                    >
                    <img :src="mem.imageUrl" alt="">
                    <div style="display: hidden;" :id="idx+'graph'"></div>
                    <label :for="`member-${idx}`" class="member-name">{{ mem.nickname }} </label>
                </div>
            </div>
        </template>
        <!-- 지목대상이 필요없는 능력이라면 -->
        <div v-if=" ability.flagTarget==false && ability.status">
            <button class="ability-btn" @click="useAbility(participant.id,0)">능력 사용</button>
        </div>
        
        

    </div>
  </div>


</template>

<script>
import { useGameStore } from '@/store/gameStore';

export default {
    name:'jobAbility',
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
      aliveMember:Object
    },
    methods:{
        judgeAbility: async function(id){
            let targetId = null
            this.voteData.voteList.forEach(item=>{
                if(item.nickname == this.votedMember){
                targetId = item.id
                }
            })
            const game = useGameStore()
            try{
                let value = await game.useAbility(id, targetId)
                this.judgeResult = value.data 
                alert(this.judgeResult)
            }catch(err){
                console.log(err)
            }
        },
        useAbility: async function(id,targetId){
        const game = useGameStore()
            try{
                let value = await game.useAbility(id, targetId)
                this.abilityResult = value.data 
                alert(this.abilityResult)
                window.location.reload()
            }catch(err){
                console.log(err)
            }
        }
    }
}
</script>

<style scoped src="./css/jobAbility.css">

</style>