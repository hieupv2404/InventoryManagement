package inventory.service;

import inventory.dao.CategoryDAO;
import inventory.dao.ProductDetailDAO;
import inventory.dao.ProductInfoDAO;
import inventory.dao.SupplierDAO;
import inventory.model.*;
import inventory.util.ConfigLoader;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductDetailService {
    @Autowired
    private ProductInfoDAO<ProductInfo> productInfoDAO;

    @Autowired
    private CategoryDAO<Category> categoryDAO;

    @Autowired
    private SupplierDAO<Supplier> supplierDAO;

    @Autowired
    private ProductDetailDAO<ProductDetail> productDetailDAO;

    private static final Logger log = Logger.getLogger(ProductDetailService.class);

    // PRODUCT Deatil

    public void saveProductDetail(ProductDetail productDetail)  throws Exception{
        log.info("Insert productDetail "+productDetail.toString());
        productDetail.setActiveFlag(1);
        productDetail.setCreateDate(new Date());
        productDetail.setUpdateDate(new Date());
        productDetailDAO.save(productDetail);
    }
    public void updateProductDetail(ProductDetail productDetail) throws Exception {
        log.info("Update productDetail "+productDetail.toString());

        productDetail.setUpdateDate(new Date());
        productDetailDAO.update(productDetail);
    }
    public void deleteProductDetail(ProductDetail productDetail) throws Exception{
        productDetail.setActiveFlag(0);
        productDetail.setUpdateDate(new Date());
        log.info("Delete productDetail "+productDetail.toString());
        productDetailDAO.update(productDetail);
    }
    public List<ProductDetail> findProductDetail(String property , Object value){
        log.info("=====Find by property productDetail start====");
        log.info("property ="+property +" value"+ value.toString());
        return productDetailDAO.findByProperty(property, value);
    }
    public List<ProductDetail> getAllProductDetail(ProductDetail productDetail, Paging paging){
        log.info("Show all productDetail");
        StringBuilder queryStr = new StringBuilder();
        Map<String, Object> mapParams = new HashMap<>();
        if(productDetail!=null && productDetail.getProductInfo()!=null && productDetail.getSupplier()!=null) {
            if(productDetail.getId()!=null && productDetail.getId()!=0) {
                queryStr.append(" and model.id=:id");
                mapParams.put("id", productDetail.getId());
            }
            if(productDetail.getCode()!=null && !StringUtils.isEmpty(productDetail.getCode())) {
                queryStr.append(" and model.code like :code");
                mapParams.put("code","%"+ productDetail.getCode()+"%");
            }
            if(productDetail.getProductInfo().getName()!=null && !StringUtils.isEmpty(productDetail.getProductInfo().getName()) ) {
                queryStr.append(" and model.productInfo.name like :name");
                mapParams.put("name", "%"+productDetail.getProductInfo().getName()+"%");
            }
            if(productDetail.getSupplier().getName()!=null && !StringUtils.isEmpty(productDetail.getSupplier().getName()) ) {
                queryStr.append(" and model.supplier.name like :supplierName");
                mapParams.put("supplierName", "%"+productDetail.getSupplier().getName()+"%");
            }
        }
        return productDetailDAO.findAll(queryStr.toString(), mapParams,paging);
    }
    public ProductDetail findByIdProductDetail(int id) {
        log.info("find productDetail by id ="+id);
        return productDetailDAO.findById(ProductDetail.class, id);
    }

    // Product Info Service
    public void saveProductInfo(ProductInfo productInfo)  throws Exception{
        log.info("Insert productInfo "+productInfo.toString());
        productInfo.setActiveFlag(1);
        productInfo.setCreateDate(new Date());
        productInfo.setUpdateDate(new Date());
        String fileName = System.currentTimeMillis()+"_"+productInfo.getMultipartFile().getOriginalFilename();
        processUploadFile(productInfo.getMultipartFile(),fileName);
        productInfo.setImgUrl("/upload/"+fileName);
        productInfoDAO.save(productInfo);
    }
    public void updateProductInfo(ProductInfo productInfo) throws Exception {
        log.info("Update productInfo "+productInfo.toString());


        if(!productInfo.getMultipartFile().getOriginalFilename().isEmpty()) {

            String fileName =  System.currentTimeMillis()+"_"+productInfo.getMultipartFile().getOriginalFilename();
            processUploadFile(productInfo.getMultipartFile(),fileName);
            productInfo.setImgUrl("/upload/"+fileName);
        }
        productInfo.setUpdateDate(new Date());
        productInfoDAO.update(productInfo);
    }
    public void deleteProductInfo(ProductInfo productInfo) throws Exception{
        productInfo.setActiveFlag(0);
        productInfo.setUpdateDate(new Date());
        log.info("Delete productInfo "+productInfo.toString());
        productInfoDAO.update(productInfo);
    }
    public List<ProductInfo> findProductInfo(String property , Object value){
        log.info("=====Find by property productInfo start====");
        log.info("property ="+property +" value"+ value.toString());
        return productInfoDAO.findByProperty(property, value);
    }
    public List<ProductInfo> getAllProductInfo(ProductInfo productInfo,Paging paging){
        log.info("show all productInfo");
        StringBuilder queryStr = new StringBuilder();
        Map<String, Object> mapParams = new HashMap<>();
        if(productInfo!=null) {
            if(productInfo.getId()!=null && productInfo.getId()!=0) {
                queryStr.append(" and model.id=:id");
                mapParams.put("id", productInfo.getId());
            }
//            if(productInfo.getCode()!=null && !StringUtils.isEmpty(productInfo.getCode())) {
//                queryStr.append(" and model.code=:code");
//                mapParams.put("code", productInfo.getCode());
//            }
            if(productInfo.getName()!=null && !StringUtils.isEmpty(productInfo.getName()) ) {
                queryStr.append(" and model.name like :name");
                mapParams.put("name", "%"+productInfo.getName()+"%");
            }
        }
        return productInfoDAO.findAll(queryStr.toString(), mapParams,paging);
    }
    public ProductInfo findByIdProductInfo(int id) {
        log.info("find productInfo by id ="+id);
        return productInfoDAO.findById(ProductInfo.class, id);
    }
    private void processUploadFile(MultipartFile multipartFile,String fileName) throws IllegalStateException, IOException {
        if(!multipartFile.getOriginalFilename().isEmpty()) {
            File dir = new File(ConfigLoader.getInstance().getValue("upload.location"));
            if(!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(ConfigLoader.getInstance().getValue("upload.location"),fileName);
            multipartFile.transferTo(file);
        }
    }

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

}
