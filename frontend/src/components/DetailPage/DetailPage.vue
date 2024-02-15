<template>
    <div class="detail-back"
        @click.self="close"
    >
        <div :class="{'detail-container':!isDarkMode, 'detail-container-dark':isDarkMode}">
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
                        <div :class="{'list-container':!isDarkMode,'list-container-dark':isDarkMode}" v-if="optionFlag && me.id==itemData.userId">
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
                
                <div :class="{'line':!isDarkMode,'line-dark':isDarkMode}"></div>
                <div class="comment-list" @click="optionFlag = false">
                    <p :class="{'comment-count':!isDarkMode, 'comment-count-dark':isDarkMode}">댓글 {{ commentCount }}개</p>
                    <div class="comment" v-for="(item,idx) in comment" :key="idx">
                        <img class="comment-img" :src="item.imageUrl">
                        <div class="info-container">
                            <p v-if="isDarkMode" style="color: #B1AFAF;">{{ item.nickname }}</p>
                            <p v-else>{{ item.nickname }}</p>
                            <p v-if="isDarkMode" style="color: #fff;">{{ item.content }}</p>
                            <p v-else >{{ item.content }}</p>
                            <div style="display: flex; align-items: center; margin-left: 2px;">
                                <img class="heart-img" src="./Icon/heart.png" alt="" v-if="item.isCommentLike==0 && !isDarkMode"
                                @click.self.prevent="likeComment(item.id); item.isCommentLike = 1"
                                >
                                <img class="heart-img" src="./Icon/heart_dark.png" alt="" v-if="item.isCommentLike==0 && isDarkMode"
                                @click.self.prevent="likeComment(item.id); item.isCommentLike = 1"
                                >
                                <img src="./Icon/fullheart.png" class="heart-img" v-if="item.isCommentLike==1"
                                @click.self.prevent="likeComment(item.id); item.isCommentLike = 0"
                                >
                                <img v-if="isDarkMode" class="chat-img" src="./Icon/chat_dark.png" alt="" @click="showRecomment(idx,item.id)">
                                <img v-if="!isDarkMode" class="chat-img" src="./Icon/chat.png" alt="" @click="showRecomment(idx,item.id)">
                            </div>
                            <div class="re-comment" v-if="recommentView[idx]">
                                <div class="re-content" v-for="(re,reIdx) in recommentList[idx]" :key="reIdx">
                                    <img class="comment-img" :src="re.imageUrl">
                                    <div class="info-container">
                                        <p v-if="isDarkMode" style="color: #B1AFAF;">{{ re.nickname }}</p>
                                        <p v-else>{{ re.nickname }}</p>
                                        <p v-if="isDarkMode" style="color: #fff;">{{ re.content }}</p>
                                        <p v-else >{{ re.content }}</p>
                                        <img class="heart-img" src="./Icon/heart.png" alt="" v-if="re.isCommentLike==0 && !isDarkMode"
                                        @click.prevent="likeComment(re.id); re.isCommentLike=1"
                                        >
                                        <img class="heart-img" src="./Icon/heart_dark.png" alt="" v-if="re.isCommentLike==0 && isDarkMode"
                                        @click.prevent="likeComment(re.id); re.isCommentLike=1"
                                        >
                                        <img src="./Icon/fullheart.png" class="heart-img" v-if="re.isCommentLike==1"
                                        @click.prevent="likeComment(re.id); re.isCommentLike=0"
                                        >
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
                <div :class="{'line2':!isDarkMode,'line2-dark':isDarkMode}"></div>
                <div class="bottom" style="margin-top: -10px;">
                    <img src="./Icon/heart_dark.png" alt="" v-if="itemData.isLike==0" style="height: 20px; width: 20px; cursor: pointer;" @click="likeArticle">
                    <img src="./Icon/fullheart.png" alt="" v-if="itemData.isLike==1" style="height: 20px; width: 20px; cursor: pointer;" @click="likeArticle">
                    <p :class="{'comment-count':!isDarkMode, 'comment-count-dark':isDarkMode}" style="font-size: 0.9em;">{{ likeCount }}명의 사람들이 이 글을 좋아합니다.</p>
                    <p :class="{'comment-count':!isDarkMode, 'comment-count-dark':isDarkMode}" style="font-size: 0.85em;">{{ createTime }}</p>
                    <div class="write" style="width: 100%;">
                        <textarea  cols="30" rows="10" :class="{'write-comment':!isDarkMode,'write-comment-dark':isDarkMode}" maxlength="20"
                        v-model="writeComment" @keyup.enter.prevent="send"
                        ></textarea>
                        <div :class="{'submit':!isDarkMode, 'submit-dark':isDarkMode}" @click.prevent="send">댓글쓰기</div>
                    </div>
                </div>
            </div>
        </div>
        <div class="edit-back" v-if="editFlag" @click.self="editFlag=false">
            <div :class="{'edit-content':!isDarkMode,'edit-content-dark':isDarkMode}">
                <p :class="{'edit-header':!isDarkMode,'edit-header-dark':isDarkMode}"><img src="./Icon/edit.png" alt="">게시물 내용 수정</p>
                <div class="edit-body">
                    <textarea  cols="30" rows="10" :class="{'edit-write':!isDarkMode,'edit-write-dark':isDarkMode}" maxlength="20"
                    v-model="editComment" @keyup.enter.prevent="send"
                    ></textarea>
                </div>
                <div style="display: flex; justify-content: flex-end; padding: 0px 20px;">
                    <div class="edit-button" @click="editFeed">수정하기</div>
                </div>
            </div>
        </div>
        <div class="edit-back" v-if="deleteFlag" @click.self="deleteFlag=false">
            <div :class="{'delete-content':!isDarkMode,'delete-content-dark':isDarkMode}">
                <div :class="{'delete-body':!isDarkMode,'delete-body-dark':isDarkMode}">
                    <p><img src="./Icon/delete.png" alt="">게시물을 삭제하시겠습니까?</p>
                    <div style="display: flex; flex-direction: row;">
                        <div class="delete-false" @click="deleteFlag=false">취소하기</div>
                        <div class="delete-button" @click="deleteFeed">삭제하기</div>
                    </div>
                </div>
                
            </div>
        </div>
    </div>
