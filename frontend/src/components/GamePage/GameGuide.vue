<template>
  <div class="game-guide-container">
    <div class="sub-container">
      <div :class="{'guide-title':!isDarkMode, 'guide-title-dark':isDarkMode}">1. 게임 소개</div>
      <div :class="{'sub-title':!isDarkMode,'sub-title-dark':isDarkMode}">
        <div :class="{'icon-guide':!isDarkMode,'icon-guide-dark':isDarkMode}"></div>
        MSG 게임의 기본 개념 설명</div>
      <div  :class="{'content':!isDarkMode,'content-dark':isDarkMode}">MSG 게임은 플레이어들이 <span class="red" >마피아</span>와 <span class="blue" >시민</span> 두 진영으로 나뉘어 상호작용하며 진행되는 리얼타임으로 즐기는 사회적 추리 게임입니다.</div>
      <br>
      <div :class="{'sub-title':!isDarkMode,'sub-title-dark':isDarkMode}">
        <div :class="{'icon-guide':!isDarkMode,'icon-guide-dark':isDarkMode}"></div>
        게임의 목적 소개</div>
        <div :class="{'content':!isDarkMode,'content-dark':isDarkMode}">게임의 목적은 자신의 진영이 <span class="red">승리</span>하는 것입니다. 마피아 진영은 소수이지만, 각자의 역할을 알고 있으며 밤에 시민을 몰래 제거해야합니다.</div>
        <div :class="{'content':!isDarkMode,'content-dark':isDarkMode}">시민들은 낮 시간에 각자 미션에 맞는 미션을 올리고 서로의 피드를 구경, 토론을 통해 마피아를 추리하고 투표로 제거해야 합니다.</div>
        <div  :class="{'content':!isDarkMode,'content-dark':isDarkMode}">각종 미션 수행과 직업별 능력들을 활용하여 마피아를 예측 혹은 시민을 제거하여 할 수 있습니다.</div>
      <br>

    </div>

    <div class="sub-container">
      <div :class="{'guide-title':!isDarkMode, 'guide-title-dark':isDarkMode}">2. 게임 시작 전 준비</div>
      <div :class="{'sub-title':!isDarkMode,'sub-title-dark':isDarkMode}">
        <div :class="{'icon-guide':!isDarkMode,'icon-guide-dark':isDarkMode}"></div>
        게임에 필요한 최소 및 최대 플레이어 수</div>
        <div :class="{'content':!isDarkMode,'content-dark':isDarkMode}">한 게임당 총 <span class="red">7명</span>의 플레이어가 필요합니다.</div>
        <div :class="{'content':!isDarkMode,'content-dark':isDarkMode}">현재 MSG 게임에는 <SPan class="red">2명</SPan>의 마피아와 <SPan class="red" >5명</SPan>의 시민으로 이루어집니다.</div>
        <br>
      <div :class="{'sub-title':!isDarkMode,'sub-title-dark':isDarkMode}">
        <div :class="{'icon-guide':!isDarkMode,'icon-guide-dark':isDarkMode}"></div>
        게임을 시작하기 위한 기본 설정 방법</div>
        <div :class="{'content':!isDarkMode,'content-dark':isDarkMode}"><span class="blue">방만들기</span> : 친구 혹은 MSG 유저들을 초대하여 게임을 즐길 수 있습니다.</div>
        <div :class="{'content':!isDarkMode,'content-dark':isDarkMode}"><span class="blue">입장하기</span> : 랜덤으로 게임을 입장하여 MSG 유저들과 <span class="red">익명</span>으로 게임을 즐길 수 있습니다.</div>
        <br>
        <div :class="{'job-content':!isDarkMode,'job-content-dark':isDarkMode}">게임 시작 조건</div>
        <div :class="{'content':!isDarkMode,'content-dark':isDarkMode}"><span class="red">오후 1시 이전</span> 7명의 플레이어가 입장이 완료되면 바로 게임시작이 가능합니다.</div>
        <div :class="{'content':!isDarkMode,'content-dark':isDarkMode}"><span class="red">오후 1시 이후</span> 플레이어 7명이 입장이 완료되면 <span class="red">다음 날 오전 8시</span>에 게임이 시작됩니다.</div>
        <div :class="{'content':!isDarkMode,'content-dark':isDarkMode}">게임 시작 전에도 방에 있는 유저들과 채팅이 가능합니다.</div>
        <div :class="{'content':!isDarkMode,'content-dark':isDarkMode}">게임 시작이 시작되면 <span class="blue">랜덤</span>으로 직업을 배정받습니다.</div>

      </div>

    <div class="sub-container">
      <div :class="{'guide-title':!isDarkMode, 'guide-title-dark':isDarkMode}">3. 게임 진행 방법</div>
      <div :class="{'sub-title':!isDarkMode,'sub-title-dark':isDarkMode}">
        <div :class="{'icon-guide':!isDarkMode,'icon-guide-dark':isDarkMode}"></div>
        게임의 주요 단계별 설명</div>
        <div :class="{'job-content':!isDarkMode,'job-content-dark':isDarkMode}"  style="display: flex; flex-direction: row; align-items: center;">
          <div class="day"></div>
          아침이 되었습니다. (08시 ~ 20시)</div>
        <div :class="{'content':!isDarkMode,'content-dark':isDarkMode}">아침이 되면 <span class="red">미션</span>을 배당받습니다. 각자 미션에 해당하는 <span class="red">미션 피드</span>를 올려주세요.</div>
        <div :class="{'content':!isDarkMode,'content-dark':isDarkMode}">미션을 수행해야 <span class="blue">마피아 지목 투표</span>가 가능합니다. 성실히 미션을 수행해주세요! </div>
        <div :class="{'content':!isDarkMode,'content-dark':isDarkMode}">미션을 수행하지 않은 사람은 <span class="red">실종</span> 처리가 돼버립니다. 더 이상 게임에 참여할 수 없어요.</div>
        <br>
        <div :class="{'job-content':!isDarkMode,'job-content-dark':isDarkMode}"  style="display: flex; flex-direction: row; align-items: center;" >
          <div class="night"></div>
          밤이 되었습니다. (20시 ~ 08시)</div>
        <div :class="{'content':!isDarkMode,'content-dark':isDarkMode}">밤이 되면 마피아 지목 투표가 마감됩니다.</div>
        <div :class="{'content':!isDarkMode,'content-dark':isDarkMode}">다음 날 제거할 <span class="blue">시민 지목 투표</span>가 가능합니다.</div>
        <div :class="{'content':!isDarkMode,'content-dark':isDarkMode}">마피아들은 <span class="red">마피아 귓속말</span>을 이용해 몰래 이야기할 수 있습니다.</div>
        <br>
        <div :class="{'job-content':!isDarkMode,'job-content-dark':isDarkMode}">투표 규칙</div>
        <div :class="{'content':!isDarkMode,'content-dark':isDarkMode}"><span class="red">최다 투표</span>를 받는 사람이 지목됩니다. 최다 득표자가 2명 이상일 경우 해당 투표는 무효입니다.</div>
      
        <br>        
      <div :class="{'sub-title':!isDarkMode,'sub-title-dark':isDarkMode}">
        <div :class="{'icon-guide':!isDarkMode,'icon-guide-dark':isDarkMode}"></div>
        각 역할별 기능</div>
        <div :class="{'job-content':!isDarkMode,'job-content-dark':isDarkMode}"  style="display: flex; flex-direction: row; align-items: center;" >
          <div class="mafia-badge"></div>
          <span class="red">마피아 직업</span></div>
        <div class="mafia-job-box">
          <div :class="{'mafia-job':!isDarkMode,'mafia-job-dark':isDarkMode}"></div>
        </div>
        <!-- <div :class="{'content':!isDarkMode,'content-dark':isDarkMode}">마피아 : 밤에 시민 한명을 지목하여 제거할 수 있습니다.</div>
        <div :class="{'content':!isDarkMode,'content-dark':isDarkMode}">훼방꾼 : 능력을 하용하면 다음날, 모든 시민에게 마피아 미션을 줍니다.(1회 한정)</div>
        <div :class="{'content':!isDarkMode,'content-dark':isDarkMode}">스파이 : 밤에 시민 한명을 지목하여, 다음 날 지목한 시민의 직업을 알아 낼 수 있습니다.(1회 한정)</div>
        <div :class="{'content':!isDarkMode,'content-dark':isDarkMode}">건달 : 밤에 시민 한명을 지목하여 다음날 지목한 시민의 투표권을 압수합니다.(1회 한정)</div> -->
        <br>
        <div :class="{'job-content':!isDarkMode,'job-content-dark':isDarkMode}" style="display: flex; flex-direction: row; align-items: center;" >
          <div class="civil-badge"></div>
          <span class="blue">시민 직업</span></div>
        <div class="civil-job-box">
          <div :class="{'civil-job':!isDarkMode,'civil-job-dark':isDarkMode}"></div>
        </div>
          <!-- <div :class="{'content':!isDarkMode,'content-dark':isDarkMode}">경찰 : 밤에 마피아로 생각되는 사람을 지목하면 마피아인지 아닌지 확인할 수 있습니다.</div>
        <div :class="{'content':!isDarkMode,'content-dark':isDarkMode}">의사 : 마피아가 지목할 것 같은 시민을 지목하면 해당 시민을 살릴 수 있습니다.</div>
        <div :class="{'content':!isDarkMode,'content-dark':isDarkMode}">정치인 : 마피아 지목 투표에서 최다 투표 수를 얻더라도 한 번 살아남을 수 있습니다.(1회 한정)</div>
        <div :class="{'content':!isDarkMode,'content-dark':isDarkMode}">군인 : 마피아에게 지목되어도 한번 살아남을 수 있습니다.(!회 한정)</div>
        <div :class="{'content':!isDarkMode,'content-dark':isDarkMode}">판사 : 투표 집계와 상관없이 판사가 찍은 사람이 마피아 지목 대상이 됩니다.(1회 한정)</div>
        <div :class="{'content':!isDarkMode,'content-dark':isDarkMode}">미치광이 : 본인의 역할이 경찰인 줄 알고, 마피아를 지목합니다. 그러나 거짓의 랜덤 결과를 제공 받습니다.</div>
        <div :class="{'content':!isDarkMode,'content-dark':isDarkMode}">기자 : 밤에 지목한 플레이어의 직업을 알아내어, 다음날 전체 공개할 수 있습니다.(1회 한정)</div>
        <div :class="{'content':!isDarkMode,'content-dark':isDarkMode}">불침번 : 밤 시간에 마피아 채팅(귓속말)을 몰래 볼 수 있습니다.</div>
        <div :class="{'content':!isDarkMode,'content-dark':isDarkMode}">자경단 : 마피아에게 지목되어 죽게 되면 마피아 중 1명의 정체를 강제로 공개시킵니다.</div> -->

    </div>

    <div class="sub-container">
      <div :class="{'guide-title':!isDarkMode, 'guide-title-dark':isDarkMode}">4. 승리 조건</div>
      <div :class="{'sub-title':!isDarkMode,'sub-title-dark':isDarkMode}">
        <div :class="{'icon-guide':!isDarkMode,'icon-guide-dark':isDarkMode}"></div>
        마피아와 시민 진영의 승리 조건</div>
        <div :class="{'content':!isDarkMode,'content-dark':isDarkMode}"> 마피아와 시민의 수가 같아지면 <span class="red">마피아의 승리</span>입니다.</div>
        <div :class="{'content':!isDarkMode,'content-dark':isDarkMode}"> 마피아 수가 0명이 되면 <span class="blue">시민의 승리</span>입니다.</div>
        <br>
      <div :class="{'sub-title':!isDarkMode,'sub-title-dark':isDarkMode}">
        <div :class="{'icon-guide':!isDarkMode,'icon-guide-dark':isDarkMode}"></div>
        게임 종료 시점</div>
        <div :class="{'content':!isDarkMode,'content-dark':isDarkMode}">"게임이 종료되었습니다."라는 화면이 나오면 게임이 종료됩니다.</div>
        <div :class="{'content':!isDarkMode,'content-dark':isDarkMode}">게임에 참여한 유저 목록과 직업들이 공개됩니다.</div>
        <div :class="{'content':!isDarkMode,'content-dark':isDarkMode}">죽었던 유저들도 다시 채팅이 가능해집니다. </div>

    </div>

    <div class="sub-container">

      <div :class="{'guide-title':!isDarkMode, 'guide-title-dark':isDarkMode}">5. 커뮤니티 규칙 및 행동 지침</div>
        <div :class="{'sub-title':!isDarkMode,'sub-title-dark':isDarkMode}">
          <div :class="{'icon-guide':!isDarkMode,'icon-guide-dark':isDarkMode}"></div>
          플레이어 간의 소통 규칙</div>
        <div  :class="{'content':!isDarkMode,'content-dark':isDarkMode}">1. 모든 플레이어는 서로 존중하며 긍정적인 소통을 유지해야 합니다.</div>
        <div  :class="{'content':!isDarkMode,'content-dark':isDarkMode}">2. 차별적, 성적 내용이나 폭력적인 발언은 어떤 상황에서도 허용되지 않습니다.</div>
        <br>
        <div :class="{'sub-title':!isDarkMode,'sub-title-dark':isDarkMode}">
          <div :class="{'icon-guide':!isDarkMode,'icon-guide-dark':isDarkMode}"></div>
          게임 내외에서의 예의 및 행동 지침</div>
        <div  :class="{'content':!isDarkMode,'content-dark':isDarkMode}">1. 게임 내외에서 부적절한 언어 사용, 개인 공격 또는 괴롭힘은 엄격히 금지됩니다.</div>
        <div  :class="{'content':!isDarkMode,'content-dark':isDarkMode}">2. 게임의 공정성을 해치는 행위(부정 행위, 사기, 스포일러 공유 등)는 금지됩니다.</div>
        <div  :class="{'content':!isDarkMode,'content-dark':isDarkMode}">3. 모든 플레이어는 커뮤니티의 규칙을 준수하며, 위반 시 제재를 받을 수 있습니다.</div>
    <br>
    <br>
      </div>
  </div>
</template>

<script>
export default {
    name:'GameGuide',
    props:{
      isDarkMode : Boolean
    }
}
</script>

<style scoped src="./css/GameGuide.css">

</style>