package com.ld.elasticsearch.verify;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@ApiModel(value = "后台角色验证")
public class RoleVerify {
    private Integer id;
    @NotEmpty(message = "名称不能为空")
    @ApiModelProperty(value = "名称")
    @Pattern(regexp = "^[\u4e00-\u9fa5]+$", message = "输入汉字")
    private String name;
}
