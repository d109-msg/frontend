<template>
    <div :class="{'feed-title-box':!isDarkMode, 'feed-title-box-dark':isDarkMode}">
      <div class="invite-code-title">{{ roomData.title }}</div>
    </div>
    <div :class="{'feed-content':!isDarkMode,'feed-content-dark':isDarkMode}">
      <div>
        <p :class="{'invite-code-title':!isDarkMode,'invite-code-title-dark':isDarkMode}">초대코드</p>
        <p :class="{'invite-code':!isDarkMode,'invite-code-dark':isDarkMode}" >
          {{ roomData.id }}
        </p>
        <div style="display: flex; justify-content: space-evenly; ">
          <button id="invite-copy" class="invite-btn" @click="copyBtn()">초대코드 복사하기</button>
          <button class="invite-btn" @click="listFlag = true">초대코드 공유하기</button>
        </div>
        <p :class="{'invite-code-title':!isDarkMode,'invite-code-title-dark':isDarkMode}">아직 멤버가 다 모이지 않았어요. <span>({{member.length}}/7)</span></p>
      </div>
    <div class="search-back" @click.self="listFlag = false" v-if="listFlag">
      <RoomSearchList :invite="roomData.id" />
    </div>
    </div>

</template>

<script>
import { useChatStore } from '@/store/chatStore';
import RoomSearchList from './RoomSearchList.vue';
import { toast} from 'vue3-toastify'
import "vue3-toastify/dist/index.css"

export default {
    name: 'RoomFeedReady',
    data(){
      return{
        listFlag : false,
      }
    },

    props:{
      roomData : Object,
      participant:Object,
      mission:Object,
      ability:Object,
      member: Object,
      roomTime:Number,
      isDarkMode:Boolean
    },
    components:{
      RoomSearchList
    },
    methods:{
      reload: function(){
        window.location.reload()
      },
      copyBtn(){
        const inviteCode = this.roomData.id;
        if (inviteCode){
          const textArea = document.createElement('textarea');
          textArea.value = inviteCode;
          document.body.appendChild(textArea);
          textArea.select()
          try{
            const successful = document.execCommand('copy');
            toast('초대코드가 클립보드에 복사되었습니다.',{
                    theme : "auto",
                    "type": "success",
                    "pauseOnHover": false,
                    "position": "top-center",
                    "transition": "slide",
                    "autoClose": 1000,
                })
          }catch(err){
            console.err('클립보드 복사에 실패했습니다:', err)
          }
          document.body.removeChild(textArea);
        }
      }
    },
    mounted(){
      this.emitter.emit('pageChange',1)
    }
}
</script>

<style scoped src="./css/RoomFeedReady.css">

</style>