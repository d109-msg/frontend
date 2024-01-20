package com.ssafy.msg.user.util;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class PasswordUtil {
	private static final char[] rndAllCharacters = new char[]{
	        // 숫자
	        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
	        // 대문자
	        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
	        'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
	        // 소문자
	        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
	        'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
	        // 특수 문자
	        '@', '$', '!', '%', '*', '?', '&'
	};

	private static final char[] numberCharacters = new char[] {
	        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
	};

	private static final char[] uppercaseCharacters = new char[] {
	        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
	        'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
	};

	private static final char[] lowercaseCharacters = new char[] {
	        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
	        'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
	};

	private static final char[] specialSymbolCharacters = new char[] {
	        '@', '$', '!', '%', '*', '?', '&'
	};
	
	public String generateRandomPassword() {
	    SecureRandom random = new SecureRandom();
        int length = random.nextInt(13) + 8; // 8에서 20 사이의 랜덤 길이
	    StringBuilder stringBuilder = new StringBuilder();
	    
	    List<Character> passwordCharacters = new ArrayList<>();

	    int numberCharactersLength = numberCharacters.length;
	    passwordCharacters.add(numberCharacters[random.nextInt(numberCharactersLength)]);

	    int uppercaseCharactersLength = uppercaseCharacters.length;
	    passwordCharacters.add(uppercaseCharacters[random.nextInt(uppercaseCharactersLength)]);

	    int lowercaseCharactersLength = lowercaseCharacters.length;
	    passwordCharacters.add(lowercaseCharacters[random.nextInt(lowercaseCharactersLength)]);

	    int specialSymbolCharactersLength = specialSymbolCharacters.length;
	    passwordCharacters.add(specialSymbolCharacters[random.nextInt(specialSymbolCharactersLength)]);

	    int rndAllCharactersLength = rndAllCharacters.length;
	    for (int i = 0; i < length-4; i++) {
	        passwordCharacters.add(rndAllCharacters[random.nextInt(rndAllCharactersLength)]);
	    }

	    Collections.shuffle(passwordCharacters);

	    for (Character character : passwordCharacters) {
	        stringBuilder.append(character);
	    }

	    return stringBuilder.toString();
	}
}
