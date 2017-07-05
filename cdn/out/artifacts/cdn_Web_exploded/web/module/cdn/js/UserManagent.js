/**
 * Created by xzl on 2017/5/29.
 */
var userHtml = "";
var nums = 9; //每页出现的数据量
var currpage=0;
var totalpage=0;
var datalength=0;
var osid = {};//所属部门
var _vmData = {};
var sextransfer={1:'男',2:'女'};


var zTreeObj;
// zTree 的参数配置，深入使用请参考 API 文档（setting 配置详解）
var setting = {
    view:{
        showLine:false,
        addHoverDom: addHoverDom, //当鼠标移动到节点上时，显示用户自定义控件
        removeHoverDom: removeHoverDom, //离开节点时的操作
    },
    edit:{
        enable: true,
        showRemoveBtn: true,
        removeTitle: "remove",
        showRenameBtn: true,
        renameTitle: "rename"

    },
    callback: {
        onClick: zTreeOnClick,
        beforeRemove: zTreeBeforeRemove,
        beforeRename: zTreeBeforeRename,
        onRename:zTreeOnRename,
        onRemove:zTreeOnRemove
    }
};
// zTree 的数据属性，深入使用请参考 API 文档（zTreeNode 节点数据详解）
var zNodes = [
];
$(document).ready(function(){
    $.ajax({
        type:'GET',
        url: AppBase + '/usergroupinfo.do?method=request9',
        dataType: 'json',
        success: function (data){
            zNodes = data.data.children;
            zTreeObj = $.fn.zTree.init($("#tree-nav"), setting, zNodes);
        }
    });
});
function addHoverDom(treeId, treeNode) {
    var sObj = $("#" + treeNode.tId + "_span"); //获取节点信息
    if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;

    var addStr = "<span class='button add' id='addBtn_" + treeNode.tId + "' title='add node' onfocus='this.blur();'></span>"; //定义添加按钮
    sObj.after(addStr); //加载添加按钮
    var btn = $("#addBtn_"+treeNode.tId);

    //绑定添加事件，并定义添加操作
    if (btn) btn.bind("click", function(){
        var zTree = $.fn.zTree.getZTreeObj("tree-nav");
        //将新节点添加到数据库中
        var name='NewNode';
        $.ajax({
            type: 'post',
            data: {
                "osparentid": treeNode.id,
                "osparentname":treeNode.name,
                "osname":name
            },
            url: AppBase + '/usergroupinfo.do?method=request13',
            dataType: 'json',
            success: function (data) {
                var newID = data.data.osid; //获取新添加的节点Id
                zTree.addNodes(treeNode, {id:newID, pId:treeNode.id, name:name}); //页面上添加节点
                var node = zTree.getNodeByParam("id", newID, null); //根据新的id找到新添加的节点
                zTree.selectNode(node); //让新添加的节点处于选中状态
                }
        })
    });
}
function removeHoverDom(treeId, treeNode) {
    $("#addBtn_"+treeNode.tId).unbind().remove();
};
function zTreeOnClick(event, treeId, treeNode) {
    //alert(treeNode.tId + ", " + treeNode.name);
    //alert(node.id) //node即为当前点击的节点数据
    $.ajax({
        type:'post',
        data: {"id":treeNode.id},
        url: AppBase + '/usergroupinfo.do?method=request2',
        dataType: 'json',
        success: function (data) {
            if (data.data != null) {
               osid.id = treeNode.id;
               osid.name = treeNode.name;
               //osid =treeNode;
                vmData(data.data);
                //初始化
                currpage =1;
                $("#usertable tbody").empty();
                userHtml = "";
                if(_vmData!=null) {
                    for (var i = 0; i < _vmData["page1"].length; i++) {
                        userHtml += "<tr>";
                        userHtml += "<td>" + _vmData["page1"][i].username + "</td>";
                        userHtml += "<td>" + _vmData["page1"][i].name + "</td>";
                        userHtml += "<td>" + sextransfer[_vmData["page1"][i].sex] + "</td>";
                        userHtml += "<td>" + _vmData["page1"][i].tel + "</td>";
                        userHtml += "<td>" + osid.name + "</td>";
                        userHtml += "<td>" + _vmData["page1"][i].CreateTime + "</td>";
                        userHtml += "<td>" + _vmData["page1"][i].LastLoginTime + "</td>";
                        userHtml += "</tr>";
                    }
                    $("#userinfo").append(userHtml);
                    $(".my-page-text").text(currpage+"/"+totalpage);
                    BingEvent();
                }
            }
        }
    });
}

