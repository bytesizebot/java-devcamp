package za.co.entelect.java_devcamp.webclientdto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerTypesDto {

    private Integer id;
    private String name;
    private String description;

}
