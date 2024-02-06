<template>
  <div class="message-container">
    <MessageList @chat-info="getInfo"></MessageList>
    <MessageDetail @user-info="userInfo" :chatInfo="detail"></MessageDetail>
    <div>
      <MiniProfile :userInfo="user"/>
    </div>
  </div>


</template>

<script>
import { useAuthStore } from '@/store/authStore';
import MiniProfile from '../MiniProfile/MiniProfile.vue';
import MessageDetail from './MessageDetail.vue';
import MessageList from './MessageList.vue';
import router from '@/router';


export default {
    name: 'MessagePage',
    data(){
      return{
        userName: "",
        message: "",
        recvList:[],
        user: {},
        detail: {},
      }
    },
    methods: {
      userInfo : function(data){
        this.user = data
      },
      getInfo(chat){
        this.detail = chat
      }
    },
    components:{
      MiniProfile,
      MessageList,
      MessageDetail
    },
    mounted(){
      this.emitter.emit('pageChange',2)
    }

   
}
</script>

<style src="./css/MessagePage.css">

</style>