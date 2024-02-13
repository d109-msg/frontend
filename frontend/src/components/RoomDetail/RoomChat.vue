<template>
  <div>
    <div class="chat-content">
      <img src="./Img/arrow_up.png" class="toggle-job" @click="boxFlag=!boxFlag" v-if="boxFlag == true">
      <img src="./Img/arrow_down.png" class="toggle-job" @click="boxFlag=!boxFlag" v-else>
      <div :class="{'job-box':!isDarkMode,'job-box-dark':isDarkMode}" v-if="boxFlag">
        <div :class="{'job-text':!isDarkMode,'job-text-dark':isDarkMode}">내 이름 : {{ participant.nickname }}</div>
        <div :class="{'job-text':!isDarkMode,'job-text-dark':isDarkMode}" v-if="participant.jobId == '미치광이'">내 직업 : 경찰</div>
        <div :class="{'job-text':!isDarkMode,'job-text-dark':isDarkMode}" v-else>내 직업 : {{participant.jobId }}</div>
      </div>
      <div class="chat-box">
        <div v-for="(message,idx) in chatStore.getMessage[roomData.id]" :key="idx" style="margin-top: 10px;" :id="'message'+idx">
          <div v-if="message.userId == 1">
                <div style="display: flex;">
                  <img src="./Img/mafia.png" alt="" style="width: 35px; height: 35px; background: #fff; border-radius: 5px;" >
                  <div>
                    <p 
                    :class="{'chat-nick-msg':!isDarkMode,'chat-nick-msg-dark':isDarkMode}"
                    >MSG</p>
                    <div class="chat-other-box">
                      <div class="chat-other-text">
                        {{ message.content }}
                      </div>
                    </div>
                  </div>
                </div>
              </div>
          <div v-if="message.userId != participant.userId">
            <div v-for="(user,key) in member" :key="key" >
             
              <div v-if="user.userId == message.userId">
                <div style="display: flex;"  v-if="message.flagMafia==0">
                  <img :src="user.imageUrl" alt="" style="width: 35px; height: 35px; background: #fff; border-radius: 5px;">
                  <div>
                    <p :class="{'chat-nick':!isDarkMode,'chat-nick-dark':isDarkMode}" >{{ user.nickname }}</p>
                    <div class="chat-other-box">
                      <div class="chat-other-text">
                        {{ message.content }}
                      </div>
                    </div>
                  </div>
                </div>
                <div style="display: flex;"  v-if="(participant.flagMafia==true || participant.jobId=='불침번') && message.flagMafia==1">
                  <img src="./Img/mafia.png" alt="" style="width: 35px; height: 35px;">
                  <div>
                    <p style="font-size: 13px; padding-left: 10px; font-weight: bold;" v-if="participant.jobId == '불침번'">마피아</p>
                    <p style="font-size: 13px; padding-left: 10px; font-weight: bold;" v-if="participant.flagMafia==true">{{ user.nickname }}</p>

                    <div class="chat-other-box">
                      <div class="chat-other-mafia">
                        {{ message.content }}
                      </div>
                    </div>
                  </div>
                  </div>
              </div>
            </div>
          </div>
          <div class="chat-my-box" style="margin-top: 10px;" v-else-if="message.userId == participant.userId">
            <div class="chat-my-text" v-if="message.flagMafia == 0">
              {{ message.content }}
            </div>
            <div class="chat-my-mafia" v-if="message.flagMafia == 1">
              {{ message.content }}
            </div>
          </div>
        </div>
    </div>
    </div>
    <div class="chat-input-box">
      <textarea type="text" :class="{'chat-input':!isDarkMode,'chat-input-dark':isDarkMode}" id="content" cols="40" rows="3" maxlength="100" v-model="message" @keyup.enter.prevent="send"
      v-if="mafiaFlag==false"
      ></textarea>
      <textarea type="text" class="chat-input-mafia" id="content" cols="40" rows="3" maxlength="100" v-model="message" @keyup.enter.prevent="sendMafia"
      v-if="participant.flagMafia && mafiaFlag==true"></textarea>
      <div class="input-num" >{{ inputNum }}/100</div>
      <div class="mafia-chat" v-if="participant.flagMafia "
      @click="mafiaChat"
      ></div>
      <div class="submit-icon" @click.prevent="send" v-if="mafiaFlag == false">보내기</div>
      <div class="submit-mafia" @click.prevent="sendMafia" v-if="participant.flagMafia  && mafiaFlag==true">보내기</div>
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
        boxFlag : true,
        mafiaFlag : false,
        io : {},
        endGame : 0,
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
      isDarkMode:Boolean,
    },
    computed:{
      endFlag : function(){
        const chat = useChatStore()
        return chat.getEnd
      }
    },
    watch:{
      endFlag(nv,ov){
        if(nv==1){
          this.endGame = 1
        }
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
        this.$nextTick(()=>{
          const messageContent =  document.querySelector('.chat-box')
          messageContent.scrollTop = messageContent.scrollHeight
        })
      },
      mafiaChat(){
        this.mafiaFlag = !(this.mafiaFlag)
      },
      send : function(){
        if(this.participant.flagDie == 1 && this.endGame == 0){
          alert('이미 당신은 사망하였습니다. 더이상 게임에 참여하실 수 없습니다.')
          this.message = ""
          return
        }
        let data = {
          'roomId' : JSON.parse(this.$route.params.data).id,
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
      loadChat : async function(){
        const chat = useChatStore()
        const id = JSON.parse(this.$route.params.data).id
        if(id in chat.getReload){
          if(chat.getReload[id] == false){
            return
          }
        }
        const nextRoom = chat.getNextRoom
        if(!(id in nextRoom)){
          nextRoom[id] = `?room-id=${id}`
        }
        let elem = document.querySelector('.chat-box')
        let value = await chat.readMessage(nextRoom[id])
        if(value.data != ""){
          nextRoom[id] = value.data.nextUrl
          if(nextRoom[id] != null){
            let itemCount = 0
            value.data.messageResponseDtos.forEach(item=>{
              if(id in chat.getMessage){
                chat.message[id].unshift(item)
              }else{
                chat.message[id] = [item]
              }
              itemCount += 1
            })
            let target = null
            await this.$nextTick(()=>{
              target = document.getElementById('message0')
            })
            let totalHeight = 0
            for(let i=0; i<itemCount; i++){
              totalHeight += document.getElementById(`message${i}`).offsetHeight
            }
            elem.scrollTop = totalHeight
            this.io.observe(target)
          }else{
            chat.getReload[id] = false
          }
        }
      },
      call : async function(items,io){
        items.forEach(async item=>{
          if(item.isIntersecting){
            io.unobserve(item.target)
            await this.loadChat()
          }
        })
      },
      sendMafia : function(){
        if(this.participant.flagDie == 1 && this.endGame == 0){
          alert('이미 당신은 사망하였습니다. 더이상 게임에 참여하실 수 없습니다.')
          this.message = ""
          return
        }
        let data = {
          'roomId' : JSON.parse(this.$route.params.data).id,
          'flagMafia' : 1,
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
              await this.loadChat()
              setTimeout(()=>{
              this.scrollToBottom()
              },100)
              // this.connect()
            } catch(err){
              console.log(err)
            }
        }
      },
      mounted(){
        this.io = new IntersectionObserver(this.call,{threshold : 1.0})
        this.startPage()
        setTimeout(()=>{
          const id = JSON.parse(this.$route.params.data).id
          if(!(id in useChatStore().getMessage)){
            useChatStore().sub([id])
          }
        },300)
      },

    
}
</script>

<style scoped src="./css/RoomChat.css">

</style>