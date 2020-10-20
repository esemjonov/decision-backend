package testTask.decision.dto;

import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Column;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Customer")
public class CustomerDto {

    @ApiModelProperty(notes = "Customer ID")
    private Long id;

    @ApiModelProperty(notes = "Customer identitycode")
    private String identitycode;
    
    @ApiModelProperty(notes = "Customer creditModifier")
    private String creditModifier;

}
