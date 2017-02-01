package com.limethecoder.entity;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum InvoiceData {
    JOHN_INVOICE_1(1, RequestData.JOHN_REQUEST_1.request,
            RouteData.KYIV_KHARKIV_ROUTE.route, Invoice.Status.PENDING),
    LESLEY_INVOICE_1(2, RequestData.LESLEY_REQUEST_1.request,
            RouteData.KYIV_KHARKIV_ROUTE.route, Invoice.Status.PAID),
    JOHN_INVOICE_2(3, RequestData.JOHN_REQUEST_2.request,
            RouteData.LVIV_ODESSA_ROUTE.route, Invoice.Status.REJECTED);

    public Invoice invoice;

    InvoiceData(long id, Request request, Route route, Invoice.Status status) {
        invoice = Invoice.newBuilder()
                .setId(id)
                .setRequest(request)
                .setRoute(route)
                .setStatus(status)
                .build();
    }

    public static List<Invoice> getInvoicesList() {
        return Arrays.stream(InvoiceData.values())
                .map((x) -> x.invoice)
                .collect(Collectors.toList());
    }
}
