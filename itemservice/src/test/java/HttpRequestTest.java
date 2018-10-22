import com.sap.itemservice.ItemServiceApplication;
import com.sap.itemservice.controller.ItemController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedHashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ItemServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class HttpRequestTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ItemController controller;

    @Test
    public void testContextLoads() {
        assertThat(controller).isNotNull();
    }

    @Test
    public void testCreateAndGetItem() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>("{\"item\": {\"id\": 134, \"timestamp\": \"2016-01-01T23:01:01.000Z\"}}", headers);
        ResponseEntity response = this.restTemplate.exchange("http://localhost:8080/items", HttpMethod.POST, entity, ResponseEntity.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        List items = this.restTemplate.getForObject("http://localhost:8080/items", List.class);
        assertThat(items).isNotNull();
        assertThat(items).hasSize(1);
        assertThat(items.get(0)).isInstanceOf(LinkedHashMap.class);
        assertThat(((LinkedHashMap) ((LinkedHashMap) items.get(0)).get("item")).get("id")).isEqualTo(134);
    }
}
