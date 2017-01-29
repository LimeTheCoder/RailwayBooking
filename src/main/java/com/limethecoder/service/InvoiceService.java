package com.limethecoder.service;


import com.limethecoder.entity.Invoice;

import java.util.List;
import java.util.Optional;

public interface InvoiceService {
    Optional<Invoice> findById(long id);
    List<Invoice> findAllByPassenger(long passengerId);
    List<Invoice> findAllByRoute(long routeId);
    Invoice create(Invoice invoice);
    void delete(long id);
}
