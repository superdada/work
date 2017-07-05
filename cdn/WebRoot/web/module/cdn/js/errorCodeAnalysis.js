var errorRateChart;
var layer;
/*var currntDimension = "dcCity";//当前维度,默认地市
var currentTimeType = "2";//当前时间粒度,默认天*/
var ttime;//时间
var chartsDimension = "dcCity";//当前图形的维度
layui.use('layer', function(){
    layer = layui.layer;
    initDateCom();//初始化时间控件
    setHeights();
    changeDateCom();
    initWdName([
        {text: '全网', type: "allNet", conditions: ""},
        {text: '内容中心', type: "dataCenter", conditions: ""},
        {text: '边缘节点', type: "fringe_node", conditions: ""},
        {text: 'ICP', type: "ICP", conditions: ""},
        {text: '用户', type: "User", conditions: ""},
        {text: '业务分类', type: "serviceType", conditions: ""},
        {text: '业务名称', type: "serviceName", conditions: ""},
        {text: '服务器', type: "server", conditions: ""}
    ]);
});
//回调函数
function callbackFn(){
   // currntDimension = "allNet";//当前维度,默认地市
    loadChartData();
    //changeDimension();
    $(window).bind("resize", function(){setHeights()});
}
//初始化
function loadChartData(){
    layer.load(2, {time: 600});
    ttime = $("#timeStart").val();
    if($.trim(ttime) == ""){
        layer.alert('时间是必选条件', {icon: 7});
        return;
    }
    $.cattAjax({
        url: AppBase +'/erroranalysis.do?method=request1',
        data: {timeType: currentTimeType,dimension:currntDimension,ttime:ttime,isNeedCode:"1",isNeedLimit:"false",cache_cdn:curr_cdn_cache_all},
        dataType: "json",
        callback: function (data, textStatus, requestParam) {
            if (data.SUCCESS == "false") {
                layer.closeAll();
                layer.alert('数据库底层异常,请联系管理员', {icon: 2});
                return;
            }
            var yAxisData =data.data["yAxis"];
            if (yAxisData.length == 0) {
                layer.closeAll();
                layer.alert('查询范围没有统计数据', {icon: 7});
                return;
            }
            chartsDimension = currntDimension;
            var reSetting = classifyBarData(data.data);
            var endLocal = getDataZoomEndLocal(yAxisData.length);//dataZoom控件结束位置
            var barOption = getErrorCodeOption({titleName:'错误码占比',legend:reSetting["legend"],
                series:reSetting["series"],yAxis:yAxisData,dataZoomEnd:endLocal,unit:"%"});
            if(!errorRateChart){
                errorRateChart = echarts.init($('#errorRate')[0]);
               /* errorRateChart.on('click', function (params) {
                    if(chartsDimension != "dcCity"){//图形当前维度不是地市时可钻取
                        showRelDate(params);
                    }else{
                        layer.alert("当前维度下不能下钻数据",{icon:7,offset:'120px'})
                    }
                });*/
            }else{
                errorRateChart = echarts.getInstanceByDom($("#errorRate")[0])
            }
            errorRateChart.setOption(barOption,true);
        }
    });
    $(window).bind("resize", function(){setHeights()});
}

