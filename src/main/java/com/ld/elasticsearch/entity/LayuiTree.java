package com.ld.elasticsearch.entity;

import lombok.Data;

import java.util.List;

/**
 * layuiTree的树形结构
 */
@Data
public class LayuiTree {
    private Integer id;
    private String name;
    private Integer parentId;
    private String url;
    private List<LayuiTree> children;

    public LayuiTree(Integer id, String name, Integer parentId, String url, List<LayuiTree> children) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.url = url;
        this.children = children;
    }
    public LayuiTree() {
    }
}
