package inventory.dao;

import inventory.model.ProductStatusDetail;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(rollbackFor=Exception.class)
public class ProductStatusDetailDAOImpl extends BaseDAOImpl<ProductStatusDetail> implements ProductStatusDetailDAO<ProductStatusDetail>{
}
