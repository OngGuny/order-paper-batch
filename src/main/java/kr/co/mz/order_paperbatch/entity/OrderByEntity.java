package kr.co.mz.order_paperbatch.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderByEntity {

  @Id
  private Long id;

  private String name;

  private String position;

  public OrderByEntity changeSlaveNumber(BigDecimal number) {
    String newPosition;
    if (this.position.startsWith("oldSlave")) {
      newPosition = this.position.replaceAll("oldSlave(\\d+)", "oldSlave" + number.toString());
    } else if (this.position.startsWith("Slave")) {
      newPosition = this.position.replaceAll("Slave(\\d+)", "Slave" + number.toString());
    } else {
      newPosition = this.position; // 기존 값 유지
    }
    var newOrderBy = new OrderByEntity();
    newOrderBy.name = this.name;
    newOrderBy.id = this.id;
    newOrderBy.position = newPosition;
    return newOrderBy;
  }
}
