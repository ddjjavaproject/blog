package com.ld.elasticsearch.controller.admin;

import com.ld.elasticsearch.common.Function;
import com.ld.elasticsearch.entity.LayuiTree;
import com.ld.elasticsearch.entity.mysql.MFunction;
import com.ld.elasticsearch.service.FunctionService;
import com.ld.elasticsearch.utils.ResponseLayuiResult;
import com.ld.elasticsearch.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/function")
@Slf4j
@Api(tags = "后台菜单相关操作")
public class FunctionController {
    private static final int pagesize = 10;
    @Autowired
    private FunctionService functionService;
    @GetMapping("/findByName")
    @ApiOperation("通过name模糊匹配用户")
    @ResponseBody
    public Object findByName(@RequestParam("name") String name){
        List<MFunction> functionList = new ArrayList<>();
        try{
            functionList = functionService.findByName(name);
            log.info("查询的信息：{}",functionList);
        }catch (Exception e){
            log.error("functionController-findByName-根据name查询菜单异常：" + e.getMessage());
        }
        return ResultUtil.success(functionList);
    }
    @PostMapping(value = "/addEdit", produces = "application/json;charset=UTF-8")
    @ApiOperation("添加或修改菜单，注意当前节点下菜单的重复以及验证规则")
    @ResponseBody
    public Object addEdit(@RequestBody MFunction mFunction){
        Object save = functionService.save(mFunction);
        log.info("save:{}",save);
        return save;
    }
    @PostMapping(value = "/delete")
    @ApiOperation("删除节点")
    @ResponseBody
    public Object delete(@RequestParam(value = "ids",defaultValue = "") @ApiParam("ids") String ids){
        List<Integer> idsList = Function.idsRes(ids);
        functionService.delete(idsList);
        return ResultUtil.success();
    }
    @PostMapping("/list")
    @ApiOperation("菜单列表接口")
    @ResponseBody
    public Object list(@RequestParam("page") @ApiParam("page当前页码 从0开始") Integer page,
                       @RequestParam(value = "name",defaultValue = "") @ApiParam("name名称查询使用") String name,
                       @RequestParam(value = "url",defaultValue = "") @ApiParam("url") String url){
        Pageable pageable  = PageRequest.of(page-1, pagesize, Sort.Direction.DESC, "id");
        MFunction mFunction = new MFunction();
        ExampleMatcher matcher = ExampleMatcher.matching();
        matcher = matcher.withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)  ;//改变默认字符串匹配为:模糊查询
        //.withMatcher("name", ExampleMatcher.GenericPropertyMatchers.startsWith())  //name字段开头模糊匹配
        //.withMatcher("name", ExampleMatcher.GenericPropertyMatchers.endsWith())  //name字段结尾模糊匹配
        //.withIgnorePaths("id","phone"); //忽略id,phone字段
        if(null != name && !name.equals("")){
            mFunction.setName(name);
            matcher = matcher.withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains());
        }
        if(null != url && !url.equals("")){
            mFunction.setUrl(url);
            matcher = matcher.withMatcher("url", ExampleMatcher.GenericPropertyMatchers.contains());
        }
        Example<MFunction> of = Example.of(mFunction, matcher);
        Page<MFunction> list = functionService.list(of, pageable);
        if(list.getTotalElements() > 0){
            return ResponseLayuiResult.createBySuccess(list.getTotalElements(), list.getContent());
        }else{
            return ResponseLayuiResult.createByNull("无数据");
        }
    }
    @GetMapping("/index")
    public String index(){
        return "function_index";
    }

    @GetMapping("/_function")
    public String addEditShow(@RequestParam(value = "id",defaultValue = "0") Integer id,Model model){
        if(id>0){
            MFunction mFunction = functionService.findById(id);
            model.addAttribute("mFunction", mFunction);
            if(mFunction.getParentId() > 0){
                String name = functionService.findById(mFunction.getParentId()).getName();
                model.addAttribute("name", name);
            }
        }else{
            model.addAttribute("mFunction", new MFunction());
        }
        return "_function";
    }
    @GetMapping("/treeList")
    @ResponseBody
    public List<LayuiTree> treeList(@RequestParam(name = "id",required = false) Integer id){
        List<LayuiTree> treeList = new ArrayList<>();
        if(null != id && id != 0){

        }else{
            treeList = functionService.selectFunctionTree();
        }
        return treeList;
    }
}
