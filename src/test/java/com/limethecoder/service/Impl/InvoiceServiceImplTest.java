package com.limethecoder.service.Impl;


import com.limethecoder.dao.InvoiceDao;
import com.limethecoder.dao.connection.DaoConnection;
import com.limethecoder.dao.factory.DaoFactory;
import com.limethecoder.entity.Invoice;
import com.limethecoder.service.InvoiceService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;

public class InvoiceServiceImplTest {
    @Mock
    private DaoConnection daoConnection;

    @Mock
    private DaoFactory daoFactory;

    @Mock
    private InvoiceDao invoiceDao;

    private InvoiceService invoiceService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        when(daoFactory.getConnection()).thenReturn(daoConnection);
        when(daoFactory.getInvoiceDao(daoConnection)).thenReturn(invoiceDao);

        invoiceService = new InvoiceServiceImpl();
        ((InvoiceServiceImpl) invoiceService).setDaoFactory(daoFactory);

    }

    @Test(expected=NullPointerException.class)
    public void testNullAsArgument() {
        invoiceService.create(null);
        verify(invoiceDao, never()).insert(anyObject());
    }

    @Test
    public void rejectInvoiceTest() {
        long invoiceId = 1L;
        invoiceService.rejectInvoice(invoiceId);
        verify(invoiceDao).updateInvoiceStatus(invoiceId, Invoice.Status.REJECTED);
    }

    @Test
    public void submitInvoiceTest() {
        long invoiceId = 1L;
        invoiceService.submitInvoice(invoiceId);
        verify(invoiceDao).updateInvoiceStatus(invoiceId, Invoice.Status.PAID);
    }
}
