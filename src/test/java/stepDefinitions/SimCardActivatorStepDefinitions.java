package stepDefinitions;

import au.com.telstra.simcardactivator.SimCardActivator;
import io.cucumber.spring.CucumberContextConfiguration;
import io.cucumber.java.en.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ContextConfiguration;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = SimCardActivator.class, loader = SpringBootContextLoader.class)
public class SimCardActivatorStepDefinitions {

    @Autowired
    private TestRestTemplate restTemplate;

    private ResponseEntity<String> postResponse;
    private ResponseEntity<Map> getResponse;

    @Given("the SIM card with ICCID {string} and email {string} is submitted for activation")
    public void submit_sim_card_activation(String iccid, String email) {
        String url = "http://localhost:8080/activate";

        Map<String, String> request = Map.of(
                "iccid", iccid,
                "customerEmail", email
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(request, headers);

        postResponse = restTemplate.postForEntity(url, entity, String.class);
        assertEquals(HttpStatus.OK, postResponse.getStatusCode());
    }

    @When("I query the status of the SIM card with ID {int}")
    public void query_sim_status(int id) {
        String url = "http://localhost:8080/status?simCardId=" + id;

        getResponse = restTemplate.getForEntity(url, Map.class);
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
    }

    @Then("the activation should be successful")
    public void assert_successful_activation() {
        Map<String, Object> body = getResponse.getBody();
        assertNotNull(body);
        assertTrue((Boolean) body.get("active"));
    }

    @Then("the activation should be unsuccessful")
    public void assert_unsuccessful_activation() {
        Map<String, Object> body = getResponse.getBody();
        assertNotNull(body);
        assertFalse((Boolean) body.get("active"));
    }
}
