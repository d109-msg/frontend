<template>
    <div class="detail-back"
        @click.prevent="close"
    >
        <div class="detail-container">
            <img alt="" class="detail-img" :src="img">
        </div>
    </div>
</template>

<script>
import { useFeedStore } from '@/store/feedStore'
export default {
    name: "DetailPage",
    data(){
        return{
            itemIdx : this.$props.idx,
            itemData : Object,
            img : "",
        }
    },
    methods: {
        close : function(){
            this.$emit('closeDetail')
        },
        readDetail : async function(idx){
            console.log(idx)
            const feed = useFeedStore()
            try{
                let value = await feed.getDetail(idx)
                this.itemData = value.data
                console.log(this.itemData)
                this.img = this.itemData.urls[0]
            } catch(err){
                this.$emit('closeDetail')
                alert('예기치 않은 오류가 발생했습니다.')
            }
        }

    },
    props: {
        idx : Object,
    },
    mounted(){
        // console.log(this.idx.articleId)
        this.readDetail(this.idx.articleId)
        
    }

}
</script>

<style scoped src="./css/DetailPage.css">

</style>