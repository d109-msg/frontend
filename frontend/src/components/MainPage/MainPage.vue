<template>
    <!-- 메인 페이지 -->
    <div>
        <div class="banner"></div>
        <div class="main-container">
            <!-- 추후 컴포넌트로 분리 가능(피드) -->
            <FeedPage/>

            <!-- 추후 컴포넌트로 분리 가능(작은 프로필) -->
            <MiniProfile/>

        </div>
    </div>
</template>

<script>
import { usePageStore } from '@/store/pageStore'
import FeedPage from '../FeedPage/FeedPage.vue'
import MiniProfile from '../MiniProfile/MiniProfile.vue'

export default {
    name : 'MainPage',
    data(){
        return {
            prevScrollY : '',
        }
    },
    components :{
        MiniProfile,
        FeedPage,
    },

    mounted(){
        this.emitter.emit('pageChange',0)
        // this.$emit('pageChange',0)

        // 스크롤 이벤트에 따른 배너 margin 조정
        let banner = document.querySelector('.banner')
        this.prevScrollY = window.scrollY
        window.addEventListener('scroll',()=>{
            let nowScrollY = window.scrollY
            if(this.prevScrollY < nowScrollY){
                banner.classList.remove('banner-up-event')
                banner.classList.add('banner-down-event')
            }else{
                banner.classList.add('banner-up-event')
                banner.classList.remove('banner-down-event')
            }
            this.prevScrollY = nowScrollY
        })



    }
}
</script>

<style src="./MainPage.css">

</style>