//问题排查-健康度|命中率|可用性 条形图配置
function getBarOption(setObject) {
    if(setObject['legend'].length != 0) {
        return {
            title: {
                text: setObject['titleName']
                //subtext: '统计时间:' + setObject['analysisTime']
            },
            tooltip: {
                trigger: 'axis',
                formatter: '{b}: <br />{c}' + ' %',
                axisPointer: {
                    type: 'shadow'
                }
            },
            legend: {
                show: true,
                data: setObject['legend'],
                selected: {'1XX': false, '2XX': false, '3XX': false, '4XX': true, '5XX': false},
                top:28,
                left:15
            },
            grid: {
                left: '3%',
                right: '3%',
                bottom: '5%',
                containLabel: true
            },
            xAxis: {
                type: 'value',
                nameLocation: 'middle',
                nameGap: 20,
                name: "(%)",
                boundaryGap: [0, 0.01]
            },
            yAxis: {
                type: 'category',
                data: setObject['yData']
            },
            dataZoom: [
                {
                    type: 'slider',
                    show: true,
                    bottom: '10%',
                    yAxisIndex: 0,
                    showDetail: false,
                    filterMode: 'filter',//联动影响x轴的数据范围
                    width: 25,
                    height: '80%',
                    showDataShadow: false,
                    left: '93%',
                    start: 100,
                    //end: 0
                    end: setObject['dataZoomEnd']
                }
            ],
            series: [
                {
                    name: '1XX',
                    type: 'bar',
                    data: setObject['xData']['1XX']
                },
                {
                    name: '2XX',
                    type: 'bar',
                    data: setObject['xData']['2XX']
                },
                {
                    name: '3XX',
                    type: 'bar',
                    data: setObject['xData']['3XX']
                },
                {
                    name: '4XX',
                    type: 'bar',
                    data: setObject['xData']['4XX']
                },
                {
                    name: '5XX',
                    type: 'bar',
                    data: setObject['xData']['5XX']
                }
            ]
        }
    }
       else{
            return {
                title: {
                    text: setObject['titleName']
                    //subtext: '统计时间:' + setObject['analysisTime']
                },
                tooltip: {
                    trigger: 'axis',
                    formatter: '{b}: <br />{c}' + ' %',
                    axisPointer: {
                        type: 'shadow'
                    }
                },
                legend: {
                    show: false,
                    data: setObject['legend'],
                },
                grid: {
                    left: '3%',
                    right: '3%',
                    bottom: '5%',
                    containLabel: true
                },
                xAxis: {
                    type: 'value',
                    nameLocation: 'middle',
                    nameGap: 20,
                    name: "(%)",
                    boundaryGap: [0, 0.01]
                },
                yAxis: {
                    type: 'category',
                    data: setObject['yData']
                },
                dataZoom: [
                    {
                        type: 'slider',
                        show: true,
                        bottom: '10%',
                        yAxisIndex: 0,
                        showDetail: false,
                        filterMode: 'filter',//联动影响x轴的数据范围
                        width: 25,
                        height: '80%',
                        showDataShadow: false,
                        left: '93%',
                        start: 100,
                        //end: 0
                        end: setObject['dataZoomEnd']
                    }
                ],
                series:[
                    {
                        name: setObject['titleName'],
                        type: 'bar',
                        data: setObject['xData']['default']
                    }
                ]
            }
        }

}

