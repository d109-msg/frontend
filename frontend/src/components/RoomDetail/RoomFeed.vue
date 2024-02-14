<template>
  <div :class="{'feed-container':!isDarkMode,'feed-container-dark':isDarkMode}">
    <img src="./Img/icon_chat.png" alt="" :class="{'is-chat':!isDarkMode,'is-chat-dark':isDarkMode}" @click="$emit('openChat',1)" v-if="size=='xs' && isDarkMode">
      <img src="./Img/icon_chat_active.png" alt="" :class="{'is-chat':!isDarkMode,'is-chat-dark':isDarkMode}" @click="$emit('openChat',1)" v-if="size=='xs' && !isDarkMode">
      <RoomFeedReady v-if="member.length!=7" 
      :room-data="roomData"
      :participant="participant"
      :mission="mission"
      :ability="ability"
      :member="member"
      :room-time="roomTime"
      :is-dark-mode="isDarkMode">
    </RoomFeedReady>
    <RoomFeedStart v-if="member.length==7" 
    :room-data="roomData"
    :participant="participant"
    :mission="mission"
    :ability="ability"
    :member="member"
    :room-time="roomTime"
    :is-dark-mode="isDarkMode"  
    >
  </RoomFeedStart>

    
  </div>
</template>

<script>
import RoomFeedReady from './RoomFeedReady.vue';
import RoomFeedStart from './RoomFeedStart.vue';
export default {
    name: 'RoomFeed',
    data(){
      return{
        startFlag : false
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
      size : String,
    },
    components:{
      RoomFeedReady,
      RoomFeedStart
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
            alert('초대코드가 클립보드에 복사되었습니다.')
          }catch(err){
            console.err('클립보드 복사에 실패했습니다:', err)
          }
          document.body.removeChild(textArea);
        }
      }
    },
    mounted(){
      this.emitter.emit('pageChange',1)
    },
    watch:{
      member(){
        if (this.member.length == 7){
          this.startFlag = true
        }
      }
    }
}
</script>

<style scoped src="./css/RoomFeed.css">

</style>