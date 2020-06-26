package inventory.dao;


import inventory.model.VatDetail;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(rollbackFor=Exception.class)
public class VatDetailDAOImpl extends BaseDAOImpl<VatDetail> implements VatDetailDAO<VatDetail>{

}
