<template>
  <div>
    <div>
        <div class="banner"></div>
        <div style="height: 100000px;"></div>
    </div>
  </div>
</template>

<script>
export default {
    name: "GamePage",
    data(){
        return{
            prevScrollY : '',
        }
    },

    mounted(){
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
    },
}
</script>

<style scoped src="./GamePage.css">

</style>