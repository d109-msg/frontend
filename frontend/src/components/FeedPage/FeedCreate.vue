<template>
    <div class="feed-modal">
            <ImageEdit @close="close" :roomId="roomId" :mission="missions"
            />
    </div>
</template>

<script>
import ImageEdit from '../ImageEdit/ImageEdit.vue';
export default {
    name: "FeedCreate",
    data(){
      return{
        missions:"",
      }
    },
    components:{
        ImageEdit,
    },
    props:{
      roomId : String,
      mission : String,
    },
    methods: {
      close : function(value){
        this.$emit('close',value)
      }  
    },
    mounted(){
      this.$nextTick(()=>{
        this.missions = this.$props.mission
      })
    }

}
</script>

<style scoped src="./css/FeedCreate.css">
</style>