package inventory.service;

import inventory.dao.InvoiceTempDAO;
import inventory.model.InvoiceTemp;
import inventory.model.InvoiceTemp;
import inventory.model.Paging;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InvoiceTempService {
    @Autowired
    private InvoiceTempDAO<InvoiceTemp> invoiceTempDAO;
    @Autowired
//    private ProductInfoDAO<ProductInfo> productInfoDAO;
    private static final Logger log = Logger.getLogger(InvoiceTempService.class);
    public void saveInvoiceTemp(InvoiceTemp invoiceTemp)  throws Exception{
        log.info("Insert category "+invoiceTemp.toString());

        invoiceTempDAO.save(invoiceTemp);
    }

    public void updateInvoiceTemp(InvoiceTemp invoiceTemp) throws Exception {
        log.info("Update category "+invoiceTemp.toString());
        invoiceTemp.setActiveFlag(0);
        invoiceTempDAO.update(invoiceTemp);
    }
    public void deleteInvoiceTemp(InvoiceTemp invoiceTemp) throws Exception{

        log.info("Delete category "+invoiceTemp.toString());
        invoiceTemp.setActiveFlag(0);
        invoiceTempDAO.deleteDone(invoiceTemp);
    }

    public List<InvoiceTemp> findInvoiceTemp(String property , Object value){
        log.info("=====Find by property invoice temp start====");
        log.info("property ="+property +" value"+ value.toString());
        return invoiceTempDAO.findByProperty(property, value);
    }


    public InvoiceTemp findByIdInvoiceTemp(int id) {
        log.info("find category by id ="+id);
        return invoiceTempDAO.findById(InvoiceTemp.class, id);
    }
}
