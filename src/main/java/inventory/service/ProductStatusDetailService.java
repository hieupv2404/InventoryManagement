package inventory.service;

import inventory.dao.ProductInfoDAO;
import inventory.dao.ProductStatusDetailDAO;
import inventory.dao.ProductStatusListDAO;
import inventory.dao.VatDAO;
import inventory.model.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductStatusDetailService {
    @Autowired
    private ProductStatusDetailDAO<ProductStatusDetail> productStatusDetailDAO;
    
    @Autowired
    private ProductInfoDAO<ProductInfo> productInfoDAO;
    
    @Autowired
    private ProductStatusListDAO<ProductStatusList> productStatusListDAO;

    private static final Logger log = Logger.getLogger(ProductStatusDetailService.class);

    public void saveProductStatusDetail(ProductStatusDetail productStatusDetail){
        log.info("Insert ProductStatusDetail "+productStatusDetail.toString());
        productStatusDetail.setPriceTotal(BigDecimal.valueOf(productStatusDetail.getQty()).multiply(productStatusDetail.getPriceOne()));
       productStatusDetail.setActiveFlag(1);
        productStatusDetailDAO.save(productStatusDetail);
    }

    public void updateProductStatusDetail(ProductStatusDetail productStatusDetail) throws Exception {
        log.info("Update ProductStatusDetail "+productStatusDetail.toString());
        productStatusDetail.setPriceTotal(BigDecimal.valueOf(productStatusDetail.getQty()).multiply(productStatusDetail.getPriceOne()));

        productStatusDetailDAO.update(productStatusDetail);
    }

    public void deleteProductStatusDetail(ProductStatusDetail productStatusDetail) throws Exception{

        log.info("Delete ProductStatusDetail "+productStatusDetail.toString());
        productStatusDetail.setActiveFlag(0);
        productStatusDetailDAO.update(productStatusDetail);
    }

    public List<ProductStatusDetail> findProductStatusDetail(String property , Object value){
        log.info("=====Find by property ProductStatusDetail start====");
        log.info("property ="+property +" value"+ value.toString());
        return productStatusDetailDAO.findByProperty(property, value);
    }

    public List<ProductStatusDetail> getAllProductStatusDetail(ProductStatusDetail productStatusDetail, Paging paging){
        log.info("Show all ProductStatusDetail");
        StringBuilder queryStr = new StringBuilder();
        Map<String, Object> mapParams = new HashMap<>();
        if(productStatusDetail!=null && productStatusDetail.getProductInfo()!=null && productStatusDetail.getProductStatusList()!=null) {
            if(productStatusDetail.getId()!=null && productStatusDetail.getId()!=0) {
                queryStr.append(" and model.id=:id");
                mapParams.put("id", productStatusDetail.getId());
            }

            if(productStatusDetail.getProductStatusList().getCode()!=null && !StringUtils.isEmpty(productStatusDetail.getProductStatusList().getCode())) {
                queryStr.append(" and model.productStatusList.code=:code");
                mapParams.put("code", productStatusDetail.getProductStatusList().getCode());
            }

            if(productStatusDetail.getProductStatusList().getType()!=0) {
                queryStr.append(" and model.productStatusList.type=:type");
                mapParams.put("type", productStatusDetail.getProductStatusList().getType());
            }

            if(productStatusDetail.getFromPriceOne()!=null) {
                queryStr.append(" and model.priceOne >= :fromPriceOne");
                mapParams.put("fromPriceOne", productStatusDetail.getFromPriceOne());
            }

            if(productStatusDetail.getToPriceOne()!=null) {
                queryStr.append(" and model.priceOne <= :toPriceOne");
                mapParams.put("toPriceOne", productStatusDetail.getToPriceOne());
            }

            if(productStatusDetail.getFromPriceTotal()!=null) {
                queryStr.append(" and model.priceTotal >= :fromPriceTotal");
                mapParams.put("fromPriceTotal", productStatusDetail.getFromPriceTotal());
            }

            if(productStatusDetail.getToPriceTotal()!=null) {
                queryStr.append(" and model.priceTotal <= :toPriceTotal");
                mapParams.put("toPriceTotal", productStatusDetail.getToPriceTotal());
            }

            if(productStatusDetail.getProductInfo().getName()!=null && !StringUtils.isEmpty(productStatusDetail.getProductInfo().getName()) ) {
                queryStr.append(" and model.productInfo.name like :name");
                mapParams.put("name", "%"+productStatusDetail.getProductInfo().getName()+"%");
            }

        }
        return productStatusDetailDAO.findAll(queryStr.toString(), mapParams,paging);
    }
    public ProductStatusDetail findByIdProductStatusDetail(int id) {
        log.info("find ProductStatusDetail by id ="+id);
        return productStatusDetailDAO.findById(ProductStatusDetail.class, id);
    }

}
