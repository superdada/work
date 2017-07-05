/**
 * Created by zhongguohua
 * on 2017/3/20.
 */
//var form;
//当前选中cdn_cache_all的值
var curr_cdn_cache_all='CDN'; //初始值保持和jsp中设置的class一致
var currntDimension = "allNet";//当前维度,默认地市 dcServer
var currentTimeType = "2";//当前时间粒度,默认小时
var initTimes = 2;//根据当前控件联动情况,会初始化3次
var currentTimes = 0;//标记当前控件初始化次数
var callbackFn;//回调函数
var callbackflag =0;//回调函数
var callbackFn_times = 3;//回调函数
var searchvalue =false; //该值专门用来判断多选框的标志，当开始选择框的时候，需要把搜索的值清空
//地市查询条件枚举
var cityNameArr = [{id: "深圳", text: "深圳"}, {id: "广州", text: "广州"}, {id: "珠海", text: "珠海"}, {id: "中山", text: "中山"},
    {id: "佛山", text: "佛山"}, {id: "汕头", text: "汕头"}, {id: "东莞", text: "东莞"}, {id: "惠州", text: "惠州"},
    {id: "潮州", text: "潮州"}, {id: "河源", text: "河源"}, {id: "汕尾", text: "汕尾"}, {id: "湛江", text: "湛江"},
    {id: "肇庆", text: "肇庆"}, {id: "梅州", text: "梅州"}, {id: "茂名", text: "茂名"}, {id: "云浮", text: "云浮"},
    {id: "阳江", text: "阳江"}, {id: "揭阳", text: "揭阳"}, {id: "清远", text: "清远"}, {id: "江门", text: "江门"},
    {id: "韶关", text: "韶关"}];
/*var cityList = [
    "深圳市", "广州市", "珠海市", "中山市", "佛山市",
    "汕头市", "东莞市", "惠州市", "潮州市", "河源市",
    "汕尾市", "肇庆市", "梅州市", "茂名市", "云浮市",
    "湛江市", "阳江市", "揭阳市", "清远市", "江门市", "韶关市"
];*/
/*var cityNameArr = [{id: "深圳", text: "深圳"},
 {id: "佛山", text: "佛山"}];
 var cityList = [
 "深圳市", "佛山市"
 ];*/

//选项框联动关系,单选框
/*var conditionReflesh = {
    'cityName1': {conId: ""},
    'cityName2': {conId: ""},
    'dataCenter':{conId: ""},
    'ICP1':{conId:'domain1'},
    'domain1':{conId: ""},
   // 'serviceTypeCategory':{conId: ""},
   // 'serviceNameCategory':{conId: ""},
    'server1':{conId: ""},
};
//多选框的处理
var conditionReflesh_multiple = {
    'ICP2':{conId:'domain2'},
    'domain2':{conId: ""},
    'server2':{conId: ""}
};*/

$(function () {
    if($.isEmptyObject(conditionReflesh) ){
        callbackFn_times = callbackFn_times -1;
    }
    if( $.isEmptyObject(conditionReflesh_multiple)){
        callbackFn_times = callbackFn_times -1;
    }
    //时间控件初始化，并给时间控件赋初值
    initTimeSelect();
    getLatesTime(true,loadChartData); //获取最新的时间
    //下拉框全部初始化
    //给所有选项框赋初始值
    //地市选项框 shcdn.c_conf_datacenter_new(city_name)
    //内容中心选项框  shcdn.c_conf_datacenter_new(content_center)
    //服务器选项框 shcdn.c_conf_datacenter_new(equipment_ip)
    //ICP选项框 shcdn.c_conf_icp_new(icp_name)
    //域名选项框  shcdn.c_conf_icp_new(http_host)，和ICP联动
    //业务分类
    //业务名称选项框
    initConditioValue(conditionReflesh,conditionReflesh_multiple,loadChartData);
    SelectBoxevent();
});

