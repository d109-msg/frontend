<template>
    <div class="nav-container" >
        <div style="width:58px; height: 58px; border: 1px dotted; margin-left: 50px;">

        </div>
        <div class="right-bar">
            <div class="tag" @click="tagClick" id="HOME">HOME</div>
            <div class="tag" @click="tagClick" id="GAME">GAME</div>
            <div class="tag" @click="tagClick" id="MESSAGE">MESSAGE</div>
            <div class="tag" @click="tagClick" id="MYPAGE">MYPAGE</div>
            <div class="search-container">
                <input type="text" class="search-bar" placeholder="검색">
                <div class="search-icon"></div>
            </div>
        </div>
    </div>
</template>

<script>

export default {    
    name : 'NavBar',
    data(){
        return{
            selectedTag : Object,
        }
    },
    methods : {
        tagClick : function(event){
            this.selectedTag.classList.toggle('tag-click')
            const current = event.currentTarget
            current.classList.add('tag-click')
            this.selectedTag = current

            //라우터 추가 함수 여기에 넣으면 됩니다.
        },
        navScroll : function(){
            
        }
    },
    mounted() {
        this.selectedTag = document.querySelector('#HOME')
        this.selectedTag.classList.add('tag-click')

    },
}


</script>



<style scoped src="./NavBar.css">

</style>