function zTreeBeforeRemove(treeId, treeNode){
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

function zTreeOnRemove(event, treeId, treeNode){

    if(osid.id == treeNode.id){
        //清空对象
        for(var key in osid){
            delete osid[key];
        }
        for(var key in vmData){
            delete vmData[key];
        }
        $("#usertable tbody").empty();

    }
    $.ajax({
        type: 'post',
        data: {
            "osid": treeNode.id
        },
        url: AppBase + '/usergroupinfo.do?method=request12',
        dataType: 'json',
        success: function (data) {
            if(data.msg=='机构名称删除失败'){
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
function zTreeBeforeRename(treeId, treeNode, newName, isCancel){
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
function zTreeOnRename(event, treeId, treeNode, isCancel){

    if(osid.id == treeNode.id){
        osid.id =treeNode.id;
        osid.name =treeNode.name;
        tablecontent(currpage);
      }
    $.ajax({
        type: 'post',
        data: {
            "osid": treeNode.id,
            "osname": treeNode.name
        },
        url: AppBase + '/usergroupinfo.do?method=request11',
        dataType: 'json',
        success: function (data) {
            if(data.msg=='数据库异常,机构名称修改失败'){
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
function BingEvent(){
    $("#userinfo tr").bind("click",function(){
        $(this).parent().find("tr").removeClass("trSelected");
        $(this).addClass("trSelected");
    });
}

function  vmData(data){
    datalength =data.length;
    totalpage =Math.ceil(datalength/nums);
    if(datalength <= nums ) {
        _vmData["page1"] = data;
    } else {
        for(var d = 1; d <= Math.ceil(datalength/nums) ; d++) {
            var _li = [];
            if(d!=Math.ceil(datalength /nums)){
                for(var j=1; j < nums+1 ; j++){
                    _li.push(data[nums*(d-1)+(j-1)]);
                };
            }else{
                for(var j=1; j < datalength%nums+1 ; j++){
                    _li.push(data[nums*(d-1)+(j-1)]);
                };
            }
            _vmData["page"+d] = _li;
        }
    };
}
function changePage(_type) {
    if (_type=='Previous') {
        if (currpage < 2) {
            return 0;
        } else {
            currpage = currpage - 1;
            tablecontent(currpage);
        }
    } else if (_type=='next') {
        if (currpage < totalpage ) {
            currpage = currpage + 1;
            tablecontent(currpage);
        } else {
            return 0;
        }
    } else if (_type=='jump') {
        var _pageNum_input =  $("input[id='my-page-num']").val();
        var _pageNum = _pageNum_input-0;
        _pageNum = _pageNum*1;
        var re = /^\+?[1-9][0-9]*$/;　　//正整数
        if ( (re.test(_pageNum)) && (_pageNum <= totalpage) ) {
            currpage = _pageNum;
            tablecontent(currpage);
        } else {
            alert("请输入1到"+totalpage+"的整数")
            return 0;
        }
    } else
        return 0;
}
function tablecontent(current_page) {
    $("#usertable tbody").empty();
    userHtml = "";
    if (_vmData != null) {
        for (var i = 0; i < _vmData["page" + current_page].length; i++) {
            userHtml += "<tr>";
            userHtml += "<td>" + _vmData["page" + current_page][i].username + "</td>";
            userHtml += "<td>" + _vmData["page" + current_page][i].name + "</td>";
            userHtml += "<td>" + sextransfer[_vmData["page" + current_page][i].sex] + "</td>";
            userHtml += "<td>" + _vmData["page" + current_page][i].tel + "</td>";
            userHtml += "<td>" + osid.name + "</td>";
            userHtml += "<td>" + _vmData["page" + current_page][i].CreateTime + "</td>";
            userHtml += "<td>" + _vmData["page" + current_page][i].LastLoginTime + "</td>";
            userHtml += "</tr>";
        }
        $("#userinfo").append(userHtml);
        $(".my-page-text").text(currpage + "/" + totalpage);
        BingEvent();
    }
}
function popupbox(type){
    layui.use('layer', function() {
        var layer = layui.layer;
        //触发事件
        var active = {
            adduser:function(){
                layer.open({
                    type:  2,
                    title: '新建用户',
                    content:['adduser/AddUser.html', 'no'],
                    area: ['540px', '420px'],
                    shade: 0,
                    maxmin: true,
                    btn: ['确认', '取消'],
                    yes: function(){
                        //通过后写入数据库
                        var body = layer.getChildFrame('body');
                        var  username = body.find("input[name='username']").val();
                        var  password = body.find("input[name='password']").val();
                        var  realname = body.find("input[name='realname']").val();
                        var  tel = body.find("input[name='tel']").val();
                        var  sex = body.find("input[name='sex']:checked").val();
                        $.ajax({
                            type:'post',
                            data: {
                                "username":username,
                                "password":password,
                                "realname":realname,
                                "osid":osid.id,
                                "tel":tel,
                                "sex":sex
                            },
                            url: AppBase + '/usergroupinfo.do?method=request3',
                            dataType: 'json',
                            success: function (data) {
                                if (data.data != null) {
                                    layer.closeAll();
                                    if(data.data.error == null){
                                        //界面跳到
                                        if(totalpage == 0){
                                            totalpage =1;
                                        }
                                        if (_vmData["page" + totalpage].length < nums) {
                                            _vmData["page" + totalpage].push(data.data);
                                            currpage = totalpage;
                                            tablecontent(currpage);
                                        } else {
                                            totalpage = totalpage + 1;
                                            currpage = totalpage;
                                            var _li = [];
                                            _li.push(data.data)
                                            _vmData["page" + totalpage]=_li;
                                            tablecontent(currpage);
                                        }
                                    }else {
                                        layer.closeAll();
                                        layer.open({
                                            type: 1,
                                            content: '<div style="padding: 20px 100px;">'+ data.data.error +'</div>',//这里content是一个普通的String
                                            btn: '关闭全部'
                                            ,btnAlign: 'c' //按钮居中
                                            ,shade: 0 //不显示遮罩
                                            ,yes: function(){
                                                layer.closeAll();
                                            }
                                        });
                                    }

                                }
                            }
                        });

                    },
                    btn2: function(){
                        layer.closeAll();
                    },
                    zIndex: layer.zIndex,
                    success: function(layero){
                        layer.setTop(layero);
                    }
                });
            },
            edituser: function(){
                if($(".trSelected").text() ==""){
                    var msg ="请先选择一行用户数据";
                    layer.open({
                        type: 1,
                        content: '<div style="padding: 20px 100px;">'+ msg +'</div>',//这里content是一个普通的String
                        btn: '关闭全部'
                        ,btnAlign: 'c' //按钮居中
                        ,shade: 0 //不显示遮罩
                        ,yes: function(){
                            layer.closeAll();
                        }
                    });
                }else {
                    var username = $(".trSelected").find("td:eq(0)").text();
                    var realname = $(".trSelected").find("td:eq(1)").text();
                    var sex =1;
                    if($(".trSelected").find("td:eq(2)").text() =='女'){
                        sex =2;
                    }
                   // var sex = $(".trSelected").find("td:eq(2)").text();
                    var tel = $(".trSelected").find("td:eq(3)").text();
                    layer.open({
                        type: 2,
                        title: '编辑用户',
                        content: ['adduser/AddUser.html', 'no'],
                        area: ['540px', '420px'],
                        shade: 0,
                        maxmin: true,
                        btn: ['确认', '取消'],
                        success: function (layero, index) {
                            var body = layer.getChildFrame('body', index);
                            body.find("input[name='username']").parent().css("display", "none");
                            body.find("input[name='password']").parent().css("display", "none");
                            body.find("input[name='realname']").val(realname);
                            body.find("input[name='tel']").val(tel);
                            if (sex == '1') {
                                body.find("input[name='sex']:eq(1)").attr("checked", 'unchecked');
                                body.find("input[name='sex']:eq(0)").attr("checked", 'checked');
                            } else if (sex == '2') {
                                body.find("input[name='sex']:eq(0)").attr("checked", 'unschecked');
                                body.find("input[name='sex']:eq(1)").attr("checked", 'checked');
                            }
                        },
                        yes: function () {
                            var body = layer.getChildFrame('body');
                            $.ajax({
                                type: 'post',
                                data: {
                                    "username": username,
                                    "realname": body.find("input[name='realname']").val(),
                                    "osid": osid.id,
                                    "tel": body.find("input[name='tel']").val(),
                                    "sex": body.find("input[name='sex']:checked").val()
                                },
                                url: AppBase + '/usergroupinfo.do?method=request4',
                                dataType: 'json',
                                success: function (data) {
                                    if (data.data != null) {
                                        layer.closeAll();
                                        $(".trSelected").find("td:eq(1)").text(data.data.name);
                                        $(".trSelected").find("td:eq(2)").text(sextransfer[data.data.sex]);
                                        $(".trSelected").find("td:eq(3)").text(data.data.tel);
                                    }
                                }
                            });
                        }
                    });
                }
            },
            modifypaswd:function(){
                if($(".trSelected").text() ==""){
                    var msg ="请先选择一行用户数据";
                    layer.open({
                        type: 1,
                        content: '<div style="padding: 20px 100px;">'+ msg +'</div>',//这里content是一个普通的String
                        btn: '关闭全部'
                        ,btnAlign: 'c' //按钮居中
                        ,shade: 0 //不显示遮罩
                        ,yes: function(){
                            layer.closeAll();
                        }
                    });
                }else {
                    var username = $(".trSelected").find("td:eq(0)").text();
                    layer.open({
                        type: 2,
                        title: '编辑用户',
                        content: ['adduser/AddUser.html', 'no'],
                        area: ['540px', '420px'],
                        shade: 0,
                        maxmin: true,
                        btn: ['确认', '取消'],
                        success: function (layero, index) {
                            var body = layer.getChildFrame('body', index);
                            body.find("input[name='username']").parent().css("display", "none");
                            body.find("input[name='realname']").parent().css("display", "none");
                            body.find("input[name='tel']").parent().css("display", "none");
                            body.find("input[name='sex']").parent().css("display", "none");
                        },
                        yes: function () {
                            var body = layer.getChildFrame('body');
                            $.ajax({
                                type: 'post',
                                data: {
                                    "username": username,
                                    "password": body.find("input[name='password']").val(),
                                    "osid": osid.id,
                                },
                                url: AppBase + '/usergroupinfo.do?method=request7',
                                dataType: 'json',
                                success: function (data) {
                                    if (data.msg != null) {
                                        layer.closeAll();
                                         //新弹出弹框，密码修改成功
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
                            });
                        }
                    });
                }
            },
            delete:function(){
                if($(".trSelected").text() ==""){
                    var msg ="请先选择一行用户数据";
                    layer.open({
                        type: 1,
                        content: '<div style="padding: 20px 100px;">'+ msg +'</div>',//这里content是一个普通的String
                        btn: '关闭全部'
                        ,btnAlign: 'c' //按钮居中
                        ,shade: 0 //不显示遮罩
                        ,yes: function(){
                            layer.closeAll();
                        }
                    });
                }else{
                    var username=$(".trSelected").find("td:eq(0)").text();
                    $.ajax({
                        type: 'post',
                        data: {
                            "username": username,
                            "osid": osid.id
                        },
                        url: AppBase + '/usergroupinfo.do?method=request5',
                        dataType: 'json',
                        success: function (data) {
                            //删除表格中的数据，重新请求数据
                            _vmData = {};
                            vmData(data.data);
                            //初始化
                            currpage =1;
                            tablecontent(currpage);
                        }
                    });
                }
            },
            allocategroup:function(){
                if($(".trSelected").text() ==""){

                    var msg ="请先选择一行用户数据";
                    layer.open({
                        type: 1,
                        content: '<div style="padding: 20px 100px;">'+ msg +'</div>',//这里content是一个普通的String
                        btn: '关闭全部'
                        ,btnAlign: 'c' //按钮居中
                        ,shade: 0 //不显示遮罩
                        ,yes: function(){
                            layer.closeAll();
                        }
                    });
                }else{
                    var username = $(".trSelected").find("td:eq(0)").text();
                    $.ajax({
                        type:'GET',
                        url: AppBase + '/usergroupinfo.do?method=request1',
                        dataType: 'json',
                        success: function (data){
                            var grouphtml="";
                            var groupdata=data.data.children;
                            //用户组默认只有一级结构，为了方便，我也使用了树形菜单
                            for(var i = 0;i<groupdata.length;i++){
                                grouphtml +='<p>';
                                grouphtml +='<input type="radio" name="group" value="'+groupdata[i].id
                                grouphtml += '"><span>'+groupdata[i].name+'</span>'
                                grouphtml +='</p>';
                            }
                            layer.open({
                                type:2,
                                title: '分配用户组权限',
                                content: ['adduser/UserGroup.html', 'no'],
                                area: ['540px', '420px'],
                                shade: 0,
                                maxmin: true,
                                btn: ['确认', '取消'],
                                success: function (layero, index) {
                                    var body = layer.getChildFrame('body', index);
                                    body.find("#radioGroup input").empty();
                                    body.find("#radioGroup").append(grouphtml);
                                },
                                yes: function () {
                                    var body = layer.getChildFrame('body');
                                    var ugroupid =body.find("input[name='group']:checked").val();
                                    $.ajax({
                                        type: 'post',
                                        data: {
                                            "username": username,
                                            "ugroupid":ugroupid
                                        },
                                        url: AppBase + '/usergroupinfo.do?method=request10',
                                        dataType: 'json',
                                        success: function (data) {
                                            layer.closeAll();
                                            //新弹出弹框，密码修改成功
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
                                    });

                                }
                            });

                        }
                    });

                }
            }
        };

        if (type == 'adduser') {
            active.adduser();
        }else if(type == 'edituser'){
            active.edituser();
        }else if(type == 'modifypaswd'){
            active.modifypaswd();
        }else if(type == 'delete'){
            active.delete();
        }else if(type == 'allocategroup'){
            active.allocategroup();
        }
    });
}


