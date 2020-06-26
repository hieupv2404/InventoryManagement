package inventory.dao;


import inventory.model.ProductStatusList;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(rollbackFor=Exception.class)
public class ProductStatusListDAOImpl extends BaseDAOImpl<ProductStatusList> implements ProductStatusListDAO<ProductStatusList>{

}
