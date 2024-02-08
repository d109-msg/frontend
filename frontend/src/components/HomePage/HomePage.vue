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
      page : ''
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
          await chat.makeConnect(value.data)
          // await chat.notifyConnect()
        }catch(err){
          console.log('앱뷰 조짐')
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
