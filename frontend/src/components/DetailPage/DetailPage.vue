<template>
    <div class="detail-back"
        @click.self="close"
    >
        <div class="detail-container">
            <div class="detail-img" id="detailImg">
                <div class="left-arrow" @click="leftStep"></div>
                <div class="right-arrow" @click="rightStep"></div>
            </div>
            <div class="detail-info">
                <div class="user-info">
                    <div class="info" @click="userProfile">
                        <img class="user-img" :src="itemData.imageUrl">
                        
                        <div style="display: flex; flex-direction: column; justify-content: space-around;">
                            <p class="nick">{{ itemData.nickname }}</p>
                            <p class="nick">{{ itemData.content }}</p>
                        </div>
                    </div>
                    <div class="list" @click.self.prevent="optionFlag = !optionFlag;">
                        <div class="list-container" v-if="optionFlag && me.id==itemData.userId">
                            <p style="width: 100px;"
                            @click="editFlag=true"
                            ><img src="./Icon/edit.png" alt="">게시물 수정</p>
                            
                            <p style="width: 100px;"
                            @click="deleteFlag=true"
                            ><img src="./Icon/delete.png" alt="">게시물 삭제</p>
                        </div>
                        <div class="list-container" v-if="optionFlag && me.id!=itemData.userId">
                            <p style="width: 100px;"><img src="./Icon/report.png" alt="">게시물 신고</p>
                        </div>
                    </div>
                </div>
                
                <div class="line"></div>
                <div class="comment-list" @click="optionFlag = false">
                    <p>댓글 {{ commentCount }}개</p>
                    <div class="comment" v-for="(item,idx) in comment" :key="idx">
                        <img class="comment-img" :src="item.imageUrl">
                        <div class="info-container">
                            <p class="comment-nick">{{ item.nickname }}</p>
                            <p>{{ item.content }}</p>
                            <div style="display: flex; align-items: center; margin-left: 2px;">
                                <img class="heart-img" src="./Icon/heart.png" alt="" v-if="item.isCommentLike==0"
                                @click.prevent="likeComment(item.id)"
                                >
                                <img src="./Icon/fullheart.png" class="heart-img" v-if="item.isCommentLike==1"
                                @click.prevent="likeComment(item.id)"
                                >
                                <img class="chat-img" src="./Icon/chat.png" alt="" @click="showRecomment(idx,item.id)">
                            </div>
                            <div class="re-comment" v-if="recommentView[idx]">
                                <div class="re-content" v-for="(re,reIdx) in recommentList[idx]" :key="reIdx">
                                    <img class="comment-img" :src="re.imageUrl">
                                    <div class="info-container">
                                        <p class="comment-nick">{{ re.nickname }}</p>
                                        <p>{{ re.content }}</p>
                                        <!-- <div style="display: flex; align-items: center; margin-left: 2px;"> -->
                                            <img class="heart-img" src="./Icon/heart.png" alt="" v-if="re.isCommentLike==0"
                                            @click.prevent="likeComment(re.id); re.isCommentLike=1"
                                            >
                                            <img src="./Icon/fullheart.png" class="heart-img" v-if="re.isCommentLike==1"
                                            @click.prevent="likeComment(re.id); re.isCommentLike=0"
                                            >
                                        <!-- </div> -->
                                    </div>
                                </div>
                                <div class="resend-form">
                                    <input class="resend" type="text" v-model="recomment[idx]" maxlength="30">
                                    <div class="resend-button" @click.prevent="writeRecomment(idx,item.id)">전송</div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="line2"></div>
                <div class="bottom">
                    <p>{{ likeCount }}명의 사람들이 이 글을 좋아합니다.</p>
                    <p class="time">{{ createTime }}</p>
                    <div class="write">
                        <textarea  cols="30" rows="10" class="write-comment" maxlength="20"
                        v-model="writeComment" @keyup.enter.prevent="send"
                        ></textarea>
                        <div class="submit" @click.prevent="send">댓글쓰기</div>
                    </div>
                </div>
            </div>
        </div>
        <div class="edit-back" v-if="editFlag" @click.self="editFlag=false">
            <div class="edit-content">
                <p class="edit-header"><img src="./Icon/edit.png" alt="">게시물 내용 수정</p>
                <div class="edit-body">
                    <textarea  cols="30" rows="10" class="edit-write" maxlength="20"
                    v-model="editComment" @keyup.enter.prevent="send"
                    ></textarea>
                </div>
                <div class="edit-button" @click="editFeed">edit</div>
            </div>
        </div>
        <div class="edit-back" v-if="deleteFlag" @click.self="deleteFlag=false">
            <div class="delete-content">
                <div class="delte-body">
                    <p><img src="./Icon/delete.png" alt="">게시물 삭제</p>
                    <div class="delete-false" @click="deleteFlag=false">취소하기</div>
                    <div class="delete-button" @click="deleteFeed">삭제하기</div>
                </div>
                
            </div>
        </div>
    </div>
