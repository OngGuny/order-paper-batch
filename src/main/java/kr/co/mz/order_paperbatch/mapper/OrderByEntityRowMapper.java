package kr.co.mz.order_paperbatch.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import kr.co.mz.order_paperbatch.entity.OrderByEntity;
import org.springframework.jdbc.core.RowMapper;

public class OrderByEntityRowMapper implements RowMapper<OrderByEntity> {

  @Override
  public OrderByEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
    return OrderByEntity
        .builder()
        .id(rs.getLong("id"))
        .position(rs.getString("position"))
        .build();
  }
}
