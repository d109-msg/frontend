<template>
    <div style=" width: 100%; height: 100%;">

    <div  :class="{'feed-title-box':!isDarkMode, 'feed-title-box-dark':isDarkMode}">
      <div @click="refresh" class="reset-icon"></div>
      <div :class="{'invite-code-title':!isDarkMode,'invite-code-title-dark':isDarkMode}">{{ roomData.title }}</div>
      <div v-if="mission.flagSuccess==0" class="create-icon" @click="createOn"></div>
      <div v-else class="disable-icon"></div>

    </div>
    <div class="follow-back" v-if="gameMemberFlag==1" @click.self="gameMemberFlag=0">
      <div class="follow-modal">
        <div class="follow-title">
          GAME MEMBER
        </div>
        <div class="follow-content">
          <div v-for="(mem,idx) in member" :key="idx">
            <div class="follow-body" v-if="mem.id != participant.id">
              <img :src="mem.imageUrl" style="cursor: pointer;" class="follow-img" @click="goFriend(mem.userId)">
              <p class="follow-name" style="cursor: pointer;" @click="goFriend(mem.userId)">{{ mem.nickname }}</p>
              <div class="follow-btn" v-if="!mem.isFollowing" @click="follow(mem.userId,idx)">Follow</div>
              <div class="follow-btn" v-if="mem.isFollowing" @click="follow(mem.userId,idx)">Unfollow</div>
              <div class="report-btn" @click="report(mem.nickname)">Report</div>
          </div>
        </div>
        </div>
      </div>
    </div>
    <div :class="{'feed-content':!isDarkMode,'feed-content-dark':isDarkMode}">

        <img src="./Img/arrow_up.png" class="toggle-job" @click="missionBox=!missionBox" v-if="missionBox">
        <img src="./Img/arrow_down.png" class="toggle-job" @click="missionBox=!missionBox" v-else>
        <div class="mission-box" v-if="missionBox">
            <div v-if="participant.flagMafia == false">
                오늘의 미션 : {{ mission.normalMission }}
            </div>
            <div v-if="participant.flagMafia == true">
                오늘의 미션 : {{ mission.mafiaMission }}
            </div>
            <div v-if="participant.flagMafia == true">
                시민 미션 : {{ mission.normalMission }}
            </div>
        </div>
        <div class="feed-content-box">
            <div v-for="(feed,idx) in feedList" :key="idx"  style="display: flex; justify-content: center;" :id="`feed${idx}`">
                    <RoomFeedCardVue :item="feed" :is-dark-mode="isDarkMode" :room="roomData"/>
            </div>
            <div :class="{'member-btn':!isDarkMode,'member-btn-dark':isDarkMode}" @click="gameMemberFlag=1" v-if="roomData.endTime != null || (roomData.id in chat.getEndRoom)">Member List</div>
        </div>
        

    </div>
    <FeedCreate v-if="create && participant.flagMafia" @close="reload" :roomId="roomData.id" :is-dark-mode="isDarkMode" />
    <FeedCreate v-if="create && !(participant.flagMafia)" @close="reload" :roomId="roomData.id" :is-dark-mode="isDarkMode" />
</div>

</template>

<script>
import { useAuthStore } from '@/store/authStore';
import FeedCreate from '../FeedPage/FeedCreate.vue';
import { useFeedStore } from '@/store/feedStore';
import { useGameStore } from '@/store/gameStore';
import RoomFeedCardVue from './RoomFeedCard.vue';
import router from '@/router';
import { useChatStore } from '@/store/chatStore';
import { toast} from 'vue3-toastify'
import "vue3-toastify/dist/index.css"

export default {
    name:'RoomFeedStart',
    data(){
        return{
            create: false,
            feedList:[],
            nextUrl : `?roomId=${this.roomData.id}`,
            io : {},
            feedList : [],
            missionBox : true,
            chat : useChatStore()
        }
    },
    props:{
      roomData : Object,
      participant:Object,
      mission:Object,
      ability:Object,
      member: Object,
      roomTime:Number,
      isDarkMode:Boolean,
      gameMemberFlag : Number,
    },
    components:{
        FeedCreate,
        RoomFeedCardVue
    },
    methods:{
        report(value){
            toast(`${value} 사용자 신고가 완료되었습니다.`,{
                    theme : "auto",
                    "type": "error",
                    "pauseOnHover": false,
                    "position": "top-center",
                    "transition": "slide",
                    "dangerouslyHTMLString": true,
                    "autoClose": 1000,
                    
                })
        },
        async follow(data,idx){
            const auth = useAuthStore()
            await auth.follow(data)
            this.member[idx].isFollowing = !this.member[idx].isFollowing
            let value = false
            let type = "success"

            if(this.member[idx].isFollowing == 0){
                value = "팔로우 취소"
                type = "error"
            }else{
                value = "팔로우"
            }
            toast(`${this.member[idx].nickname} ${value}가 완료되었습니다.`,{
                    theme : "auto",
                    "type": type,
                    "pauseOnHover": false,
                    "position": "top-center",
                    "transition": "slide",
                    "dangerouslyHTMLString": true,
                    "autoClose": 1000,
                    
                })
        },
        goFriend(data){
        router.push(`/user/${data}`)
      },
        refresh : function(){
            window.location.reload()
        },
        createOn : function(){
            if(this.participant.flagDie == 1){
                toast('당신은 사망하셨습니다. 더이상 게임에 참여하실 수 없습니다.',{
                    theme : "auto",
                    "type": "error",
                    "pauseOnHover": false,
                    "position": "top-center",
                    "transition": "slide",
                    "autoClose": 1000,
                })
                return
            }
            const feed = useFeedStore()
            if(this.participant.flagMafia){
                feed.setMission(this.mission.mafiaMission)
            }else{
                feed.setMission(this.mission.normalMission)
            }
            this.create = true
        },
        reload : function(value){
                this.create = false
                if(value == 1){
                    window.location.reload()
                    //emit으로 글 작성 완료되었다는 이벤트 왔을 시 현 화면 새로고침
                }
        },
        readFeed : async function(){
            const game = useGameStore()
            if(this.nextUrl != ""){
                let value = await game.readFeed(this.nextUrl)
                if(value.data != ""){
                    let count = 0
                    value.data.articles.forEach(item=>{
                        console.log('값',item)
                        this.feedList.unshift(item)
                        count+=1
                    })
                    this.nextUrl = value.data.nextUrl
                    if(this.nextUrl != ""){
                        this.$nextTick(()=>{
                            const target = document.getElementById(`feed${0}`)
                            let height = 0
                            for(let i=0; i<count; i++){
                                const el = document.getElementById(`feed${i}`)
                                height += el.offsetHeight
                            }
                            const elem = document.querySelector('.feed-content-box')
                            elem.scrollTop = height
                            this.io.observe(target)
                        })
                    }
                }
            } else{
                return
            }
        },
        call : function(items,io){
            items.forEach(async item=>{
                if(item.isIntersecting){
                    io.unobserve(item.target)
                    await this.readFeed()
                }
            })
        }


        
    },
    mounted(){
        this.io = new IntersectionObserver(this.call,{threshold:0.7})
        this.readFeed()
    }
}
</script>

<style scoped src="./css/RoomFeedStart.css">
#top-center{
    z-index: 10000000;
}
</style>