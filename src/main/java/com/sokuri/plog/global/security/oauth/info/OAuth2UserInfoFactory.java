package com.sokuri.plog.global.security.oauth.info;

import com.sokuri.plog.domain.eums.SocialProvider;

import java.util.Map;

public class OAuth2UserInfoFactory {
	public static OAuth2UserInfo getOAuth2UserInfo(SocialProvider providerType, Map<String, Object> attributes) {
		return switch (providerType) {
			case KAKAO -> new KakaoOAuth2UserInfo(attributes);
			default -> throw new IllegalArgumentException("Invalid Provider Type.");
		};
	}
}
