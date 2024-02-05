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

export default {
    name: 'UserGame',
    data(){
      return{
        chart : Object,
        civil : 10,
        mafia : 20,
        civilRange : "0%",
        mafiaRange : "0%",
      }
    },
    mounted(){
      const total = this.civil+this.mafia
      const value = Math.round(200/total,1)
      const civil = document.querySelector('.civil')
      const mafia = document.querySelector('.mafia')
      let civilFrame =  [
        {width : 0},
        {width : `${value*this.civil}px`}  
      ]
      let mafiaFrame = [
        {width : 0},
        {width : `${value*this.mafia}px`}  
      ]
      this.civilRange = `${Math.round(this.civil/total*100,1)}%`
      this.mafiaRange = `${Math.round(this.mafia/total*100,1)}%`

      let option = {
        duration : 2000,
        easing : "ease",
        fill : "forwards"
      }
      civil.animate(civilFrame,option)
      mafia.animate(mafiaFrame,option)

      const data = [
    { rate: "Win", count: 10 },
    { rate: "Lose", count: 20 },
    ]

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
  },
}
</script>

<style scoped src="./css/MyGame.css">

</style>