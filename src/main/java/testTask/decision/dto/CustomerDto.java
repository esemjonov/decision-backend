package testTask.decision.dto;

import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;


@Getter
@Setter
@Builder
@ApiModel(value = "Customer")
public class CustomerDto {
    @ApiModelProperty(notes = "Customer ID")
    private Long id;

    @ApiModelProperty(notes = "Customer identitycode")
    private String identityCode;
    
    @ApiModelProperty(notes = "Customer credditModifer")
    private String credditModifer;


}
