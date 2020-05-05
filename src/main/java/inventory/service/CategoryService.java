package inventory.service;

import inventory.dao.CategoryDAO;
import inventory.model.Category;
import inventory.model.Paging;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryService {
    @Autowired
    private CategoryDAO<Category> categoryDAO;
    @Autowired
//    private ProductInfoDAO<ProductInfo> productInfoDAO;
    private static final Logger log = Logger.getLogger(CategoryService.class);
    public void saveCategory(Category category)  throws Exception{
        log.info("Insert category "+category.toString());
        category.setActiveFlag(1);
        category.setCreateDate(new Date());
        category.setUpdateDate(new Date());
        categoryDAO.save(category);
    }
    public void updateCategory(Category category) throws Exception {
        log.info("Update category "+category.toString());
        category.setUpdateDate(new Date());
        categoryDAO.update(category);
    }
    public void deleteCategory(Category category) throws Exception{
        category.setActiveFlag(0);
        category.setUpdateDate(new Date());
        log.info("Delete category "+category.toString());
        categoryDAO.update(category);
    }
    public List<Category> findCategory(String property , Object value){
        log.info("=====Find by property category start====");
        log.info("property ="+property +" value"+ value.toString());
        return categoryDAO.findByProperty(property, value);
    }
    public List<Category> getAllCategory(Category category, Paging paging){
        log.info("show all category");
        StringBuilder queryStr = new StringBuilder();
        Map<String, Object> mapParams = new HashMap<>();
        if(category!=null) {
            if(category.getId()!=null && category.getId()!=0) {
                queryStr.append(" and model.id=:id");
                mapParams.put("id", category.getId());
            }
            if(category.getCode()!=null && !StringUtils.isEmpty(category.getCode())) {
                queryStr.append(" and model.code=:code");
                mapParams.put("code", category.getCode());
            }
            if(category.getName()!=null && !StringUtils.isEmpty(category.getName()) ) {
                queryStr.append(" and model.name like :name");
                mapParams.put("name", "%"+category.getName()+"%");
            }
        }
        return categoryDAO.findAll(queryStr.toString(), mapParams,paging);
    }
    public Category findByIdCategory(int id) {
        log.info("find category by id ="+id);
        return categoryDAO.findById(Category.class, id);
    }
}
