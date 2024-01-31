<template>
  <div class="container">
    <NavBarVue :page="page"/>
    <router-view @page-change="change"></router-view>
  </div>
</template>

<script>
import NavBarVue from '@/components/NavBar/NavBar.vue'
import MainPage from '../MainPage/MainPage.vue';

export default {
  name:"HomePage",
  data(){
    return{
      page : ''
    }
  },
  methods:{
    change : function(value){
      this.page = value
    }
  },
  components:{
      NavBarVue,
      MainPage
  },
 
}
</script>


<style scoped src="./Homepage.css">
  
</style>
