<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/web/lib/nav.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>">
    <meta charset="utf-8">
    <title>magic lake CDN日志分析系统</title>
    <!-- js库引入 -->
    <script type="text/javascript" src="web/module/cdn/js/libs/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="web/module/cdn/js/libs/vue-1.0.min.js"></script>
    <script src="web/module/cdn/js/libs/bootstrap.min.js"></script>
    <script src="web/module/cdn/js/libs/echarts.min.js"></script>
    <script src="web/module/cdn/js/libs/guangdong.js"></script>
    <!-- 私有js文件引入 -->
    <script src="web/module/cdn/js/charts.js"></script>
    <!-- timeSelector -->
    <link href="web/module/cdn/timeSelector/css/bootstrap-datetimepicker.min.css" rel="stylesheet"/>
    <script src="web/module/cdn/timeSelector/js/bootstrap-datetimepicker.min.js"></script>
    <script src="web/module/cdn/timeSelector/js/bootstrap-datetimepicker.zh-CN.js"></script>
    <!-- bootstrap样式库引入 -->
    <link href="web/module/cdn/css/bootstrap.min.css" rel="stylesheet"/>
    <!-- 私有样式引入 -->
    <link href="web/module/cdn/css/mycss/cdn.css" rel="stylesheet" type="text/css"/>
    <!-- 测试数据引入 -->
    <script src="web/module/cdn/testData/itemsData2.js"></script>
    <script>
        /**
         * @type: // 请求配置（无配置默认请求所有库文件）
         * @动态库(reqLibraries)有：['jQuery', 'ext', 'd3', 'echarts_config', 'highcharts_config']
         * @插件(reqPlugins)有：['bootstrap', 'highcharts_config3d']
         *   requireConfig = {
         *      navScript: string,  // 动态库的nav文件
         *      libBaseURL: string, // 动态库根路径 （默认通过nav去寻找）
         *      reqLibraries: Array, // 需要请求的动态库
         *      reqPlugins: Array, // 需要请求的插件
         *      libVersions: {}, // 多个版本切换 （请保持目录结构一致）
         *		versionURL: win.versionURL, // 系统版本库地址
         *		useMask: boolean, // 是否启动时显示遮罩，默认为true
         *      style: { // ext多种样式切换
         *          extStyle || 'triton' // aria/classic/crisp/gray/neptune/triton
         *      }
         *   }
         **/

        var AppBase = "<%=AppBase%>";
        var baseURL = AppBase+"/web/module/cdn"; // 模块根目录

        var requireConfig = {
            userMask: false,
            reqLibraries: ['ext', 'jQuery', baseURL+"/event.js"],
            reqPlugins: []
        };
    </script>
</head>

<body style="background:#E7E8EB">

<div class="my-header">
    <div class="my-header-container">
        <div ><img src="web/module/cdn/images/logo_1.png" class="my-logo1" /></div>
        <a href="index.jsp"><div id="xingnengkuaizhao" class="my-header-item">性能快照</div></a>
        <a href="web/module/cdn/realtime_monitoring.jsp"><div  class="my-header-item-selected">实时监控</div></a>
        <a href="#"><div class="my-header-item">分析报表</div></a>
        <a href="#"><div class="my-header-item">告警报表</div></a>
        <a href="#"><div class="my-header-item">日志管理</div></a>
        <a href="#"><div class="my-header-item">我的账号</div></a>
        <div onclick="quit()" ><span class="glyphicon glyphicon-log-out my-quit my-float-right" aria-hidden="true"></span></div>
    </div>
</div>

<div id="loading">
    <div class="progress my-progress">
        <div class="progress-bar progress-bar-striped active" role="progressbar" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100" style="width: 100%">
        </div>
    </div>
</div>

