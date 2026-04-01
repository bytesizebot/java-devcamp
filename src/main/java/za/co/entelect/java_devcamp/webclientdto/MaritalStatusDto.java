package za.co.entelect.java_devcamp.webclientdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MaritalStatusDto {
    private MaritalStatus maritalStatus;
    private Date effectiveFrom;
    private Date effectiveTo;
}
