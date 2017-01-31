package com.limethecoder.dao;


import com.limethecoder.entity.Invoice;

import java.util.List;

public interface InvoiceDao extends GenericDao<Invoice, Long> {
    List<Invoice> findAllByPassenger(String passengerEmail);
    List<Invoice> findAllByRoute(Long routeId);
    List<Invoice> findAllByStatus(Invoice.Status status);
}
