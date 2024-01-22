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
      Lorem Ipsum is simply dummy text of the printing and typesetting industry.
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
        <span>
        {{ word }}
      </span>
        <!-- <div class="chat-count">댓글 개</div> -->
        <!-- <div class="see-all">모두보기</div> -->
      </div>
    </div>
  </div>
</template>

<script>
export default {
    name: "FeedComp",
    data(){
        return{
          word : "",
          imgUrl : require(`./example/${this.img}.jpg`)
        }
    },
    methods: {

    },
    props:{
      img : Number,
    },
    created(){
      setTimeout(()=>{
        this.word = "안뇨안ㄴㅇㅁ아퍼밍나퍼미ㅏㄴㅇ퍼ㅣ만어팜ㄴ어ㅣ파먼아ㅣㅍ먼아ㅣ펌ㄴ아ㅣ펌나ㅣㅇ퍼마ㅣㄴ어피나어파ㅣ"
      },500)
      
    }
}
</script>

<style scoped src="./Feed.css">

</style>