<template>
  <div class="feed-container">
    <div class="feed-title-box">
      <div @click="reload" class="reset-icon"></div>
      <div class="create-icon"></div>
    </div>
  </div>
</template>

<script>
export default {
    name: 'RoomFeed',
    methods:{
      reload: function(){
        window.location.reload()
      }
    },
    mounted(){
      this.emitter.emit('pageChange',1)
    }
}
</script>

<style scoped src="./css/RoomFeed.css">

</style>