function initDiv() {
    var width = $("body").width();
    width = width > 1200 ? width : 1200;
    $("#conditionDiv").width(width);
}

//由各个页面的js初始化，给维度赋初值
/*
 1、初始化维度中的值
 2、根据维度中默认的值，加载选项框
 3、类型、维度、时间类型控件 绑定点击事件，时间控件赋初始值
*/
function initWdName(wdNameArr, initFn) {
    //callbackFn = initFn;
    var html = "";
    for (var k in wdNameArr) {
        if(curr_cdn_cache_all=='CDN'){
            $("#cdn_cache_all li").eq(0).removeClass("my-options-off").addClass("my-options-on");
            $("#cdn_cache_all li").eq(1).removeClass("my-options-on").addClass("my-options-off");
            html += '<li class="my-options-off" type=' + wdNameArr[k]['type'] + '>'
                + wdNameArr[k]['text'] + '</li>';
        }else{
            $("#cdn_cache_all li").eq(0).removeClass("my-options-on").addClass("my-options-off");
            $("#cdn_cache_all li").eq(1).removeClass("my-options-off").addClass("my-options-on");
            if(wdNameArr[k]['type']=='dataCenter'){
                html += '<li class="my-options-off" style="display:none" type=' + wdNameArr[k]['type'] + '>'
                    + wdNameArr[k]['text'] + '</li>';
            }else  {
                html += '<li class="my-options-off" type=' + wdNameArr[k]['type'] + '>'
                    + wdNameArr[k]['text'] + '</li>';
            }
        }
    }
    $("#WDSelected").empty().append(html);
    $("#WDSelected li").eq(0).removeClass("my-options-off").addClass("my-options-on");
    //匹配选项框控件，只匹配第一个
    initCondition(wdNameArr[0]['text'], [wdNameArr[0]], initFn);
    bindEvent(wdNameArr);
}
//根据维度初始化选项框的出现与隐藏
function initCondition(wdName, wdNameArr, initFn) {
    var conditions;
    for (var i = 0, len = wdNameArr.length; i < len; i++) {
        if (wdNameArr[i]['text'] == wdName) {
            conditions = wdNameArr[i]['conditions'].split(",");
        }
    }
    if (wdName) {
        var $obj = $("#WDSelected li:contains('" + wdName + "')");
        $obj.removeClass("my-options-off").addClass('my-options-on')
            .siblings().removeClass("my-options-on").addClass("my-options-off");
    }
    $("#searchCondition .floatDiv").hide();
    for (i = 0, len = conditions.length; i < len; i++) {
        $("#" + conditions[i] + "_div").show();
    }

}
function bindEvent(wdNameArr) {
    $("#cdn_cache_all li").click(function (ele){
        curr_cdn_cache_all=$(this).text();
        $(this).siblings().removeClass("my-options-on").addClass("my-options-off");
        $(this).removeClass("my-options-off").addClass("my-options-on");
        if( $(ele.target).html()=='CDN'){
            $("#WDSelected li").each(function(index,element){
               if($(element).attr('type')=='dataCenter'){
                    $(element).css("display","inline");
                }
            });
        }else{
            $("#WDSelected li").each(function(index,element){
                if($(element).attr('type') =='dataCenter'){
                    $(element).css("display","none");
                    if(currntDimension =='dataCenter'){
                        currntDimension = 'fringe_node';
                        for(var i in wdNameArr){
                            if(wdNameArr[i]['type'] =='fringe_node'){
                                initCondition(wdNameArr[i]['text'], [wdNameArr[i]], false);
                                break;
                            }
                        }
                    }
                }
            });
        }
    });
    $("#WDSelected li").click(function (ele) {
        currntDimension = $(ele.target).attr('type');
        initCondition($(ele.target).text(), wdNameArr, false);
    });
    $("#timeTypeSelected li").click(function (ele) {
        currentTimeType = $(ele.target).attr('type');
        //var currentDateType = $(ele.target).val();
        $(ele.target).removeClass("my-options-off").addClass('my-options-on')
            .siblings().removeClass("my-options-on").addClass("my-options-off");
        if ($.cookie("timeValueS_" + currentTimeType)) {
            $("#timeStart").val($.cookie("timeValueS_" + currentTimeType));
        } else {
            getLatesTime(false,'');
        }
        if ($.cookie("timeValueE_" + currentTimeType)) {
            $("#timeEnd").val($.cookie("timeValueE_" + currentTimeType));
        } else {
            getLatesTime(false,'');
        }
    });
}
/*************************************************/

