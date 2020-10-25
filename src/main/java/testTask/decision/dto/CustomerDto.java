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

    @ApiModelProperty(notes = "Customer identityCode")
    private String identityCode;

    @ApiModelProperty(notes = "Customer creditModifier")
    private String creditModifier;

}