<div id="my-m2-vm" >
    <div class="my-m2-submodule-title">全网实时监控<span style="float: right;font-size: 16px;color: #4787F3">最新时间：{{module1_data.time}}</span></div>
    <div id="my-m2-submodule1">
        <ul>
            <li class="my-m2-submodule1-item">
                <img src="web/module/cdn/images/m2_icon1.png" class="my-m2-submodule1-icon"/>
                <div class="my-m2-submodule1-text">
                    <h1>健康度</h1>
                    <h2 style="color : {{m1_health_rate_color}} ">{{module1_data.health_rate}}<span>%</span></h2>
                </div>
                <div id="my-m2-submodule1-chart1" class="my-m2-submodule1-chart"></div>
            </li>
            <li class="my-m2-submodule1-item">
                <img src="web/module/cdn/images/m2_icon2.png" class="my-m2-submodule1-icon"/>
                <div class="my-m2-submodule1-text">
                    <h1>缓存命中率</h1>
                    <h2 style="color:{{m1_hit_rate_color}}">{{module1_data.hit_rate}}<span>%</span></h2>
                </div>
                <div id="my-m2-submodule1-chart2" class="my-m2-submodule1-chart"></div>
            </li>
            <li class="my-m2-submodule1-item">
                <img src="web/module/cdn/images/m2_icon3.png" class="my-m2-submodule1-icon"/>
                <div class="my-m2-submodule1-text">
                    <h1>平均下载速率</h1>
                    <h2 style="color : {{m1_avg_dl_speed_color}}">{{module1_data.avg_dl_speed}}<span>KB/s</span></h2>
                </div>
                <div id="my-m2-submodule1-chart3" class="my-m2-submodule1-chart"></div>
            </li>
        </ul>
    </div>
    <div class="my-m2-submodule-title">地市实时监控</div>
    <div id="my-m2-submodule2" class="tbback-tabs">
        <ul class="tbback-nav-tabs" >
            <li v-for="index in m2_indexes" v-on:click="m2_switch_index(index.type)" v-bind:class="{'tbback-nav-tabs-selected':curr_m2_index==index.type,'tbback-nav-tabs-unselected':curr_m2_index!=index.type}">{{index.text}}</li>
            <li v-for="IPtype in m2_IPtypes" v-on:click="m2_switch_IPtype(IPtype.type)" v-bind:class="{'tbback-nav-tabs-selected':curr_m2_IPtype==IPtype.type,'tbback-nav-tabs-unselected':curr_m2_IPtype!=IPtype.type}"style="float: right;border-right:none;border-left:1px solid #BBBBBB ">{{IPtype.text}}</li>
        </ul>
        <div id="my-m2-submodule2-chart-box">
            <div id="my-m2-submodule2-chart1"></div>
            <div class="my-m2-submodule2-form">
                <table class="table table-striped table-bordered" style="text-align: center">
                    <thead>
<%--                        <th style="text-align: center">地市</th>
                        <th style="text-align: center">指标 ({{m2_index_units[curr_m2_index]}})</th>--%>
                    </thead>
                    <tbody>
                        <tr v-for="item in module2_data.slice(11)">
                            <td>{{item.name}}</td>
                            <td>{{item.value}} {{m2_index_units[curr_m2_index]}}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div class="my-m2-submodule2-form">
                <table class="table table-striped table-bordered" style="text-align: center">
                    <thead >
<%--                        <th style="text-align: center">地市</th>
                        <th style="text-align: center">指标 ({{m2_index_units[curr_m2_index]}})</th>--%>
                    </thead>
                    <tbody>
                        <tr v-for="item in module2_data.slice(0,11)">
                            <td>{{item.name}}</td>
                            <td>{{item.value}} {{m2_index_units[curr_m2_index]}}</td>
                        </tr>
                    </tbody>
                </table>
            </div>            
        </div>
    </div>
    <div class="my-m2-submodule-title">全网监控曲线</div>
