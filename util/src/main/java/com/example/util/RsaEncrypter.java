package com.example.util;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.RSAEncrypter;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.RSAKey;
import java.text.ParseException;

public class RsaEncrypter {

  private final RSAKey key;

  public RsaEncrypter(String publicKeyJwk) {
    try {
      this.key = JWK.parse(publicKeyJwk).toRSAKey();
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
  }

  public String encrypt(String data) {
    try {
      JWEHeader header = new JWEHeader(JWEAlgorithm.RSA_OAEP_256, EncryptionMethod.A256GCM);
      Payload payload = new Payload(data);
      JWEObject jweObject = new JWEObject(header, payload);
      jweObject.encrypt(new RSAEncrypter(key.toRSAPublicKey()));
      return jweObject.serialize();
    } catch (JOSEException e) {
      throw new RuntimeException(e);
    }
  }
}
