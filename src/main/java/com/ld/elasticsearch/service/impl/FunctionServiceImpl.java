package com.ld.elasticsearch.service.impl;

import com.ld.elasticsearch.entity.LayuiTree;
import com.ld.elasticsearch.entity.mysql.MFunction;
import com.ld.elasticsearch.enums.ResultEnum;
import com.ld.elasticsearch.repository.mysql.MFunctionRepository;
import com.ld.elasticsearch.service.FunctionService;
import com.ld.elasticsearch.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class FunctionServiceImpl implements FunctionService {
    @Autowired
    private MFunctionRepository mFunctionRepository;

    @Override
    public Object save(MFunction mFunction) {
        List<MFunction> functionList = new ArrayList<>();
        try{
            if(null != mFunction.getId() && mFunction.getId() > 0){
                functionList = findByNameAndParentIdNotId(mFunction.getName(), mFunction.getParentId(), mFunction.getId());
                if(functionList.size()>0){
                    return ResultUtil.error(ResultEnum.FUNCTION_EXISTS_ERROR);
                }
            }else{
                functionList = findByNameAndParentId(mFunction.getName(), mFunction.getParentId());
                if(functionList.size()>0){
                    return ResultUtil.error(ResultEnum.FUNCTION_EXISTS_ERROR);
                }
            }
            //插入数据库
            MFunction save = mFunctionRepository.save(mFunction);
            return ResultUtil.success(save);
        }catch (Exception e){
            log.error("权限节点操作异常：{}", e.getMessage());
            return ResultUtil.error(ResultEnum.FUNCTION_ERROR);
        }
    }

    @Override
    public List<MFunction> findByName(String name) {
        List<MFunction> functionList = mFunctionRepository.queryFunction(name);
        return functionList;
    }

    @Override
    public MFunction findById(Integer id) {
        Optional<MFunction> byId = mFunctionRepository.findById(id);
        MFunction mFunction = byId.get();
        return mFunction;
    }

    @Override
    public void delete(List<Integer> idList) {
        idList.forEach(id->{
            mFunctionRepository.deleteById(id);
        });
    }

    @Override
    public List<MFunction> findByNameAndParentId(String name, Integer parentId) {
        return mFunctionRepository.findAllByNameAndParentId(name, parentId);
    }

    @Override
    public List<MFunction> findByNameAndParentIdNotId(String name, Integer parentId, Integer id) {
        return mFunctionRepository.findAllByNameAndParentIdAndIdIsNot(name, parentId, id);
    }

    @Override
    public Boolean checkNameUnique(MFunction mFunction) {
        return null;
    }

    @Override
    public Page<MFunction> list(Example<MFunction> of, Pageable pageable) {
        return mFunctionRepository.findAll(of, pageable);
    }

    @Override
    public List<LayuiTree> selectFunctionTree() {
        List<MFunction> functionList = mFunctionRepository.findAll();
        return getLayuiTrees(functionList);
    }
    @Override
    public List<LayuiTree> selectFunctionTree(List<Integer> functionIdsList) {
        List<MFunction> functionList = mFunctionRepository.findByIdIn(functionIdsList);
        return getLayuiTrees(functionList);
    }

    /**
     * 获取layui的树形结构
     * @param functionList
     * @return
     */
    private List<LayuiTree> getLayuiTrees(List<MFunction> functionList) {
        List<LayuiTree> treeList = new ArrayList<>();
        for (MFunction m : functionList) {
            if(m.getParentId() == 0){
                LayuiTree layuiTree = new LayuiTree(m.getId(), m.getName(), m.getParentId(), m.getUrl(), getChildren(m.getId(), functionList));
                treeList.add(layuiTree);
            }
        }
        return treeList;
    }

    /**
     * 获取子节点
     * @param id
     * @param mFunctionList
     * @return
     */
    public List<LayuiTree> getChildren(Integer id,List<MFunction> mFunctionList){
        List<LayuiTree> list = new ArrayList<>();
        for (MFunction m : mFunctionList) {
            if(m.getParentId().equals(id)){
                LayuiTree layuiTree = new LayuiTree(m.getId(), m.getName(), m.getParentId(), m.getUrl(), getChildren(m.getId(),mFunctionList));
                list.add(layuiTree);
            }
        }
        return list;
    }
}
