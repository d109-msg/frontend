<template>
    <div class="feed-container">
        <div class="first-col">
            <div class="feed-create">
                <div class="create-container">
                    <img class="create-img" src="./example/3.jpg">
                    <div class="create-comment">What are you thinking?</div>
                </div>
                <div class="create-btn-box">
                    <div class="create-btn"
                    @click="create=true"
                    >Create</div>
                </div>
            </div>
            <template  v-for="(feed,idx) in arr" :key="idx" >
                <Feed v-if="idx%2==1" :img="feed" :feedIdx="idx"></Feed>                    
            </template>
        </div>
        <div class="second-col">
            <template  v-for="(feed,idx) in arr" :key="idx" >
                <Feed v-if="idx%2==0" :img="feed" :feedIdx="idx"></Feed>                    
            </template>            <FeedCreate v-if="create" @close="create=false"/>
        </div>


        </div>
</template>

<script>
    import Feed from './Feed.vue';
    import FeedCreate from './FeedCreate.vue';
    import { VueFlexWaterfall } from 'vue-flex-waterfall';
export default {
    name: "FeedPage",

    data(){
        return{
            col : 2,
            colSapce : 16,
            arr : [1,2,3],
            create : false
        }
    },  

    methods: {
    },
    components: {
        Feed,
        VueFlexWaterfall,
        FeedCreate,
    },
}
</script>

<style scoped src="./css/FeedPage.css">
.container{
    width: 1000px;
}
</style>