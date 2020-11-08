package com.example.util;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import java.text.ParseException;

public class RsaDecryptor {

  private final RSAKey key;

  public RsaDecryptor() {
    try {
      this.key = new RSAKeyGenerator(4096).generate();
    } catch (JOSEException e) {
      throw new RuntimeException(e);
    }
  }

  public String getPublicKey() {
    return key.toPublicJWK().toJSONString();
  }

  public String decrypt(String jwe) {
    try {
      JWEObject jweObject = JWEObject.parse(jwe);
      jweObject.decrypt(new RSADecrypter(key));
      return jweObject.getPayload().toString();
    } catch (ParseException | JOSEException e) {
      throw new RuntimeException(e);
    }
  }
}
