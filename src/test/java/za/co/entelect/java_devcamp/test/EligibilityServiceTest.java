package za.co.entelect.java_devcamp.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import za.co.entelect.java_devcamp.entity.Eligibility;
import za.co.entelect.java_devcamp.repository.EligibilityRepository;
import za.co.entelect.java_devcamp.service.EligibilityService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EligibilityServiceTest {
    @Mock
    private EligibilityRepository eligibilityRepository;

    @InjectMocks
    private EligibilityService eligibilityService;

    private Eligibility mockEligibilityEntity;

    @BeforeEach
    public void setup() {
        eligibilityService = new EligibilityService(eligibilityRepository);
        mockEligibilityEntity = Eligibility
                .builder()
                .eligibilityId(1L)
                .productId(2L)
                .customerId(11L)
                .result(false)
                .build();
    }

    @Test
    void testGetEligibilityByCustomerIdAndProductId_WhenEligibilityIsPresent_ThenReturnEligibility() {
        Long customerId = 11L;
        Long productId = 2L;
        when(eligibilityRepository.findByCustomerIdAndProductId(customerId, productId))
                .thenReturn(mockEligibilityEntity);

        Eligibility eligibility = eligibilityService.getEligibilityByProductIdAndCustomerId(customerId, productId);

        assertNotNull(eligibility);
        assertThat(eligibility.getResult()).isEqualTo(mockEligibilityEntity.getResult());
        verify(eligibilityRepository, only()).findByCustomerIdAndProductId(customerId, productId);
    }

    @Test
    void testGetEligibilityExistsByCustomerIdAndProductId_WhenCustomerIsNotEligible_ThenReturnFalse(){
        Long customerId = 11L;
        Long productId = 2L;


        when(eligibilityRepository.findByCustomerIdAndProductId(customerId, productId))
                .thenReturn(mockEligibilityEntity);

        Eligibility eligibility = eligibilityService.getEligibilityByProductIdAndCustomerId(customerId, productId);
        assertNotNull(eligibility);
        assertThat(eligibility.getResult()).isEqualTo(false);
    }

    @Test
    void testGetEligibilityExistsByCustomerIdAndProductId_WhenCustomerIsEligible_ThenReturnTrue(){
        Long customerId = 4L;
        Long productId = 16L;
        Eligibility mockIsEligible = new  Eligibility(2L, 16L, 4L, true );

        when(eligibilityRepository.findByCustomerIdAndProductId(customerId, productId))
                .thenReturn(mockIsEligible);

        Eligibility eligibility = eligibilityService.getEligibilityByProductIdAndCustomerId(customerId, productId);

        assertNotNull(eligibility);
        assertThat(eligibility.getResult()).isEqualTo(true);
    }
}