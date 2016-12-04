package ru.click.reporting.query;

import org.springframework.stereotype.Service;
import ru.click.reporting.model.ClientPayments;

import static ru.click.reporting.util.IOUtils.fileToString;

@Service
public class ClientPaymentsQueryHolder implements QueryHolder<ClientPayments> {

    @Override
    public String getQuery() {
        return fileToString("/sql/client-payments.sql");
    }

}