function initConditioValue(condition1,condition2,callback) {
    var url =  AppBase + "/analyreport.do?method=request2";
    if(!$.isEmptyObject(condition1)){
        var param = {
            relation:JSON.stringify(condition1), //所有选择框联动关系
        };
        $.post(url, param, function (data) {
            //单选框赋值
            for(var key in condition1){
                initSelectOption(key+ "_div", data.data[key], '');
            }
            callbackflag +=1;
            if(callbackflag == callbackFn_times){
                callback();
            }
        }, "JSON");
    }
    if(!$.isEmptyObject(condition2)){
        var param = {
            relation:JSON.stringify(condition2), //所有选择框联动关系
        };
        $.post(url, param, function (data) {
            //多选框赋值
            for(var key_multiple in condition2) {
                initmultipleOption(key_multiple+ "_div", data.data[key_multiple], 'true');
            }
            callbackflag +=1;
            if(callbackflag == callbackFn_times){
                callback();
            }
        }, "JSON");
    }

}
//初始化下拉框的option选项
function initSelectOption(ele, data, defaultText) {
    var size = data.length;
    var id = ele.substring(0, ele.indexOf("_"));
    var html = "";
    var htmlstart ="<option value=''></option>";
        layui.use('form', function(){
            var form = layui.form()
            for (var i = 0; i < size; i++) {
                if(defaultText =='' && i == 0){
                    html += "<option value='" + data[i] + "' selected>" + data[i] + "</option>";
                }else if(data[i] == defaultText ){
                    html += "<option value='" + data[i] + "' selected>" + data[i] + "</option>";
                }else {
                    html += "<option value='" + data[i] + "'>" + data[i] + "</option>";
                }
            }
            $("#" + ele + " .layui-input-block").children("select").empty().append(html);
            //刷新所有select的表单
            form.render('select');
            //刷新了elect选项框，就应该重新去给该选项框增加事件
            $(".lay-search-sync").next().find("input.layui-input.layui-unselect").keyup(function (ele) {
                var id = $(ele.target).parents(".layui-unselect").prev().attr("id");
                refleshConditionSync(id);
            });
    });
}
//初始化多选框选项
function initmultipleOption(ele, data, valueflag){
    var size = data.length;
    var id = ele.substring(0, ele.indexOf("_"));
    var html = "";
    layui.use('form', function() {
        var form = layui.form()
        for (var i = 0; i < size; i++) {
            html += "<tr >";
            html += "<td><input type='checkbox' name='' lay-skin='primary' lay-filter='*'></td>"
            html += "<td>"+data[i]+"</td>"
            html += "</tr>"
        }

        if(valueflag =='false'){
            $("#" + id).next().find("table").children("tbody").empty().append(html);
            form.render('checkbox');
            searchvalue =true;
        } else{
            $("#" + id).next().find("table").children("tbody").empty().append(html);
            form.render('checkbox');
            $("#" + id).next().find("table").children("tbody").children("tr").eq(0).find("input").attr("checked",'checked');
            $("#" + id).next().find("table").children("tbody").children("tr").eq(0).find("input").next().addClass("layui-form-checked");
            $("#" + id).next().find("table").children("tbody").children("tr").eq(0).addClass("index-this");
            $("#" + id).val(data[0]);
        }
    });
}
//初始化下拉框的联动关系和点击事件
function SelectBoxevent(){
    //下拉框点击事件
    layui.use('form', function () {
        var form = layui.form();
        //获取所有的select选项框的id
        $("#searchCondition").find("select").each(function(index,element){
            var id= $(element).attr("id");
            if(!$.isEmptyObject(conditionReflesh)){
                if(!(id =="serviceTypeCategory" || id == "serviceNameCategory")){
                    if (conditionReflesh[id]['conId'] !="") {
                        form.on("select(" + id + ")", function (obj) {
                            // obj.initFn = initFn;
                            obj.targetCondition = conditionReflesh[id]['conId'];
                            obj.refreshCondition = conditionReflesh[conditionReflesh[id]['conId']];
                            getConditioValue(obj);
                        });
                    }
                }
            }
        });
            form.on('checkbox(allChoose)', function(data){
                var child = $(data.elem).parents('table').find('tbody input[type="checkbox"]');
                var inputid = $(data.elem).parents('table').parent().prev();
                var result="";
                if( $(data.elem).prop("checked")) {
                    child.each(function (index, item) {
                        item.checked = data.elem.checked;
                        $(item).parents('tr').addClass("index-this");
                        result+=$(item).parent().next("td").html()+',';
                    });
                    inputid.val(result.substring(0,result.length-1));
                }else{
                    inputid.val("");
                    child.each(function (index, item) {
                        item.checked = data.elem.checked;
                        $(item).parents('tr').removeClass("index-this");
                    });
                }
                form.render('checkbox');
            });
            form.on('checkbox(*)', function(data){
                var result="";
                var inputid = $(data.elem).parents('table').parent().prev();
                if( $(data.elem).prop("checked")){
                    $(data.elem).parents('tr').addClass("index-this");
                    if(searchvalue == false){
                        if(inputid.val() != ""){
                            result = inputid.val() +',';
                        }
                    }else{
                        searchvalue =false;
                    }
                    result = result + $(data.elem).parent().next("td").html();
                    inputid.val(result);
                }else{
                    $(data.elem).removeClass("index-input-this");
                    $(data.elem).parents('tr').removeClass("index-this");
                    result =  inputid.val();
                    var str = $(data.elem).parent().next("td").html();
                    if(result.indexOf(str) == 0 ){
                        inputid.val(result.substring(str.length+1));
                    }else if( result.indexOf(str)  == (result.length -str.length )){
                        inputid.val(result.substring(0,(result.length -str.length-1 )));
                    }else{
                        inputid.val(inputid.val().replace(str+',',""));
                    }
                }
                form.render('checkbox');
            });

    });
    if(!$.isEmptyObject(conditionReflesh_multiple)) {
        //增加click事件
       /* $(".layui-input-block").children("input").focus(function (ele) {
            $(ele.target).next().css("display", "block");

        });
       $(".layui-input-block").children("input").blur(function (ele){
           $(ele.target).next().css("display", "none");
        });*/
        $(".layui-input-block").children("input").click(function (ele){
            if( $(ele.target).next().css("display")== "block"){
                $(ele.target).next().css("display", "none");
                var id = $(ele.target).attr("id");
                if (conditionReflesh_multiple[id]['conId'] != "") {
                    obj = {};
                    obj.elem = $("#" + id)[0];//dom对象
                    obj.value = $("#" + id).val();//dom对象
                    obj.targetCondition = conditionReflesh_multiple[id]['conId'];
                    obj.targetCondition = conditionReflesh_multiple[id]['conId'];
                    $("#"+obj.targetCondition).val("");
                    getmultipleConditioValue(obj);
                }
            }else {
                $(ele.target).next().css("display", "block");
            }
        });
        //失去焦点事件，当有联动的时候，需要绑定事件去重新请求数据
      /*  $(".multip-input").click(function () {
            $(ele.target).prev().css("display", "block");
            $(ele.target).prev().focus($(ele.target).prev()[0]);
            var id = $(ele.target).parent().prev().attr("id");
            if (conditionReflesh_multiple[id]['conId'] != "") {
                obj = {};
                obj.elem = $("#" + id)[0];//dom对象
                obj.value = $("#" + id).val();//dom对象
                obj.targetCondition = conditionReflesh[id]['conId'];
                obj.targetCondition = conditionReflesh[id]['conId'];
                getmultipleConditioValue(obj);
            }
        });*/
        //给多选框绑定keyup事件，这个得测试是否只需要绑定一次
        $(".layui-input-block").children("input").keyup(function (ele) {
            var id = $(ele.target).attr("id");
            refleshMultipleConditionSync(id);
        });
    }

}

