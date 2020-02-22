package ee.company.crm.persistence.customer.personal_data;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface PersonalDataDao {
    public void updateCustomerMarketingConsent(Integer customerId, int consent);
    public PersonalDataEntity getConsentByCustomerId(Integer customerId);
}
