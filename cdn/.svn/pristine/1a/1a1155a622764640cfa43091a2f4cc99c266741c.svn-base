/**
 * Created by zhongguohua
 * on 2017/3/29.
 */
var downloadBindChart;
$(function () {
    currntDimension = "dataCenter";
    initWdName([
        {text: '内容中心', type: "dataCenter", conditions: "server2"},  //不同内容中心下不同服务器的指标对比
        {text: '边缘节点', type: "fringe_node", conditions: "server2"}, //不同地市下不同服务器的指标对比
        {text: 'ICP', type: "ICP", conditions: "ICP2,domain2"} //不同ICP下不同域名的指标对比
    ]);
    initDiv(); //设定图表的高度
    changeDateCom(); //时间段改变成时间点
    //loadChartData();//加载click函数，点击不同的维度，加载不同的图表
    // initData();
    $(window).resize(function () {
        initDiv();
        resizeChart();
    });
});

/**
 * 首次进入初始化数据和绑定事件
 */
function initData() {
   /* $("#chart_download_ICP").hide();
    var $obj = $("#searchCondition .layui-form-label");
    $obj.css({cursor: 'pointer'}).attr("title", "点击切换条件")
        .prepend("<span class='switch-icon'></span>");
    $obj.click(function (ele) {
        $("#searchCondition .floatDiv").hide();
        var text = $(ele.target).text();
        if ($(ele.target).hasClass("switch-icon")) {
            text = $(ele.target).parent().text();
        }
        if (text == "地市") {
            $("#ICP_div").show();
            queryType = "ICP";
        } else {
            $("#cityName_div").show();
            queryType = "cityName";
        }
    });*/
    $("#WDSelected li").click(function (ele) {
        $(".singleChart").hide();
        if ($(ele.target).text() == "数据中心下载带宽") {
            $("#searchCondition .floatDiv").hide();
            $("#" + queryType + "_div").show();
            $("#chart_download_dataCenter").show();
        } else {
            $("#chart_download_ICP").show();
        }
        resizeChart();//切换维度后需要重绘，否则图形会变形
    });
    loadPieChartData();
    loadBarChartData();

}

function initDiv() {
    var bodyHeight = $("body").height();
    $(".singleChart").css({
        height: bodyHeight - 230
    });
}

function loadChartData() {
    layer.load(2, {time: 600});

    var reParam = getConditionParam();
    var ttime= reParam.timeStart;
    if($.trim(ttime) == ""){
        layer.alert('时间是必选条件', {icon: 7});
        return;
    }
    $.cattAjax({
        url: AppBase +'/downloadbind.do?method=request5',
        data: {
            timeType: reParam.timeType,
            ttime: ttime,
            dimension:currntDimension,
            cache_cdn:curr_cdn_cache_all,
            ICP2:reParam.ICP2,
            server2:reParam.server2,
            domain2:reParam.domain2
        },
        dataType: "json",
        callback: function (data, textStatus, requestParam) {
            if (data.SUCCESS == "false") {
                layer.closeAll();
                layer.alert('数据库底层异常,请联系管理员', {icon: 2});
                return;
            }
            var yAxisData =data.data["yname"];
            if (yAxisData.length === 0) {
                layer.closeAll();
                layer.alert('查询范围没有统计数据', {icon: 7});
                //return;
            }
            chartsDimension = currntDimension;
            var reSetting = classifyBarData(data.data);
            var endLocal = getDataZoomEndLocal(yAxisData.length);//dataZoom控件结束位置
            var barOption = downLoadBind({titleName:'下载带宽',legend:reSetting["legend"],  //借用了一下错误码占比的配置
                series:reSetting["series"],yAxis:yAxisData,dataZoomEnd:endLocal,unit:"MB"});
            if(!downloadBindChart){
                downloadBindChart = echarts.init($('#chart_download')[0]);
                /* errorRateChart.on('click', function (params) {
                 if(chartsDimension != "dcCity"){//图形当前维度不是地市时可钻取
                 showRelDate(params);
                 }else{
                 layer.alert("当前维度下不能下钻数据",{icon:7,offset:'120px'})
                 }
                 });*/
            }else{
                downloadBindChart = echarts.getInstanceByDom($("#chart_download")[0])
            }
            downloadBindChart.setOption(barOption,true);
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
            stack:'bandwith',
            data: series[name]
        })
    }
    return {legend:legend,series:reSeries}
}
function getDataZoomEndLocal(dataSize){
    var result = parseInt((1-12/dataSize)*100);
    return result<0 ? 0:result;
}














/**
 * 加载数据中心下载带宽数据-饼图
 * @param isClick
 */
function loadDataCenterbarChartData(isClick) {
    var extendParam = {
        'chartClick': barChartClick
    };
    var url = AppBase + '/downloadbind.do?method=request1';
    loadData(url, ['chart_download'], extendParam);
}

/**
 * 加载ICP下载带宽 条形图数据
 * @param isClick
 */
function loadICPBarChartData(isClick) {
    var extendParam = {
        'chartClick': barChartClick,
    };
    var url = AppBase + '/downloadbind.do?method=request2';
    loadData(url, ['chart_download'],extendParam);
}

/**
 * 饼图扇区点击执行的方法
 */
function pieChartClick(data) {
    if (!data) return;
    openWin({
        title: '[' + data['name'] + ']下域名和服务器带宽分布',
        wdId: "dataCenter",
        successCallback: function (domObj, index) {//开窗成功后回调，用于加载数据
            loadSubChartData(data);
        }
    });
}

function barChartClick(data) {
    if (!data) return;
    openWin({
        title: '[' + data['name'] + ']下域名和服务器带宽分布',
        wdId: "ICP",
        successCallback: function (domObj, index) {//开窗成功后回调，用于加载数据
            loadSubChartICPData(data);
        }
    });
}

/**
 * 钻取开窗
 * @param options
 */
function openWin(options) {
    var $bodyHeight = $("body").height();
    var option = {
        type: 1,
        offset: '12px',
        area: [1180, $bodyHeight - 200],
        content: "<div id='detailDataDiv'><div id='chart_domain_" + options['wdId'] + "' class='charts'></div>" +
        "<div id='chart_server_" + options['wdId'] + "' class='charts'></div></div>",
        success: function (domObj, index) {
            $("#detailDataDiv .charts").css({height: $bodyHeight - 250});
            options.successCallback(domObj, index);
        }
    };
    option = $.extend({}, option, options);
    layer.open(option);
}

function loadSubChartData(clickData) {
    var url = AppBase + '/downloadbind.do?method=request3';
    var param = {
        id: clickData.data.id,
        timeStart: $("#timeStart").val(),
        timeType: $("#timeTypeSelected .my-options-on").val()
    };
    $.post(url, param, function (data) {
        initChartOptions("chart_server_dataCenter", data);
        initChartOptions("chart_domain_dataCenter", data);
    }, "JSON");
}

function loadSubChartICPData(clickData) {
    var url = AppBase +  '/downloadbind.do?method=request4';
    var param = {
        name: clickData.name,
        timeStart: $("#timeStart").val(),
        timeType: $("#timeTypeSelected .my-options-on").val()
    };
    $.post(url, param, function (data) {
        initChartOptions("chart_server_ICP", data);
        initChartOptions("chart_domain_ICP", data);
    }, "JSON");
}

