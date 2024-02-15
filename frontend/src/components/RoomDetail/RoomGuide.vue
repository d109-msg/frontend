<template>

    <div class="chat-content">
      <img v-if="!isDarkMode" class="job-guide" src="https://team109testbucket.s3.ap-northeast-2.amazonaws.com/job_guide.png" alt="">
      <img v-else class="job-guide" src="https://team109testbucket.s3.ap-northeast-2.amazonaws.com/job_guide_dark.png" alt="">

    </div>

</template>

<script>
export default {
    name: 'RoomGuide',
    props:{
      isDarkMode : Boolean
    }
    
}
</script>

<style scoped src="./css/RoomGuide.css">

</style>