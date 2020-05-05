package inventory.dao;

import inventory.model.Supplier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(rollbackFor=Exception.class)
public class SupplierDAOImpl extends BaseDAOImpl<Supplier> implements SupplierDAO<Supplier> {
}
