<template>
  <div class="detail-container">
    <RoomFeed/>
    <div class="chat-container">
      <div class="chat-title-box">
        <div class="is-chat" @click="isChat"></div>
        <div class="is-guide" @click="isGuide"></div>
        <div class="is-vote" @click="isVote"></div>
    </div>
      <RoomChat v-if="isOpen==1" ></RoomChat>
      <RoomGuide v-else-if="isOpen==2" ></RoomGuide>
      <RoomVote v-else-if="isOpen==3" ></RoomVote>
    </div>

  </div>
</template>

<script>
import RoomFeed from './RoomFeed.vue';
import RoomChat from './RoomChat.vue';
import RoomGuide from './RoomGuide.vue';
import RoomVote from './RoomVote.vue';

export default {
  
    name: 'RoomDetailPage',
    data(){
      return{
        isOpen : '1',
        
      }
    },
    components:{
        RoomFeed,
        RoomChat,
        RoomGuide,
        RoomVote
    },
    mounted(){
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

      }
    }
}
</script>

<style scoped src="./css/RoomDetailPage.css">

</style>