//package com.pmc.market.security.oauth;
//
//import org.springframework.security.oauth2.client.registration.ClientRegistration;
//import org.springframework.security.oauth2.core.AuthorizationGrantType;
//import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
//
//public enum CustomOAuthProvider {
//    KAKAO {
//        @Override
//        public ClientRegistration.Builder getBuilder() {
//            return getBuilder("kakao", ClientAuthenticationMethod.POST)
//                    .scope("profile", "talk_message")
//                    .authorizationUri("https://kauth.kakao.com/oauth/authorize")
//                    .tokenUri("https://kauth.kakao.com/oauth/token")
//                    .userInfoUri("https://kapi.kakao.com/v2/user/me")
//                    .clientId("c1ba1f09ecd07ec2d41685d595cf8f15")
//                    .clientSecret("hZZUJawJxIHACvlIOG8yQFHWMUR9D2FB")
//                    .userNameAttributeName("id")
//                    .clientName("Kakao");
//        }
//    };
//
//    private static final String DEFAULT_LOGIN_REDIRECT_URL = "http://localhost:8086/login/oauth2/code/kakao";
//
//    protected final ClientRegistration.Builder getBuilder(String registrationId,
//                                                          ClientAuthenticationMethod method) {
//
//        ClientRegistration.Builder builder = ClientRegistration.withRegistrationId(registrationId);
//        builder.clientAuthenticationMethod(method);
//        builder.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE);
//        builder.redirectUriTemplate(CustomOAuthProvider.DEFAULT_LOGIN_REDIRECT_URL);
//
//        return builder;
//    }
//
//    public abstract ClientRegistration.Builder getBuilder();
//}
