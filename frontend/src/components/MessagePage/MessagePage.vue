<template>
  <div class="message-container">
    <MessageList @chat-info="getInfo" :is-dark-mode="isDarkMode" @close-list="stepUp" v-if="(step == 0&& size=='xs') || size=='md' || size=='lg' "></MessageList>
    <MessageDetail @user-info="userInfo" :chatInfo="detail" :chatId="detail.id" :is-dark-mode="isDarkMode" v-if="size=='md' || size=='lg' || (step == 1 && size=='xs')" :size="size"
    @step-down="stepDown"
    ></MessageDetail>
    <div>
      <MiniProfile :userInfo="user" :is-dark-mode="isDarkMode" v-if="size=='lg'"/>
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
        step : 0,
        size : "",
        width : 0,
        height : 0,
      }
    },
    props:{
      isDarkMode : Boolean
    },
    methods: {
      stepDown : function(data){
        this.step = data
      },
      stepUp: function(data){
        this.step = data
      },
      userInfo : function(data){
        this.user = data
      },
      getInfo(chat){
        this.detail = chat
      },
      handleResize(event) {
            this.width = window.innerWidth;
            this.height = window.innerHeight;
        },
      reactiveSize : function(){
          const viewportWidth = window.innerWidth
          if (viewportWidth<860) {
                  this.size =  "xs"
              }
              else if (viewportWidth >= 860 && viewportWidth < 1440
              ) {
                  this.size = "md"}
              else {this.size = "lg"}
          window.addEventListener('resize', this.handleResize);
      },
    },
    watch:{
      width(nv,ov){
            if(nv<860){
                this.size = "xs"
            console.log('사이즈',this.size)

            } else if(nv >= 860 && nv < 1440){
                this.size = "md"
            console.log('사이즈',this.size)

            }else{
                this.size = "lg"
            console.log('사이즈',this.size)

            }
        }
    },
    components:{
      MiniProfile,
      MessageList,
      MessageDetail
    },
    mounted(){
      this.emitter.emit('pageChange',2)
      this.reactiveSize()
    }

   
}
</script>

<style scoped src="./css/MessagePage.css">

</style>