//多选框下拉联动
function getmultipleConditioValue(obj) {
    var url =  AppBase + "/analyreport.do?method=request5";
    var param = {
        conditionId: obj.targetCondition,
    };
    param[obj.elem.id] = obj.value; //查询的结果中增加的条件；
    $.post(url, param, function (data) {
        initmultipleOption(obj.targetCondition+ "_div", data.data, 'true');
    }, "JSON");
}
//下拉联动
function getConditioValue(obj) {
    var url =  AppBase + "/analyreport.do?method=request4";
    var param = {
        conditionId: obj.targetCondition,
};
    param[obj.elem.id] = obj.value; //查询的结果中增加的条件；
    $.post(url, param, function (data) {
        initSelectOption(obj.targetCondition+ "_div", data.data, '');
    }, "JSON");
}
//获取最新的时间
function getLatesTime(callflag,callback) {
    var url =  AppBase + "/analyreport.do?method=request3";
    var param = {
        TimeType: currentTimeType,
        cdn_cache:curr_cdn_cache_all,
        Dimension:currntDimension
    };
    $.post(url, param, function (data) {
        if(!$.isEmptyObject(data.data)){
            var currentDateType = $("#timeTypeSelected .my-options-on").val();
            var format = getDateFormat(currentDateType," laydate");
            var newDateS = new Date(Date.parse(data.data["timeValueS_"+currentTimeType]));
            var newDateE = new Date(Date.parse(data.data["timeValueE_"+currentTimeType]));
            $.cookie("timeValueS_" + currentTimeType, newDateS.format(format));
            $.cookie("timeValueE_" + currentTimeType, newDateE.format(format))
            $("#timeStart").val( newDateS.format(format));
            $("#timeEnd").val( newDateE.format(format));
            if(callflag == true){
                callbackflag +=1;
                if(callbackflag == callbackFn_times){
                    callback();
                }
            }
        }
    }, "JSON");
}
function refleshMultipleConditionSync(eleId) {
    var url =  AppBase + "/analyreport.do?method=request5";
    if(  $("#" + eleId).val() !=""){
        var param = {
            conditionId: eleId,
            searchValue: $("#" + eleId).val()
        };
        if(!$.isEmptyObject(conditionReflesh_multiple)) {
            for(var key in conditionReflesh_multiple){
                if(conditionReflesh_multiple[key]['conId'] == eleId){
                    param[key] = $("#"+key).val();
                }
            }
        }
        $.post(url, param, function (data) {
            initmultipleOption(eleId+ "_div", data.data, 'false');
        }, "JSON");
    }
}
/**
 * 异步模糊搜索选择框-请求数据
 * @param eleId
 */