//错误分析图配置
function getErrorCodeOption(setObject) {
    return {
        color:['#c23531','#2f4554', '#61a0a8', '#d48265', '#91c7ae','#749f83',
            '#ca8622', '#bda29a','#6e7074', '#546570', '#c4ccd3','#CD853F',
            '#CDB7B5','#D15FEE','#D2691E','#F0E68C','#FFA07A','#FFBBFF',
            '#66CD00','#EE9A00','#61a0a8','#91c7ae','#CDAF95','#B0E0E6'],
 /*       color:['#FF3030','#FF8C00','#FFA07A','#d48265','#EEC900','#749f83',
            '#ca8622','#bda29a','#6e7074','#BCEE68','#00F3FF','#CD853F',
            '#CDB7B5','#D15FEE','#D2691E','#F0E68C','#FFA07A','#FFBBFF',
            '#66CD00','#EE9A00','#61a0a8','#91c7ae','#CDAF95','#B0E0E6'],*/
        title: {
            text: setObject['titleName']
        },
        tooltip: {
            enterable: true,
            confine: true,//限制在可视范围
            trigger: 'axis',
            axisPointer: {
                type: 'shadow'
            },
            formatter: function (param) {
                return getFormatter(param);
            }
        },
        legend: {
            left: '15%',
            orient: 'horizontal',
            itemWidth: 25,
            itemHeight: 12,
            data: setObject['legend']
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: {
            type: 'value',
            nameLocation: 'middle',
            nameGap: 20,
            name: "(%)",
            boundaryGap: [0, 0.01]
        },
        yAxis: {
            type: 'category',
            data: setObject['yAxis']
        },
        dataZoom: [
            {
                orient: "vertical",
                show: true,
                realtime: true,
                start: 100,
                end: setObject['dataZoomEnd']
            }
        ],
        series: setObject['series']
    };
}
//错误分析图配置
function downLoadBind(setObject) {
    return {
        color:['#c23531','#2f4554', '#61a0a8', '#d48265', '#91c7ae','#749f83',
            '#ca8622', '#bda29a','#6e7074', '#546570', '#c4ccd3','#CD853F',
            '#CDB7B5','#D15FEE','#D2691E','#F0E68C','#FFA07A','#FFBBFF',
            '#66CD00','#EE9A00','#61a0a8','#91c7ae','#CDAF95','#B0E0E6'],
        /*       color:['#FF3030','#FF8C00','#FFA07A','#d48265','#EEC900','#749f83',
         '#ca8622','#bda29a','#6e7074','#BCEE68','#00F3FF','#CD853F',
         '#CDB7B5','#D15FEE','#D2691E','#F0E68C','#FFA07A','#FFBBFF',
         '#66CD00','#EE9A00','#61a0a8','#91c7ae','#CDAF95','#B0E0E6'],*/
        title: {
            text: setObject['titleName'],
            x: 'center'
        },
        tooltip: {
            enterable: true,
            confine: true,//限制在可视范围
            trigger: 'axis',
            axisPointer: {
                type: 'shadow'
            },
            formatter: function (param) {
                return changeToolTipFormat(param);
            }
        },
        legend: {
            top: '30px',
            data: setObject['legend']
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: {
            type: 'value'
        },
        yAxis: {
            type: 'category',
            data: setObject['yAxis']
        },
        dataZoom: [
            {
                type: 'slider',
                show: true,
                bottom: '10%',
                yAxisIndex: 0,
                showDetail: false,
                filterMode: 'filter',//联动影响x轴的数据范围
                width: 25,
                height: '80%',
                showDataShadow: false,
                left: '93%',
                start: 100,
                //end: 0
                end: setObject['dataZoomEnd']
            }
        ],
        series: setObject['series']
    };
}
//初始化时间控件
function initDateCom() {
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
            choose: function (datas) {
                var timeType = $("#timeTypeSelected .my-options-on").val();
                $.cookie("error_timeValue_" + timeType, datas);
            }
        });
        $("#timeStart").click(function () {
            if (currentTimeType == '2') {//小时类型
                start.istime = true;
                start.format = 'YYYY-MM-DD hh:00:00';
            } else if (currentTimeType == '5') { //月类型
                start.istime = false;
                start.format = 'YYYY-MM-01';
            } else {
                start.istime = false;
                start.format = 'YYYY-MM-DD';
            }
            start.elem = this;
            start.onMonthChange = function () {
                initDateByType(currentTimeType);
            };
            start.onYearChange = function () {
                initDateByType(currentTimeType);
            };
            laydate(start);
            initDateByType(currentTimeType);
        });
    });
    $("#timeStart").val('2017-03-8 00:00:00');//演示需要,设置有数据的时间
}
//根据周期类型判断显示时间样式
function initDateByType(timeType) {
    $("#laydate_table").show();
    if (timeType == 4) {//周类型
        $('.laydate_table tbody tr').find('td:not(:last-child)').addClass('laydate_void');
    } else if (timeType == 5) {//月类型
        $("#laydate_table").hide();
    }
}

//获取tooltip显示格式
function getFormatter(param) {
    param.sort(compare('value'));//先根据值排序
    var paramSize = param.length;
    var name = param[0]["name"];//Y轴名称
    var color;//颜色
    var seriesName;//错误码名称
    var value;//错误码占比
    var SumValue=0;//错误码占比汇总
    var obj;
    var listStr="";
    for (var i = paramSize - 1; i >= 0; i--) {
        obj = param[i];
        color = obj["color"];
        seriesName = obj["seriesName"];
        value = obj["value"];
        SumValue+=value;
        value = parseFloat(value);
        if(value == 0){
            value = 0;
        }
        listStr+="<span class='chartsSpot' style='background-color:"+color+"'></span>"+seriesName+" : "+value+" %</br>"
    }
    SumValue =parseFloat(SumValue).toFixed(3);//保留最多三位小数
    return name+"</br>"+" 汇总 : "+SumValue+" %</br>"+listStr;
}
//下载带宽tooltip显示格式
function changeToolTipFormat(param) {
    param.sort(compare('value'));//先根据值排序
    var format = param[0]["name"] + "<br/>汇总 : {:sumValue} <br/>";
    var sumValue = 0;
    for (var i = 0, len = param.length; i < len; i++) {
        sumValue += parseFloat(param[i]['value']);
        format += "<span class='chartsSpot' style='background-color:" + param[i]['color'] + "'></span>"
            + param[i]['seriesName'] + " : " + param[i]['value'] + "&nbsp;&nbsp;&nbsp;&nbsp;";
        if (i % 2) {
            format += "<br/>";
        }
    }
    return format.replace("{:sumValue}", sumValue);
}
//对比排序
function compare(property) {
    return function (a, b) {
        var value1 = a[property];
        var value2 = b[property];
        return value1 - value2;
    }
}
//获取时间格式
function getDateFormat(timeType, formatType) {
    var format;
    if (timeType == 2) {
        format = formatType != "laydate" ? "yyyy-MM-dd HH:00:00" : "YYYY-MM-DD hh:00:00";
    } else if (timeType == 3 || timeType == 4) {
        format = formatType != "laydate" ? "yyyy-MM-dd" : "YYYY-MM-DD";
    } else if (timeType == 5) {
        format = formatType != "laydate" ? "yyyy-MM-01" : "YYYY-MM-01";
    }
    return format;
}