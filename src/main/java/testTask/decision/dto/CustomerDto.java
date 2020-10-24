package testTask.decision.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@ApiModel(value = "Customer")
public class CustomerDto {

    @ApiModelProperty(notes = "Customer ID")
    private Long id;

    @ApiModelProperty(notes = "Customer identitycode")
    private String identitycode;

    @ApiModelProperty(notes = "Customer creditModifier")
    private String creditModifier;

}