</template>

<script>
import { useFeedStore } from '@/store/feedStore'
import { useAuthStore } from '@/store/authStore'
export default {
    name: "DetailPage",
    data(){
        return{
            itemIdx : this.$props.idx,
            itemData : Object,
            img : "",
            imgList : [],
            optionFlag : false,
            step : 0,
            comment : [],
            commentCount : 0,
            likeCount : 0,
            createTime : "",
            writeComment : "",
            recommentList : [],
            recommentView : [],
            recomment : [],
            me : {},
            editComment: "",
            editFlag : false,
            deleteFlag : false,
        }   
    },
    methods: {
        close : function(){
            this.$emit('closeDetail')
        },
        send : async function(){
            const feed = useFeedStore()
            try{
                await feed.writeComment(this.idx,this.writeComment,0)
                await this.readDetail(this.idx)
                this.writeComment = ""
            } catch(err){
                console.log(err)
            }
        },
        readDetail : async function(idx){
            const feed = useFeedStore()
            try{
                let value = await feed.getDetail(idx)
                this.itemData = value.data
                console.log(this.itemData)
                this.imgList = this.itemData.urls
                this.likeCount = this.itemData.likeCount
                this.createTime = this.itemData.createTime
                if(value.data.commentList != null){
                    this.comment = this.itemData.commentList
                    this.comment.forEach(item=>{
                        this.recommentList.push([])
                        this.recommentView.push(false)
                        this.recomment.push("")
                    })
                    this.commentCount = this.comment.length
                }
                const img = document.getElementById('detailImg')
                img.style.background = `url(${this.imgList[this.step]})`
                img.style.backgroundSize = 'cover'
                img.style.backgroundRepeat = 'no-repeat'
            } catch(err){
                this.$emit('closeDetail')
                alert('로그인이 필요한 페이지입니다.')
            }
        },
        leftStep : function(){
            if(this.step >0) {
                this.step--
            } else{
                this.step = this.imgList.length-1
            } 
        },
        rightStep : function(){
            if(this.step <this.imgList.length-1) {
                this.step++
            } else{
                this.step = 0
            } 
        },
        likeComment : async function(value){
        const feed = useFeedStore()
        try{
          await feed.likeComment(value)
          this.readDetail(this.idx)
        }catch(err){
          console.log(err)
        }
      },
      showRecomment : async function(idx,commentId){
        const feed = useFeedStore()
        try{
            let value = await feed.readRecomment(commentId,this.itemData.articleId)
            this.recommentList[idx]= value.data
            this.recommentView[idx] = !(this.recommentView[idx])
        } catch(err){
            console.log(err)
        }
      },
      writeRecomment : async function(idx,commentId){
        const feed = useFeedStore()
        const auth = useAuthStore()
        try{
            let value = await feed.writeComment(this.itemData.articleId,this.recomment[idx],commentId)
            this.showRecomment(idx,commentId)
            this.recomment[idx] = ""
        } catch(err){
            await auth.useRefresh()
            alert('일시적 오류입니다. 다시 시도해주세요.')
        }
      },
      getUser : async function(){
        const auth = useAuthStore()
        this.me = auth.getUserInfo
      },
      editFeed : async function(){
        const feed =useFeedStore()
        try{
            let value = await feed.editFeed(this.me.id,this.idx,this.editComment)
            alert('게시글 수정이 완료되었습니다.')
            this.editFlag = false
            this.readDetail(this.idx)
        } catch(err){
            console.log(err)
        }
      },
      deleteFeed: async function(){
        const feed = useFeedStore()
        try{
            await feed.deleteFeed(this.idx)
            alert("게시글 삭제가 완료되었습니다.")
            window.location.reload()
        }catch(err){
            console.log(err)
        }
      },
      userProfile : function(){
        this.$router.push(`/user/${this.itemData.userId}`)
      }

    },
    props: {
        idx : Number,
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
        this.readDetail(this.idx)
        this.getUser()
    }

}
</script>

<style scoped src="./css/DetailPage.css">

</style>