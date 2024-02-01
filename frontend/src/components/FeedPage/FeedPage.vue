<template>
    <div class="feed-container">
        <div class="first-col">
            <div class="feed-create">
                <div class="create-container">
                    <img class="create-img" :src="userImage">
                    <div class="create-comment">What are you thinking?</div>
                </div>
                <div class="create-btn-box">
                    <div class="create-btn"
                    @click="create=true"
                    >Create</div>
                </div>
            </div>
            <template  v-for="(feed,idx) in feedList" :key="idx" >
                <div v-if="idx%2===1" :id="idx">
                    <Feed v-if="idx%2===1" :item="feed"
                    ></Feed>
                </div>
            </template>
        </div>
        <div class="second-col">
            <template  v-for="(feed,idx) in feedList" :key="idx" >
                <div :id="idx" v-if="idx%2===0">
                    <Feed v-if="idx%2===0" :item="feed"
                    ></Feed>                    
                </div>
            </template>
            <FeedCreate v-if="create" @close="complete"/>
        </div>
        
    </div>
</template>

<script>
    import Feed from './Feed.vue';
    import FeedCreate from './FeedCreate.vue';
    import { useFeedStore } from '@/store/feedStore';
    import { useAuthStore } from '@/store/authStore';

    export default {
        name: "FeedPage",

        data(){
            return{
                col : 2,
                colSapce : 16,
                arr : [1,2,3],
                create : false,
                baseUrl : 'http://localhost:8080/article/feed',
                feedList : [],
                detailFlag : false,
                last : {},
                io : {},
                item: {},
                userImage: "",
            }
        },  

        methods: {
            getUser : async function(){
                const auth = useAuthStore()
                try{
                    let value = await auth.getUser()
                    this.userImage = value.data.imageUrl
                }catch(err){
                    console.log(err)
                }
            },
            readFeed : async function(){
                try{
                    await this.axiosRead()
                    //첫 요청에서의 가시성 관찰을 위한 if문 5개 단위로 게시물이 오기 때문에 마지막 요소를 관찰하기 위한 로직
                    if(this.feedList.length%5==0 && this.feedList.length !=0){
                        this.last = document.getElementById(`${this.feedList.length-1}`)
                        //저장 된 feedList의 길이 -1 이 곧 id
                        this.io.observe(this.last)
                    }
                } catch(err){
                    // accessToken의 기한 만료로 인한 error일시
                    console.log(err)
                    try{
                        const authStore = useAuthStore()
                        await authStore.useRefresh()
                        // 토큰 refresh 시켜서 다시 요청
                        await this.axiosRead()
                        if(this.feedList.length%5==0 && this.feedList.length !=0){
                            this.last = document.getElementById(`${this.feedList.length-1}`)
                            this.io.observe(this.last)
                        }
                    } catch(err){
                        console.log(err)
                    }
                }
            },
            axiosRead : async function(){
                const feedStore = useFeedStore()
                let value = await feedStore.readFeed(this.baseUrl)
                this.baseUrl = value.data.nextUrl
                value.data.articleDetailDtos.forEach(item=>{
                    this.feedList.push(item)
                    })
                },

            callBack : function(items,io){
                items.forEach(async item=>{
                    if(item.isIntersecting){
                        io.unobserve(item.target)
                        try{
                            await this.axiosRead()
                            // 현 페이지에서 5개씩 받아옴, 만약 5개씩 받아온게 꽉 찼더라면 한번 더 요청해서 그 다음 요청 갈 수 있게
                            // 마지막 요소를 intersectionObserver로 가시성 관찰
                            if(this.feedList.length%5==0 && this.feedList.length !=0){
                                this.last = document.getElementById(`${this.feedList.length-1}`)
                                this.io.observe(this.last)
                            }
                        } catch(err){
                            try{
                                const authStore = useAuthStore()
                                await authStore.useRefresh()
                                await this.axiosRead()
                            } catch(err){
                                console.log(err)
                            }
                        }
                    }
                })
            },
            complete : function(value){
                this.create = false
                if(value == 1){
                    window.location.reload()
                    //emit으로 글 작성 완료되었다는 이벤트 왔을 시 현 화면 새로고침
                }
            },
            
        },
        components: {
            Feed,
            FeedCreate,
        },
        mounted(){
            this.getUser()
            this.readFeed()
            this.io = new IntersectionObserver(this.callBack,{ threshold : 0.7})
            // 요소의 가시성이 70% 정도 관찰되었을 때, 콜백함수 실행
        },
        created() {
        },
    }
</script>

<style scoped src="./css/FeedPage.css">
.container{
    width: 1000px;
}
</style>