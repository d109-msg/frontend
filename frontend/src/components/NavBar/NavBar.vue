<template>
        <div class="sub-main">
            <!-- 로고 -->
            <div class="logo">HONEY COMB</div>
            <!-- 비로그인 시 : 로그인 폼 영역 -->
            <div class="login-box">
              <div class="login-btn" @click="$router.push({path:'/login'})">로그인</div>
              <div class="signup-btn" @click="$router.push({path:'/signup'})">회원가입</div>
            </div>

            <!-- 로그인 시 : 내 프로필 -->
            <!-- <div class="myprofile">
                <div class="my-img"></div>
                <div class="my-name">mango_mom</div>
            </div> -->

            <!-- 검색바 -->
            <div class="search-style">
                <input type="text" class="search-input">
                <div class="search-btn">
                    <img src="@/assets/css/HomepageImg/icon/search_white_icon.png" class="search-icon">
                </div>
            </div>

            <!-- 페이지메뉴 -->
            <div class="menu-list">
                <img src="@/assets/css/HomepageImg/icon/home_active_icon.png" class="menu-icon" alt="">
                <div class="menu-active-name">Home</div>
            </div>

            <div class="menu-list">
                <img src="@/assets/css/HomepageImg/icon/menu_grey_icon.png" class="menu-icon" alt="">
                <div class="menu-name">오늘의 꿀조합</div>
            </div>

            <div class="menu-list">
                <img src="@/assets/css/HomepageImg/icon/message_grey_icon.png" class="menu-icon" alt="">
                <div class="menu-name">대화하기</div>
            </div>

            <div class="menu-list">
                <img src="@/assets/css/HomepageImg/icon/category_grey_icon.png" class="menu-icon" alt="">
                <div class="menu-name">카테고리</div>
            </div>

            <div class="menu-list">
                <img src="@/assets/css/HomepageImg/icon/user_grey_icon.png" class="menu-icon" alt="">
                <div class="menu-name">마이페이지</div>
            </div>


            <!-- 이웃 프로필 -->
            <div class="honeys-title">
                <img src="@/assets/css/HomepageImg/icon/honeys_grey_icon.png" class="honeys-icon" alt="">
                <div class="menu-name">My honeys</div>
            </div>
            <div class="honeys-profiles">
                <div class="honeys-img"></div>
                <div class="honeys-name">user1</div>
            </div>
            <div class="honeys-profiles">
                <div class="honeys-img"></div>
                <div class="honeys-name">user2</div>
            </div>
            <div class="honeys-profiles">
                <div class="honeys-img"></div>
                <div class="honeys-name">user3</div>
            </div>
        </div>
</template>

<script>
export default {
    name : 'NavBar'
}
</script>

<style src="./NavBar.css">

</style>