<template>
    <div class="feed-page">
   <VueFlexWaterfall
    align-content="center"
    col="2"
    col-spacing="70"
    :break-at="{ 900: 3, 600: 2, 300: 1 }"
  >
    <div class="feed-create">
        <div class="create-container">
            <div class="create-img"></div>
            <div class="create-comment">What are you thinking?</div>
        </div>
        <div class="create-btn">Create</div>
    </div>
    <Feed v-for="i in [1,2,3,4,5]" :key="i"/>
  </VueFlexWaterfall>
  </div>
</template>

<script>
import { VueFlexWaterfall } from 'vue-flex-waterfall';
import Feed from './Feed.vue';
export default {
    name: "FeedPage",

    data(){
        return{}
    },

    methods: {

    },

    components: {
        Feed,
        VueFlexWaterfall
    }
}
</script>

<style scoped src="./FeedPage.css">

</style>