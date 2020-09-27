package com.example.warehouse;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WarehouseApplication implements CommandLineRunner {

  @Autowired private RefundGenerationService refundGenerationService;

  @Value("${refundsPath:data/refunds.aes}")
  private String refundsPath;

  @Value("${refunds.key}")
  private String key;

  @Override
  public void run(String... args) throws Exception {
    var refunds =
        List.of(
            new Refund("5555555555554444", BigDecimal.valueOf(500)),
            new Refund("4012888888881881", BigDecimal.valueOf(250)));

    refundGenerationService.generateReport(Path.of(refundsPath), refunds, key);
  }

  public static void main(String[] args) {
    SpringApplication.run(WarehouseApplication.class, args);
  }
}
