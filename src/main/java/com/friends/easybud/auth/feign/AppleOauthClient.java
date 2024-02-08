package com.friends.easybud.auth.feign;

import com.friends.easybud.auth.dto.OIDCPublicKeysResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "appleOauthClient", url = "https://appleid.apple.com")
public interface AppleOauthClient {

    @Cacheable(cacheNames = "AppleOICD", cacheManager = "oidcCacheManager")
    @GetMapping("/auth/keys")
    OIDCPublicKeysResponse getAppleOIDCOpenKeys();

}
