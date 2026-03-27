package za.co.entelect.java_devcamp.webclientdto;

import lombok.*;
import za.co.entelect.java_devcamp.webclientdto.AccountTypeDto;

@Getter
@Setter
public class CustomerAccountsDto {
    private Long customerAccountsId;
    private AccountTypeDto accountType;
    private Long customerId;
}


