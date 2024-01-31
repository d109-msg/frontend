<template>
    <div class="detail-back"
        @click.self="close"
    >
        <div class="detail-container">
            <div class="detail-img" id="detailImg">
                <div class="left-arrow"></div>
                <div class="right-arrow"></div>
            </div>
            <div class="detail-info">
                <div class="user-info">
                    <div class="info">
                        <div class="user-img"></div>
                        <p class="nick">{{ itemData.nickname }}</p>
                    </div>
                    <div class="list"></div>
                </div>
                <div class="line"></div>
                <div class="comment-list">
                    <p>댓글 {{ commentCount }}개</p>
                    <div class="comment" v-for="(item,idx) in comment" :key="idx">
                        <div class="comment-img"></div>
                    </div>
                    
                </div>
                <div class="line2"></div>
                <div class="bottom">
                    <p>{{ likeCount }}명의 사람들이 이 글을 좋아합니다.</p>
                    <p class="time">{{ createTime }}</p>
                    <div class="write">
                        <textarea  cols="30" rows="10" class="write-comment" maxlength="50"
                        v-model="writeComment" @keyup.enter.prevent="send"
                        ></textarea>
                        <div class="submit" @click.prevent="send">댓글쓰기</div>
                    </div>
                </div>
            </div>
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
            imgList : [],
            step : 0,
            comment : [],
            commentCount : 0,
            likeCount : 0,
            createTime : "",
            writeComment : ""
        }   
    },
    methods: {
        close : function(){
            this.$emit('closeDetail')
        },
        send : async function(){
            const feed = useFeedStore()
            try{
                await feed.writeComment(this.idx.articleId,this.writeComment,0)
                await this.readDetail(this.idx.articleId)
                this.writeComment = ""
            } catch(err){
                console.log(err)
            }
        },
        readDetail : async function(idx){
            console.log(idx)
            const feed = useFeedStore()
            try{
                let value = await feed.getDetail(idx)
                this.itemData = value.data
                this.imgList = this.itemData.urls
                this.likeCount = this.itemData.likeCount
                this.createTime = this.itemData.createTime
                if(value.data.commentList != null){
                    this.comment = this.itemData.commentList
                    this.commentCount = this.comment.length
                    console.log(this.comment)
                }
                const img = document.getElementById('detailImg')
                img.style.background = `url(${this.imgList[this.step]})`
                img.style.backgroundSize = 'cover'
                img.style.backgroundRepeat = 'no-repeat'
            } catch(err){
                this.$emit('closeDetail')
                alert('예기치 않은 오류가 발생했습니다.')
            }
        }

    },
    props: {
        idx : Object,
    },
    watch:{
        step(){
            const img = document.getElementById('detailImg')
            img.style.background = `url(${this.imgList[this.step]})`
            img.style.backgroundSize = 'cover'
            img.style.backgroundRepeat = 'no-repeat'
        }
    },
    mounted(){
        // console.log(this.idx.articleId)
        this.readDetail(this.idx.articleId)
        
    }

}
</script>

<style scoped src="./css/DetailPage.css">

</style>