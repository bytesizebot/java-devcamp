package za.co.entelect.java_devcamp.webclientdto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerAccountsDto {
    private Long customerAccountsId;
    private AccountTypeDto accountType;
    private Long customerId;
}


