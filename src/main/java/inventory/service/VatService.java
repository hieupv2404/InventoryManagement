package inventory.service;

import inventory.dao.VatDAO;
import inventory.model.Paging;
import inventory.model.Vat;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VatService {
    @Autowired
    private VatDAO<Vat> vatDAO;

    private static final Logger log = Logger.getLogger(VatService.class);

    // VAT service
    public void saveVat(Vat vat){
        log.info("Insert Vat "+vat.toString());
//        if(vat.getTax()!=null && !StringUtils.isEmpty(vat.getTax()))
//        {
//            vat.setTax("123456789");
//        }
        vat.setPrice(BigDecimal.valueOf(0));
        vat.setTotal(BigDecimal.valueOf(0));
        vat.setPercent(BigDecimal.valueOf(0.1));
        vat.setActiveFlag(1);
        vat.setCreateDate(new Date());
        vat.setUpdateDate(new Date());
        vatDAO.save(vat);
    }

    public void updateVat(Vat vat) throws Exception {
        log.info("Update vat "+vat.toString());

        vat.setUpdateDate(new Date());
        vatDAO.update(vat);
    }
    public void deleteVat(Vat vat) throws Exception{
        vat.setActiveFlag(0);
        vat.setUpdateDate(new Date());
        log.info("Delete Vat "+vat.toString());
        vatDAO.update(vat);
    }
    public List<Vat> findVat(String property , Object value){
        log.info("=====Find by property Vat start====");
        log.info("property ="+property +" value"+ value.toString());
        return vatDAO.findByProperty(property, value);
    }
    public List<Vat> getAllVat(Vat vat, Paging paging){
        log.info("Show all Vat");
        StringBuilder queryStr = new StringBuilder();
        Map<String, Object> mapParams = new HashMap<>();
        if(vat!=null && vat.getSupplier()!=null) {
            if(vat.getId()!=null && vat.getId()!=0) {
                queryStr.append(" and model.id=:id");
                mapParams.put("id", vat.getId());
            }
            if(vat.getCode()!=null && !StringUtils.isEmpty(vat.getCode())) {
                queryStr.append(" and model.code like :code");
                mapParams.put("code","%"+ vat.getCode()+"%");
            }
            if(vat.getTax()!=null && !StringUtils.isEmpty(vat.getTax()) ) {
                queryStr.append(" and model.tax like :tax");
                mapParams.put("tax", "%"+vat.getTax()+"%");
            }
            if(vat.getSupplier().getName()!=null && !StringUtils.isEmpty(vat.getSupplier().getName()) ) {
                queryStr.append(" and model.supplier.name like :supplierName");
                mapParams.put("supplierName", "%"+vat.getSupplier().getName()+"%");
            }

            if(vat.getFromDate()!=null) {
                queryStr.append(" and model.updateDate >= :fromDate");
                mapParams.put("fromDate", vat.getFromDate());
            }
            if(vat.getToDate()!=null) {
                queryStr.append(" and model.updateDate <= :toDate");
                mapParams.put("toDate", vat.getToDate());
            }
        }
        return vatDAO.findAll(queryStr.toString(), mapParams,paging);
    }
    public Vat findByIdVat(int id) {
        log.info("find Vat by id ="+id);
        return vatDAO.findById(Vat.class, id);
    }

}
