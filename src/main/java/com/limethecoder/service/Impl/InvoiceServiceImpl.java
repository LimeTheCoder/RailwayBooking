package com.limethecoder.service.Impl;


import com.limethecoder.dao.InvoiceDao;
import com.limethecoder.dao.RouteDao;
import com.limethecoder.dao.connection.DaoConnection;
import com.limethecoder.dao.factory.DaoFactory;
import com.limethecoder.entity.Invoice;
import com.limethecoder.service.InvoiceService;
import com.limethecoder.service.exception.ServiceException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class InvoiceServiceImpl implements InvoiceService {

    private DaoFactory daoFactory = DaoFactory.getInstance();

    private final static String NO_FREE_PLACES =
            "Cannot insert new row: all places already reserved";
    private final static String INVALID_ID = "Invalid id";

    InvoiceServiceImpl() {}

    private static class InstanceHolder {
        private final static InvoiceService INSTANCE = new InvoiceServiceImpl();
    }

    public static InvoiceService getInstance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public Optional<Invoice> findById(long id) {
        try(DaoConnection connection = daoFactory.getConnection()) {
            InvoiceDao invoiceDao = daoFactory.getInvoiceDao(connection);
            return invoiceDao.findOne(id);
        }
    }

    @Override
    public List<Invoice> findAllByPassenger(String email) {
        try(DaoConnection connection = daoFactory.getConnection()) {
            InvoiceDao invoiceDao = daoFactory.getInvoiceDao(connection);
            return invoiceDao.findAllByPassenger(email);
        }
    }

    @Override
    public List<Invoice> findAllByRoute(long routeId) {
            try(DaoConnection connection = daoFactory.getConnection()) {
                InvoiceDao invoiceDao = daoFactory.getInvoiceDao(connection);
                return invoiceDao.findAllByRoute(routeId);
            }
    }

    @Override
    public List<Invoice> findPendingInvoices() {
        try(DaoConnection connection = daoFactory.getConnection()) {
            InvoiceDao invoiceDao = daoFactory.getInvoiceDao(connection);
            return invoiceDao.findAllByStatus(Invoice.Status.PENDING);
        }
    }

    @Override
    public Invoice create(Invoice invoice) {
        Objects.requireNonNull(invoice);

        try(DaoConnection connection = daoFactory.getConnection()) {
            InvoiceDao invoiceDao = daoFactory.getInvoiceDao(connection);
            RouteDao routeDao = daoFactory.getRouteDao(connection);

            connection.startSerializableTransaction();

            int reservedCnt = routeDao.findReservedCnt(invoice.getRoute().getId());
            int totalCapacity = invoice.getRoute().getTrain().getCapacity();

            if(reservedCnt + 1 > totalCapacity) {
                throw new ServiceException(NO_FREE_PLACES);
            }

            Invoice inserted = invoiceDao.insert(invoice);
            daoFactory.getRouteDao(connection)
                    .incrementReservedCnt(inserted.getRoute().getId());

            connection.commit();

            return inserted;
        }
    }

    @Override
    public void delete(long id) {
        try(DaoConnection connection = daoFactory.getConnection()) {
            InvoiceDao invoiceDao = daoFactory.getInvoiceDao(connection);

            connection.startSerializableTransaction();

            Invoice old = invoiceDao.findOne(id)
                    .orElseThrow(() -> new ServiceException(INVALID_ID));
            invoiceDao.delete(id);

            long routeIdToUpdate = old.getRoute().getId();
            daoFactory.getRouteDao(connection).decrementReservedCnt(routeIdToUpdate);

            connection.commit();
        }
    }

    @Override
    public void rejectInvoice(long id) {
        try(DaoConnection connection = daoFactory.getConnection()) {
            InvoiceDao invoiceDao = daoFactory.getInvoiceDao(connection);

            connection.startSerializableTransaction();

            Invoice old = invoiceDao.findOne(id)
                    .orElseThrow(() -> new ServiceException(INVALID_ID));

            invoiceDao.updateInvoiceStatus(id, Invoice.Status.REJECTED);

            long routeIdToUpdate = old.getRoute().getId();
            daoFactory.getRouteDao(connection).decrementReservedCnt(routeIdToUpdate);

            connection.commit();
        }
    }

    @Override
    public void submitInvoice(long id) {
        try(DaoConnection connection = daoFactory.getConnection()) {
            InvoiceDao invoiceDao = daoFactory.getInvoiceDao(connection);
            invoiceDao.updateInvoiceStatus(id, Invoice.Status.PAID);
        }
    }

    void setDaoFactory(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
}
