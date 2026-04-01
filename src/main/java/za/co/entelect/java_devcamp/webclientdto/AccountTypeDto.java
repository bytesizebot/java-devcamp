package za.co.entelect.java_devcamp.webclientdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountTypeDto {

    private Integer id;
    private String name;
    private String description;

    public AccountTypeDto(String name, String description){
        this.name = name;
        this.description = description;
    }
}
