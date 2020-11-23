package com.example.warehouse;

import com.example.util.RsaEncrypter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class RefundController {

  private final RefundService refundService;

  RefundController(RefundService refundService) {
    this.refundService = refundService;
  }

  @PostMapping("/refunds")
  String generateReport(@RequestBody String rsaPublicKeyJwk) {
    /*
     * The code here is insecure because it has no way to validate that the public key in the JWK
     * belongs to the sender of the request. A man in the middle attack is easy to execute. The goal
     * of this sample is to illustrate the concept of public key encryption using JSON Web
     * Encryption. You should use HTTPS always for everything really using HTTP is a very bad idea,
     * HTTPS everywhere even when you think HTTP is fine use HTTPS.
     */
    var rsaEncryptor = new RsaEncrypter(rsaPublicKeyJwk);
    return rsaEncryptor.encrypt(refundService.generateReport());
  }
}
