package inventory.dao;

import inventory.model.Shelf;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(rollbackFor=Exception.class)
public class ShelfDAOImpl extends BaseDAOImpl<Shelf> implements ShelfDAO<Shelf> {

}
