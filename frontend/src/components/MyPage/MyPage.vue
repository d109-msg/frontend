<template>
  <div>
    <div class="mypage-banner-box">
      <div class="mypage-banner">
      </div>
    </div>

    <div class="mypage-box">
      <div class="profile-box">
        <div class="profile-content">
          <div class="edit-profile" @click="openEdit" ></div>
          <img class="profile-img" :src="userInfo.imageUrl" alt="">
          <div class="profile-section">
            <div class="">닉네임 <span> {{ userInfo.nickname  }} </span></div>
            <div class="" >소개글 <span> {{ userInfo.bio }} </span></div>
            <div class="profile-bot-section">
              <div>게시물 <span> {{ userInfo.articleCount }} </span></div>
              <div>팔로우 <span> {{ userInfo.followerCount }} </span></div>
              <div>팔로잉 <span> {{ userInfo.followingCount }}</span></div>
            </div>
        </div>
      </div>
    </div>
    <div class="feed-game-box" v-if="size=='xs'" style="display: flex; flex-direction: column;">
      <div style="display: flex; justify-content: flex-end; margin-bottom: 5px;">
        <label class="toggle_switch">
          <input type="checkbox" @click="changePage">
          <span class="slider"></span>
        </label>
      </div>
      <MyFeedVue v-if="pageNum=='1'"  ></MyFeedVue>
      <MyGameVue v-else-if="pageNum=='2'" ></MyGameVue>
    </div>

    <div class="feed-game-box" v-if="size=='md'">
      <MyFeedVue   style="width: 100%;"></MyFeedVue>
      <MyGameVue ></MyGameVue>
    </div>
    <div class="feed-game-box" v-if="size=='lg'">
      <MyFeedVue ></MyFeedVue>
      <MyGameVue ></MyGameVue>
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
import { watch } from 'vue'

export default {
    name : 'MyPage',
    data(){
      return{
        userInfo : {},
        isEdit : false,
        userId : "",
        MyFeed : {},
        width: 0,
        height: 0,
        size : 'lg',
        pageNum : '1'
      }
    },
    components : {
      MyFeedVue,
      MyGameVue,
      EditProfile,
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
                else if (viewportWidth >= 700 && viewportWidth < 840
                ) {
                    this.size = "md"}
                else {this.size = "lg"}
            window.addEventListener('resize', this.handleResize);
        },
      getUser : async function(){
        const auth = useAuthStore()
        try{
          let value = await auth.getUser()
          this.userInfo = value.data
          auth.setUserInfo(this.userInfo)
          this.userId = this.userInfo.id
          // console.log(this.userInfo)
        } catch (error) {
            alert('로그인 세션이 만료되었습니다.')
            router.push('/login')
          }
      },
      changePage(){
        if (this.pageNum == 1){
          this.pageNum = 2
          console.log(this.pageNum)

        }else{
          this.pageNum = 1
          console.log(this.pageNum)

        }
      },
      
      openEdit(){
        this.isEdit=true;
      },
      startPage : async function(){
        const auth = useAuthStore()
        try{
            await auth.useRefresh()
            const info = await this.getUser()
        } catch(err){console.log(err)}
      }
    },
  watch:{
    width(){
            if (this.width<600) {
                this.size =  "xs"
            }
            else if (this.width >= 600 && this.width < 840) {
                this.size = "md"}
            else {this.size = "lg"}
        },
  },
  beforeDestroy() {
        window.removeEventListener('resize', this.handleResize);
    },
  mounted(){
    this.emitter.emit('pageChange',3)
    this.startPage()
    this.reactiveSize()

  }
}
</script>

<style scoped src="./css/MyPage.css">

</style>