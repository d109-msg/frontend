<template>
    <div class="messageDetail-container">
        <div class="message-user-profile">
            <div class="message-user-profile-img"></div>
            <div class="message-user-name">kim_jjang_gu</div>
        </div>
        <div class="message-content-box">
            <div class="message-content">



            </div>
            <textarea type="text" class="message-textarea" name="" id="" cols="30" rows="10" v-model="message" @keyup.enter.prevent="send">
            </textarea>
            <button class="message-submit-btn" @click.prevent="send"></button>
        </div>

    </div>
</template>

<script>
import SockJs from 'sockjs-client'
import Stomp from 'webstomp-client'
import { useAuthStore } from '@/store/authStore'
import router from '@/router'

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
      receive : ""
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
    scrollToBottom(){
      const messageContent = document.querySelector('.message-content');
      messageContent.scrollTop = messageContent.scrollHeight;
    },
    showMessage : function(data){
      this.receive = data.text 
      const chatBox = document.createElement('div')
      chatBox.style.marginTop = '10px';
      chatBox.style.marginRight = '20px';
      chatBox.style.textAlign = 'right';
      const chatText = document.createElement('div')
      chatText.style.display = 'inline-block';
      chatText.style.position = 'relative';
      chatText.style.backgroundColor = '#486880';
      chatText.style.borderRadius = '20px 0px 20px 20px';
      chatText.style.color = '#fff';
      chatText.style.padding = '10px 15px';
      chatText.style.marginBottom = '10px;'
      chatText.style.maxWidth = '230px'
      chatText.innerHTML = this.receive
      document.querySelector('.message-content').appendChild(chatBox)

      const chatTime = document.createElement('div')
      chatTime.style.position = 'absolute';
      chatTime.style.left = '-55px';
      chatTime.style.top = '15px';
      chatTime.style.color = '#B1AFAF';
      chatTime.style.fontSize = '12px'
      chatTime.innerHTML = '오후 2:43';
      chatBox.appendChild(chatText)
      chatText.appendChild(chatTime)

      this.scrollToBottom();




    },
    callBack : function(){
      // console.log(data)
    },
    connect :  function(){
      let socket = new SockJs("http://localhost:8080/api/ws-stomp")
      this.stompClient = Stomp.over(socket)
      const auth = useAuthStore()
      let value = auth.getAccess

      const headers = {"Authorization": value}
      this.stompClient.connect(headers,()=>{
        this.sub = this.stompClient.subscribe('/sub/'+this.roomId, (e)=>{
          // console.log(JSON.parse(e.body))
          this.showMessage(JSON.parse(e.body))

        })
      },
      function(e){
        alert('에러발생!')
      })
    },
    send : function(){
      let now = new Date()
      let time = now.getMilliseconds;
      let data = {
        'roomId' : this.roomId,
        'text' : this.message,
      }
      this.stompClient.send("/pub/message/text",JSON.stringify(data))
      this.message = ""
    },
    startPage: async function(){
      const auth = useAuthStore()
      if(auth.getAccess != ""){
        await auth.useRefresh()
        try{
          await this.getUser()
          this.$emit('userInfo',this.userInfo)
          this.connect()
        } catch(err){
           console.log(err)
        }
      } else{
        router.push('/')
        alert('회원 전용 페이지입니다.')
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