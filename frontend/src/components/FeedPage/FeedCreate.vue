<template>
    <div class="feed-modal">
            <ImageEdit/>
    </div>
</template>

<script>
import ImageEdit from '../ImageEdit/ImageEdit.vue';
export default {
    name: "FeedCreate",
    components:{
        ImageEdit,
    }

}
</script>

<style scoped src="./css/FeedCreate.css">
</style>