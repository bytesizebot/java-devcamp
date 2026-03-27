package za.co.entelect.java_devcamp.webclientdto;

import lombok.*;

@Getter
@Setter
public class KYCCheckDto {
    private boolean primaryIndicator;
    private boolean secondaryIndicator;
    private TaxCompliance taxCompliance;
}
