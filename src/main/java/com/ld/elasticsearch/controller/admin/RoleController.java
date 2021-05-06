package com.ld.elasticsearch.controller.admin;

import com.ld.elasticsearch.common.Function;
import com.ld.elasticsearch.entity.mysql.MRole;
import com.ld.elasticsearch.enums.ResultEnum;
import com.ld.elasticsearch.service.RoleService;
import com.ld.elasticsearch.utils.ResponseLayuiResult;
import com.ld.elasticsearch.utils.ResultUtil;
import com.ld.elasticsearch.verify.RoleVerify;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.ui.Model;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@Slf4j
@Api(tags = "后台管理员相关操作")
@RequestMapping("/role")
public class RoleController {
    private static final int pagesize = 10;
    @Autowired
    private RoleService roleService;
    @GetMapping("/findById")
    @ApiOperation("通过id查询角色详情")
    public Object findById(@RequestParam("id") Integer id){
        MRole user = new MRole();
        try{
            user = roleService.findById(id);
        }catch (Exception e){
            log.error("RoleController-findById-根据id查询用户异常：" + e.getMessage());
        }
        log.info("RoleController-findById-user:"+user.getName());
        return user;
    }
    @PostMapping(value = "/addEdit", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation("添加或修改管理员，注意用户名的重复以及验证规则")
    public Object addEdit(@RequestBody @Valid RoleVerify roleVerify, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            log.error("roleController-addEdit-添加或修改用户出错：" + bindingResult.getFieldError().getDefaultMessage());
            return ResultUtil.error(ResultEnum.NAME_PWD_FORMAT_ERROR);
        }
        MRole role = new MRole();
        //验证用户名的唯一性
        BeanUtils.copyProperties(roleVerify, role);
        Boolean nameUnique = roleService.checkNameUnique(role);
        if(nameUnique){
            return ResultUtil.error(ResultEnum.NAME_EXISTS_ERROR);
        }else{
            roleService.save(role);
            log.info("roleController-addEdit-user:{}",role);
            return ResultUtil.success(role);
        }
    }
    @PostMapping("/list")
    @ApiOperation("角色列表，分页查询")
    @ResponseBody
    public Object list(@RequestParam("page") @ApiParam("page当前页码 从0开始") Integer page,
                       @RequestParam(value = "name",defaultValue = "") @ApiParam("用户名") String name){
        Pageable pageable  = PageRequest.of(page - 1, pagesize, Sort.Direction.DESC, "id");
        MRole mRole = new MRole();
        ExampleMatcher matcher = ExampleMatcher.matching();
        matcher = matcher.withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)  ;//改变默认字符串匹配为:模糊查询
        if(null != name && !name.equals("")){
            mRole.setName(name);
            matcher = matcher.withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains());
        }
        log.info("roleController-list-name:{}",name);
        Example<MRole> of = Example.of(mRole, matcher);
        Page<MRole> list = roleService.list(of, pageable);
        if(list.getTotalElements() > 0){
            return ResponseLayuiResult.createBySuccess(list.getTotalElements(), list.getContent());
        }else{
            return ResponseLayuiResult.createByNull("无数据");
        }
    }
    @GetMapping("/list")
    @ApiOperation("角色列表，获取所有角色")
    @ResponseBody
    public Object list(){
        List<MRole> list = roleService.list();
        if(list.size() > 0){
            return ResponseLayuiResult.createBySuccess(list.size(), list);
        }else{
            return ResponseLayuiResult.createByNull("无数据");
        }
    }
    @GetMapping("/index")
    public String index(){
        return "role_index";
    }
    @GetMapping("/_role")
    public String addEditShow(@RequestParam(value = "id",defaultValue = "0") Integer id, Model model){
        if(id>0){
            MRole mRole = roleService.findById(id);
            model.addAttribute("mRole", mRole);
        }else{
            model.addAttribute("mRole", new MRole());
        }
        return "_user";
    }
    @PostMapping(value = "/delete")
    @ApiOperation("删除节点")
    @ResponseBody
    public Object delete(@RequestParam(value = "ids",defaultValue = "") @ApiParam("ids") String ids){
        List<Integer> idsList = Function.idsRes(ids);
        roleService.delete(idsList);
        return ResultUtil.success();
    }
}
