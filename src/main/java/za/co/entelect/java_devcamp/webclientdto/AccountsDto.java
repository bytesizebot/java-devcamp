package za.co.entelect.java_devcamp.webclientdto;

import java.util.List;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountsDto {

    private List<AccountTypeDto> accountType;
}
