package ru.click.reporting;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import ru.click.reporting.model.ClientPayments;
import ru.click.reporting.query.QueryHolder;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.util.Assert.notNull;

@Service
public class ClientPaymentsService {

    private final JdbcOperations operations;

    private final QueryHolder clientPaymentsQueryHolder;

    private static final RowMapper<ClientPayments> rowMapper = (rs, i) -> ClientPayments
            .builder()
            .clientName(rs.getString("client_name"))
            .paymentDate(rs.getDate("payment_date"))
            .productName(rs.getString("product_name"))
            .sumPayments(rs.getLong("sum_payments"))
            .build();

    @Autowired
    public ClientPaymentsService(JdbcOperations operations, QueryHolder clientPaymentsQueryHolder) {
        notNull(operations, "Jdbc Operations must not be null");
        notNull(clientPaymentsQueryHolder, "Query holder must not be null");
        this.operations = operations;
        this.clientPaymentsQueryHolder = clientPaymentsQueryHolder;
    }

    public List<ClientPayments> clientPayments(LocalDate from, LocalDate to, Long clientId, Long trainerId) {
        val sql = clientPaymentsQueryHolder.getQuery();
        return operations.query(sql, rowMapper, from, to);
    }
}
