package com.example.warehouse;

import com.example.util.JsonUtils;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class RefundService {

  public String generateReport() {
    List<Refund> refunds =
        List.of(
            new Refund("12345", BigDecimal.valueOf(500)),
            new Refund("6789", BigDecimal.valueOf(250)));
    return JsonUtils.toJson(refunds);
  }
}
