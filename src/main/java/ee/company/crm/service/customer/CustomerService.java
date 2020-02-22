package ee.company.crm.service.customer;

import ee.company.crm.persistence.customer.CustomerDao;
import ee.company.crm.persistence.customer.CustomerEntity;
import ee.company.crm.persistence.customer.personal_data.PersonalDataDao;
import ee.company.crm.persistence.customer.personal_data.PersonalDataEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {

    private CustomerDao customerDao;
    private PersonalDataDao personalDataDao;

    public CustomerService(CustomerDao customerDao, PersonalDataDao personalDataDao) {
        this.customerDao = customerDao;
        this.personalDataDao = personalDataDao;
    }

    public Optional<CustomerDto> getCustomerByCustomerId(Integer customerId) {
        CustomerEntity customerEntity = customerDao.getCustomerById(customerId);
        if (customerEntity == null) {
            return Optional.empty();
        }

        boolean marketingConsent = isCustomerMarketingConsentEnabled(customerId);
        ModelMapper modelMapper = new ModelMapper();
        CustomerDto customerDto = modelMapper.map(customerEntity, CustomerDto.class);
        customerDto.setMarketingConsent(marketingConsent);
        return Optional.of(customerDto);
    }

    public void updateMarketingConsent(Integer customerId, boolean marketingConsent) {
        int consent = marketingConsent ? 1 : 0;
        personalDataDao.updateCustomerMarketingConsent(customerId, consent);
    }

    /*
        We are introducing a bug into the application on purpose
        To see if QA figures out that whenever a new customer is created
        Then that customer won't receive marketing consent from the payload
     */
    public Long createCustomer(CustomerDto customerDto) {
        CustomerEntity entity = mapCustomerData(customerDto);
        customerDao.createCustomer(entity);
        return entity.getId();
    }

    public CustomerEntity mapCustomerData(CustomerDto customerDto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(customerDto, CustomerEntity.class);
    }

    private boolean isCustomerMarketingConsentEnabled(Integer customerId) {
        PersonalDataEntity personalDataEntity = personalDataDao.getConsentByCustomerId(customerId);
        return personalDataEntity != null && personalDataEntity.isMarketingConsent();
    }
}
