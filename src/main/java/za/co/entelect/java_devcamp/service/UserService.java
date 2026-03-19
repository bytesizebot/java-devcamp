package za.co.entelect.java_devcamp.service;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import za.co.entelect.java_devcamp.dto.UserDto;
import za.co.entelect.java_devcamp.entity.User;
import za.co.entelect.java_devcamp.exception.IncorrectPasswordException;
import za.co.entelect.java_devcamp.exception.ResourceNotFoundException;
import za.co.entelect.java_devcamp.mapper.UserMapper;
import za.co.entelect.java_devcamp.repository.UserRepository;
import za.co.entelect.java_devcamp.request.LogInRequest;
import za.co.entelect.java_devcamp.response.LogInResponse;

import java.util.Map;

@Service
@Slf4j
public class UserService implements IUserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public void createUser(UserDto userDto) {
        logger.info("Registering a new user");

        if(userRepository.existsByUsername(userDto.username())){
            throw new RuntimeException("User already exists.");
        }

        User user = userMapper.toEntity(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        userMapper.toDto(savedUser);
    }

    private String fetchRsToken(String username) {

        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:8080/token"; // your unsecured endpoint
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("client-username", "client-password");
        Map<String, String> request = Map.of(
                "username", username
        );

        ResponseEntity<Map> response =
                restTemplate.postForEntity(url, request, Map.class);

        return (String) response.getBody().get("access_token");
    }


    @Override
    public LogInResponse logIn(LogInRequest request) {
        logger.info("Logging in...");
        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new ResourceNotFoundException(("User not found with username: ") + request.username()));

        if(!passwordEncoder.matches(request.password(), user.getPassword())){
            throw new IncorrectPasswordException(("Incorrect password provided"));
        }
       // String token = jwtUtils.generateToken(user.getUsername());
       // String token = fetchRsToken(user.getUsername());
        String token = "";
        return new LogInResponse(token, user.getUsername(), "Login successful");
    }
}
