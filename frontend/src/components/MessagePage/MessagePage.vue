<template>
  <div class="message-container">
    <MessageList></MessageList>
    <MessageDetail></MessageDetail>
    <div>
      <MiniProfile></MiniProfile>
    </div>
  </div>


</template>

<script>
import MiniProfile from '../MiniProfile/MiniProfile.vue';
import MessageDetail from './MessageDetail.vue';
import MessageList from './MessageList.vue';

export default {
    name: 'MessagePage',
    data(){
      return{
        userName: "",
        message: "",
        recvList:[]

      }
    },
    components:{
      MiniProfile,
      MessageList,
      MessageDetail
    }

   
}
</script>

<style src="./MessagePage.css">

</style>