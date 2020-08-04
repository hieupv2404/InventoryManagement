package inventory.service;

import inventory.dao.ShelfDAO;
import inventory.model.Paging;
import inventory.model.Shelf;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ShelfService {
    @Autowired
    private ShelfDAO<Shelf> shelfDAO;
    @Autowired
//    private ProductInfoDAO<ProductInfo> productInfoDAO;
    private static final Logger log = Logger.getLogger(ShelfService.class);
    public void saveShelf(Shelf category)  throws Exception{
        log.info("Insert category "+category.toString());
        category.setActiveFlag(1);
        category.setCreateDate(new Date());
        category.setUpdateDate(new Date());
        shelfDAO.save(category);
    }

    public void updateShelf(Shelf category) throws Exception {
        log.info("Update category "+category.toString());
        category.setQtyRest(category.getTotal()-category.getQty());
        category.setUpdateDate(new Date());
        shelfDAO.update(category);
    }
    public void deleteShelf(Shelf category) throws Exception{
        category.setActiveFlag(0);
        category.setUpdateDate(new Date());
        log.info("Delete category "+category.toString());
        shelfDAO.update(category);
    }
    public List<Shelf> findShelf(String property , Object value){
        log.info("=====Find by property category start====");
        log.info("property ="+property +" value"+ value.toString());
        return shelfDAO.findByProperty(property, value);
    }

    public List<Shelf> getAllShelf(Shelf category, Paging paging){
        log.info("show all category");
        StringBuilder queryStr = new StringBuilder();
        Map<String, Object> mapParams = new HashMap<>();
        if(category!=null) {
            if(category.getId()!=null && category.getId()!=0) {
                queryStr.append(" and model.id=:id");
                mapParams.put("id", category.getId());
            }
            if(category.getQtyRest() != 0 ) {
                queryStr.append(" and model.qtyRest >= :qtyRest");
                mapParams.put("qtyRest", category.getQtyRest());
            }

            if(category.getName()!=null && !StringUtils.isEmpty(category.getName()) ) {
                queryStr.append(" and model.name like :name");
                mapParams.put("name", "%"+category.getName()+"%");
            }
            if(category.getDescription()!=null && !StringUtils.isEmpty(category.getDescription()) ) {
                queryStr.append(" and model.description like :description");
                mapParams.put("description", "%"+category.getDescription()+"%");
            }
        }
        return shelfDAO.findAll(queryStr.toString(), mapParams,paging);
    }

    public Shelf findByIdShelf(int id) {
        log.info("find category by id ="+id);
        return shelfDAO.findById(Shelf.class, id);
    }
}