function refleshConditionSync(eleId) {
    var url =  AppBase + "/analyreport.do?method=request4";
    if( $("#" + eleId).next().find("input").val() !=""){
        var param = {
            conditionId: eleId,
            searchValue: $("#" + eleId).next().find("input").val()
        };
        $.post(url, param, function (data) {
            //initSelectOption(eleId+ "_div", data.data, false);
            initSearchOptions(eleId,data.data);
        }, "JSON");
    }
}
//异步模糊搜索选择框-数据渲染
function initSearchOptions(eleId, data) {
    var $ele = $("#" + eleId);
    var html_op = "<option value=''></option>";
    var html_dd = "";
    for (var i = 0, len = data.length; i < len; i++) {
        html_op += "<option value='" + data[i] + "'>" + data[i]
            + "</option>";
        html_dd += "<dd lay-value='" + data[i] + "'>" + data[i]
            + "</dd>"
    }
    $ele.empty().append(html_op);
    $ele.next().find("dl").empty().append(html_dd);
    $ele.next().find("dd").on('click', function (ele) {
        initSelectOption(eleId+ "_div", data,  $(ele.target).html());
        var id = eleId;
        while (conditionReflesh[id]['conId'] !="") {
            obj={};
            obj.elem = $("#"+id)[0];//dom对象
            obj.value = $("#"+id).val();//dom对象
            obj.targetCondition = conditionReflesh[id]['conId'];
            obj.refreshCondition = conditionReflesh[conditionReflesh[id]['conId']];
            getConditioValue(obj);
            id = conditionReflesh[id]['conId'];
        }
        /*$(ele.target).addClass("layui-this").siblings().removeClass("layui-this");
        $("#" + eleId).children("option").each(function(index,element){
            if($(element).val() ==$(ele.target).text() ){
                $(element).attr("selected",true);
            }
        })
        $("#" + eleId).next().removeClass("layui-form-selected")
            .find("input").val($(ele.target).text())
            .attr("idValue", $(ele.target).attr("lay-value"));
        ele.stopPropagation();*/
    })
}
//根据时间粒度改变时间控件
function initDateByType(timeType) {
    $("#laydate_table").show();
    if (timeType == 10080) {
        $('.laydate_table tbody tr').find('td:not(:last-child)').addClass('laydate_void');
    } else if (timeType == 44640) {
        $("#laydate_table").hide();
    }
}
//时间段改变成时间点
function changeDateCom(labelText) {
    labelText = labelText ? labelText : "时间选择";
    $("#timeLable").html(labelText);
    $("#dateEnd").hide();
}
//初始化时间控件
function initTimeSelect() {
    layui.use('laydate', function () {
        var laydate = layui.laydate;
        var dateOptions = {
            event: 'click', //触发事件
            format: 'YYYY-MM-DD hh:00:00', //日期格式
            max: '2099-12-31 23:00:00',
            istime: true, //是否开启时间选择
            isclear: true, //是否显示清空
            istoday: false, //是否显示今天
            issure: true //是否显示确认
        };
        var start = $.extend({}, dateOptions, {
            //min: laydate.now(),
            choose: function (datas) {
                end.min = datas; //开始日选好后，重置结束日的最小日期
                end.start = datas; //将结束日的初始值设定为开始日
                var timeType = $("#timeTypeSelected .my-options-on").val();
                $.cookie("timeValueS_" + timeType, datas);
            }
        });

        var end = $.extend({}, dateOptions, {
            choose: function (datas) {
                start.max = datas; //结束日选好后，重置开始日的最大日期
                var timeType = $("#timeTypeSelected .my-options-on").val();
                $.cookie("timeValueE_" + timeType, datas);
            }
        });
        $("#timeStart").unbind('click');
        $("#timeStart").click(function () {
            var timeType = $("#timeTypeSelected .my-options-on").val();
            start.istime = timeType == 60;
            start.format = getDateFormat(timeType, "laydate");
            start.elem = this;

            start.onMonthChange = function () {
                initDateByType(timeType);
            };
            start.onYearChange = function () {
                initDateByType(timeType);
            };
            laydate(start);
            initDateByType(timeType);
        });
        $("#timeEnd").unbind('click');
        $("#timeEnd").click(function () {
            var timeType = $("#timeTypeSelected .my-options-on").val();
            end.istime = timeType == 60;
            end.format = getDateFormat(timeType, "laydate");
            end.elem = this;
            end.onMonthChange = function () {
                initDateByType(timeType);
            };
            end.onYearChange = function () {
                initDateByType(timeType);
            };
            laydate(end);
            initDateByType(timeType);
        });
    });
}

