<template>
    <div class="feed-modal">
            <ImageEdit @close="close"
            />
    </div>
</template>

<script>
import ImageEdit from '../ImageEdit/ImageEdit.vue';
export default {
    name: "FeedCreate",
    components:{
        ImageEdit,
    },
    methods: {
      close : function(value){
        this.$emit('close',value)
      }  
    },

}
</script>

<style scoped src="./css/FeedCreate.css">
</style>