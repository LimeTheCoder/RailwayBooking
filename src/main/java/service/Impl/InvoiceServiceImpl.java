package service.Impl;


import dao.InvoiceDao;
import dao.connection.DaoConnection;
import dao.factory.DaoFactory;
import entity.Invoice;
import service.InvoiceService;

import java.util.List;
import java.util.Optional;

public class InvoiceServiceImpl implements InvoiceService {

    private DaoFactory daoFactory = DaoFactory.getInstance();

    private InvoiceServiceImpl() {}

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
    public List<Invoice> findAllByPassenger(long passengerId) {
        try(DaoConnection connection = daoFactory.getConnection()) {
            InvoiceDao invoiceDao = daoFactory.getInvoiceDao(connection);
            return invoiceDao.findAllByPassenger(passengerId);
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
    public Invoice create(Invoice invoice) {
        try(DaoConnection connection = daoFactory.getConnection()) {
            InvoiceDao invoiceDao = daoFactory.getInvoiceDao(connection);
            return invoiceDao.insert(invoice);
        }
    }

    @Override
    public void delete(long id) {
        try(DaoConnection connection = daoFactory.getConnection()) {
            InvoiceDao invoiceDao = daoFactory.getInvoiceDao(connection);
            invoiceDao.delete(id);
        }
    }
}
