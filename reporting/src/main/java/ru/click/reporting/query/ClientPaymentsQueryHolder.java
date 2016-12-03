package ru.click.reporting.query;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import ru.click.reporting.model.ClientPayments;

import static org.springframework.util.Assert.notNull;
import static ru.click.reporting.util.IOUtils.fileToString;

@Slf4j
@Service
public class ClientPaymentsQueryHolder implements QueryHolder {

    private final JdbcOperations operations;

    private static final RowMapper<ClientPayments> rowMapper = (rs,i) -> ClientPayments
            .builder()
            .clientName(rs.getString("clientName"))
            .build();

    @Autowired
    public ClientPaymentsQueryHolder(JdbcOperations operations) {
        notNull(operations, "Jdbc Operations must not be null");
        this.operations = operations;
    }

    @Override
    public String getQuery() {
        val sql = fileToString("/sql/client-payments.sql");
        return null;
    }

}
