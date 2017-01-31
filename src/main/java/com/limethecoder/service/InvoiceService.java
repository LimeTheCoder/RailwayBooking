package com.limethecoder.service;


import com.limethecoder.entity.Invoice;

import java.util.List;
import java.util.Optional;

public interface InvoiceService {
    Optional<Invoice> findById(long id);
    List<Invoice> findAllByPassenger(String email);
    List<Invoice> findAllByRoute(long routeId);
    List<Invoice> findPendingInvoices();

    Invoice create(Invoice invoice);
    void delete(long id);

    void rejectInvoice(long id);
    void submitInvoice(long id);
}
