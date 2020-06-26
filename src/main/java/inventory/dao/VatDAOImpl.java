package inventory.dao;

import inventory.model.Vat;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(rollbackFor=Exception.class)
public class VatDAOImpl extends BaseDAOImpl<Vat> implements VatDAO<Vat>{

}
