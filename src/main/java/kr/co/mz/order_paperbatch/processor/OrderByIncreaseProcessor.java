package kr.co.mz.order_paperbatch.processor;

import jakarta.annotation.Nullable;
import java.math.BigDecimal;
import kr.co.mz.order_paperbatch.entity.OrderByEntity;
import org.springframework.batch.item.ItemProcessor;

public class OrderByIncreaseProcessor implements ItemProcessor<OrderByEntity, OrderByEntity> {

  public static final BigDecimal NEW_NUMBER = new BigDecimal("77745");

  @Nullable
  @Override
  public OrderByEntity process(OrderByEntity item) throws Exception {
    return item.changeSlaveNumber(NEW_NUMBER);
  }
}
