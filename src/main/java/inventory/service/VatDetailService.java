package inventory.service;

import inventory.dao.ProductInfoDAO;
import inventory.dao.VatDAO;
import inventory.dao.VatDetailDAO;
import inventory.model.*;
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
public class VatDetailService {
    @Autowired
    private VatDetailDAO<VatDetail> vatDetailDAO;
    
    @Autowired
    private ProductInfoDAO<ProductInfo> productInfoDAO;
    
    @Autowired
    private VatDAO<Vat> vatDao;

    private static final Logger log = Logger.getLogger(VatDetailService.class);

    public void saveVatDetail(VatDetail vatDetail){
        log.info("Insert VatDetail "+vatDetail.toString());
        vatDetail.setPriceTotal(BigDecimal.valueOf(vatDetail.getQty()).multiply(vatDetail.getPriceOne()));
       vatDetail.setActiveFlag(1);
        vatDetailDAO.save(vatDetail);
    }

    public void updateVatDetail(VatDetail vatDetail) throws Exception {
        log.info("Update VatDetail "+vatDetail.toString());
        vatDetail.setPriceTotal(BigDecimal.valueOf(vatDetail.getQty()).multiply(vatDetail.getPriceOne()));

        vatDetailDAO.update(vatDetail);
    }

    public void deleteVatDetail(VatDetail vatDetail) throws Exception{

        log.info("Delete VatDetail "+vatDetail.toString());
        vatDetail.setActiveFlag(0);
        vatDetailDAO.update(vatDetail);
    }

    public List<VatDetail> findVatDetail(String property , Object value){
        log.info("=====Find by property VatDetail start====");
        log.info("property ="+property +" value"+ value.toString());
        return vatDetailDAO.findByProperty(property, value);
    }

    public List<VatDetail> getAllVatDetail(VatDetail vatDetail, Paging paging){
        log.info("Show all VatDetail");
        StringBuilder queryStr = new StringBuilder();
        Map<String, Object> mapParams = new HashMap<>();
        if(vatDetail!=null && vatDetail.getProductInfo()!=null && vatDetail.getVat()!=null) {
            if(vatDetail.getId()!=null && vatDetail.getId()!=0) {
                queryStr.append(" and model.id=:id");
                mapParams.put("id", vatDetail.getId());
            }

            if(vatDetail.getVat().getCode()!=null && !StringUtils.isEmpty(vatDetail.getVat().getCode())) {
                queryStr.append(" and model.vat.code=:code");
                mapParams.put("code", vatDetail.getVat().getCode());
            }

            if(vatDetail.getFromPriceOne()!=null) {
                queryStr.append(" and model.priceOne >= :fromPriceOne");
                mapParams.put("fromPriceOne", vatDetail.getFromPriceOne());
            }

            if(vatDetail.getToPriceOne()!=null) {
                queryStr.append(" and model.priceOne <= :toPriceOne");
                mapParams.put("toPriceOne", vatDetail.getToPriceOne());
            }

            if(vatDetail.getFromPriceTotal()!=null) {
                queryStr.append(" and model.priceTotal >= :fromPriceTotal");
                mapParams.put("fromPriceTotal", vatDetail.getFromPriceTotal());
            }

            if(vatDetail.getToPriceTotal()!=null) {
                queryStr.append(" and model.priceTotal <= :toPriceTotal");
                mapParams.put("toPriceTotal", vatDetail.getToPriceTotal());
            }

            if(vatDetail.getProductInfo().getName()!=null && !StringUtils.isEmpty(vatDetail.getProductInfo().getName()) ) {
                queryStr.append(" and model.productInfo.name like :name");
                mapParams.put("name", "%"+vatDetail.getProductInfo().getName()+"%");
            }

        }
        return vatDetailDAO.findAll(queryStr.toString(), mapParams,paging);
    }
    public VatDetail findByIdVatDetail(int id) {
        log.info("find VatDetail by id ="+id);
        return vatDetailDAO.findById(VatDetail.class, id);
    }

}
