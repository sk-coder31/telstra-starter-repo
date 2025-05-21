package au.com.telstra.simcardactivator.dto;

public class SimActivationRequest {
    String iccid;
    String customerEmail;
    public SimActivationRequest(){

    }
    public SimActivationRequest(String iccid,String customerEmail){
        this.iccid = iccid;
        this.customerEmail = customerEmail;
    }

    public String getIccid() {
        return iccid;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }
}
