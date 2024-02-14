<template>
  <div class="mygame-box">
    <p class="mygame-title"> 
      <img src="./Img/icon_gamerecord.png" alt="">
      Game Record
    </p>
    <div class="mygame-content">
      <div style="" class="chart">
        <canvas id="doughnut"></canvas>
      </div>
      <div class="bar-chart">
        <div>
          <span>시민 ({{ civilRange }})</span>
          <div class="bar">
            <div class="civil"></div>
          </div>
        </div>
        <div>
          <span>마피아 ({{ mafiaRange }})</span>
          <div class="bar">
            <div class="mafia"></div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Chart from 'chart.js/auto'
import { useAuthStore } from '@/store/authStore'

export default {
    name: 'UserGame',
    data(){
      return{
        chart : null,
        civilRange : "0%",
        mafiaRange : "0%",
        game : {},
      }
    },
    watch:{
        
        },
      
    methods:{
      getG : async function(){
        const auth = useAuthStore()
        console.log(this.$route.params.id)
        let value = await auth.getGame(this.$route.params.id)
        this.game = value.data
      },
      showChart : function(){
        if(this.chart !== null){
          this.chart.destroy()
        }
        let total = this.game.totalGameCnt
        let civiltotal = this.game.civilGameCnt
        let mafiatotal = this.game.mafiaGameCnt
        let civilValue = Math.round(200/civiltotal,1)   
        let mafiaValue = Math.round(200/mafiatotal,1)
        const civil = document.querySelector('.civil')
        const mafia = document.querySelector('.mafia')
        let civilFrame = null
        let mafiaFrame = null
        if(total == 0){
          civilFrame =  [
          {width : 0},
          {width : `200px`}  
        ]
          mafiaFrame = [
          {width : 0},
          {width : `200px`}  
        ]
        this.civilRange = "0%"
        this.mafiaRange = "0%"
        }
        else if(civiltotal == 0){
            civilFrame =  [
            {width : 0},
            {width : `200px`}  
          ]
            mafiaFrame = [
            {width : 0},
            {width : `${mafiaValue*this.game.mafiaWinCnt}px`}  
          ]
          this.civilRange = `0%`
          this.mafiaRange = `${Math.round(this.game.mafiaWinCnt/mafiatotal*100,1)}%`
        }
        else if(mafiatotal == 0){
          civilFrame =  [
          {width : 0},
          {width : `${civilValue*this.game.civilWinCnt}px`}  
          ]
          mafiaFrame = [
              {width : 0},
              {width : '0px'} 
          ]
          this.civilRange = `${Math.round(this.game.civilWinCnt/civiltotal*100,1)}%`
          this.mafiaRange = `0%`
        }
        else{
          civilFrame =  [
          {width : 0},
          {width : `${civilValue*this.game.civilWinCnt}px`}  
        ]
          mafiaFrame = [
          {width : 0},
          {width : `${mafiaValue*this.game.mafiaWinCnt}px`}  
        ]
        this.civilRange = `${Math.round(this.game.civilWinCnt/civiltotal*100,1)}%`
        this.mafiaRange = `${Math.round(this.game.mafiaWinCnt/mafiatotal*100,1)}%`
        }

        let option = {
          duration : 2000,
          easing : "ease",
          fill : "forwards"
        }
        civil.animate(civilFrame,option)
        mafia.animate(mafiaFrame,option)
        let data = null
        if(this.game.totalGameCnt>0){
          data = [
          { rate: "Win", count: this.game.totalWinCnt },
          { rate: "Lose", count: (this.game.totalGameCnt-this.game.totalWinCnt) },
          ]
        } else{
          data = [
          { rate: "Win", count: 100 },
          { rate: "Lose", count: 0 }, 
          ]
        }
      this.chart = new Chart(
        document.getElementById('doughnut'),{
          type: 'doughnut',
          data: {
            labels: data.map(row=> row.rate),
            datasets: [{
              label: 'count',
              data: data.map(row=> row.count),
            }]
          },
        }
      )
    }
    },
    async mounted(){
      await this.getG()
      await this.showChart()
    }

}
</script>

<style scoped src="./css/UserGame.css">

</style>