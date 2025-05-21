package au.com.telstra.simcardactivator.dto;

public class AcuatorRequest {
    String iccid;

    public AcuatorRequest(String iccid){
        this.iccid = iccid;
    }

    public String getIccid() {
        return iccid;
    }
}
