<template>
  <div>

    <div class="mypage-banner">
    </div>
    
    <div class="mypage-box">
      <div class="profile-box">
        <div class="profile-content">
          <img class="profile-img" :src="userInfo.imageUrl" alt="">
          <div class="profile-section">
          <div class="">닉네임 <span> {{ userInfo.nickname  }} </span></div>
          <div class="" >소개글 <span> {{ userInfo.bio }} </span></div>
          <div class="profile-bot-section">
            <div>게시물 <span> {{ userInfo.articleCount }} </span></div>
              <div>팔로우 <span> {{ userInfo.followerCount }} </span></div>
              <div>팔로잉 <span> {{ userInfo.followingCount }}</span></div>
          </div>
        <div class="button-box">
          <div class="follow-button" v-if="userInfo.isFollow == 0" @click="followUser">팔로우하기</div>
          <div class="message-button" @click="goMessage">메시지 보내기</div>
        </div>
        </div>
      </div>
    </div>
    <div class="feed-game-box">
      <MyFeedVue :id="userInfo.id"></MyFeedVue>
      <MyGameVue :game="gameInfo"></MyGameVue>
    </div>
  </div>
  <div v-if="isEdit==true"  >
    <!-- <EditProfile @close-edit="isEdit=false" :userInfo="userInfo" /> -->
  </div>
  
  
</div>
</template>

<script>  
import { useAuthStore } from '@/store/authStore'
import MyFeedVue from './UserFeed.vue'
import MyGameVue from './UserGame.vue'
import router from '@/router'
import { useFeedStore } from '@/store/feedStore'
import { watch } from 'vue'
import { useChatStore } from '@/store/chatStore'

export default {
    name : 'UserPage',
    data(){
      return{
        userInfo : {},
        isEdit : false,
        userId : "",
        MyFeed : {},
        gameInfo : {},
      }
    },
    components : {
      MyFeedVue,
      MyGameVue,
    },
    methods:{
    getUser : async function(){
      const auth = useAuthStore()
      try{
        let value = await auth.getOtherUser(this.$route.params.id)
        this.userInfo = value.data
        if(value.data === ""){
          alert('존재하지 않는 회원 정보입니다.')
          router.push('/')
        }
        // console.log(this.userInfo)
        // this.userNickname = this.userInfo.nickname
        // this.userEmail = this.userInfo.emailId
        // this.userId = this.userInfo.id

      } catch (error) {
        
        console.log(error)  
      }
    },
    getGame : async function(){
      const auth = useAuthStore()
      let value = await auth.getGame(this.$route.params.id)
      this.gameInfo = value.data
    },
    followUser: async function(){
      const auth = useAuthStore()
      try{
        await auth.useRefresh()

        await auth.follow(this.userInfo.id)
        this.userInfo.isFollow = 1

      }catch(err){
        
      }
    },
    goMessage: async function(){
      const auth= useAuthStore()
      try{
        await auth.useRefresh()
        const chat = useChatStore()
        let value = await chat.makeChat(this.$route.params.id)
        if(value.status == 201){
          await chat.sub([value.data.id])
        }
        router.push({name : 'message'})
      }catch(err){
        console.log(err)
      }
    } ,
    startPage : async function(){
      const auth = useAuthStore()
      try{
          await auth.useRefresh()
          await this.getUser()
          await this.getGame()
      } catch(err){
        console.log('팔로우 정보를 가져오지 못했습니다.')
      }
    }
  },
  watch:{
    $route(to,from){
      if(to.path!==from.path){
        this.startPage()
      }
    }
  },
  mounted(){
    this.startPage()
    this.$route.params.id
    const chat = useChatStore()
  }
}
</script>

<style scoped src="./css/UserPage.css">

</style>