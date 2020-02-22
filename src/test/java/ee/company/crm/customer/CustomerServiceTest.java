package ee.company.crm.customer;

import ee.company.crm.persistence.customer.CustomerDao;
import ee.company.crm.persistence.customer.CustomerEntity;
import ee.company.crm.persistence.customer.personal_data.PersonalDataDao;
import ee.company.crm.service.customer.CustomerDto;
import ee.company.crm.service.customer.CustomerService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CustomerServiceTest {

    @Captor
    ArgumentCaptor<CustomerEntity> customerEntityCaptor;

    @Mock
    CustomerDao customerDao;

    @Mock
    PersonalDataDao personalDataDao;

    @Spy
    @InjectMocks
    CustomerService customerService;

    @Test
    void isCustomerDataAccessedFromNecessaryRepositories() {
        // given
        Integer customerId = 1;
        CustomerEntity customerEntity = createCustomerEntity(customerId);

        // when
        when(customerDao.getCustomerById(customerId)).thenReturn(customerEntity);
        customerService.getCustomerByCustomerId(customerId);

        // then
        verify(customerDao, times(1)).getCustomerById(customerId);
        verify(personalDataDao, times(1)).getConsentByCustomerId(customerId);
    }

    @Test
    void isCustomerCreationDoneOverEntities() {
        // given
        CustomerDto customerDto = createCustomerDto();

        // when
        customerService.createCustomer(customerDto);

        // then
        verify(customerDao).createCustomer(customerEntityCaptor.capture());
        assertEquals(customerDto.getFirstName(), customerEntityCaptor.getValue().getFirstName());
        assertEquals(customerDto.getLastName(), customerEntityCaptor.getValue().getLastName());
        assertEquals(customerDto.getEmail(), customerEntityCaptor.getValue().getEmail());
    }

    @Test
    void isMarketingConsentUpdatedWithNecessaryRepositories() {
        // Given
        Integer customerId = 1;

        // when
        customerService.updateMarketingConsent(customerId, true);

        // then
        verify(personalDataDao).updateCustomerMarketingConsent(customerId, 1);
    }

    @Disabled("This test is catching the bug we want QA to find")
    @Test
    void isPersonalDataUpdatedWhenCreatingNewCustomer() {
        // Given
        Long customerIdL = 1L;
        CustomerDto customerDto = createCustomerDto();
        CustomerEntity customerEntity = createCustomerEntity(1);

        // when
        when(customerDao.createCustomer(any())).thenReturn(customerIdL);
        when(customerService.mapCustomerData(customerDto)).thenReturn(customerEntity);

        customerService.createCustomer(customerDto);

        // then
        verify(personalDataDao).updateCustomerMarketingConsent(1, 1);
    }

    private CustomerEntity createCustomerEntity(Integer customerId) {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(Long.valueOf(customerId));
        customerEntity.setFirstName("firstName");
        customerEntity.setLastName("lastName");
        customerEntity.setEmail("email");
        return customerEntity;
    }

    private CustomerDto createCustomerDto() {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setMarketingConsent(false);
        customerDto.setEmail("email");
        customerDto.setFirstName("firstName");
        customerDto.setLastName("lastName");
        return customerDto;
    }

}
