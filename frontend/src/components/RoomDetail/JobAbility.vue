<template>
    <div :class="{'ability-content':!isDarkMode,'ability-content-dark':isDarkMode}">
        <div>{{ ability.message }}</div>
        <div v-if="participant.jobId == '훼방꾼' || participant.jobId == '스파이' || participant.jobId == '건달' || participant.jobId == '정치인' || participant.jobId == '군인' ||participant.jobId == '판사' || participant.jobId == '기자' || participant.jobId == '자경단'"
        >(1회 한정) 
            <span v-if="participant.jobId == '훼방꾼' || participant.jobId == '스파이' || participant.jobId == '건달' || participant.jobId == '판사' || participant.jobId == '기자'" >신중히 사용해주세요.</span></div>
        
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
            <button class="ability-btn" @click="useAbility(participant.id,-1)">능력 사용</button>
        </div>
        
        

    </div>


</template>

<script>
import { useGameStore } from '@/store/gameStore';
import { toast} from 'vue3-toastify'
import "vue3-toastify/dist/index.css"

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
      aliveMember:Object,
      isDarkMode:Boolean
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
                toast(this.judgeResult,{
                    theme : "auto",
                    "type": "success",
                    "pauseOnHover": false,
                    "position": "top-center",
                    "transition": "slide",
                    "autoClose": 1000,
                })
            }catch(err){
                console.log(err)
            }
        },
        useAbility: async function(id,targetId){
        const game = useGameStore()
            try{
                let value = await game.useAbility(id, targetId)
                this.abilityResult = value.data
                toast(this.abilityResult,{
                    theme : "auto",
                    "type": "warning",
                    "pauseOnHover": false,
                    "position": "top-center",
                    "transition": "slide",
                    "autoClose": 1000,
                }) 
            }catch(err){
                console.log(err)
            }
        }
    }
}
</script>

<style scoped src="./css/jobAbility.css">
#top-center{
    z-index: 10000000;
}
</style>