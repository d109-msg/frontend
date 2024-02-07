<template>
  <div class="messagelist-container">
    <div class="messagelist-title-box">
        <p class="messagelist-title">Message List</p>
        <img class="messagelist-title-icon" src="./Img/icon_plus.png" alt="" @click="showList">
        <div class="list-back" v-if="listFlag" @click.self="listFlag=false">
          <div class="list-modal">
            <div class="search-container">
                    <input type="text" class="search-bar" placeholder="search" maxlength="30" >
                    <div class="search-icon"></div>
            </div>
            <div class="search-result">
              <div class="result" v-for="(item,key) in userList" :key="key" >
                  <img :src="item.imageUrl" class="profile-img" :id="key">
                  <div class="info">
                    <span>
                      {{ item.nickname}}
                    </span>
                    <p>
                      plz add bio
                    </p>
                  </div>
                  <div class="button-box" @click="chatRoom(item.userId)">
                    <img src="./Img/icon_plus.png" class="make-message">
                  </div>
              </div>
            </div>
          </div>
        </div>
    </div>
    <div class="messagelist-content">
        <div v-for="(item,key) in messageList" :key="key" class="chat-room" :id="key"
        @click="clickChat(key)"
        >
          <img :src="item.imageUrl" alt="" class="chat-img">
          <div class="chat-info" >
            <span>{{ item.title }}</span>
            <p>DM 확인 부탁 드려요 ~~~!</p>
          </div>
        </div>
            
        </div>
  </div>
</template>

<script>
import { useAuthStore } from '@/store/authStore'
import { nextTick } from 'vue'
import { useChatStore } from '@/store/chatStore'
// const server =  'https://i10d109.p.ssafy.io/api'
// const server2 = 'http://localhost:8080/api'
const server = 'http://localhost:8080/api'
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
      }
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
        if(this.baseUrl != null){
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
                const last = document.getElementById(`${this.userList.length-1}`)
                this.io.observe(last)
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
          this.messageList.unshift(value.data)
          await chat.subscribe(value.data.id)
        }catch(err){
          console.log(err)
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

    mounted(){
      this.io = new IntersectionObserver(this.ioCall,{threshold:0.7})
      this.readMessageList()
    }
}
</script>

<style  scoped src="./css/MessageList.css">

</style>