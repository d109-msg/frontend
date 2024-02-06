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
              <div>게시물 <span> {{ 8 }} </span></div>
              <div>팔로우 <span> {{ 10 }} </span></div>
              <div>팔로잉 <span> {{ 11 }}</span></div>
            </div>
        </div>
      </div>
    </div>
    <div class="feed-game-box" v-if="size=='xs'">
      <MyFeedVue v-if="pageNum=='1'" class="myfeed-box" :size="size" @change-page="changePage"></MyFeedVue>
      <MyGameVue v-else class="mygame-box" :size="size"  @change-page="changePage"></MyGameVue>
    </div>

    <div class="feed-game-box" v-if="size=='md'">
      <MyFeedVue  class="myfeed-box" style="width: 100%;"></MyFeedVue>
      <MyGameVue  class="mygame-box"></MyGameVue>
    </div>
    <div class="feed-game-box" v-if="size=='lg'">
      <MyFeedVue  class="myfeed-box"></MyFeedVue>
      <MyGameVue  class="mygame-box"></MyGameVue>
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
          console.log(this.userInfo)
        } catch (error) {
            alert('로그인 세션이 만료되었습니다.')
            router.push('/login')
          }
      },
      changePage(value){
        this.pageNum = value
        console.log(value)
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
                console.log(this.size)
            }
            else if (this.width >= 600 && this.width < 890) {
                this.size = "md"}
            else {this.size = "lg"}
        },
  },
  beforeDestroy() {
        // console.log("beforeDestroy...");
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