<template>
    <div class="feed-card">
    <div class="list-svg"></div>
    <div class="feed-item">
      <img class="user-img" src="./example/6.jpg">
      <div class="user-info">
        <div class="user-name">부수환</div>
        <div class="user-comment">subtitle</div>
      </div>
    </div>
    <div class="feed-comment">
    </div>
    <img class="feed-img" :src="feed.urls[0]">
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

export default {
    name: "FeedComp",
    data(){
        return{
          feed : this.item,
          detailFlag : false,
          isLike : 0,
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
    }
    
}
</script>

<style scoped src="./css/Feed.css">

</style>