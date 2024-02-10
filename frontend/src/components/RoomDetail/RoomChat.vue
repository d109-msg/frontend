<template>
  <div>
    <div class="chat-content">
      <img src="./Img/arrow_up.png" class="toggle-job" @click="boxFlag=!boxFlag" v-if="boxFlag == true">
      <img src="./Img/arrow_down.png" class="toggle-job" @click="boxFlag=!boxFlag" v-else>
      <div class="job-box" v-if="boxFlag">
        <div>내 이름 : {{ participant.nickname }}</div>
        <div v-if="participant.jobId == '미치광이'">내 직업 : 경찰</div>
        <div v-else>내 직업 : {{participant.jobId }}</div>
      </div>
      <div class="chat-box">
        <div v-for="(message,idx) in chat[roomId]" :key="idx">
          <div v-if="message.userId != participant.userId">
            <div v-for="(user,key) in member" :key="key" >
              <div v-if="user.userId == message.userId">
                <div style="display: flex;">
                  <img :src="user.imageUrl" alt="" style="width: 35px; height: 35px;">
                  <div>
                    <p style="font-size: 13px; padding-left: 10px; font-weight: bold;">{{ user.nickname }}</p>
                    <div class="chat-other-box">
                      <div class="chat-other-text">
                        {{ message.content }}
                      </div>
                    </div>
                  </div>
                </div>
                  
              </div>
            </div>
          </div>

          <div class="chat-my-box">
            <div class="chat-my-text">
              {{ message.content }}
            </div>
          </div>
        </div>
    </div>
    </div>
    <div class="chat-input-box">
      <textarea type="text" class="chat-input" id="content" cols="40" rows="3" maxlength="100" v-model="message" @keyup.enter.prevent="send"
      ></textarea>
      <div class="input-num" >{{ inputNum }}/100</div>
      <div class="photo-icon"></div>
      <div class="submit-icon" @click.prevent="send">보내기</div>
    </div>
  </div>
</template>
 
<script>

import { useAuthStore } from '@/store/authStore'
import { useChatStore } from '@/store/chatStore'


export default {
    name: 'RoomFeed',
    data(){
      return{
        stompClient : Object,
        inputNum:0,
        roomId : this.roomData.id,
        receive :"",
        message : "",
        chatStore : useChatStore(),
        chat : useChatStore().getMessage,
        boxFlag : true,
      }
    },
    props:{
      isOpen: Number,
      roomData: Object,
      participant:Object,
      mission:Object,
      ability:Object,
      member: Object,
      roomTime:Number,
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
        this.$nextTick(()=>{
          const messageContent =  document.querySelector('.chat-content')
          messageContent.scrollTop = messageContent.scrollHeight
        })
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
      send : function(){
        let data = {
          'roomId' : this.roomId,
          'flagMafia' : 0,
          'content' : this.message,
          'base64Images' : [],
        }
        this.chatStore.getStomp.send("/pub/message",JSON.stringify(data))
        this.message = ""
        setTimeout(()=>{
          this.scrollToBottom()
        },100)
      },
        startPage: async function(){
          const auth = useAuthStore()
            await auth.useRefresh()
            try{
              await this.getUser()
              this.$emit('userInfo',this.userInfo)
              // this.connect()
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