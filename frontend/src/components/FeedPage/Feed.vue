<template>
    <div class="feed-card" @click.prevent="detailFlag=true">
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
      <div class="heart-icon"></div>
      <div class="chat-icon"></div>
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

export default {
    name: "FeedComp",
    data(){
        return{
          feed : this.item,
        }
    },
    methods: {
      closeDetail : function(){
        this.detailFlag = !this.detailFlag
      }
    },
    props:{
      item : Object,
    },
    mounted(){
    }
    
}
</script>

<style scoped src="./css/Feed.css">

</style>