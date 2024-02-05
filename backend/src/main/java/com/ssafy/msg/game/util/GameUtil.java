package com.ssafy.msg.game.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class GameUtil {
    private static final Map<String, String> roles = new HashMap<>();
    private static final List<String> civilRoles = new ArrayList<>();
    private static final List<String> mafiaRoles = new ArrayList<>();
    private static final Map<String, Boolean> abilityRoles = new HashMap<>();

    static {
        //직업의 세력
        //시민 직업
        roles.put("경찰", "시민");
        roles.put("정치인", "시민");
        roles.put("군인", "시민");
        roles.put("판사", "시민");
        roles.put("미치광이", "시민");
        roles.put("불침번", "시민");
        roles.put("청소부", "시민");
        roles.put("기자", "시민");
        roles.put("의사", "시민");
//        roles.put("사냥꾼", "시민");
//        roles.put("본인이 죽으면 마피아 공개시키기", "시민");

        //마피아 직업
        roles.put("훼방꾼", "마피아");
        roles.put("변장술사", "마피아");
        roles.put("스파이", "마피아");
        roles.put("건달", "마피아");
        roles.put("마피아", "마피아");

        //직업의 ability 선택 필요 여부
        abilityRoles.put("스파이", true);
        abilityRoles.put("경찰", true);

        //랜덤 출력을 위한 list
        //마피아
        mafiaRoles.add("훼방꾼");
        mafiaRoles.add("변장술사");
        mafiaRoles.add("스파이");
        mafiaRoles.add("건달");
        mafiaRoles.add("마피아");

        //시민
        civilRoles.add("경찰");
        civilRoles.add("정치인");
        civilRoles.add("군인");
        civilRoles.add("판사");
        civilRoles.add("미치광이");
        civilRoles.add("불침번");
        civilRoles.add("청소부");
        civilRoles.add("기자");
        civilRoles.add("의사");
    }

    /**
     * 직업을 입력하면 해당 직업의 세력(마피아, 시민)을 리턴
     * @param roleName 세력을 알고싶은 직업을 입력합니다.
     * @return 입력받은 직업의 세력을 리턴
     */
    public static String getRoleType(String roleName) {
        return roles.getOrDefault(roleName, "알 수 없는 직업");
    }

    /**
     * 해당 직업이 투표 이외의 선택이 필요한 직업인지 여부를 리턴합니다.
     * @param roleName 능력을 알고싶은 직업 입력
     * @return 해당 직업의 능력이 따로 선택이 필요하다면 true 아니라면 false 를 리턴
     */
    public static boolean isChoosingAbility(String roleName) {
        return abilityRoles.getOrDefault(roleName, false);
    }

    /**
     * 입력된 숫자 만큼 랜덤한 시민의 직업을 리턴
     * @param num 몇 개의 직업을 리턴할지 입력
     * @return 랜덤으로 num 만큼의 랜덤 직업 출력
     */
    public static List<String> getCivilRoles(int num){
        Collections.shuffle(civilRoles);
        List<String> result = new ArrayList<>();

        while(num-->0) {
            result.add(civilRoles.get(num));
        }

        return result;
    }

    /**
     * 입력된 숫자 만큼 랜덤한 마피아의 직업을 리턴
     * @param num 몇 개의 직업을 리턴할지 입력
     * @return 랜덤으로 num 만큼의 랜덤 직업 출력
     */
    public static List<String> getMafiaRoles(int num){
        Collections.shuffle(mafiaRoles);
        List<String> result = new ArrayList<>();

        while(num-->0) {
            result.add(mafiaRoles.get(num));
        }

        return result;
    }
}
