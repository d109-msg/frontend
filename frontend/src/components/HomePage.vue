<template>
  <div class="container">
      <NavBarVue></NavBarVue>
      <MainPageVue></MainPageVue>
  </div>
</template>

<script>
import MainPageVue from '@/components/MainPage/MainPage.vue'
import NavBarVue from '@/components/NavBar/NavBar.vue'

export default {
  name:"HomePage",
  components:{
      NavBarVue,
      MainPageVue,
  }
 ,
}
</script>


<style scoped src="../assets/css/Homepage.css">

</style>
