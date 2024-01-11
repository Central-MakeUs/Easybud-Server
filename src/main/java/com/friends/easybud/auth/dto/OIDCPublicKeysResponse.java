package com.friends.easybud.auth.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OIDCPublicKeysResponse {

    List<OIDCPublicKey> keys;

}