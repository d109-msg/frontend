<template>
  <div class="feed-container">
    <RoomFeedReady v-if="startFlag === false" 
    :room-data="roomData"
    :participant="participant"
    :mission="mission"
    :ability="ability"
    :member="member"
    
    ></RoomFeedReady>
    <RoomFeedStart v-else 
    :room-data="roomData"
    :participant="participant"
    :mission="mission"
    :ability="ability"
    :member="member"
    ></RoomFeedStart>

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
      member: Object
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