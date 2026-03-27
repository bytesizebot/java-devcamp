package za.co.entelect.java_devcamp.mapper;

import org.springframework.stereotype.Component;
import za.co.entelect.java_devcamp.dto.FulfilmentTypeDto;
import za.co.entelect.java_devcamp.entity.FulfilmentType;

@Component
public class FulfilmentMapper {

    public FulfilmentType toEntity(FulfilmentTypeDto fulfilmentTypeDto){
        return new FulfilmentType(
                fulfilmentTypeDto.name(),
                fulfilmentTypeDto.description(),
                fulfilmentTypeDto.product()
        );
    }

    public FulfilmentTypeDto fulfilmentTypeDto(FulfilmentType fulfilmentType){
        return new FulfilmentTypeDto(
                fulfilmentType.getName(),
                fulfilmentType.getDescription(),
                fulfilmentType.getProduct()
        );
    }
}
