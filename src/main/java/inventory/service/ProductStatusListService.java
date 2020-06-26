package inventory.service;

import inventory.dao.ProductStatusListDAO;
import inventory.model.Paging;
import inventory.model.ProductStatusList;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductStatusListService {
    @Autowired
    private ProductStatusListDAO<ProductStatusList> productStatusListDAO;

    private static final Logger log = Logger.getLogger(ProductStatusListService.class);

    // Product status service
    public void saveProductStatusList(ProductStatusList productStatusList){
        log.info("Insert ProductStatusList "+productStatusList.toString());
//        if(vat.getTax()!=null && !StringUtils.isEmpty(vat.getTax()))
//        {
//            vat.setTax("123456789");
//        }
        productStatusList.setPrice(BigDecimal.valueOf(0));
        productStatusList.setActiveFlag(1);
        productStatusList.setCreateDate(new Date());
        productStatusList.setUpdateDate(new Date());
        productStatusListDAO.save(productStatusList);
    }

    public void updateProductStatusList(ProductStatusList productStatusList) throws Exception {
        log.info("Update ProductStatusList "+productStatusList.toString());

        productStatusList.setUpdateDate(new Date());
        productStatusListDAO.update(productStatusList);
    }

    public void deleteProductStatusList(ProductStatusList productStatusList) throws Exception{
        productStatusList.setActiveFlag(0);
        productStatusList.setUpdateDate(new Date());
        log.info("Delete ProductStatusList "+productStatusList.toString());
        productStatusListDAO.update(productStatusList);
    }
    public List<ProductStatusList> findProductStatusList(String property , Object value){
        log.info("=====Find by property ProductStatusList start====");
        log.info("property ="+property +" value"+ value.toString());
        return productStatusListDAO.findByProperty(property, value);
    }
    public List<ProductStatusList> getAllProductStatusList(ProductStatusList productStatusList, Paging paging){
        log.info("Show all ProductStatusList");
        StringBuilder queryStr = new StringBuilder();
        Map<String, Object> mapParams = new HashMap<>();
        if(productStatusList!=null && productStatusList.getSupplier()!=null && productStatusList.getUser()!=null) {
            if(productStatusList.getId()!=null && productStatusList.getId()!=0) {
                queryStr.append(" and model.id=:id");
                mapParams.put("id", productStatusList.getId());
            }
            if(productStatusList.getCode()!=null && !StringUtils.isEmpty(productStatusList.getCode())) {
                queryStr.append(" and model.code like :code");
                mapParams.put("code","%"+ productStatusList.getCode()+"%");
            }
            if(productStatusList.getType()!=0 ) {
                queryStr.append(" and model.type like :type");
                mapParams.put("type", productStatusList.getType());
            }
            if(productStatusList.getSupplier().getName()!=null && !StringUtils.isEmpty(productStatusList.getSupplier().getName()) ) {
                queryStr.append(" and model.supplier.name like :supplierName");
                mapParams.put("supplierName", "%"+productStatusList.getSupplier().getName()+"%");
            }
            if(productStatusList.getUser().getName()!=null && !StringUtils.isEmpty(productStatusList.getUser().getName()) ) {
                queryStr.append(" and model.user.name like :userName");
                mapParams.put("userName", "%"+productStatusList.getUser().getName()+"%");
            }

            if(productStatusList.getFromDate()!=null) {
                queryStr.append(" and model.updateDate >= :fromDate");
                mapParams.put("fromDate", productStatusList.getFromDate());
            }
            if(productStatusList.getToDate()!=null) {
                queryStr.append(" and model.updateDate <= :toDate");
                mapParams.put("toDate", productStatusList.getToDate());
            }
        }
        return productStatusListDAO.findAll(queryStr.toString(), mapParams,paging);
    }
    public ProductStatusList findByIdProductStatusList(int id) {
        log.info("find ProductStatusList by id ="+id);
        return productStatusListDAO.findById(ProductStatusList.class, id);
    }

}
