package ee.company.crm.persistence.customer.personal_data;

public class PersonalDataEntity {

    private Long customerId;
    private boolean marketingConsent;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public boolean isMarketingConsent() {
        return marketingConsent;
    }

    public void setMarketingConsent(boolean marketingConsent) {
        this.marketingConsent = marketingConsent;
    }
}