<!--     <div id="my-m2-submodule3" class="tbback-tabs" style="border:none;">
        <ul class="tbback-nav-tabs" style="border-top:1px solid #BBBBBB;height:36px"> -->
    <div id="my-m2-submodule3" class="tbback-tabs">
        <ul class="tbback-nav-tabs" >
            <li v-for="index in m3_indexes" v-on:click="m3_switch_index(index.type)" v-bind:class="{'tbback-nav-tabs-selected':curr_m3_index==index.type,'tbback-nav-tabs-unselected':curr_m3_index!=index.type}">{{index.text}}</li>
        </ul>
        <div class="my-m2-submodule3-chart-box">
            <div class="my-m2-submodule3-chart-controller">
                <ul class="tbback-btns" style="margin-left:20px">
                    <li v-for="time in m3_times" v-on:click="m3_switch_time(time.type)" v-bind:class="{'tbback-btns-selected':curr_m3_timeType==time.type,'tbback-btns-unselected':curr_m3_timeType!=time.type}">{{time.text}}</li>
                </ul>
                <div id="my-time-selector">
                    <p>自定义日期</p>
                    <div style="width:200px; float:left">
                        <div id="my-datetime1" class="input-group date form_datetime" >
                            <input v-model="curr_m3_custom_startTime" class="form-control" size="16" type="text" readonly >
                            <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
                        </div>
                    </div>
                    <p>至</p>
                    <div style="width:200px; float:left">
                        <div  id="my-datetime2"  class="input-group date form_datetime" >
                            <input v-model="curr_m3_custom_endTime" class="form-control" size="16" type="text" readonly >
                            <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
                        </div>
                    </div>
                    <div v-on:click = "m3_custom_time_query()" class="tbback-btn my-float-left" style="margin: 0 20px 0 10px">查询</div>
                </div>
            </div>
            <div id="my-m2-submodule3-chart1"></div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(function () {
        $('#my-datetime1').datetimepicker({
            format:"yyyy-mm-dd hh:ii",
            language:  'zh-CN',
            //weekStart: 1,
            todayBtn:  1,
            minView : 0,
            maxView : 2,
            autoclose: true,
            todayHighlight: 1,
            initialDate: '2017-02-21 00:00'
            //startView: 2,
            //forceParse: 0,
            //showMeridian: 1
        });
        $('#my-datetime2').datetimepicker({
            format:"yyyy-mm-dd hh:ii",
            language:  'zh-CN',
            //weekStart: 1,
            todayBtn:  1,
            minView : 0,
            maxView : 2,
            autoclose: true,
            todayHighlight: 1,
            //startView: 2,
            //forceParse: 0,
            //showMeridian: 1
        });
        $("#my-datetime1").on("changeDate",function (e) {
            $('#my-datetime2').datetimepicker('setStartDate',e.date);
        });
        $("#my-datetime2").on("changeDate",function (e) {
            $('#my-datetime1').datetimepicker('setEndDate',e.date);
        });
        /*初始化页面vm*/
        var _vmData = {};
        $.ajax({
            url : AppBase + '/realtimeonline.do?method=request1',
            data:{},
            cache : false,
            async : false,
            type : "GET",
            dataType : 'json',
            success : function (Response){
                _vmData.module1Data = Response.data;
            }
        });
        $.ajax({
            url : AppBase + '/realtimeonline.do?method=request2',
            data:{
                index : 'avg_dl_speed',
                IPtype : 'user',
            },
            cache : false,
            async : false,
            type : "GET",
            dataType : 'json',
            success : function (Response){
                _vmData.module2Data = Response.data;
            }
        });
        $.ajax({
            url : AppBase + '/realtimeonline.do?method=request3',
            data:{
                index:'avg_dl_speed',
                timeType:'today',
                startTime:'',
                endTime:'',
            },
            cache : false,
            async : false,
            type : "GET",
            dataType : 'json',
            success : function (Response){
                _vmData.module3Data = Response.data;
            }
        });
        initVM(_vmData);
        $('#loading').hide();
    });

    function initVM(_data) {
        var vm = new Vue({
            el: '#my-m2-vm',
            data: {
                thresholds : {
                    health_rate : [[70, '#cc0033'], [80, '#FF9900'], [90, '#FFcc33'], [95, '#99CC33'], [100, '#00CD00']],
                    hit_rate : [[70, '#cc0033'], [80, '#FF9900'], [90, '#FFcc33'], [95, '#99CC33'], [100, '#00CD00']],
                    avg_dl_speed : [[1000, '#cc0033'], [2000, '#FF9900'], [3000, '#FFcc33'], [4000, '#99CC33'], [5000, '#00CD00']],
                },//图例门限配置
                charts_config : {
                    chart1: {'domID' : 'my-m2-submodule1-chart1', 'type': 'ring'},
                    chart2: {'domID' : 'my-m2-submodule1-chart2', 'type': 'ring'},
                    chart3: {'domID' : 'my-m2-submodule1-chart3', 'type': 'gauge'},
                    chart4: {'domID' : 'my-m2-submodule2-chart1', 'type': 'map'},
                    chart5: {'domID' : 'my-m2-submodule3-chart1', 'type': 'line'},
                },//图表配置
                m2_indexes: [
                    {type:'health_rate',text:'健康度', unit:'%'},
                    {type:'hit_rate',text:'缓存命中率',unit:'%'},
                    {type:'avg_dl_speed',text:'平均下载速率',unit:'KB/s'},
                ],
                m2_index_units: {
                    health_rate :'%',
                    hit_rate:'%',
                    avg_dl_speed:'KB/s',
                },
                m2_IPtypes: [
                    {type:'user',text:'用户所在地'},
                    {type:'data_center',text:'数据中心所在地'},
                ],
                m3_indexes: [
                    {type:'health_rate',text:'健康度', unit:'%'},
                    {type:'hit_rate',text:'缓存命中率',unit:'%'},
                    {type:'avg_dl_speed',text:'平均下载速率',unit:'KB/s'},
                ],
                m3_index_units: {
                    health_rate :'%',
                    hit_rate:'%',
                    avg_dl_speed:'KB/s',
                },
                m3_times: [
                    {type:'3hours',text:'最近3小时'},
                    {type:'today',text:'今天'},
                    {type:'7days',text:'最近7天'},
                    {type:'30days',text:'最近30天'},
                ],
                module1_data : _data.module1Data,
                module2_data : _data.module2Data.data,
                module3_data : _data.module3Data,
                charts : {},
                curr_m2_index : 'avg_dl_speed',//枚举值 : 'health_rate', 'hit_rate', 'avg_dl_speed'
                curr_m2_IPtype : 'user', //枚举值 : 'data_center', 'user'
                curr_m3_index : 'avg_dl_speed',//枚举值 : 'health_rate', 'hit_rate', 'avg_dl_speed'
                curr_m3_timeType : 'today',//枚举值 : '3hours', 'today', '7days', '30days', 'custom'
                curr_m3_custom_startTime : '',//
                curr_m3_custom_endTime : '',//
            },
            computed : {
                m1_health_rate_color : function() {
                    var _color = '';
                    for (var i = 0 ; i < this.thresholds.health_rate.length; i++) {
                        if ( this.module1_data.health_rate <= this.thresholds.health_rate[i][0] ) {
                            _color =  this.thresholds.health_rate[i][1];
                            return _color;
                        };
                     }; 
                },
                m1_hit_rate_color : function() {
                    var _color = '';
                    for ( var i = 0 ; i <  this.thresholds.hit_rate.length ; i++) {
                        if ( this.module1_data.hit_rate <= this.thresholds.hit_rate[i][0] ) {
                            _color =  this.thresholds.hit_rate[i][1];
                            return _color;
                        };
                     }; 
                },
                m1_avg_dl_speed_color : function() {
                    var _color = '';
                    for ( var i = 0 ; i <  this.thresholds.avg_dl_speed.length ; i++) {
                        if ( this.module1_data.avg_dl_speed <= this.thresholds.avg_dl_speed[i][0] ) {
                            _color =  this.thresholds.avg_dl_speed[i][1];
                            return _color;
                        };
                     }; 
                },
/*                m2_map_thresholds : function() {
                    var _thresholds =  this.thresholds[this.curr_m2_index];
                    return _thresholds;
                }*/
            },
            created :  function(){
            },
            ready : function () {
                var _data = {};
                /*子模块1 健康度 图表*/
                _data  = {
                    'data' : [
                        {'value' : this.module1_data.health_rate, 'name':'health'},
                        {'value' : 100-this.module1_data.health_rate, 'name':''}
                    ],
                    'others': {
                        color : this.m1_health_rate_color
                    }
                };
                this.charts.chart1 = chartFactory(this.charts_config.chart1.type, this.charts_config.chart1.domID, _data);
                /*子模块1 命中率 图表*/
                _data  = {
                    'data' : [
                        {'value' : this.module1_data.hit_rate, 'name':'hit rate'},
                        {'value' : 100-this.module1_data.hit_rate, 'name':''}
                    ],
                    'others': {
                        color : this.m1_hit_rate_color
                    }
                };
                this.charts.chart2 = chartFactory(this.charts_config.chart2.type, this.charts_config.chart2.domID, _data);
                /*子模块1 速率 图表*/
                _data  = {
                    'data' : [
                    {'value' : this.module1_data.avg_dl_speed, 'name':'下载速率'}],
                    'others': {
                        thresholds : this.thresholds.avg_dl_speed
                    }
                };
                this.charts.chart3 = chartFactory(this.charts_config.chart3.type, this.charts_config.chart3.domID, _data);
                /*子模块2 地图*/
                _data  = {
                    'data' : this.module2_data,
                    'others':{
                        'thresholds' :this.thresholds[this.curr_m2_index],
                        'unit' : this.m2_index_units[this.curr_m2_index],
                    }
                };
                this.charts.chart4 = chartFactory(this.charts_config.chart4.type, this.charts_config.chart4.domID, _data);
                /*子模块3 折线图*/
                _data  = {
                    'data' : this.module3_data,
                    'others':{
                        'unit' : this.m3_index_units[this.curr_m3_index],
                    }
                };
                this.charts.chart5 = chartFactory(this.charts_config.chart5.type, this.charts_config.chart5.domID, _data);
            },
            methods : {
                m2_switch_index : function(_type) {
                    $.getJSON(AppBase + '/realtimeonline.do?method=request2', {index : _type, IPtype : this.curr_m2_IPtype}, function (Response) {
                        vm.$data.module2_data = Response.data.data;
                        vm.$data.curr_m2_index = _type;
                        var _data  = {
                            'data' : vm.$data.module2_data,
                            'others':{
                                'thresholds' : vm.$data.thresholds[vm.$data.curr_m2_index],
                                'unit' : vm.$data.m2_index_units[vm.$data.curr_m2_index],
                            }
                        };
                        vm.$data.charts.chart4 = chartFactory(vm.$data.charts_config.chart4.type, vm.$data.charts_config.chart4.domID, _data);
                    });
                },
                m2_switch_IPtype : function(_type) {
                    $.getJSON(AppBase + '/realtimeonline.do?method=request2', {index : this.curr_m2_index, IPtype : _type}, function (Response) {
                        vm.$data.module2_data = Response.data.data;
                        vm.$data.curr_m2_IPtype = _type;
                        var _data  = {
                            'data' : vm.$data.module2_data,
                            'others':{
                                'thresholds' : vm.$data.thresholds[vm.$data.curr_m2_index],
                                'unit' : vm.$data.m2_index_units[vm.$data.curr_m2_index],
                            }
                        };
                        vm.$data.charts.chart4 = chartFactory(vm.$data.charts_config.chart4.type, vm.$data.charts_config.chart4.domID, _data);
                    });
                },
                m3_switch_index : function(_type) {
                    $.getJSON(AppBase + '/realtimeonline.do?method=request3', {
                        index : _type,
                        timeType: this.curr_m3_timeType,
                        startTime:vm.$data.curr_m3_custom_startTime,
                        endTime:vm.$data.curr_m3_custom_endTime,
                    }, function (Response) {
                        vm.$data.module3_data = Response.data;
                        vm.$data.curr_m3_index = _type;
                        var _data  = {
                            'data' : vm.$data.module3_data,
                            'others':{
                                'unit' : vm.$data.m3_index_units[vm.$data.curr_m3_index],
                            }
                        };
                        vm.$data.charts.chart5 = chartFactory(vm.$data.charts_config.chart5.type, vm.$data.charts_config.chart5.domID, _data);
                    });
                },
                m3_switch_time : function(_type) {
                    $.getJSON(AppBase + '/realtimeonline.do?method=request3', {index : this.curr_m3_index, timeType: _type}, function (Response) {
                        vm.$data.module3_data = Response.data;
                        vm.$data.curr_m3_timeType = _type;
                        var _data  = {
                            'data' : vm.$data.module3_data,
                            'others':{
                                'unit' : vm.$data.m3_index_units[vm.$data.curr_m3_index],
                            }
                        };
                        vm.$data.charts.chart5 = chartFactory(vm.$data.charts_config.chart5.type, vm.$data.charts_config.chart5.domID, _data);
                        vm.$data.curr_m3_custom_startTime = '';
                        vm.$data.curr_m3_custom_endTime = '';
                    });
                },
                m3_custom_time_query:function() {
                    if ( this.curr_m3_custom_startTime !=''&& this.curr_m3_custom_endTime!=''){
                        $.getJSON(AppBase + '/realtimeonline.do?method=request3', {
                            index : this.curr_m3_index,
                            timeType: 'custom',
                            startTime:vm.$data.curr_m3_custom_startTime,
                            endTime:vm.$data.curr_m3_custom_endTime,
                        }, function (Response) {
                            vm.$data.module3_data = Response.data;
                            vm.$data.curr_m3_timeType = 'custom';
                            var _data  = {
                                'data' : vm.$data.module3_data,
                                'others':{
                                    'unit' : vm.$data.m3_index_units[vm.$data.curr_m3_index],
                                }
                            };
                            vm.$data.charts.chart5 = chartFactory(vm.$data.charts_config.chart5.type, vm.$data.charts_config.chart5.domID, _data);
                        });
                    }
                },
            }
        });
        $('#vm').show();
    }

    function quit() {
        $.ajax(AppBase + '/logout.do', {
            success: function(){
                location.href = location.href;
            }
        });
    }
</script>
<!--链接文件导航-->
</body>
</html>