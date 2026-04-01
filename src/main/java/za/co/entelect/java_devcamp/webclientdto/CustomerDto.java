package za.co.entelect.java_devcamp.webclientdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String idNumber;
    private Integer customerTypeId;
    private CustomerTypesDto customerType;
    private List<AccountTypeDto> customerAccounts;

}
