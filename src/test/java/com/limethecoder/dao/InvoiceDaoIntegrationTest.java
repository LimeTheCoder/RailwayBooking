package com.limethecoder.dao;


import com.limethecoder.DBInitializer;
import com.limethecoder.dao.connection.DaoConnection;
import com.limethecoder.dao.factory.DaoFactory;
import com.limethecoder.entity.Invoice;
import com.limethecoder.entity.InvoiceData;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.List;


public class InvoiceDaoIntegrationTest {
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
}
