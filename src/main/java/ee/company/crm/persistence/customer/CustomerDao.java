package ee.company.crm.persistence.customer;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CustomerDao {
    public Long createCustomer(CustomerEntity customerEntity);
    public CustomerEntity getCustomerById(Integer customerId);
}
