<template>
    <div class="feed-container">
            <VueFlexWaterfall
                col="2"
                col-spacing="16"
            >
            <div class="feed-create">
                <div class="create-container">
                    <img class="create-img" src="./example/3.jpg">
                    <div class="create-comment">What are you thinking?</div>
                </div>
                <div class="create-btn"
                @click="create=true"
                >Create</div>
            </div>
            <Feed  v-for="i in arr" :key="i" :img="i"></Feed>
            <!-- <Feed></Feed>
            <Feed></Feed>

            <Feed></Feed>       

            <Feed></Feed>
            <Feed></Feed> -->
            </VueFlexWaterfall>
            <FeedCreate v-if="create" @close="create=false"/>
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