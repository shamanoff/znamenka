package ru.click.reporting.query;

import static ru.click.reporting.util.IOUtils.fileToString;

public class ClientPaymentsQueryHolder implements QueryHolder {

    @Override
    public String getQuery() {
        return fileToString("/sql/client-payments.sql");
    }

}
