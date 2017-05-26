function chartFactory(_type, _domID, _data) {
    switch(_type) {
        case "ring":
            CF_Ring(_domID, _data);
            break;
        case "gauge":
            CF_Gauge(_domID, _data);
            break;
        case "map":
            CF_Map(_domID, _data);
            break;
        case "line":
            CF_Line(_domID, _data);
            break;
    }
}
function CF_Ring(_domID, _data){
    var myChart = echarts.init(document.getElementById(_domID));
    var option = {
        series: [
        {
            name:'健康度',
            type:'pie',
            radius: ['85%', '100%'],
            avoidLabelOverlap: false,
            hoverAnimation:false,
            label: {
                normal: {
                    show: true,
                    position: 'center',
                    textStyle:{fontSize:16},
                },
            },
/*            labelLine: {
                normal: {
                    show: true,
                },
            },*/
            data: _data.data,
        }
        ],
        color: [_data.others.color,'#E7E8EB'],
    };
    myChart.setOption(option);
    return myChart;
}

function CF_Gauge(_domID, _data){
    var myChart = echarts.init(document.getElementById(_domID));

    var _color = [];
    for (var i = 0 ; i < _data.others.thresholds.length ; i++) {
        var _a = _data.others.thresholds[i][0]/(_data.others.thresholds[_data.others.thresholds.length-1][0]);
        var _b = _data.others.thresholds[i][1];
        _color.push([_a, _b]);
    };
    var option = {
        series: [
            {
                name: '业务指标',
                type: 'gauge',
                radius : '100%',
                startAngle : 240,
                endAngle: -60,
                min : 0,
                max : _data.others.thresholds[_data.others.thresholds.length-1][0],
                title:false,
                splitNumber:4,
                axisLine:{
                    lineStyle:{
                        width:8,
                        color : _color
                    }
                },
                splitLine:{
                    length:10
                },
                detail: {
                    show: false ,
                    formatter:'{value}KB/s'
                },
                data: _data.data,
            }
        ],
    };
    myChart.setOption(option);
    return myChart;
}

function CF_Map(_domID, _data){
    var myChart = echarts.init(document.getElementById(_domID));
    var _pieces = [];
    for (var i = _data.others.thresholds.length -1;i >=0; i--) {
        var _a = _data.others.thresholds[i][0];
        var _b = _data.others.thresholds[i][1];
        var _c = 0;
        if (i > 0) {
            _c = _data.others.thresholds[i-1][0];
        };
        if (i == _data.others.thresholds.length -1) {
            _pieces.push({gt : _c, color:_b});
        } else {
            _pieces.push({gt : _c, lte: _a, color:_b});
        };
    };
    var option = {
        tooltip: {
            formatter:'{b}<br /> {c} '+ _data.others.unit,
        },
        visualMap: {
            type:'piecewise',
            pieces: _pieces,
            formatter: "{value}-{value2}",
            textStyle:{
                color: '#6B6B6B'
            },
            left: '3%',
            bottom: '5%',
            //calculable: true,
            itemWidth: 15,
            itemHeight: 10,
        },
        toolbox: {
            show: true,
            feature : {
                mark : {show: true},
                //dataView : {show: true, readOnly: false},
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },
        series: [
            {
                name: '4G用户数',
                type: 'map',
                map: '广东',
                roam: false,
                //left: 'center',
                //top: 140,
                itemStyle: {
                    normal: {
                        borderColor: '#fff',
                        borderWidth: 1
                    },
                },
                data: _data.data,
            },
        ],
        color: ['rgb(189,32,16)','rgb(231,186,16)','rgb(82,89,107)'],
        //backgroundColor:'rgba(82,89,107,1)'
    };
    myChart.setOption(option);
    return myChart;
}

function CF_Line(_domID, _data){
    var myChart = echarts.init(document.getElementById(_domID));
    var option = {
        tooltip: {
            trigger: 'axis',
            formatter:'{b}<br /> {c} '+ _data.others.unit,
        },
            grid:{
                show:true,
                left: 30,
                right:30,
                bottom:40,
                containLabel:true,
                //backgroundColor:'rgb(206,219,231)',
                //borderColor:'red',
                borderWidth:0,
                //shadowBlur:20,
                //shadowColor: 'rgba(0, 0, 0, 0.5)',
            },
            xAxis: {
                type:'category',
                boundaryGap : false,
                data: _data.data.x,
                splitArea: {
                    //show: true
                },
                axisLabel: {
                    //inside: true
                    //rotate: 30
                    interval:Math.ceil(_data.data.x.length/8)
                },
                splitLine: {
                    show: false,
                    //interval:0
                }
            },
            yAxis: {
                name: _data.others.unit,
                nameGap: 15,

            },
            series: [{
                name: 'xx',
                type: 'line',
                stack: '总量',
                areaStyle: {normal: {}},
                data: _data.data.y
            }],
            toolbox: {
                show: true,
                top:10,
                right:30,
                //orient: 'vertical',
                feature: {
                    restore: {
                        show: true
                    },
                    dataView:{
                        show:true
                    },
                    magicType: {
                        show: true,
                        type: ['line', 'bar']
                    },
                    saveAsImage: {
                        show: true
                    }
                },
                //top: '5%',
                //right: '5%'
            },
            color: ['#387EFF','rgb(189,32,16)','rgb(231,186,16)','rgb(82,89,107)'],
            //backgroundColor:'rgba(231,239,247,0.6)'
        };

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
    return myChart;
}
