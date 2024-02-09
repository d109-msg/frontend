<template>
  <div>
    <div class="chat-content">
      <div class="job-box">
        <div>내 이름 : {{ participant.nickname }}</div>
        <div v-if="participant.jobId == '미치광이'">내 직업 : 경찰</div>
        <div v-else>내 직업 : {{participant.jobId }}</div>
      </div>
    </div>
    <div class="chat-input-box">
      <textarea type="text" class="chat-input" id="content" cols="40" rows="3" maxlength="100" v-model="chatInput" @keyup.enter.prevent="send"
      ></textarea>
      <div  class="input-num" >{{ inputNum }}/100</div>
      <div class="photo-icon"></div>
      <div class="submit-icon" @click.prevent="send">보내기</div>
    </div>
  </div>
</template>
 
<script>
import SockJs from 'sockjs-client'
import Stomp from 'webstomp-client'
import { useAuthStore } from '@/store/authStore'


export default {
    name: 'RoomFeed',
    data(){
      return{
        stompClient : Object,
        inputNum:0,
        roomId : this.roomData.id,
        chatInput: "",
        receive :"",
      }
    },
    props:{
      isOpen: Number,
      roomData: Object,
      participant:Object,
      mission:Object,
      ability:Object,
      member: Object
    },
    watch:{
      chatInput(newValue,oldValue){
        this.inputNum = newValue.length;
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
          const messageContent = document.querySelector('.chat-content');
          messageContent.scrollTop = messageContent.scrollHeight;
        },
      // showMessage : function(data){
      //     this.receive = data.text 
      //     const chatBox = document.createElement('div')
      //     chatBox.style.marginTop = '10px';
      //     chatBox.style.marginRight = '20px';
      //     chatBox.style.textAlign = 'right';
      //     const chatText = document.createElement('div')
      //     chatText.style.display = 'inline-block';
      //     chatText.style.position = 'relative';
      //     chatText.style.backgroundColor = '#486880';
      //     chatText.style.borderRadius = '20px 0px 20px 20px';
      //     chatText.style.color = '#fff';
      //     chatText.style.padding = '10px 15px';
      //     chatText.style.marginBottom = '10px;'
      //     chatText.style.maxWidth = '230px'
      //     chatText.innerHTML = this.receive
      //     document.querySelector('.chat-content').appendChild(chatBox)

      //     const chatTime = document.createElement('div')
      //     chatTime.style.position = 'absolute';
      //     chatTime.style.left = '-55px';
      //     chatTime.style.top = '15px';
      //     chatTime.style.color = '#B1AFAF';
      //     chatTime.style.fontSize = '12px'
      //     chatTime.innerHTML = '오후 2:43';
      //     chatBox.appendChild(chatText)
      //     chatText.appendChild(chatTime)

      //     this.scrollToBottom();
      //   },
      //   callBack : function(){
      //   },
      //   connect :  function(){
      //     let socket = new SockJs("http://localhost:8080/api/ws-stomp")
      //     this.stompClient = Stomp.over(socket)
      //     const auth = useAuthStore()
      //     let value = auth.getAccess

      //     const headers = {"Authorization": value}
      //     this.stompClient.connect(headers,()=>{
      //       this.sub = this.stompClient.subscribe('/sub/'+this.roomId, (e)=>{
      //         this.showMessage(JSON.parse(e.body))

      //       })
      //     },
      //     function(e){
      //       alert('에러발생!')
      //     })
      //   },
      //   send : function(){
      //     let now = new Date()
      //     let time = now.getMilliseconds;
      //     let data = {
      //       'roomId' : this.roomData.id,
      //       'text' : this.chatInput,
      //     }
      //     this.stompClient.send("/pub/message/text",JSON.stringify(data))
      //     this.chatInput = ""
      //   },
        startPage: async function(){
          const auth = useAuthStore()
            await auth.useRefresh()
            try{
              await this.getUser()
              this.$emit('userInfo',this.userInfo)
              this.connect()
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

<style scoped src="./css/RoomChat.css">

</style>