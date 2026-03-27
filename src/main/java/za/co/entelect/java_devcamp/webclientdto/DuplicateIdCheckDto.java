package za.co.entelect.java_devcamp.webclientdto;

import lombok.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DuplicateIdCheckDto {

    private boolean hasDuplicateId;
    private Date duplicatedIdIssueDate;
}
