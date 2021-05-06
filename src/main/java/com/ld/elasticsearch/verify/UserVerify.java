package com.ld.elasticsearch.verify;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@ApiModel(value = "后台用户验证")
public class UserVerify {
    private Integer id;
    @NotEmpty(message = "名称不能为空")
    @ApiModelProperty(value = "名称")
    @Pattern(regexp = "^[A-Za-z0-9_]+$", message = "请输入数字及大小写字母作为用户名")
    private String name;

    @NotEmpty(message = "密码不能为空")
    @ApiModelProperty(value = "密码")
    @Pattern(regexp = "^[0-9a-zA-Z_]{6,20}$", message = "输入6-20位的数字及大小写字母作为密码")
    private String pwd;

    @NotEmpty(message = "角色不能为空")
    @ApiModelProperty(value = "角色id")
    private List<Integer> roleId;
}
