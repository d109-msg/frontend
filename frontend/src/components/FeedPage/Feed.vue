<template>

    <div class="feed-card">
    <div class="list-svg"></div>
    <div class="feed-item">
      <img class="user-img" src="./example/6.jpg">
      <div class="user-info">
        <div class="user-name">Random_user</div>
        <div class="user-comment">subtitle</div>
      </div>
    </div>
    <div class="feed-comment">
    </div>
    <img class="feed-img" :src="imgUrl">
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
          imgUrl : require(`./example/${this.img}.jpg`)
        }
    },
    methods: {

    },
    props:{
      img : Number,
    },
    
}
</script>

<style scoped src="./css/Feed.css">

</style>