//弹窗显示关联维度数据
function showRelDate(params){
    var ralDimension = {dcName:[{name:'域名',dimension:'dcDomain'},{name:'服务器',dimension:'dcServer'}],
        icp:[{name:'数据中心',dimension:'icpDc'},{name:'服务器',dimension:'icpServer'}]};
    var dimension = chartsDimension;//取数据所属维度
    var relObj = ralDimension[dimension];
    var $body = $('body');
    var height = $body.height();
    var width = $body.width();
    var url = AppBase + '/erroranalysis.do?method=request1';
    var param1 = {timeType: currentTimeType,dimension:relObj[0]['dimension'],ttime:ttime,isNeedCode:"1",isNeedLimit:"false",needGroup:"true"};
    var param2 = {timeType: currentTimeType,dimension:relObj[1]['dimension'],ttime:ttime,isNeedCode:"1",isNeedLimit:"false",needGroup:"true"};
    if(dimension == "dcName"){ //数据中心
        param1.dc_name = params['name'];
        param2.dc_name = params['name'];
    }else if(dimension == "icp"){ //ICP
        param1.icp_name = params['name'];
        param2.icp_name = params['name'];
    }
    var index = layer.open({
        type: 1,
        title: params['name']+'下'+relObj[0]['name']+'和'+relObj[1]['name']+'错误码占比分布',
        offset :'12px',
        area: [width*0.92+'px', height*0.67+'px'],
        content: "<div id='realDateDiv'><div id='chart_1'></div><div id='chart_2'></div></div>",
        success: function(layero, index){
            var $realDateDiv = $("#realDateDiv");
            $realDateDiv.css({"width": '100%', "height": '100%'});
            $.cattAjax({
                url: url,
                data: param1,
                dataType: "json",
                callback: function (data, textStatus, requestParam) {
                    if (data.SUCCESS == "false") {
                        layer.closeAll();
                        layer.alert('数据库底层异常,请联系管理员', {icon: 2});
                        return;
                    }
                    if (data.length == 0) {
                        layer.msg(relObj[0]['name']+'暂无数据', { icon:6, time:99999,offset:[height*0.35+'px', width*0.25+'px']});
                    }

                    var yAxisData_1 = data.data["yAxis"];
                    var reData_1 = classifyBarData(data.data);
                    var endLocal_1 = getDataZoomEndLocal(yAxisData_1.length);//dataZoom控件结束位置
                    var chart_1_Option = getErrorCodeOption({titleName:relObj[0]['name'],legend:reData_1["legend"],
                        series:reData_1["series"],yAxis:yAxisData_1,dataZoomEnd:endLocal_1,unit:"%"});

                    var $chart_1 = $("#chart_1");
                    $chart_1.css({"width": 0.49*$realDateDiv.width(), "height": $realDateDiv.height()*0.95,"float": "left"});
                    var chart_1 = echarts.init($chart_1[0]);
                    chart_1.setOption(chart_1_Option,true);
                }
            });
            $.cattAjax({
                url: url,
                data: param2,
                dataType: "json",
                callback: function (data, textStatus, requestParam) {
                    if (data.SUCCESS == "false") {
                        layer.closeAll();
                        layer.alert('数据库底层异常,请联系管理员', {icon: 2});
                        return;
                    }
                    if (data.length == 0) {
                        layer.msg(relObj[1]['name']+'暂无数据', { icon:6, time:99999,offset:[height*0.35+'px', width*0.7+'px']});
                    }
                    var yAxisData_2 = data.data["yAxis"];
                    var reData_2 = classifyBarData(data.data);
                    var endLocal_2 = getDataZoomEndLocal(yAxisData_2.length);//dataZoom控件结束位置
                    var chart_2_Option = getErrorCodeOption({titleName:relObj[1]['name'],legend:reData_2["legend"],
                        series:reData_2["series"],yAxis:yAxisData_2,dataZoomEnd:endLocal_2,unit:"%"});

                    var $chart_2 = $("#chart_2");
                    $chart_2.css({"width": 0.49*$realDateDiv.width(), "height": $realDateDiv.height()*0.95,"float": "right"});
                    var chart_2 = echarts.init($chart_2[0]);
                    chart_2.setOption(chart_2_Option,true);
                }
            });
        },
        cancel: function(){
            layer.closeAll();
        }
    });
}

//柱图数据分类
function classifyBarData(data){
    var series = data["series"];
    var legend =[];
    var reSeries = [];
    for ( var name in series ){
        legend.push(name);
        reSeries.push({
            name:name,
            type: 'bar',
            stack:'errorCode',
            data: series[name]
        })
    }
    return {legend:legend,series:reSeries}
}
/**
 * 根据数据量获取初始化dataZoom结束位置
 *  默认只展示25条
 */
function getDataZoomEndLocal(dataSize){
    var result = parseInt((1-12/dataSize)*100);
    return result<0 ? 0:result;
}

//维度和时间粒度切换
function changeDimension() {
    $("#dimensionSelected li").click(function (ele) {
        currntDimension = $(ele.target).attr('type');
        $(ele.target).addClass('my-options-on')
            .siblings().removeClass("my-options-on").addClass("my-options-off");
    });
    $("#timeTypeSelected li").click(function (ele) {
        currentTimeType = $(ele.target).val();
        $(ele.target).addClass('my-options-on')
            .siblings().removeClass("my-options-on").addClass("my-options-off");
        var format = getDateFormat(currentTimeType);
        var date = new Date("2017/02/21");
        if (currentTimeType == 4) {
            date = new Date("2017/02/25");
           /* while (date.getDay() != 6) {
                date.setDate(date.getDate() - 1);
            }*/
        }
        if ($.cookie("timeValueS_" + currentTimeType)) {
            $("#timeStart").val($.cookie("error_timeValue_" + currentTimeType));
        } else {
            $("#timeStart").val(date.format(format));
        }
    });
}

//窗口自适应
function setHeights() {
    var $body = $('body');
    var height = $body.height();
    var width = $body.width();
    var $chartIndex = $("#chartIndex");
    var $errorRate = $("#errorRate");

    $chartIndex.css({ "height": height-230,"width": width-20,
        "overflow":"hidden"});
    $errorRate.css({"width": '100%', "height": '100%',
        "margin": 'auto'});
    if (errorRateChart) {
        errorRateChart.resize();
    }
}
