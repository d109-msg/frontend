<template>
  <div style="height: 100%; width: 100%; position: relative;" class="test">
    <div class="mypage-banner-box">
      <div :class="{'mypage-banner-light': !isDarkMode , 'mypage-banner-dark':isDarkMode}">
      </div>
    </div>
    <div class="mypage-box">
      <div :class="{'profile-box-light':!isDarkMode, 'profile-box-dark':isDarkMode}">
        <div class="profile-content">
          <div class="edit-profile" @click="openEdit" ></div>
          <img class="profile-img" :src="userInfo.imageUrl" alt="">
          <div :class="{'profile-section':!isDarkMode, 'profile-section-dark':isDarkMode}">
            <div class="">닉네임 <span> {{ userInfo.nickname  }} </span></div>
            <div class="" >소개글 <span> {{ userInfo.bio }} </span></div>
            <div :class="{'profile-bot-section':!isDarkMode, 'profile-bot-section-dark':isDarkMode}">
              <div>게시물 <span> {{ userInfo.articleCount }} </span></div>
              <div style="cursor: pointer;" @click="followClick(1)">팔로우 <span> {{ userInfo.followerCount }} </span></div>
              <div style="cursor: pointer;" @click="followClick(2)">팔로잉 <span> {{ userInfo.followingCount }}</span></div>
            </div>
        </div>
      </div>
    </div>
    <div class="follow-back" @click.self="followStep = 0" v-if="followStep>0">
      <div class="follow-modal">
        <div class="follow-title">
          {{ followTitle }}
        </div>
        <div class="follow-content">
          <div class="follow-body" v-for="(member,idx) in followData" :key="idx">
            <img :src="member.imageUrl" style="cursor: pointer;" @click="goFriend(member.userId)" class="follow-img">
            <p class="follow-name" style="cursor: pointer;" @click="goFriend(member.userId)">{{member.nickname}}</p>
            <div class="follow-btn" v-if="!member.isFollow" @click="follow(member.userId)">Follow</div>
            <div class="follow-btn" v-if="member.isFollow" @click="follow(member.userId)">Unfollow</div>
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
      <div>
        <MyFeedVue v-if="pageNum=='1'" :is-dark-mode="isDarkMode" ></MyFeedVue>
      </div>
      <div>
        <MyGameVue v-if="pageNum=='2'"  :is-dark-mode="isDarkMode"></MyGameVue>
      </div>
    </div>

    <div class="feed-game-box" v-if="size=='md'" style="display: flex; flex-direction: column;">
      <div style="display: flex; justify-content: flex-end; margin-bottom: 5px;">
        <label class="toggle_switch">
          <input type="checkbox" @click="changePage">
          <span class="slider"></span>
        </label>
      </div>
      <MyFeedVue v-if="pageNum=='1'" :is-dark-mode="isDarkMode" ></MyFeedVue>
      <MyGameVue v-else-if="pageNum=='2'"  :is-dark-mode="isDarkMode" :isMount="1"></MyGameVue>
    </div>
    <div class="feed-game-box" v-if="size=='lg'">
      <MyFeedVue :is-dark-mode="isDarkMode" ></MyFeedVue>
      <MyGameVue  :is-dark-mode="isDarkMode" :isMount="1"></MyGameVue>
    </div>

  </div>
  <div v-if="isEdit==true"  >
    <EditProfile @close-edit="isEdit=false" :userInfo="userInfo" :is-dark-mode="isDarkMode"/>
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
        userId : "",
        MyFeed : {},
        width: 0,
        height: 0,
        size : 'lg',
        pageNum : '1',
        followStep : 0,
        followData : [],
        followTitle : "",
      }
    },
    components : {
      MyFeedVue,
      MyGameVue,
      EditProfile,
    },
    props:{
        isDarkMode : Boolean
    },
    methods:{
      goFriend(idx){
        const auth = useAuthStore()
        if(idx == auth.getUserInfo.id){
          router.push('/mypage')
        }
        else{
          router.push(`/user/${idx}`)
        }
        this.followStep = 0 
      },
      async follow(idx){
        try{
          const auth = useAuthStore()
          await auth.follow(idx)
          if(this.followTitle=="My Follow"){
            await this.followClick(1)
          }else{
            await this.followClick(2)
          }
        }catch(err){
          
        }
      },
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

        }else{
          this.pageNum = 1

        }
      },
      
      openEdit(){
        this.isEdit=true;
      },
      async followClick(value){
        const auth = useAuthStore()
        if(value == 1){
          let value = await auth.followAll(this.userId,"to")
          this.followData = value.data.followUserList
          console.log(this.followData)
          this.followTitle = "My Follow"
        }else{
          let value = await auth.followAll(this.userId,"from")
          this.followData = value.data.followUserList
          console.log(this.followData)
          this.followTitle = "My Following"

        }
        this.followStep = value
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
            if (this.width<700) {
                this.size =  "xs"
            }
            else if (this.width >= 700 && this.width < 860) {
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