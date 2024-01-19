<template>
    <div class="nav-container" >
        <div style="width:58px; height: 58px; border: 1px dotted; margin-left: 50px;"></div>
        <div class="right-bar">
            <div class="tag" @click="tagClick('/')" id="/">HOME</div>
            <div class="tag" @click="tagClick('/game')" id="/game">GAME</div>
            <div class="tag" @click="tagClick('/message')" id="/message">MESSAGE</div>
            <div class="tag" @click="tagClick('/mypage')" id="/mypage">MYPAGE</div>
            <div class="search-container">
                <input type="text" class="search-bar" placeholder="검색" maxlength="30">
                <div class="search-icon"></div>
            </div>
        </div>
    </div>
</template>

<script>
import router from '@/router'

export default {    
    name : 'NavBar',
    data(){
        return{
            selectedTag : Object,
            prevScrollY : '',
        }
    },
    methods : {
        tagClick : function(place){
            this.selectedTag.classList.toggle('tag-click')
            const current = document.getElementById(place)
            current.classList.add('tag-click')
            this.selectedTag = current
            //라우터 추가 함수 여기에 넣으면 됩니다.
            router.push({path:place})
        },

    },
    mounted() {
        const basicUrl = window.location.pathname
        this.selectedTag = document.getElementById(basicUrl)
        // if(this.selectedTag==null){ //Home에서 잘못된 URI로 접근시, ex. router-view가 적용 되지 않은 local.../main 의 주소
        //     router.push({name:'main'})
        //     setTimeout(()=>{
        //         window.location.reload() // router push 이후 새로고침이 안되서 강제로 해줌
        //     },500)
        // }
        this.selectedTag.classList.add('tag-click')
        let navContainer = document.querySelector('.nav-container')
        this.prevScrollY = window.scrollY
        window.addEventListener('scroll',()=>{
            let nowScrollY = window.scrollY
            if(this.prevScrollY < nowScrollY){
                navContainer.classList.remove('nav-up-event')
                navContainer.classList.add('nav-down-event')
            }else{
                navContainer.classList.add('nav-up-event')
                navContainer.classList.remove('nav-down-event')
            }
            this.prevScrollY = nowScrollY

        })
    },
}


</script>



<style scoped src="./NavBar.css">

</style>