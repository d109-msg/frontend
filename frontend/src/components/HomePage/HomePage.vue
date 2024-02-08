<template>
  <div class="container">
    <NavBarVue :page="page"/>
    <router-view @page-change="change"></router-view>
  </div>
</template>

<script>
import NavBarVue from '@/components/NavBar/NavBar.vue'
import MainPage from '../MainPage/MainPage.vue';
import { useChatStore } from '@/store/chatStore';
import { useAuthStore } from '@/store/authStore';
import Stomp from 'webstomp-client'
import SockJS from "sockjs-client"

export default {
  name:"HomePage",
  data(){
    return{
      page : '',
      data : []
    }
  },
  computed:{
      isConnect(){
        const chat = useChatStore()
        return chat.getConnect
      }
    },
    watch:{
      isConnect(nv,ov){
        const chat = useChatStore()
          if(nv == true){
            try{
              chat.sub(this.data)
              chat.notifyConnect()
              console.log('완료')
            }catch(err){
              console.log('조짐')
            }
          }else{
            console.log('연결이 왜 해제되었나요...')
          }
      }
    },
  methods:{
    change : function(value){
      this.page = value
    },
    
    startPage : async function(){
      const auth = useAuthStore()
      const chat = useChatStore()
      if(auth.getAccess === ""){
        return
      }else{
        try{
          await auth.useRefresh()
          let value = await chat.userRoom()
          this.data = value.data
          await chat.makeConnect()
        }catch(err){
          console.log('에러내용',err)
        }
      }
    },
  },
  components:{
      NavBarVue,
      MainPage
  },
  mounted(){
    this.startPage()
  }
 
}
</script>


<style scoped src="./Homepage.css">
  
</style>
