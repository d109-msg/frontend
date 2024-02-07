<template>
    <div class="messageDetail-container">
        <div class="message-user-profile">
            <img class="message-user-profile-img" :src="chatInfo.imageUrl" v-if="Object.keys(chatInfo).length != 0">
            <div class="message-user-name"  v-if="Object.keys(chatInfo).length != 0">{{chatInfo.title}}</div>
        </div>
        <div class="message-content-box">
            <div class="message-content">
              <div v-for="(item,key) in chatStore.getMessage[chatInfo.id]" :key="key" :id="'message'+key">
                <div class="chat-my-box" v-if="userInfo.id === item.userId">
                  <div class="chat-my-text">
                    {{ item.content }}
                  </div>
                </div>
                <div class="chat-other-box" v-else>
                  <div class="chat-other-text">
                    {{ item.content }}

                  </div>
                </div>
              </div>
            </div>
            <textarea type="text" class="message-textarea" id="" cols="30" rows="10" v-model="message" @keyup.enter.prevent="send" 
            v-if="Object.keys(chatInfo).length != 0">
            </textarea>
            <div class="message-textarea" v-else></div>
            <button class="message-submit-btn" @click.prevent="send" v-if="Object.keys(chatInfo).length != 0"></button>
            <button class="message-submit-btn" v-else></button>
        </div>

    </div>
</template>

<script>
import { mapState } from 'pinia';
import { useChatStore } from '@/store/chatStore';
import SockJs from 'sockjs-client'
import Stomp from 'webstomp-client'
import { useAuthStore } from '@/store/authStore'
import router from '@/router'
import { nextTick } from 'vue'

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
      chatStore : useChatStore()
    }
  },
  props:{
    chatInfo : Object,
  },
  watch:{
    chatInfo(nv,ov){
      console.log(this.chatInfo.id)
      // this.connect(this.chatInfo.id)
    }
  },

  methods:{

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
    // scrollToBottom(){
    //   const messageContent = document.querySelector('.message-content');
    //   messageContent.scrollTop = messageContent.scrollHeight;
    // },
    // showMessage : function(data){
    //   this.messageList.push(data)
    //   this.$nextTick(()=>{
    //     const elem = document.getElementById(`message${this.messageList.length-1}`)
    //     if(data.userId == this.userInfo.id){
    //       elem.classList.add('chat-my-box')
    //       elem.childNodes[0].classList.add('chat-my-text')
    //     }
    //     else{
    //       elem.classList.add('chat-other-box')
    //       elem.childNodes[0].classList.add('chat-other-text')
    //     }
    //   })
      // if(data.userId == this.userInfo.id){
      //   chatBox.classList.add('chat-my-box')
      //   chatText.classList.add('chat-my-text')
        
      // }else{
      //   chatBox.classList.add('chat-other-box')
      //   chatText.classList.add('chat-other-text')
      // }
      // document.querySelector('.message-content').appendChild(chatBox)
      // const chatTime = document.createElement('div')
      // chatTime.style.position = 'absolute';
      // chatTime.style.left = '-55px';
      // chatTime.style.top = '15px';
      // chatTime.style.color = '#B1AFAF';
      // chatTime.style.fontSize = '12px'
      // chatTime.innerHTML = '오후 2:43';
      // chatBox.appendChild(chatText)
      // chatText.appendChild(chatTime)
    //   this.scrollToBottom();
    // },
    // callBack : function(){
    //   // console.log(data)
    // },
    // connect :  function(roomId){
    //   this.messageList = []
                                                                    
    //   this.stompClient = Stomp.over(socket)
    //   const auth = useAuthStore()
    //   let value = auth.getAccess

    //   const headers = {"Authorization": value}
    //   this.stompClient.connect(headers,()=>{
    //     this.sub = this.stompClient.subscribe('/sub/'+roomId, (e)=>{
    //       // console.log(JSON.parse(e.body))
    //       this.showMessage(JSON.parse(e.body))
    //     })
    //   },
    //   function(e){
    //     alert('에러발생!')
    //   })
    // },
    send : function(){
      let now = new Date()
      let time = now.getMilliseconds;
      let data = {
        'roomId' : this.chatInfo.id,
        'text' : this.message,
      }
      this.chatStore.getStomp[this.chatInfo.id].send("/pub/message/text",JSON.stringify(data))
      this.message = ""
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
    }
  },
  mounted(){
    this.startPage()
  },

}
</script>

<style src="./css/MessageDetail.css">

</style>