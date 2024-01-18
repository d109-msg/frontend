<template>
    <!-- 메인 페이지 -->
    <div class="main">
      <div class="event-page">
          <p class="main-title">이달의 Event</p>
          <!-- 이벤트 슬라이드 -->

              <Carousel class="event-carousel" :autoplay="2000"  :itemsToShow="2" :wrap-around="true" :pauseAutoplayOnHover="true" :transition="500">
                  <Slide v-for="slide in  slides" :key="slide">
                      <img :src="slide" alt="" class="event-contentbox">
                  </Slide>
                  <Pagination/>
              </Carousel>

          <!-- <div class="event-contentbox">
              <div class="event-content"></div>
              <div class="event-content"></div>
              <div class="event-content"></div>
          </div> -->



      </div>
      <div class="feed-page">
          <div class="comb-page">
              <p class="main-title">Comb</p>
              <div class="feed-contentbox">
                  <div class="honeys-profiles">
                      <div class="feed-user-img"></div>
                      <div class="feed-user-name">user1</div>
                  </div>
                  <div class="tag-style" >
                      <div class="category-list"> 카테고리 > 편의점 &nbsp;#라면 &nbsp;# 저녁추천</div>
                      <img src="@/assets/css/HomepageImg/icon/more_icon.png" alt="" type="button" style="cursor: pointer;">
                  </div>
                  <img src="@/assets/css/HomepageImg/feed_sample.png" class="feed-img" alt="">
                  <div class="down-btnbox">
                      <img src="@/assets/css/HomepageImg/icon/like_icon.png" class="down-btn" alt="">
                      <img src="@/assets/css/HomepageImg/icon/comment_icon.png" class="down-btn" alt="">
                  </div>
                  <div class="liked-box">
                      <div class="honeys-img"></div>
                      <div class="honeys-name">user3</div>
                      <span >&nbsp;님 외 3명이 이 게시글을 좋아합니다.</span>
                  </div>
                  <div class="comment-box">
                      <div class="comment-contentbox">
                          <div class="honeys-name">user3</div>
                          <span style="color: #6C6767;">&nbsp;오늘은 내가 요리사~ 짜파구리 드시고 가세요</span>
                      </div>
                      <div class="comment-contentbox">
                          <div class="honeys-name">user2</div>
                          <span style="color: #6C6767;">&nbsp;맛있겠다ㅏㅏㅏㅏㅏ</span>
                      </div>
                  </div>
                  <div class="feed-datebox">
                      <p class="feed-date">2023.01.07 - 약 1개월 전 게시글</p>
                  </div>
              </div>
          </div>
          <!-- 실시간 랭킹 순위 페이지 -->
          <div class="rank-page">
              <p class="main-title">실시간 콤브 순위</p>
              <div class="rank-content-box">
                  <div class="rank-content"><span class="rank">1.</span>&nbsp;#불닭막국수</div>
                  <div class="rank-content"><span class="rank">2.</span>&nbsp;#한강나들이</div>
                  <div class="rank-content"><span class="rank">3.</span>&nbsp;#서브웨이</div>
                  <div class="rank-content"><span class="rank">4.</span>&nbsp;#호치민막창국수</div>
                  <div class="rank-content"><span class="rank">5.</span>&nbsp;#오감자치즈후라이</div>
              </div>
          </div>
      </div>

  </div>
</template>

<script>
import 'vue3-carousel/dist/carousel.css'
import { Carousel, Slide } from 'vue3-carousel'
import 'vue3-carousel/dist/carousel.css'
import banner1 from '@/assets/css/HomepageImg/banner1.png'
import banner2 from '@/assets/css/HomepageImg/banner2.png'
import banner3 from '@/assets/css/HomepageImg/banner3.png'

export default {
    name : 'MainPage',
    data(){
        return {
            slides: [banner1,banner2,banner3]
        }
    },
    components :{
        Carousel, Slide, 
    }
}
</script>

<style src="./MainPage.css">

</style>