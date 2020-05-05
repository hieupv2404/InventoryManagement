package inventory.dao;

import inventory.model.ProductDetail;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(rollbackFor=Exception.class)
public class ProductDetailDAOImpl extends BaseDAOImpl<ProductDetail> implements ProductDetailDAO<ProductDetail> {
}
