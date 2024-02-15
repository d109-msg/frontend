<template>
          <div :class="{'list-modal':!isDarkMode,'list-modal-dark':isDarkMode}" style="cursor: auto;">
            <div class="search-container">
                    <input type="text" :class="{'search-bar':!isDarkMode,'search-bar-dark':isDarkMode}" placeholder="search" maxlength="30" v-model="searchResult">
                    <div class="search-icon"></div>
            </div>
            <div class="search-result">
              <div class="result" v-for="(item,key) in userList" :key="key" >
                  <img :src="item.imageUrl" class="profile-img" :id="key">
                  <div :class="{'info':!isDarkMode,'info-dark':isDarkMode}">
                    <span>
                      {{ item.nickname}}
                    </span>
                    <p>
                      {{ item.bio}}
                    </p>
                  </div>
                  <div class="button-box" @click="chatRoom(item.userId)">
                    <img src="./Img/icon_plus.png" class="make-message">
                  </div>
              </div>
            </div>
          </div>
  </template>
  
  <script>
  import { useAuthStore } from '@/store/authStore'
  import { nextTick } from 'vue'
  import { useChatStore } from '@/store/chatStore'
  import servers from '@/server'
  import { toast} from 'vue3-toastify'
  import "vue3-toastify/dist/index.css"

  // const server =  'https://i10d109.p.ssafy.io/api'
  // const server2 = 'http://localhost:8080/api'
  const server = servers
  const server2 = 'https://i10d109.p.ssafy.io/api'
  
  export default {
      name: 'MessageList',
      data(){
        return{
          listFlag : false,
          userList : [],
          baseUrl : `${server}/user/follow`,
          nextUrl : "?type=from",
          io : {},
          messageList : [],
          searchResult : "",
          last : {},
          chatStore : useChatStore()
        }
      },
      props:{
        isDarkMode : Boolean,
        invite : String,
      },
      methods:{
        showList : function(){
          this.listFlag = true
          this.getFollowing()
        },
        readMessageList : async function(){
          const chat = useChatStore()
          try{
            let value = await chat.chatList()
            this.messageList = value.data
          }catch(err){
            console.log('조짐')
          }
        },
        getFollowing : async function(){
          const auth = useAuthStore()
          await auth.useRefresh()
          if(this.nextUrl != null){
            try{
              let value = await auth.searchFollowing(this.baseUrl+this.nextUrl)
              if(value.data != ""){
                value.data.followUserList.forEach(item=>{
                  this.userList.push(item)
                  console.log(item)
                })
              }
              this.nextUrl = value.data.nextUrl
              this.$nextTick(()=>{
                if(this.nextUrl != null){
                  this.last = document.getElementById(`${this.userList.length-1}`)
                  this.io.observe(this.last)
                } 
              })
            }catch(err){
              console.log('조짐')
            }
          }
        },
        ioCall : function(items,io){
          items.forEach(async item=>{
            if(item.isIntersecting){
              this.io.unobserve(item.target)
              await this.getFollowing()
            }
          })
        },
        chatRoom : async function(idx){
          const chat = useChatStore()
          const auth = useAuthStore()
          try{
            console.log(idx)
            let value = await chat.makeChat(idx)
            let data = {
                'roomId' : value.data.id,
                'flagMafia' : 0,
                'content' : `${auth.getUserInfo.nickname}님이 게임에 초대하였습니다. 초대코드 : ${this.invite}`,
                'base64Images' : [],
            }
            if(value.status == 201){
              this.messageList.unshift(value.data)
              await chat.sub([value.data.id])
              chat.getStomp.send("/pub/message",JSON.stringify(data))

            }else if(value.status== 200){
                chat.getStomp.send("/pub/message",JSON.stringify(data))
            }
            toast('초대코드 전송 완료',{
                    theme : "auto",
                    "type": "success",
                    "pauseOnHover": false,
                    "position": "top-center",
                    "transition": "slide",
                    "autoClose": 1000,
                })
          }catch(err){
            toast('잘못된 요청입니다.',{
                    theme : "auto",
                    "type": "error",
                    "pauseOnHover": false,
                    "position": "top-center",
                    "transition": "slide",
                    "autoClose": 1000,
                })
          }
        },
        clickChat : async function(idx){
          const rooms = document.querySelectorAll('.chat-room')
          for(let i=0; i<rooms.length;i++){
            if(rooms[i].classList.contains('click-chat')){
              rooms[i].classList.remove('click-chat')
            }
          }
          const elem = document.getElementById(`${idx}`)
          elem.classList.add('click-chat')
          this.$emit('chatInfo',this.messageList[idx])
        }
  
      },
      watch:{
        async searchResult(nv,ov){
          this.userList = []
          const auth = useAuthStore()
          if(nv.length!=0){
            let value = await auth.searchUser("",nv,0)
            console.log(value)
            if(value.data !=''){
              value.data.searchResult.forEach(item=>{
                this.userList.push(item)
              })
            }
          } else{
            this.nextUrl = "?type=from"
            this.last = {}
            await this.getFollowing()
          }
        }
      },
  
      mounted(){
        this.io = new IntersectionObserver(this.ioCall,{threshold:0.7})
        this.readMessageList()
      }
  }
  </script>
  
  <style  scoped src="./css/RoomSearchList.css">
  
  </style>