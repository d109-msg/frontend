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
          <div class="follow-button">팔로우하기</div>
          <div class="message-button">메시지 보내기</div>
        </div>
        </div>
      </div>
    </div>
    <div class="feed-game-box">
      <MyFeedVue :id="userInfo.id"></MyFeedVue>
      <MyGameVue></MyGameVue>
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

export default {
    name : 'UserPage',
    data(){
      return{
        userInfo : {},
        isEdit : false,
        userId : "",
        MyFeed : {},
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
        console.log(this.$route.params.id)
        let value = await auth.getOtherUser(this.$route.params.id)
        console.log(value)
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
    startPage : async function(){
      const auth = useAuthStore()
      try{
          await auth.useRefresh()
          await this.getUser()
      } catch(err){console.log(err)}
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
  }
}
</script>

<style scoped src="./css/UserPage.css">

</style>