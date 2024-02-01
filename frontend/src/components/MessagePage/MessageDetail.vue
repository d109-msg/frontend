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
                <input type="text" v-model="message">
                <button @click.prevent="send">send</button>
                <div style="width: 500px; height: 500px; overflow: scroll;"></div>











            </div>
            <textarea class="message-textarea" name="" id="" cols="30" rows="10">
            </textarea>
            <button class="message-submit-btn"></button>
        </div>

    </div>
</template>

<script>
import SockJs from 'sockjs-client'
import Stomp from 'webstomp-client'

export default {
    name: 'MessageDetail',
    data(){
    return{
      stompClient : Object,
      sub : Object,
      nickname : "",
      roomId : 1,
      message : ""
    }
  },
  methods:{
    connect : function(){
      let socket = new SockJs("http://localhost:8080/ws-stomp")
      this.stompClient = Stomp.over(socket)
      const headers = {"Authorization": "토큰"}
      this.stompClient.connect(headers,()=>{
        this.sub = this.stompClient.subscribe('/sub/'+this.roomId, function(e){
          console.log(JSON.parse(e.body))
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
    showMessage : function(data){
      console.log(data.userId)
    }
  },
  mounted(){
    this.connect()
  }
}
</script>

<style src="./css/MessageDetail.css">

</style>