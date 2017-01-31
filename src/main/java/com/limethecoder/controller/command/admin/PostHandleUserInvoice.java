package com.limethecoder.controller.command.admin;

import com.limethecoder.controller.command.Command;
import com.limethecoder.controller.util.Util;
import com.limethecoder.controller.util.constants.PagesPaths;
import com.limethecoder.service.Impl.InvoiceServiceImpl;
import com.limethecoder.service.InvoiceService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class PostHandleUserInvoice implements Command {
    private final static String INVOICE_PARAM = "invoice";
    private final static String SUBMIT_PARAM = "submit";
    private final static String REJECT_PARAM = "reject";

    private final InvoiceService invoiceService = InvoiceServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        long invoiceId = getInvoiceIdFromRequest(request);

        if(isUserInvoiceRejected(request)) {
            invoiceService.rejectInvoice(invoiceId);
        }

        if(isUserInvoiceSubmitted(request)) {
            invoiceService.submitInvoice(invoiceId);
        }

        Util.redirectTo(request, response, PagesPaths.PENDING_INVOICES_PATH);
        return REDIRECTED;
    }

    private long getInvoiceIdFromRequest(HttpServletRequest request) {
        return Long.valueOf(request.getParameter(INVOICE_PARAM));
    }

    private boolean isUserInvoiceSubmitted(HttpServletRequest request) {
        return request.getParameterMap().containsKey(SUBMIT_PARAM);
    }

    private boolean isUserInvoiceRejected(HttpServletRequest request) {
        return request.getParameterMap().containsKey(REJECT_PARAM);
    }
}
