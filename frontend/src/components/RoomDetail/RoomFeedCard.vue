<template>
    <div class="feed-card" >
    <div class="list-svg" @click="editFlag=!editFlag">
      <div class="list-container" v-if="userInfo.id==feed.userId && editFlag == true">
          <p style="width: 100px;"
          @click="deleteFeed"
          ><img  alt="">게시물 삭제</p>
      </div>
      <div class="list-container" v-if="userInfo.id!=feed.userId  && editFlag == true">
          <p style="width: 100px;"><img  alt="">게시물 신고</p>
      </div>
    </div>
    <div v-if="editFlag==true" class="close-list" @click="editFlag=false"></div>
    <div class="feed-item">
      <img class="user-img" :src="feed.iconUrl">
      <div class="user-info">
        <div class="user-name" @click="userProfile" v-if="room.endTime!=null">{{ feed.nickname }}</div>
        <div class="user-name" v-if="room.endTime==null">{{ feed.nickname }}</div>
        <div class="user-comment">{{ feed.content }}</div>
      </div>
    </div>
    <div class="feed-comment">
    </div>
    <img class="feed-img" :src="feed.urls[0]" @click.prevent="onDetail">
    <div class="feed-btn">
      <img class="heart-icon"  
      v-if="item.isLike==0"
      @click.prevent="likeArticle">
      <img class="heart-icon" 
      v-if="item.isLike == 1" @click.prevent="likeArticle">
      
      <div class="chat-icon" @click.prevent="onDetail"></div>

      <div class="share-icon"></div>
    </div>
    <div class="feed-chat">
      <div class="latest-chat">
        <div class="chat-user-name"></div>
        <div style="color: #545454"></div>
      </div>
      <div class="chat-info">

      </div>
    </div>
  </div>
</template>

<script>
import { useFeedStore } from '@/store/feedStore';
import DetailPage from '../DetailPage/DetailPage.vue';
import { useAuthStore } from '@/store/authStore';
import router from '@/router';

export default {
    name: "RoomFeedCard",
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
      room : Object,
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

<style scoped src="./css/RoomFeedCard.css">

</style>