package au.com.telstra.simcardactivator.controller;

import au.com.telstra.simcardactivator.SimActivationRepository;
import au.com.telstra.simcardactivator.dto.AcuatorRequest;
import au.com.telstra.simcardactivator.dto.AcuatorResponse;
import au.com.telstra.simcardactivator.dto.SimActivationRequest;
import au.com.telstra.simcardactivator.model.SimActivationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class SimCardController {
    @Autowired
    private final RestTemplate restTemplate;
    @Autowired
    private SimActivationRepository repository;

    public SimCardController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/activate")
    public ResponseEntity<String> activateSim(@RequestBody SimActivationRequest request){
        String url = "http://localhost:8444/actuate";
        AcuatorRequest newrequest = new AcuatorRequest(request.getIccid());
        ResponseEntity<AcuatorResponse> response = restTemplate.postForEntity(url,newrequest,AcuatorResponse.class);
        boolean success = response.getBody() != null && response.getBody().isSuccess();

        SimActivationModel record = new SimActivationModel(request.getIccid(),request.getCustomerEmail(),success);
        repository.save(record);
        System.out.println("SIM Activation " + (success ? "succeeded" : "failed"));

        return ResponseEntity.ok("Activation " + (success ? "succeeded" : "failed"));
    }
    @GetMapping("/status")
    public ResponseEntity<?> getSimStatus(@RequestParam Long simCardId) {
        return repository.findById(simCardId)
                .map(record -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("iccid", record.getIccid());
                    response.put("customerEmail", record.getCustomerEmail());
                    response.put("active", record.isActive());
                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
