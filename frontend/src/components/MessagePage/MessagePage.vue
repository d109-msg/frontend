<template>
  <div class="message-container">
    <MessageList @chat-info="getInfo" :is-dark-mode="isDarkMode" ></MessageList>
    <MessageDetail @user-info="userInfo" :chatInfo="detail" :chatId="detail.id" :is-dark-mode="isDarkMode" ></MessageDetail>
    <div>
      <MiniProfile :userInfo="user" :is-dark-mode="isDarkMode"/>
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
    props:{
      isDarkMode : Boolean
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

<style scoped src="./css/MessagePage.css">

</style>