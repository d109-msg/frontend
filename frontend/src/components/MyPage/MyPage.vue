<template>
  <div>

    <div class="mypage-banner">
    </div>
    
    <div class="mypage-box">
      <div class="profile-box">
        <div class="profile-content">
          <div class="edit-profile" @click="openEdit" ></div>
          <img class="profile-img" :src="userInfo.imageUrl" alt="">
          <div class="profile-section">
          <div class="">닉네임 <span> {{ userInfo.nickname  }} </span></div>
          <div class="" >소개글 <span> {{  }} </span></div>
          <div class="profile-bot-section">
            <div>게시물 <span> {{ 8 }} </span></div>
            <div>팔로우 <span> {{ 10 }} </span></div>
            <div>팔로잉 <span> {{ 11 }}</span></div>
          </div>
        </div>
      </div>
    </div>
    <div class="feed-game-box">
      <MyFeedVue></MyFeedVue>
      <MyGameVue></MyGameVue>
    </div>
  </div>
  <div v-if="isEdit==true"  >
    <EditProfile @close-edit="isEdit=false" :userInfo="userInfo" />
  </div>
  
  
</div>
</template>

<script>
import { useAuthStore } from '@/store/authStore'
import MyFeedVue from './MyFeed.vue'
import MyGameVue from './MyGame.vue'
import router from '@/router'
import { useFeedStore } from '@/store/feedStore'
import EditProfile from './EditProfile.vue'

export default {
    name : 'MyPage',
    data(){
      return{
        userInfo : {},
        isEdit : false,


      }
    },
    components : {
      MyFeedVue,
      MyGameVue,
      EditProfile,
    },
    methods:{
    getUser : async function(){
      const auth = useAuthStore()
      try{
        let value = await auth.getUser()
        this.userInfo = value.data
        this.userNickname = this.userInfo.nickname
        this.userEmail = this.userInfo.emailId
        console.log(this.userInfo)
      } catch (error) {
        auth.useRefresh()
        try{
          value = await auth.getUser()
          this.userInfo = value.data
        }
        catch{
          alert('로그인 세션이 만료되었습니다.')
          router.push('/login')
        }
        console.log(error)}
    },
    getFeed : async function(){
      const feed = useFeedStore()
      try{
        let value = await feed.getUserProfile()
        console.log(value)
      } catch(error) {
        console.log(error)
      }
    },
    openEdit(){
      this.isEdit=true;
    }
    
  },
  mounted(){
    this.emitter.emit('pageChange',3)
    this.getUser()

    // this.getFeed()
  }
}
</script>

<style scoped src="./css/MyPage.css">

</style>