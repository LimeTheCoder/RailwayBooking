package com.limethecoder.dao;


import com.limethecoder.entity.Invoice;

import java.util.List;

public interface InvoiceDao extends GenericDao<Invoice, Long> {
    List<Invoice> findAllByPassenger(Long passengerId);
    List<Invoice> findAllByRoute(Long routeId);
}
