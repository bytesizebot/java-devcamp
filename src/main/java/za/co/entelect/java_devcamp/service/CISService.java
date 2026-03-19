package za.co.entelect.java_devcamp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import za.co.entelect.java_devcamp.dto.ProfileDto;
import za.co.entelect.java_devcamp.exception.DuplicateResourceException;
import za.co.entelect.java_devcamp.webclient.CISWebService;

@Slf4j
@Service
public class CISService implements ICISService {
    private final CISWebService cisWebService;

    public CISService(CISWebService cisWebService) {
        this.cisWebService = cisWebService;
    }

    @Override
    public ProfileDto createCISCustomer(ProfileDto profileDto) {
        log.info("Creating a customer in the CIS");

        if(cisWebService.getCustomerByEmail(profileDto.username()) != null){
            throw new DuplicateResourceException("User already exists. You cannot register them");
        }
        return cisWebService.registerCustomer(profileDto);
    }
}
