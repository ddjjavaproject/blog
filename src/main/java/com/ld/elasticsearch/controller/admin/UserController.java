package com.ld.elasticsearch.controller.admin;

import com.ld.elasticsearch.common.Function;
import com.ld.elasticsearch.context.LoginUserCache;
import com.ld.elasticsearch.entity.LayuiTree;
import com.ld.elasticsearch.entity.mysql.MRoleFunction;
import com.ld.elasticsearch.entity.mysql.MUser;
import com.ld.elasticsearch.entity.mysql.MUserRole;
import com.ld.elasticsearch.enums.ResultEnum;
import com.ld.elasticsearch.service.*;
import com.ld.elasticsearch.utils.ResponseLayuiResult;
import com.ld.elasticsearch.utils.ResultUtil;
import com.ld.elasticsearch.verify.UserVerify;
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
@Api(tags = "后台管理员相关操作")
@RequestMapping("/user")
public class UserController {
    private static final int pagesize = 10;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RoleFunctionService roleFunctionService;
    @Autowired
    private FunctionService functionService;
    @GetMapping("/findById")
    @ApiOperation("通过id查询用户详情")
    public Object findById(@RequestParam("id") Integer id){
        MUser user = new MUser();
        try{
            user = userService.findById(id);
        }catch (Exception e){
            log.error("userController-findById-根据id查询用户异常：" + e.getMessage());
        }
        log.info("userController-findById-user:"+user.getName());
        return user;
    }
    @PostMapping(value = "/addEdit", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation("添加或修改管理员，注意用户名的重复以及验证规则")
    public Object addEdit(@RequestBody @Valid UserVerify userVerify, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            log.error("userController-addEdit-添加或修改用户出错：" + bindingResult.getFieldError().getDefaultMessage());
            return ResultUtil.error(ResultEnum.NAME_PWD_FORMAT_ERROR);
        }
        MUser user = new MUser();
        List<Integer> roleIdList = userVerify.getRoleId();
        //验证用户名的唯一性
        BeanUtils.copyProperties(userVerify, user);
        Boolean nameUnique = userService.checkNameUnique(user);
        if(nameUnique){
            return ResultUtil.error(ResultEnum.NAME_EXISTS_ERROR);
        }else{
            userService.save(user);
            //保存用户和角色关联 首先将当前用户对应的角色删除，然后增加
            userRoleService.delete(user.getId());

            log.info("userController-addEdit-user:{}",user);
            return ResultUtil.success(user);
        }
    }
    @PostMapping("/list")
    @ApiOperation("用户列表")
    @ResponseBody
    public Object list(@RequestParam("page") @ApiParam("page当前页码 从0开始") Integer page,
                       @RequestParam(value = "name",defaultValue = "") @ApiParam("用户名") String name){
        Pageable pageable  = PageRequest.of(page - 1, pagesize, Sort.Direction.DESC, "id");
        MUser mUser = new MUser();
        ExampleMatcher matcher = ExampleMatcher.matching();
        matcher = matcher.withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)  ;//改变默认字符串匹配为:模糊查询
        if(null != name && !name.equals("")){
            mUser.setName(name);
            matcher = matcher.withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains());
        }
        Example<MUser> of = Example.of(mUser, matcher);
        Page<MUser> list = userService.list(of, pageable);
        if(list.getTotalElements() > 0){
            return ResponseLayuiResult.createBySuccess(list.getTotalElements(), list.getContent());
        }else{
            return ResponseLayuiResult.createByNull("无数据");
        }
    }
    /**
     * 登录验证
     * @param mUser
     * @return
     */
    @PostMapping("/login")
    @ApiOperation("登录")
    @ResponseBody
    public Object login(@RequestBody MUser mUser){
        log.info("userController-login-传递的数据：{}", mUser);
        try{
            if (mUser == null) {
                return ResultUtil.error(ResultEnum.NAME_PWD_ERROR);
            }else {
                MUser user = userService.findByNameAndPwd(mUser.getName(), mUser.getPwd());
                if(user != null){
                    LoginUserCache.put(user, 60* 30);
                    return ResultUtil.success();
                }else{
                    return ResultUtil.error(ResultEnum.NAME_PWD_ERROR);
                }
            }
        }catch (Exception e){
            log.error("userController-login-根据用户名查询用户异常：" + e.getMessage());
        }
        return ResultUtil.error(ResultEnum.NAME_PWD_ERROR);
    }
    @GetMapping("/functionList")
    @ApiOperation("用户所拥有的权限列表")
    @ResponseBody
    public Object functionList(){
        //通过缓存获取用户id
        //查询权限
        //根据用户信息 查询用户对应的角色 然后根据角色关联对应的权限
        List<MUserRole> roles = userRoleService.findRoleByUserId(1);

        List<Integer> roleIdsList = roles.stream().map(MUserRole::getRoleId).collect(Collectors.toList());

        //如果是超级管理员那么需要展示所有的 不是的话 根据角色id进行查询权限信息
        Boolean flag = false;
        Iterator<Integer> iterator = roleIdsList.iterator();
        while(iterator.hasNext()) {
            if(iterator.next().intValue() == 1){
                flag = true;
                break;
            }
        }
        log.info("flag的值{}",flag);
        List<LayuiTree> treeList = new ArrayList<>();
        if(flag){
            treeList = functionService.selectFunctionTree();
        }else{
            List<MRoleFunction> serviceByRoleIdIn = roleFunctionService.findByRoleIdIn(roleIdsList);
            List<Integer> functionIdsList = serviceByRoleIdIn.stream().map(MRoleFunction::getFunctionId).collect(Collectors.toList());
            treeList = functionService.selectFunctionTree(functionIdsList);
        }
        return ResultUtil.success(treeList);
    }

    @GetMapping("/index")
    public String index(){
        return "user_index";
    }
    @GetMapping("/_user")
    public String addEditShow(@RequestParam(value = "id",defaultValue = "0") Integer id, Model model){
        model.addAttribute("roleList", roleService.list());
        if(id>0){
            MUser mUser = userService.findById(id);
            model.addAttribute("mUser", mUser);
        }else{
            model.addAttribute("mUser", new MUser());
        }
        return "_user";
    }
    @PostMapping(value = "/delete")
    @ApiOperation("删除节点")
    @ResponseBody
    public Object delete(@RequestParam(value = "ids",defaultValue = "") @ApiParam("ids") String ids){
        List<Integer> idsList = Function.idsRes(ids);
        userService.delete(idsList);
        return ResultUtil.success();
    }
}
