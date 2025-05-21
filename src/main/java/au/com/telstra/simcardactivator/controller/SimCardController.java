package au.com.telstra.simcardactivator.controller;

import au.com.telstra.simcardactivator.dto.AcuatorRequest;
import au.com.telstra.simcardactivator.dto.AcuatorResponse;
import au.com.telstra.simcardactivator.dto.SimActivationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
public class SimCardController {
    @Autowired
    private final RestTemplate restTemplate;

    public SimCardController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/activate")
    public ResponseEntity<String> activateSim(@RequestBody SimActivationRequest request){
        String url = "http://localhost:8444/actuate";
        AcuatorRequest newrequest = new AcuatorRequest(request.getIccid());
        ResponseEntity<AcuatorResponse> response = restTemplate.postForEntity(url,newrequest,AcuatorResponse.class);
        boolean success = Boolean.TRUE.equals(response.getBody().isSuccess());
        System.out.println("SIM Activation " + (success ? "succeeded" : "failed"));

        return ResponseEntity.ok("Activation " + (success ? "succeeded" : "failed"));
    }
}
