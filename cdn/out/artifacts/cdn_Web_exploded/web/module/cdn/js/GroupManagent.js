/**
 * Created by xzl on 2017/5/29.
 */
var ugroupid =0;
var zTreeObj1;
// zTree 的参数配置，深入使用请参考 API 文档（setting 配置详解）
var setting1 = {
    view:{
        showLine:false
    },
    edit:{
        enable: true,
        showRemoveBtn: true,
        removeTitle: "remove",
        showRenameBtn: true,
        renameTitle: "rename"

    },
    callback: {
        onClick: zTreeOnClick1,
        beforeRemove: zTreeBeforeRemove1,
        beforeRename: zTreeBeforeRename1,
        onRename:zTreeOnRename1,
        onRemove:zTreeOnRemove1
    }
};
var zNodes1 = [
];
function zTreeBeforeRemove1(treeId, treeNode){
    layer.open({
        type: 1,
        content: '<div style="padding: 20px 100px;">'+ '确定删除该节点' +'</div>',//这里content是一个普通的String
        btn: ['确认', '取消']
        ,btnAlign: 'c' //按钮居中
        ,shade: 0 //不显示遮罩
        ,yes: function(){
            layer.closeAll();
            return true;
        },
        btn2:function(){
            layer.closeAll();
            return false;
        }
    });
}
function zTreeOnRemove1(event, treeId, treeNode){

    //清空右边列表
    if(ugroupid ==treeNode.id){
        $("#rights").empty();
    }
 //删除权限表中该用户组
    $.ajax({
        type: 'post',
        data: {
            "ugroupid": treeNode.id
        },
        url: AppBase + '/usergroupinfo.do?method=request16',
        dataType: 'json',
        success: function (data) {
            if(data.msg=='删除用户组失败'){
                layer.open({
                    type: 1,
                    content: '<div style="padding: 20px 100px;">'+ data.msg +'</div>',//这里content是一个普通的String
                    btn: '关闭全部'
                    ,btnAlign: 'c' //按钮居中
                    ,shade: 0 //不显示遮罩
                    ,yes: function(){
                        layer.closeAll();
                    }
                });
            }
        }
    })
}
function zTreeBeforeRename1(treeId, treeNode, newName, isCancel){
    if(newName.length==0){
        return false;
    }else if(newName.indexOf(" ")>=0){
        return false;
    } else if(newName == treeNode.name){
        return false;
    } else
    {
        return true;
    }
}
function zTreeOnRename1(event, treeId, treeNode, isCancel){

   //修改ztee2中的name
    //修改数据库中的表中的name
    $.ajax({
        type: 'post',
        data: {
            "ugroupid": treeNode.id,
            "ugroupname": treeNode.name
        },
        url: AppBase + '/usergroupinfo.do?method=request15',
        dataType: 'json',
        success: function (data) {
            if(data.msg=='修改用户组名称失败'){
                layer.open({
                    type: 1,
                    content: '<div style="padding: 20px 100px;">'+ data.msg +'</div>',//这里content是一个普通的String
                    btn: '关闭全部'
                    ,btnAlign: 'c' //按钮居中
                    ,shade: 0 //不显示遮罩
                    ,yes: function(){
                        layer.closeAll();
                    }
                });
            }

        }
    })
}
//-----------------------------------------------------------------------------------
var zTreeObj2;
// zTree 的参数配置，深入使用请参考 API 文档（setting 配置详解）
var setting2 = {
    check:{
        enable: true,
        chkboxType : { "Y" : "s", "N" : "s" },
        chkStyle : "checkbox",
        autoCheckTrigger: true
    },
    view:{
        showLine:false
    },
    callback: {

    }
};
// zTree 的数据属性，深入使用请参考 API 文档（zTreeNode 节点数据详解）
var zNodes2 = [
];

$(document).ready(function(){
    $.ajax({
        type:'GET',
        url: AppBase + '/usergroupinfo.do?method=request1',
        dataType: 'json',
        success: function (data){
            zNodes1 = data.data.children;
           zTreeObj1 = $.fn.zTree.init($("#tree-nav"), setting1, zNodes1);

        }
    });
});

function zTreeOnClick1(event, treeId, treeNode) {
    //alert(treeNode.tId + ", " + treeNode.name);
    //alert(node.id) //node即为当前点击的节点数据
    ugroupid =treeNode.id;
    $.ajax({
        type:'post',
        data: {"id":treeNode.id},
        url: AppBase + '/usergroupinfo.do?method=request6',
        dataType: 'json',
        success: function (data) {
            zNodes2 = data.data.children;
            zTreeObj2 = $.fn.zTree.init($("#rights"), setting2, zNodes2);
        }
    });
}

function popupbox(type){
    var active = {
        editrights: function(){
            //将树形菜单的所有checkbox状态变为可编辑
            var zTree = $.fn.zTree.getZTreeObj("rights");
            var nodes = zTree.getNodes();
            for (var i = 0; i < nodes.length; i++) {
                var node = nodes[i];
                node.chkDisabled = false; //表示显示checkbox
                zTree.updateNode(node);
            }

        },
        confirm:function(){
            var li =[];
            //获取树形菜单所有的checkbox状态并改为不可编辑状态，将获取到的checkbox状态写入数据库
            var zTree = $.fn.zTree.getZTreeObj("rights");
            var nodes = zTree.getNodes();
            for (var i = 0; i < nodes.length; i++) {
                var node = nodes[i];
                if(node.checked ==true){
                    li.push(node.id);
                }
            }
            $.ajax({
                type:'post',
                data: {"menuid":JSON.stringify(li),
                        "ugroupid":ugroupid},
                url: AppBase + '/usergroupinfo.do?method=request8',
                dataType: 'json',
                success: function (data) {
                    //修改成功
                    if(data.msg !=null) {
                        if (data.msg="修改权限成功") {
                            for (var i = 0; i < nodes.length; i++) {
                                var node = nodes[i];
                                node.chkDisabled = true; //表示显示checkbox
                                zTree.updateNode(node);
                            }
                        }else{
                            alert("data.msg");
                        }
                    }
                }
            });

        },
        addgroup: function () {
            var zTreeObj = $.fn.zTree.getZTreeObj("tree-nav");
            var id=1110+zTreeObj.getNodes().length+1;
            var newnode ={id:id,name:'NewNode',pId:0};
            //新节点写入数据库
            $.ajax({
                type:'post',
                data: {
                    "ugroupid": id,
                    "ugroupname": 'NewNode',
                    "uparentgroupid":0
                },
                url: AppBase + '/usergroupinfo.do?method=request14',
                dataType: 'json',
                success: function (data) {
                    //修改成功
                    if(data.msg =='新增用户组失败') {
                        layui.use('layer', function() {
                            var layer = layui.layer;
                            layer.open({
                                type: 1,
                                content: '<div style="padding: 20px 100px;">'+ data.msg +'</div>',//这里content是一个普通的String
                                btn: '关闭全部'
                                ,btnAlign: 'c' //按钮居中
                                ,shade: 0 //不显示遮罩
                                ,yes: function(){
                                    layer.closeAll();
                                }
                            });
                        })
                    }else
                    {
                        zTreeObj.addNodes(null,-1,newnode,false);
                    }
                }
            });

        }
    };
    if(type =='editrights'){
        active.editrights();
    }else if(type =='confirm'){
        active.confirm()
    }else if(type='addgroup'){
        active.addgroup();
    }
}

