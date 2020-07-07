package inventory.dao;


import inventory.model.InvoiceTemp;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(rollbackFor=Exception.class)
public class InvoiceTempDAOImpl extends BaseDAOImpl<InvoiceTemp> implements InvoiceTempDAO<InvoiceTemp>{

}
