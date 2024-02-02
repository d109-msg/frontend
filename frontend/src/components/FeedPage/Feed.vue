<template>
    <div class="feed-card">
    <div class="list-svg" @click="editFlag=!editFlag">
      <div class="list-container" v-if="userInfo.id==feed.userId && editFlag == true">
          <p style="width: 100px;"
          @click="deleteFeed"
          ><img src="./Icon/delete.png" alt="">게시물 삭제</p>
      </div>
      <div class="list-container" v-if="userInfo.id!=feed.userId  && editFlag == true">
          <p style="width: 100px;"><img src="./Icon/report.png" alt="">게시물 신고</p>
      </div>
    </div>
    <div v-if="editFlag==true" class="close-list" @click="editFlag=false"></div>
    <div class="feed-item">
      <img class="user-img" :src="this.feed.imageUrl">
      <div class="user-info">
        <div class="user-name">{{ this.feed.nickname }}</div>
        <div class="user-comment">{{ this.feed.content }}</div>
      </div>
    </div>
    <div class="feed-comment">
    </div>
    <img class="feed-img" :src="feed.urls[0]" @click.prevent="onDetail">
    <div class="feed-btn">
      <img class="heart-icon" src="./Icon/heart.png" 
      v-if="isLike==false"
      @click.prevent="likeArticle">
      <img class="heart-icon" src="./Icon/fullheart.png" 
      v-if="isLike==true"
      @click.prevent="likeArticle">
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
    <DetailPage v-if="detailFlag"
        @close-detail="offDetail"
        :idx="feed"
        />
  </div>
</template>

<script>
import { useFeedStore } from '@/store/feedStore';
import DetailPage from '../DetailPage/DetailPage.vue';
import { useAuthStore } from '@/store/authStore';

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
          this.isLike = !(this.isLike)
          console.log(this.isLike)
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
      }

    },
    props:{
      item : Object,
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