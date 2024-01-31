<template>
  <div>

    <div class="chat-content">

    </div>
    <div class="chat-input-box">
      <textarea class="chat-input" id="content" cols="40" rows="3" maxlength="100" v-model="chatInput"
      ></textarea>
      <div  class="input-num" >{{ inputNum }}/100</div>
      <div class="photo-icon"></div>
      <div class="submit-icon">보내기</div>
    </div>
  </div>
</template>
 
<script>


export default {
    name: 'RoomFeed',
    data(){
      return{
        chatInput:"",
        inputNum:0,
      }
    },
    props:{
      isOpen: Number,
    },
    watch:{
      chatInput(newValue,oldValue){
        this.inputNum = newValue.length;
        console.log(this.inputNum)
      }
    },
    methods:{

      },

    
}
</script>

<style scoped src="./css/RoomChat.css">

</style>