</template>

<script>
import { useFeedStore } from '@/store/feedStore'
import { useAuthStore } from '@/store/authStore'
import { toast} from 'vue3-toastify'
import "vue3-toastify/dist/index.css"
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
        likeArticle: async function(){
            const feed = useFeedStore()
            await feed.likeArticle(this.itemData.articleId)
            await this.readDetail(this.idx)
            this.$emit('updateLike')
        },
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
                img.style.backgroundSize = 'contain'
                img.style.backgroundRepeat = 'no-repeat'
            } catch(err){
                this.$emit('closeDetail')
                toast("로그인이 필요한 페이지입니다.",{
                    theme : "auto",
                    "type": "error",
                    "pauseOnHover": false,
                    "position": "top-center",
                    "transition": "slide",
                    "dangerouslyHTMLString": true,
                    "autoClose": 1000,
                })
                // alert('로그인이 필요한 페이지입니다.')
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
            toast("게시글 수정이 완료되었습니다.",{
                    theme : "auto",
                    "type": "success",
                    "pauseOnHover": false,
                    "position": "top-center",
                    "transition": "slide",
                    "autoClose": 1000,
                })
            setTimeout(async ()=>{
                await this.readDetail(this.idx)
            },1000)
            this.editFlag = false
        } catch(err){
            console.log(err)
        }
      },
      deleteFeed: async function(){
        const feed = useFeedStore()
        try{
            await feed.deleteFeed(this.idx)
            toast("게시글 삭제가 완료되었습니다.",{
                    theme : "auto",
                    "type": "success",
                    "pauseOnHover": false,
                    "position": "top-center",
                    "transition": "slide",
                    "autoClose": 1000,
                })
            setTimeout(()=>{
                window.location.reload()
            },1000)
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
        isDarkMode : Boolean
    },
    watch:{
        step(){
            const img = document.getElementById('detailImg')
            img.style.background = `url(${this.imgList[this.step]})`
            img.style.backgroundSize = 'contain'
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