//获取查询条件的值,有的搜索框没显示，会不会把值也传过去？
function getConditionParam() {
    var icp1Value = $("#ICP1").next().find("input").attr("idValue");
    var server1Value = $("#server1").next().find("input").attr("idValue");
    return {
        cache_cdn:curr_cdn_cache_all,
        wdName: currntDimension, //维度
        timeType: $("#timeTypeSelected .my-options-on").val(),//时间粒度
        timeStart: $("#timeStart").val(),
        timeEnd: $("#timeEnd").val(),
        cityName1: $("#cityName1").val(),
        cityName2: $("#cityName2").val(),
        dataCenter: $("#dataCenter").val(),//这个值还要不要不确定
        ICP1: icp1Value ? icp1Value : $("#ICP1").val(),
        ICP2: icp1Value ? icp1Value : $("#ICP2").val(),
        server1: server1Value ? server1Value : $("#server1").val(),
        server2: server1Value ? server1Value : $("#server2").val(),
        serviceNameCategory:$("#serviceNameCategory").val(),
        serviceTypeCategory:$("#serviceTypeCategory").val(),
        domain1:$("#domain1").val(),
        domain2:$("#domain2").val()
    }
}

//开始-结束时间的周期数组
function getTimeSlot(start, end, type) {
    var sd = new Date(start);
    var ed = new Date(end);
    var timeSlot = [];
    while (true) {
        var formatStr;
        if (type == 60) {
            formatStr = sd.format("yyyy-MM-dd HH:mm:ss");
            sd.setHours(sd.getHours() + 1);
        } else if (type == 1440) {
            formatStr = sd.format("yyyy-MM-dd");
            sd.setDate(sd.getDate() + 1);
        } else if (type == 10080) {
            formatStr = sd.format("yyyy-MM-dd");
            sd.setDate(sd.getDate() + 7);
        } else if (type == 44640) {
            formatStr = sd.format("yyyy-MM-dd");
            sd.setMonth(sd.getMonth() + 1);
        }
        timeSlot.push(formatStr);
        if (sd.getTime() > ed.getTime()) {
            return timeSlot.join(",");
        }
    }
}
//验证查询条件是否设置值
function validateForm() {
    var $obj = $("#searchCondition .floatDiv:visible");
    var size = $obj.length;
    var msg = {errorMsg: "", isPass: true};
    var idArray = [];
    var values = getConditionParam();
    for (var i = 0; i < size; i++) {
        var item = $obj.eq(i);
        var id = item.attr("id").split("_")[0];
        var text = item.find("label").text();
        if (!values[id]) {
            idArray.push(id);
            msg.errorMsg += "[" + text + "]条件不能为空<br>";
            msg.isPass = false;
        }
    }
    if ($("#timeStart").is(":visible") && !$("#timeStart").val()) {
        msg.errorMsg += "[开始时间]条件不能为空<br>";
        msg.isPass = false;
        idArray.push("timeStart");
    }
    if ($("#timeEnd").is(":visible") && !$("#timeEnd").val()) {
        msg.errorMsg += "[结束时间]条件不能为空<br>";
        msg.isPass = false;
        idArray.push("timeEnd");
    }
    msg.idArray = idArray;
    return msg;
}

//获取时间格式
function getDateFormat(timeType, formatType) {
    var format;
    if (timeType == 60) {
        format = formatType != "laydate" ? "yyyy-MM-dd HH:00:00" : "YYYY-MM-DD hh:00:00";
    } else if (timeType == 1440 || timeType == 10080) {
        format = formatType != "laydate" ? "yyyy-MM-dd" : "YYYY-MM-DD";
    } else if (timeType == 44640) {
        format = formatType != "laydate" ? "yyyy-MM-01" : "YYYY-MM-01";
    }
    return format;
}
