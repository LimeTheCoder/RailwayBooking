package com.limethecoder.controller.command.admin;


import com.limethecoder.controller.command.Command;
import com.limethecoder.controller.util.constants.Attributes;
import com.limethecoder.controller.util.constants.Views;
import com.limethecoder.entity.Invoice;
import com.limethecoder.service.Impl.InvoiceServiceImpl;
import com.limethecoder.service.InvoiceService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetPendingInvoices implements Command {
    private InvoiceService invoiceService = InvoiceServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Invoice> invoices = invoiceService.findPendingInvoices();
        request.setAttribute(Attributes.INVOICES_ATTR, invoices);

        return Views.INVOICES_VIEW;
    }
}
