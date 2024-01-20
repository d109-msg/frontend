<template>
  <div class="feed-card">
    <div class="list-svg"></div>
    <div class="feed-item">
      <div class="user-img"></div>
      <div class="user-info">
        <div class="user-name">Random_user</div>
        <div class="user-comment">subtitle</div>
      </div>
    </div>
    <div class="feed-comment">
      Lorem Ipsum is simply dummy text of the printing and typesetting industry.
    </div>
    <div class="feed-img"></div>
    <div class="feed-btn">
      <div class="heart-icon"></div>
      <div class="chat-icon"></div>
      <div class="share-icon"></div>
    </div>
    <div class="feed-chat">
      <div class="latest-chat">
        <div class="chat-user-name">{{  }}</div>
        <div style="color: #545454">{{  }}</div>
      </div>
      <div class="chat-info">
        <div class="chat-count">댓글 {{  }}개</div>
        <div class="see-all">모두보기</div>
      </div>
    </div>
</div>
</template>

<script>
export default {
    name: "FeedComp",
    data(){
        return{}
    },
    methods: {

    },

}
</script>

<style scoped src="./Feed.css">

</style>