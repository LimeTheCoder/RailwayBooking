package com.limethecoder.dao;


import com.limethecoder.DBInitializer;
import com.limethecoder.dao.connection.DaoConnection;
import com.limethecoder.dao.factory.DaoFactory;
import com.limethecoder.entity.*;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Optional;


public class InvoiceDaoIntegrationTest {
    private final static long VALID_ID = 1L;
    private final static long INVALID_ID = 5L;

    private final DaoFactory factory = DaoFactory.getInstance();
    private InvoiceDao invoiceDao;
    private DaoConnection connection;

    @BeforeClass
    public static void setUp() throws Exception {
        new DBInitializer().initTestJdbcDB();
    }

    @Before
    public void init() {
        connection = factory.getConnection();
        invoiceDao = factory.getInvoiceDao(connection);
        connection.startTransaction();
    }

    @After
    public void commitAndClose() {
        connection.commit();
        connection.close();
    }

    @Test
    public void findAllTest() {
        List<Invoice> invoices = invoiceDao.findAll();

        int actualSize = invoices.size();
        int expectedSize = InvoiceData.getInvoicesList().size();

        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void findOneTestInvoiceExists() {
        Optional<Invoice> invoiceOptional = invoiceDao.findOne(VALID_ID);
        assertTrue(invoiceOptional.isPresent());
    }

    @Test
    public void findOneTestInvoiceNotExists() {
        Optional<Invoice> invoiceOptional = invoiceDao.findOne(INVALID_ID);
        assertFalse(invoiceOptional.isPresent());
    }

    @Test
    public void findAllByStatusTestReturnedSize() {
        int expectedSize = 1;
        List<Invoice> invoices = invoiceDao.findAllByStatus(Invoice.Status.PAID);
        assertEquals(expectedSize, invoices.size());
    }

    @Test
    public void findAllByStatusTestReturnedContent() {
        List<Invoice> invoices = invoiceDao.findAllByStatus(Invoice.Status.PAID);
        Invoice concreteInvoice = invoices.get(0);
        assertTrue(Invoice.Status.PAID == concreteInvoice.getStatus());
    }

    @Test
    public void findAllByPassengerTestSize() {
        User passenger = UserData.USER_JOHN.user;
        List<Invoice> passengerInvoices = invoiceDao
                .findAllByPassenger(passenger.getEmail());

        int expectedSize = 2;
        assertEquals(expectedSize, passengerInvoices.size());
    }

    @Test
    public void findAllByPassengerTestContent() {
        User passenger = UserData.USER_JOHN.user;
        List<Invoice> passengerInvoices = invoiceDao
                .findAllByPassenger(passenger.getEmail());

        Invoice concreteInvoice = passengerInvoices.get(0);
        assertEquals(passenger, concreteInvoice.getRequest().getPassenger());
    }

    @Test
    public void findAllByRouteTestSize() {
        Route route = RouteData.KYIV_KHARKIV_ROUTE.route;
        List<Invoice> invoicesOnRoute = invoiceDao
                .findAllByRoute(route.getId());

        int expectedSize = 2;
        assertEquals(expectedSize, invoicesOnRoute.size());
    }

    @Test
    public void findAllByRouteTestContent() {
        Route route = RouteData.KYIV_KHARKIV_ROUTE.route;
        List<Invoice> invoicesOnRoute = invoiceDao
                .findAllByRoute(route.getId());

        Invoice concreteInvoice = invoicesOnRoute.get(0);

        assertEquals(route.getId(), concreteInvoice.getRoute().getId());
    }

    @Test
    public void insertTest() {
        int sizeBeforeInsertion = invoiceDao.findAll().size();

        Invoice invoice = buildInvoiceForInsertion();
        invoice = invoiceDao.insert(invoice);

        assertEquals("Number of elements must increase after insertion",
                sizeBeforeInsertion + 1, invoiceDao.findAll().size());

        assertTrue("Element is exist in database after insertion",
                invoiceDao.isExist(invoice.getId()));

        /* Delete inserted invoice from database to keep it consistent */
        invoiceDao.delete(invoice.getId());
    }

    @Test
    public void deleteTest() {
        Invoice invoice = buildInvoiceForInsertion();

        /* Insert invoice for deletion to keep database consistent */
        invoice = invoiceDao.insert(invoice);

        int sizeBeforeDeletion = invoiceDao.findAll().size();

        invoiceDao.delete(invoice.getId());

        assertEquals("Number of elements must decrease after deletion",
                sizeBeforeDeletion - 1, invoiceDao.findAll().size());
        assertFalse("Element can't exist in database after deletion",
                invoiceDao.isExist(invoice.getId()));
    }

    @Test
    public void updateTest() {
        long invoiceIdForUpdate = 3L;
        Invoice invoice = invoiceDao.findOne(invoiceIdForUpdate).get();

        assertEquals(invoice.getRequest().getId(),
                RequestData.JOHN_REQUEST_2.request.getId());

        /* Change request field */
        invoice.setRequest(RequestData.LESLEY_REQUEST_2.request);
        invoiceDao.update(invoice);

        invoice = invoiceDao.findOne(invoiceIdForUpdate).get();
        assertEquals("Field should changes after update",
                RequestData.LESLEY_REQUEST_2.request.getId(),
                invoice.getRequest().getId());

        /* Back database to same state as in start of test */
        invoice.setRequest(RequestData.JOHN_REQUEST_2.request);
        invoiceDao.update(invoice);
    }

    private Invoice buildInvoiceForInsertion() {
        return Invoice.newBuilder()
                .setRoute(RouteData.LVIV_ODESSA_ROUTE.route)
                .setRequest(RequestData.LESLEY_REQUEST_2.request)
                .setStatus(Invoice.Status.PENDING)
                .build();
    }
}
