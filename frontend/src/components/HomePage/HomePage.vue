<template>
  <div class="container">
    <NavBarVue/>
    <MainPage/>
  </div>
</template>

<script>
import NavBarVue from '@/components/NavBar/NavBar.vue'
import MainPage from '../MainPage/MainPage.vue';

export default {
  name:"HomePage",
  components:{
      NavBarVue,
      MainPage
  },
}
</script>


<style scoped src="./Homepage.css">
  
</style>
