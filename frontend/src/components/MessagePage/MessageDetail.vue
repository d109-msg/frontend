<template>
    <div class="messageDetail-container">
        <div class="message-user-profile">
            <div class="message-user-profile-img"></div>
            <div class="message-user-name">kim_jjang_gu</div>
        </div>
        <div class="message-content-box">
            <div class="message-content">

                <div class="nickname">
                    <span>닉네임</span>
                    <input type="text" v-model="nickname">
                </div>
                <!-- <input type="text" v-model="message"> -->
                <!-- <button @click.prevent="send">send</button> -->
                <!-- <div style="width: 500px; height: 500px; overflow: scroll;"></div> -->



            </div>
            <textarea type="text" class="message-textarea" name="" id="" cols="30" rows="10" v-model="message">
            </textarea>
            <button class="message-submit-btn" @click.prevent="send"></button>
        </div>

    </div>
</template>

<script>
import SockJs from 'sockjs-client'
import Stomp from 'webstomp-client'
import { useAuthStore } from '@/store/authStore'

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
        let myProfile = await auth.getUser()
        this.userInfo = myProfile.data
        this.nickname = this.userInfo.nickname
      }catch(err){
        console.log(err)
      }
    },
    showMessage : function(data){
      this.receive = data.text 
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
  
  },
  mounted(){
    this.connect()
  },
  watch:{
    receive(newValue, oldValue){
      const div = document.createElement('div')
      div.innerHTML = newValue
      document.querySelector('.message-content').appendChild(div)
    }
  }
}
</script>

<style src="./css/MessageDetail.css">

</style>