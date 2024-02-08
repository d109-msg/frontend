<template>
    <div class="messageDetail-container">
      <LoadingSpinner v-if="loading"/>
        <div class="preview-img-back" @click="turnOff" v-if="imgFlag">
          <img :src="preImg" alt="" style="width: 500px; height: 500px;">
        </div>
        <div class="message-user-profile">
            <img class="message-user-profile-img" :src="chatInfo.imageUrl" v-if="Object.keys(chatInfo).length != 0">
            <div class="message-user-name"  v-if="Object.keys(chatInfo).length != 0">{{chatInfo.title}}</div>
        </div>
        <div class="message-content-box">
            <div class="message-content">
              <div v-for="(item,key) in chatStore.getMessage[chatInfo.id]" :key="key" :id="'message'+key">
                <div class="chat-my-box" v-if="userInfo.id === item.userId">
                  <div class="chat-my-text">
                    <span v-if="item.content != null">{{ item.content }}</span>
                    <img v-else :src="item.messageImageDtos[0].url" @click="turnOn(item.messageImageDtos[0].url)"
                    class="preview-img"
                    >
                  </div>
                </div>
                <div class="chat-other-box" v-else>
                  <div class="chat-other-text">
                    <span v-if="item.content != null">{{ item.content }}</span>
                    <img v-else :src="item.messageImageDtos[0].url" @click="turnOn(item.messageImageDtos[0].url)"
                    class="preview-img"
                    >
                  </div>
                </div>
              </div>
            </div>
            <textarea type="text" class="message-textarea" id="" cols="30" rows="10" v-model="message" @keyup.enter.prevent="send" 
            v-if="Object.keys(chatInfo).length != 0" maxlength="200">
            </textarea>
            <textarea class="message-textarea" v-else></textarea>
            <label for="imageInput" class="btn-label">
              <div class="btn-upload"></div>
            </label>
            <input type="file" id="imageInput" @change="convertToBase64" v-if="Object.keys(chatInfo).length != 0">
            
            <button class="message-submit-btn" @click.prevent="send" v-if="Object.keys(chatInfo).length != 0"></button>
            <button class="message-submit-btn" v-else></button>
            
        </div>
        

    </div>
</template>

<script>
import { useChatStore } from '@/store/chatStore';
import SockJs from 'sockjs-client'
import Stomp from 'webstomp-client'
import { useAuthStore } from '@/store/authStore'
import router from '@/router'
import { nextTick } from 'vue';
import LoadingSpinner from '../LoadingSpinner/LoadingSpinner.vue';

export default {
    name: 'MessageDetail',
    data(){
    return{
      stompClient : Object,
      userInfo : {},
      sub : Object,
      nickname : "",
      roomId : 1,
      message : "",
      receive : "",
      messageInfo : {},
      messageList : [],
      chatStore : useChatStore(),
      base64 : "",
      loading : false,
      imgFlag : false,
      preImg : "",
    }
  },
  props:{
    chatInfo : Object,
  },
  components:{
    LoadingSpinner,
  },
  computed:{
    listLength(){
      const chat = useChatStore()

        return chat.countMessage[this.chatInfo.id]
      
    }
  },
  watch:{
    listLength(nv,ov){
      this.scrollToBottom()
    }
  },

  methods:{
    turnOff : function(){
      this.imgFlag = false
      this.preImg = ""
    },
    turnOn : function(img){
      this.imgFlag = true,
      this.preImg = img
    },
    getUser : async function(){
      const myAuth = useAuthStore()
      try{
        let myProfile = await myAuth.getUser()
        this.userInfo = myProfile.data
        this.nickname = this.userInfo.nickname
      }catch(err){
        console.log(err)
      }
    },
    scrollToBottom(){
      this.$nextTick(()=>{
        const messageContent =  document.querySelector('.message-content')
        messageContent.scrollTop = messageContent.scrollHeight
      })
    },
    send : function(){
      let data = {
        'roomId' : this.chatInfo.id,
        'flagMafia' : 0,
        'content' : this.message,
        'base64Images' : [],
      }
      this.chatStore.getStomp.send("/pub/message",JSON.stringify(data))
      this.message = ""
    },
    sendImg : function(info){
      let sst = ""
      let temp = ''
      for(let i=0; i<info.length;i++){
        if(sst == ','){
          temp += info[i]
        }
        if(info[i] == ','){
          sst = info[i]
        }
      }
      console.log('변환하거',temp)
      let data = {
        'roomId' : this.chatInfo.id,
        'flagMafia' : 0,
        'content' : "",
        'base64Images' : [temp],
      }
      this.chatStore.getStomp.send("/pub/message",JSON.stringify(data))

    },
    startPage: async function(){
      const auth = useAuthStore()
        await auth.useRefresh()
        try{
          await this.getUser()
          this.$emit('userInfo',this.userInfo)
        } catch(err){
           console.log(err)
        }
    },
    convertToBase64: function(event){
      this.loading = true
      const file = event.target.files[0]
      if(file){
        const reader = new FileReader()
        reader.onload = (e)=>{
          this.sendImg(e.target.result)
          this.loading = false
        }
        reader.readAsDataURL(file)
      }else{
        this.loading = false
      }
    }
  },
  mounted(){
    this.startPage()
    
  },

}
</script>

<style scoped src="./css/MessageDetail.css">

</style>