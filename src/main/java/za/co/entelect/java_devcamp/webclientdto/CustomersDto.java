package za.co.entelect.java_devcamp.webclientdto;

import java.util.List;
import lombok.*;
import za.co.entelect.java_devcamp.webclientdto.CustomerDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomersDto {

    private List<CustomerDto> customers;

}
