<template>
    <div class="feed-title-box">
      <div class="invite-code-title">{{ roomData.title }}</div>
    </div>
    <div class="feed-content">
      <div>
        <p class="invite-code-title">초대코드</p>
        <p class="invite-code" >
          {{ roomData.id }}
        </p>
        <div style="display: flex; justify-content: space-evenly; ">
          <button id="invite-copy" class="invite-btn" @click="copyBtn()">초대코드 복사하기</button>
          <button class="invite-btn">초대코드 공유하기</button>
        </div>
        <p class="invite-code-title">아직 멤버가 다 모이지 않았어요. <span>({{member.length}}/7)</span></p>
      </div>
    </div>
</template>

<script>
export default {
    name: 'RoomFeedReady',
    props:{
      roomData : Object,
      participant:Object,
      mission:Object,
      ability:Object,
      member: Object,
      roomTime:Number,

    },
    methods:{
      reload: function(){
        window.location.reload()
      },
      copyBtn(){
        const inviteCode = this.roomData.id;
        if (inviteCode){
          const textArea = document.createElement('textarea');
          textArea.value = inviteCode;
          document.body.appendChild(textArea);
          textArea.select()
          try{
            const successful = document.execCommand('copy');
            alert('초대코드가 클립보드에 복사되었습니다.')
          }catch(err){
            console.err('클립보드 복사에 실패했습니다:', err)
          }
          document.body.removeChild(textArea);
        }
      }
    },
    mounted(){
      this.emitter.emit('pageChange',1)
    }
}
</script>

<style scoped src="./css/RoomFeedReady.css">

</style>