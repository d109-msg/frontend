<template>
  <div style="width: 100%; height: 100%; position: relative;"> 
    <div class="mypage-banner-box">
      <div class="mypage-banner" :class="{'mypage-banner-light': !isDarkMode , 'mypage-banner-dark':isDarkMode}">
      </div>
    </div>
    
    <div class="mypage-box">
      <div :class="{'profile-box-light':!isDarkMode, 'profile-box-dark':isDarkMode}">
        <div class="profile-content">
          <img class="profile-img" :src="userInfo.imageUrl" alt="">
          <div :class="{'profile-section':!isDarkMode, 'profile-section-dark':isDarkMode}">
          <div class="">닉네임 <span> {{ userInfo.nickname  }} </span></div>
          <div class="" >소개글 <span> {{ userInfo.bio }} </span></div>
          <div :class="{'profile-bot-section':!isDarkMode, 'profile-bot-section-dark':isDarkMode}">
            <div>게시물 <span> {{ userInfo.articleCount }} </span></div>
              <div>팔로우 <span> {{ userInfo.followerCount }} </span></div>
              <div>팔로잉 <span> {{ userInfo.followingCount }}</span></div>
              
          </div>
          <div class="button-box">
          <div class="follow-button" v-if="userInfo.isFollow == 0" @click="followUser">팔로우하기</div>
          <div class="message-button" @click="goMessage">메시지 보내기</div>
        </div>
        <div class="feed-game-box"  style="display: flex; flex-direction: column;" >

  
        </div>
      </div>

    </div>
    </div>


    <div class="feed-game-box"  v-if="size=='xs'" style="display: flex; flex-direction: column;" >
      <!-- <MyFeedVue :id="userInfo.id"></MyFeedVue>
      <MyGameVue :game="gameInfo"></MyGameVue> -->
      <div style="display: flex; justify-content: flex-end; margin-bottom: 5px;">
        <label class="toggle_switch">
          <input type="checkbox" @click="changePage">
          <span class="slider"></span>
        </label>
      </div>
      <div>
        <UserFeed v-show="pageNum==1" :is-dark-mode="isDarkMode" :id="userInfo.id"></UserFeed>
      </div>
      <div>
        <UserGame v-show="pageNum=='2'"  :is-dark-mode="isDarkMode" :game="gameInfo"></UserGame>
      </div>
    </div>

    <div class="feed-game-box" v-if="size=='md'" style="display: flex; flex-direction: column;">
      <div style="display: flex; justify-content: flex-end; margin-bottom: 5px;">
        <label class="toggle_switch">
          <input type="checkbox" @click="changePage">
          <span class="slider"></span>
        </label>
      </div>
      <UserFeed v-show="pageNum=='1'" :is-dark-mode="isDarkMode" :id="userInfo.id"></UserFeed>
      <UserGame v-show="pageNum=='2'"  :is-dark-mode="isDarkMode" :isMount="1" :game="gameInfo" ></UserGame>
    </div>

    <div class="feed-game-box" v-if="size=='lg'">
      <UserFeed :is-dark-mode="isDarkMode" :id="userInfo.id"></UserFeed>
      <UserGame  :is-dark-mode="isDarkMode" :isMount="1" :game="gameInfo" ></UserGame>
    </div>

  </div>
  <div v-if="isEdit==true"  >
  </div>
  
  
</div>
</template>

<script>  
import { useAuthStore } from '@/store/authStore'
import UserFeed from './UserFeed.vue'
import UserGame from './UserGame.vue'
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
        width: 0,
        height: 0,
        size : 'lg',
        pageNum : '1',

      }
    },
    components : {
      UserFeed,
      UserGame,
    },
    props:{
        isDarkMode : Boolean
    },
    methods:{
      handleResize(event) {
            this.width = window.innerWidth;
            this.height = window.innerHeight;
        },
      reactiveSize : function(){
          const viewportWidth = window.innerWidth
          if (viewportWidth<700) {
                  this.size =  "xs"
              }
              else if (viewportWidth >= 700 && viewportWidth < 860
              ) {
                  this.size = "md"}
              else {this.size = "lg"}
          window.addEventListener('resize', this.handleResize);
      },
    getUser : async function(){
      const auth = useAuthStore()
      try{
        let value = await auth.getOtherUser(this.$route.params.id)
        this.userInfo = value.data
        if(value.data === ""){
          alert('존재하지 않는 회원 정보입니다.')
          router.push('/')
        }
      } catch (error) {
        
        console.log(error)  
      }
    },
    changePage(){
        if (this.pageNum == 1){
          this.pageNum = 2

        }else{
          this.pageNum = 1

        }
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
          await this.getG()
      } catch(err){
        console.log('팔로우 정보를 가져오지 못했습니다.')
      }
    }
  },
  watch:{
    width(){
            if (this.width<700) {
                this.size =  "xs"
            }
            else if (this.width >= 700 && this.width < 860) {
                this.size = "md"}
            else {this.size = "lg"}
        },
    beforeDestroy() {
    window.removeEventListener('resize', this.handleResize);
    },
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
    this.reactiveSize()
  }
}
</script>

<style scoped src="./css/UserPage.css">

</style>