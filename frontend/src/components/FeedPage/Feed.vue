<template>
    <div :class="{'feed-card':!isDarkMode, 'feed-card-dark':isDarkMode}">
    <div class="list-svg" @click="editFlag=!editFlag">
      <div :class="{'list-container':!isDarkMode,'list-container-dark':isDarkMode}" v-if="userInfo.id==feed.userId && editFlag == true">
          <p style="width: 100px;"
          @click="deleteFeed"
          ><img src="./Icon/delete.png" alt="">게시물 삭제</p>
      </div>
      <div class="list-container" v-if="userInfo.id!=feed.userId  && editFlag == true">
          <p style="width: 100px;" @click="report"><img src="./Icon/report.png" alt="">게시물 신고</p>
      </div>
    </div>
    <div v-if="editFlag==true" class="close-list" @click="editFlag=false"></div>
    <div class="feed-item">
      <img class="user-img" :src="feed.imageUrl" @click="userProfile" style="cursor: pointer;">
      <div class="user-info">
        <div :class="{'user-name':!isDarkMode,'user-name-dark':isDarkMode}" @click="userProfile">{{ feed.nickname }}</div>
        <div class="user-comment">{{ feed.content }}</div>
      </div>
    </div>
    <div class="feed-comment">
    </div>
    <img class="feed-img" :src="feed.urls[0]" @click.prevent="onDetail">
    <div class="feed-btn">
      <img class="heart-icon" src="./Icon/heart.png" 
      v-if="item.isLike==0 && !isDarkMode"
      @click.prevent="likeArticle">
      <img class="heart-icon" src="./Icon/heart_dark.png" 
      v-if="item.isLike==0 && isDarkMode"
      @click.prevent="likeArticle">
      <img class="heart-icon" src="./Icon/fullheart.png" 
      v-if="item.isLike == 1" @click.prevent="likeArticle">
      
      <div :class="{'chat-icon':!isDarkMode,'chat-icon-dark':isDarkMode}" @click.prevent="onDetail"></div>

    </div>
    <div class="feed-chat">
      <div class="latest-chat">
        <div class="chat-user-name"></div>
        <div style="color: #545454"></div>
      </div>
      <div class="chat-info">

      </div>
    </div>
    
    <DetailPage v-if="detailFlag" 
        @close-detail="offDetail"
        @update-like="updateLike"
        :idx="feed.articleId" :is-dark-mode="isDarkMode"
        />
  </div>
</template>

<script>
import { useFeedStore } from '@/store/feedStore';
import DetailPage from '../DetailPage/DetailPage.vue';
import { useAuthStore } from '@/store/authStore';
import router from '@/router';
import { toast} from 'vue3-toastify'
import "vue3-toastify/dist/index.css"

export default {
    name: "FeedComp",
    data(){
        return{
          feed : this.item,
          detailFlag : false,
          isLike : 0,
          userInfo : {},
          editFlag : false,
        }
    },
    methods: {
      report : function(){
        toast(`게시글 신고가 완료되었습니다.`,{
                    theme : "auto",
                    "type": "error",
                    "pauseOnHover": false,
                    "position": "top-center",
                    "transition": "slide",
                    "dangerouslyHTMLString": true,
                    "autoClose": 1000,
                    
                })
      },
      updateLike : function(){
        this.feed.isLike = !this.feed.isLike
      },
      closeDetail : function(){
        this.detailFlag = !this.detailFlag
      },
      onDetail : function(idx){
                this.detailFlag = true
            },
      offDetail : function(){
          this.detailFlag = false
      },
      likeArticle : async function(){
        let idx = this.feed.articleId
        const feed = useFeedStore()
        try{
          await feed.likeArticle(idx)
          if(this.feed.isLike==0){
            this.feed.isLike = 1
          } else{
            this.feed.isLike = 0

          }
        }catch(err){
          console.log(err)
        }
      },
      deleteFeed : async function(){
        const feed = useFeedStore()
        try{  
          await feed.deleteFeed(this.feed.articleId)
          window.location.reload()
        } catch(err){
          console.log(err)
        }
      },
      userProfile : function(){
        router.push(`/user/${this.feed.userId}`)
      }

    },
    props:{
      item : Object,
      isDarkMode : Boolean

    },
    components:{
      DetailPage,

    },
    mounted(){
      this.isLike = this.feed.isLike
      const auth = useAuthStore()
      this.userInfo = auth.getUserInfo
      
    }
    
}
</script>

<style scoped src="./css/Feed.css">

</style>