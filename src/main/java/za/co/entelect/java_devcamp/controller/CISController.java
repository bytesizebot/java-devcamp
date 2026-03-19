package za.co.entelect.java_devcamp.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import za.co.entelect.java_devcamp.dto.ProfileDto;
import za.co.entelect.java_devcamp.service.ICISService;

@RestController
@RequestMapping("cis")
@Tag(name = "Customer Information Store", description = "endpoint to communicate with CIS")
public class CISController {

    private final ICISService icisService;

    public CISController(ICISService icisService) {
        this.icisService = icisService;
    }

    @PostMapping("/register-profile")
    public ResponseEntity<ProfileDto> registerUserProfile(@RequestBody ProfileDto profileDto){
        icisService.createCISCustomer(profileDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(profileDto);
    }

}
