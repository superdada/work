/**
 * Created by zhongguohua
 * on 2017/3/15.
 */

$(function () {
    initDiv();
    $(window).resize(function () {
        initDiv();
        resizeChart();
    });
    initWdName([
        {text: '全网', type: "allNet", conditions: ""},
        {text: '内容中心', type: "dataCenter", conditions: "dataCenter"},
        {text: '边缘节点', type: "fringe_node", conditions: "cityName1"},
        {text: 'ICP', type: "ICP", conditions: "ICP1,domain1"},
        {text: '用户', type: "User", conditions: "cityName2"},
        {text: '业务分类', type: "serviceType", conditions: "serviceTypeCategory"},
        {text: '业务名称', type: "serviceName", conditions: "serviceNameCategory"},
        {text: '服务器', type: "server", conditions: "server1"}
    ]);
    //loadChartData();
});


function initDiv() {
    var bodyHeight = $("body").height();
    var $charts = $(".charts");
    $("#chartDiv").css({
        height: (bodyHeight - 230) * 2
    });
    $("#scrollDiv").css({
        'overflow-y': 'auto',
        height: bodyHeight - 230
    });
    $charts.css({
        height: bodyHeight - 230
    })
}

function loadChartData() {
    var url = AppBase + "/analyreport.do?method=request1";
    loadData(url, ['chart1', 'chart2', 'chart3','chart4', 'chart5', 'chart6']);
}



