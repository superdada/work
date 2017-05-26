/**
 * Created by xzl on 2017/5/25.
 */

layui.use('tree', function(){
    layui.tree({
        elem: '#tree-nav' //传入元素选择器
        ,nodes: [{ //节点
            name: '父节点1'
            ,children: [{
                name: '子节点11'
            },{
                name: '子节点12'
            }]
        },{
            name: '父节点2'
            ,children: [{
                name: '子节点21'
                ,children: [{
                    name: '子节点211'
                }]
            }]
        }]
    });
});
$(function () {})

