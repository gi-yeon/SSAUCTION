package com.ssafy.ssauction.auth.dto;

import com.ssafy.ssauction.domain.users.Users;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String userNickname;
    private String userEmail;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes,
        String nameAttributeKey, String userNickname, String userEmail) {

        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.userNickname = userNickname;
        this.userEmail = userEmail;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName,
                                     Map<String, Object> attributes) {
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .userNickname((String) attributes.get("userNickname"))
                .userEmail((String) attributes.get("userEmail"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }
    public Users toEntity() {
        return Users.builder()
                .userNickname(userNickname)
                .userEmail(userEmail)
//                .roles(Roles.GUEST)
                .build();
    }
}
