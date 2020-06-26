package inventory.dao;


import inventory.model.Customer;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(rollbackFor=Exception.class)
public class CustomerDAOImpl extends BaseDAOImpl<Customer> implements CustomerDAO<Customer>{

}
