var chartIndexObj = {};//存放指标
var layer;
var ttime;//时间
var selectResult=[];//查询结果
var listenTimes = 0;//监听异步请求次数
layui.use('layer', function(){
    currntDimension = "domain";
    layer = layui.layer;
    setHeights();
    $(window).bind("resize", function(){setHeights()});
    changeDateCom();
    initWdName([
        {text: '域名', type: "domain", conditions: "ICP2,domain2"},//多选
        {text: '服务器', type: "server", conditions: "server2"}   //多选
        ]);
});

//初始化
function loadChartData(flag){
    listenTimes = 0;//重新查询,重新计算监听次数
    selectResult = [];
    layer.closeAll();
    var validate = validateForm();
    if (!validate.isPass) {
        layer.alert(validate.errorMsg, {icon: 7,offset:'200px'});
        return;
    }
    var chartIndexConf = {
        statusCode:{id:"statusCode",textName:'状态码',legend:['1XX','2XX','3XX','4XX','5XX'],orderStr:"http_1xx_rate,http_2xx_rate,http_3xx_rate,http_4xx_rate,http_5xx_rate"},
        hitRate:{id:"hitRate",textName:'命中率',legend:[],orderStr:"hit_rate"},
        delay:{id:"delay",textName:'可用性',legend:[],orderStr:"delay"}
    };
    var chart_content = $(".chart_content");//需要展示的指标列表
    layer.load(2, {time: 600});
    for(var j = 0 ;j<chart_content.length;j++){
        var contentId = chart_content.eq(j).attr('id');
        var chartIndex = chartIndexConf[contentId];
        postData(chartIndex);
    }
    listenAjaxRes();
}

//请求数据
function postData(chartIndex){
    var id = chartIndex['id'];//显示DOM树中的div ID
    var textName = chartIndex['textName'];//指标名称
    var legend =chartIndex['legend'];
    var orderStr = chartIndex['orderStr'];//数据库中的指标字段名,也是排序字段
    var url =AppBase +'/trobleshoot.do?method=request1';
    var reParam = getConditionParam();
    ttime = reParam.timeStart;
    var param = {timeType: currentTimeType,dimension:currntDimension,ttime:ttime,cache_cdn:reParam.cache_cdn,
        limit:"150",orderStr:orderStr,ICP2:reParam.ICP2,server2:reParam.server2,domain2:reParam.domain2};
    var hasData = true;
    var message = "";
    var type = "success";
    $.cattAjax({
        url: url,
        data: param,
        dataType: "json",
        callback: function (data, textStatus, requestParam) {
            if (data.SUCCESS == "false") {
                hasData = false;
                type = "error";
                message = "查询结果:数据库底层异常,请联系管理员";
                return;
            }
            if (data.data.length == 0) {
                hasData = false;
                message = textName+" 没有统计数据";
            }
            selectResult.push({hasData:hasData,message:message,type:type});
            var retuData = classifyBarData(data.data,legend);
            var endLocal = getDataZoomEndLocal(data.data.length);
            var barOption = getBarOption({titleName:textName,legend:legend,analysisTime:ttime,yData:retuData["yData"],xData:retuData["xData"],dataZoomEnd:endLocal});
            var indexRate = chartIndexObj[id];
            var domObj = document.getElementById(id);
            if(!indexRate){
                indexRate = echarts.init(domObj);//不存在则先初始化在放入全局变量中
                chartIndexObj[id]= indexRate;
            }else{
                indexRate = echarts.getInstanceByDom(domObj);
            }
            indexRate.setOption(barOption,true);
        }
    });
}

//监听多个异步请求结束
function listenAjaxRes(){
    listenTimes++;
    var resLength = selectResult.length;
    if(resLength < 3 && listenTimes< 100){
        setTimeout(function(){listenAjaxRes()},100);
    }else if(resLength < 3 && listenTimes > 100) {
        layer.alert('查询超时,请稍后重试', {icon: 2});
    }else {
        var obj;
        var type =[];
        var hasDate=[];
        var message="";
        for(var i = 0;i<resLength;i++){
           obj = selectResult[i];
           type.push( obj['type']);
           hasDate.push(obj['hasData']);
           message += obj['message']+'</br>';
        }
        if($.inArray('error', type)>=0){
            layer.alert('数据库底层异常,请联系管理员', {icon: 2});
        }else if($.inArray(false, hasDate)>=0){
            layer.alert('查询结果:</br>'+message, {icon: 7,offset:'200px'});
        }
    }
}

//柱图数据分类,返回数据是小到大排序,先pus大后小
function classifyBarData(data,legend){
    var dataSize = data.length;
    var value;
    var y_name;
    var id;
    var legend;
    var yData = []
    var xxData={};
    if(legend.length != 0){

        for(var i = 0 ; i < dataSize;i++){
            y_name = data[i]["y_name"];
            id = data[i]["id"];
            yData.push(y_name);
            for(var j=0;j< legend.length;j++){
                if(xxData[legend[j]] ==null){
                    var tmpdata=[];
                    tmpdata.push(data[i]["value"+(j+1)]);
                    xxData[legend[j]]  = tmpdata;
                }else{
                    xxData[legend[j]].push(data[i]["value"+(j+1)]);//dimension是自定义添加用于表示数据维度
                }
            }

        }
    }else {
        for(var i = 0 ; i < dataSize;i++){
            y_name = data[i]["y_name"];
            id = data[i]["id"];
            yData.push(y_name);
            if (xxData["default"] == null) {
                var tmpdata = [];
                tmpdata.push(data[i]["value"]);
                xxData["default"] = tmpdata;
            } else {
                xxData["default"].push(data[i]["value"]);//dimension是自定义添加用于表示数据维度
            }
        }
    }

    return {yData:yData,xData:xxData}
}

/**
 * 根据数据量获取初始化dataZoom结束位置
 *  默认只展示12条
 */
function getDataZoomEndLocal(dataSize){
    var result = parseInt((1-12/dataSize)*100);
    return result<0 ? 0:result;
}

//窗口自适应
function setHeights() {
    var $body = $('body');
    var height = $body.height();
    var width = $body.width();
    var $chartIndex = $("#chartIndex");
    var $chart_content = $(".chart_content");

    $chartIndex.css({ "height": height-230, "width": width,"overflow":"hidden"});
    $chart_content.css({"width": $chartIndex.width()*0.33, "height": '97%', "float": 'left'});
    for(var i in chartIndexObj){
        chartIndexObj[i].resize();
    }
}
