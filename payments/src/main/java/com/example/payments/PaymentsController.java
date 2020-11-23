package com.example.payments;

import com.example.util.RsaDecrypter;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class PaymentsController {
  private final RestTemplate restTemplate;

  public PaymentsController() {
    this.restTemplate = new RestTemplate();
    restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
  }

  @GetMapping("/")
  public String processRefunds() {
    var rsaDecryptor = new RsaDecrypter();

    /*
     * The code here is insecure because it is sending the request over HTTP. A man in the middle
     * attack is easy to execute. The warehouse service has no way to check if the public key in the
     * POST belongs to the payment service. The goal of this sample is to illustrate the concept of
     * public key encryption using JSON Web Encryption. You should use HTTPS always for everything
     * really using HTTP is a very bad idea, HTTPS everywhere even when you think HTTP is fine use
     * HTTPS.
     */
    String refundsJwe =
        restTemplate.postForObject(
            "http://localhost:8082/refunds", rsaDecryptor.getPublicKey(), String.class);
    var refundsJson = rsaDecryptor.decrypt(refundsJwe);

    System.out.println("public key: " + rsaDecryptor.getPublicKey());
    System.out.println("Refunds JWE : " + refundsJwe);
    System.out.println("Decrypted Refunds ...");
    System.out.println(refundsJson);
    return refundsJson;
  }
}
