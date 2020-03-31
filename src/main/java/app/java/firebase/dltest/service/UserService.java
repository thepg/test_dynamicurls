package app.java.firebase.dltest.service;


import app.java.firebase.dltest.controller.UserController;
import app.java.firebase.dltest.entity.DynamicLinkInfo;
import app.java.firebase.dltest.entity.FirebaseBody;
import app.java.firebase.dltest.entity.User;
import app.java.firebase.dltest.repository.FirebaseRepository;
import app.java.firebase.dltest.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserRepository userRepository;

    private RestTemplate restTemplate;

    public UserService(UserRepository userRepository, FirebaseRepository firebaseRepository, RestTemplate restTemplate) {
        this.userRepository = userRepository;
        this.restTemplate = restTemplate;
    }

    public FirebaseBody parseFirebaseBodyJson(String username) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        FirebaseBody body = objectMapper.readValue(new URL("file:src/main/resources/data/body.json"), FirebaseBody.class);
        DynamicLinkInfo dynamicLinkInfo = body.getDynamicLinkInfo();
        dynamicLinkInfo.setLink(body.getDynamicLinkInfo().getLink().concat("user=" + username));
        log.info("Body : {}", body.getDynamicLinkInfo().getLink().toLowerCase());
        return body;
    }

    public String generateDynamicUrl (String username) throws IOException {

        String FIREBASE_KEY = "AIzaSyAcyFBWRAuCz8K6DqbZSQKR3YrLssclOa0";
        String REST_FIREBASE_URL = "https://firebasedynamiclinks.googleapis.com/v1/shortLinks";

        HttpHeaders headers = new HttpHeaders();

        headers.add("key", FIREBASE_KEY);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.116 Safari/537.36");
        log.info("looool : {}",headers.values());

        Map<String, Object> map = new HashMap<>();
        map.put("raw", parseFirebaseBodyJson(username));

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(map, headers);

        String result = restTemplate.postForObject(REST_FIREBASE_URL,requestEntity,String.class);
        log.info("looool : {}",result);
        return result;
    }

    public User saveUser(User user) throws IOException {
        userRepository.save(user);
        parseFirebaseBodyJson(user.getName());
        user.setUserurl(generateDynamicUrl(user.getName()));
        return user;
    }
}
