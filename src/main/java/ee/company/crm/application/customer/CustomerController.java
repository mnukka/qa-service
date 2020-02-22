package ee.company.crm.application.customer;

import ee.company.crm.service.customer.CustomerDto;
import ee.company.crm.service.customer.CustomerService;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/requestById/{customerId}")
    public ResponseEntity<CustomerDto> requestCustomerByCustomerId(@PathVariable(name = "customerId") Integer customerId) {
        return ResponseEntity.of(customerService.getCustomerByCustomerId(customerId));
    }

    @PostMapping("/create")
    public Long createCustomer(
        @ApiParam(value = "CustomerDto payload", required = true)
        @RequestBody CustomerDto customerDto) {
        return customerService.createCustomer(customerDto);
    }

    @PutMapping("/marketingConsent/{id}")
    public void setMarketingConsentTo(@PathVariable(name="id") Integer customerId, @RequestParam boolean marketingConsent) {
        customerService.updateMarketingConsent(customerId, marketingConsent);
    }
}
