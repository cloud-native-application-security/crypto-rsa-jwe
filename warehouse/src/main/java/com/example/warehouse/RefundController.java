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
  String generateReport(@RequestBody String peerJwk) {
    var rsaEncryptor = new RsaEncrypter(peerJwk);
    return rsaEncryptor.encrypt(refundService.generateReport());
  }
}
