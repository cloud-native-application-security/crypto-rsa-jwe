package com.example.warehouse;

import com.example.util.JsonUtils;
import com.example.util.RsaDecryptor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class WarehouseApplicationTests {

  @LocalServerPort private int port;

  private RestTemplate restTemplate = new RestTemplate();

  @Test
  void testReportGeneration() {

    var rsaDecryptor = new RsaDecryptor();

    var url = "http://localhost:" + port + "/refunds";
    var refundsJwe = restTemplate.postForObject(url, rsaDecryptor.getPublicKey(), String.class);
    var refundsJson = rsaDecryptor.decrypt(refundsJwe);

    Refund[] refunds = JsonUtils.fromJson(refundsJson, Refund[].class);
    Assertions.assertThat(refunds).hasSize(2);
  }
}
