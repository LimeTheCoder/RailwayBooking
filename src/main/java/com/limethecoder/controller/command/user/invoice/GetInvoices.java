package com.limethecoder.controller.command.user.invoice;


import com.limethecoder.controller.command.Command;
import com.limethecoder.controller.util.Util;
import com.limethecoder.controller.util.constants.Attributes;
import com.limethecoder.controller.util.constants.PagesPaths;
import com.limethecoder.controller.util.constants.Views;
import com.limethecoder.entity.Invoice;
import com.limethecoder.entity.User;
import com.limethecoder.service.Impl.InvoiceServiceImpl;
import com.limethecoder.service.InvoiceService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetInvoices implements Command {
    private final static String INVOICES_PARAM = "invoices";

    private InvoiceService invoiceService = InvoiceServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User)request.getSession()
                .getAttribute(Attributes.USER_ATTR);

        List<Invoice> invoices = invoiceService.findAllByPassenger(user.getEmail());
        request.setAttribute(INVOICES_PARAM, invoices);

        return Views.INVOICES_VIEW;
    }
}
