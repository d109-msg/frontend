<template>
  <div class="message-container">
    <!-- <MessageList></MessageList> -->
    <!-- <MessageDetail></MessageDetail> -->
    <!-- <div>
      <MiniProfile></MiniProfile>
    </div> -->
    <div class="temp">
      <div id="app">
        유저이름:
        <input type="text" v-model="userName" >
        내용:
        <input type="text" v-model="message" @keyup="sendMessage">
      </div>
      <div v-for="(item, idx) in recvList" :key="idx">
        <h3>유저이름: {{ item.userName }}</h3>
        <h3>내용: {{ item.content }}</h3>
      </div>
    </div>

  </div>


</template>

<script>
import MiniProfile from '../MiniProfile/MiniProfile.vue';
import MessageDetail from './MessageDetail.vue';
import MessageList from './MessageList.vue';
import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'

export default {
    name: 'MessagePage',
    data(){
      return{
        userName: "",
        message: "",
        recvList:[]

      }
    },
    created(){
      this.connect()
    },
    components: { 
      MiniProfile, MessageList, MessageDetail
    }
}
</script>

<style src="./MessagePage.css">

</style>