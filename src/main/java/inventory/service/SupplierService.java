package inventory.service;

import inventory.dao.SupplierDAO;
import inventory.model.Supplier;
import inventory.model.Paging;
import inventory.model.Supplier;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class SupplierService {
    @Autowired
    private SupplierDAO<Supplier> supplierDAO;

    private static final Logger log = Logger.getLogger(SupplierService.class);
    public List<Supplier> getAllSupplier(Supplier supplier, Paging paging){
        log.info("show all Supplier");
        StringBuilder queryStr = new StringBuilder();
        Map<String, Object> mapParams = new HashMap<>();
        if(supplier!=null) {
            if(supplier.getId()!=null && supplier.getId()!=0) {
                queryStr.append(" and model.id=:id");
                mapParams.put("id", supplier.getId());
            }
//            if(productInfo.getCode()!=null && !StringUtils.isEmpty(productInfo.getCode())) {
//                queryStr.append(" and model.code=:code");
//                mapParams.put("code", productInfo.getCode());
//            }
            if(supplier.getName()!=null && !StringUtils.isEmpty(supplier.getName()) ) {
                queryStr.append(" and model.name like :name");
                mapParams.put("name", "%"+supplier.getName()+"%");
            }
        }
        return supplierDAO.findAll(queryStr.toString(), mapParams,paging);
    }

    public void saveSupplier(Supplier supplier)  throws Exception{
        log.info("Insert supplier "+supplier.toString());
        supplier.setActiveFlag(1);
        supplier.setCreateDate(new Date());
        supplier.setUpdateDate(new Date());
        supplierDAO.save(supplier);
    }

    public void updateSupplier(Supplier supplier) throws Exception {
        log.info("Update supplier "+supplier.toString());
        supplier.setUpdateDate(new Date());
        supplierDAO.update(supplier);
    }
    public void deleteSupplier(Supplier supplier) throws Exception{
        supplier.setActiveFlag(0);
        supplier.setUpdateDate(new Date());
        log.info("Delete supplier "+supplier.toString());
        supplierDAO.update(supplier);
    }
    public List<Supplier> findSupplier(String property , Object value){
        log.info("=====Find by property supplier start====");
        log.info("property ="+property +" value"+ value.toString());
        return supplierDAO.findByProperty(property, value);
    }

    public Supplier findByIdSupplier(int id) {
        log.info("find supplier by id ="+id);
        return supplierDAO.findById(Supplier.class, id);
    }

}
