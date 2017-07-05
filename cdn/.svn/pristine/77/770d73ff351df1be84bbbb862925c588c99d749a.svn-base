<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="/web/lib/nav.jsp" %>
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
    <script src="web/module/cdn/components/layer/layer.min.js"></script>
    <script src="web/module/cdn/components/layui/layui.js"></script>
    <!-- 私有js文件引入 -->
    <script src="web/module/cdn/js/charts.js"></script>
    <!-- timeSelector -->
    <link href="web/module/cdn/timeSelector/css/bootstrap-datetimepicker.min.css" rel="stylesheet"/>
    <script src="web/module/cdn/timeSelector/js/bootstrap-datetimepicker.min.js"></script>
    <script src="web/module/cdn/timeSelector/js/bootstrap-datetimepicker.zh-CN.js"></script>
    <!-- bootstrap样式库引入 -->
    <link href="web/module/cdn/css/bootstrap.min.css" rel="stylesheet"/>
    <!-- layui样式引入 -->
    <link rel="stylesheet" type="text/css" href="web/module/cdn/components/layui/css/layui.css">
    <!-- 私有样式引入 -->
    <link href="web/module/cdn/css/mycss/cdn.css" rel="stylesheet" type="text/css"/>

    <%--<link rel="stylesheet" type="text/css" href="web/module/cdn/css/mycss/icon/iconfont.css">--%>

    <!-- 测试数据引入 -->
    <script src="web/module/cdn/js/realtime_monitoring.js"></script>
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
        var baseURL = AppBase + "/web/module/cdn"; // 模块根目录

        var requireConfig = {
            userMask: false,
            reqLibraries: ['ext', 'jQuery', baseURL + "/event.js"],
            reqPlugins: []
        };
    </script>
</head>

<body style="background:#E7E8EB">

<%--<div class="my-header">
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
</div>--%>

<div id="loading">
    <div class="progress my-progress">
        <div class="progress-bar progress-bar-striped active" role="progressbar" aria-valuenow="100" aria-valuemin="0"
             aria-valuemax="100" style="width: 100%">
        </div>
    </div>
</div>

<div id="my-m2-vm">
    <!--添加Cache/CDN/ALL查询筛选条件-->
    <div class="my-switch-box">
        <div class="my-switch-item">
            <div class="my-switch-text">类型 :</div>
            <ul class="my-switch-options">
                <li v-for="cdn in cdn_cache_all" v-on:click="switch('cdn',cdn.type)"
                    v-bind:class="{ 'my-options-on': curr_cdn == cdn.type, 'my-options-off': curr_cdn != cdn.type}">
                    {{cdn.text}}
                </li>
            </ul>
        </div>
    </div>

    <div class="my-m2-submodule-title">全网实时监控<span style="float: right;font-size: 16px;color: #4787F3">最新时间：{{module1_data.time}}</span>
    </div>
    <div id="my-m2-submodule1">
        <ul class="my-m2-submodule1-list">
            <li class="my-m2-submodule1-item">
                <div class="my-m2-submodule1-text">
                    <h1>HTTP业务成功率</h1>
                    <div class="my-m2-submodule1-text-left">
                        <img src="web/module/cdn/images/rizhi.png" class="my-m2-submodule1-icon"/>
                        <h2 style="color : {{m1_health_rate_color}} ">{{module1_data.health_rate}}<span>%</span></h2>
                    </div>

                </div>
                <div id="my-m2-submodule1-chart1" class="my-m2-submodule1-chart"></div>
            </li>
            <li class="my-m2-submodule1-item">
                <div class="my-m2-submodule1-text">
                    <h1>缓存命中率</h1>
                    <div class="my-m2-submodule1-text-left">
                        <img src="web/module/cdn/images/rizhi.png" class="my-m2-submodule1-icon"/>
                        <h2 style="color:{{m1_hit_rate_color}}">{{module1_data.hit_rate}}<span>%</span></h2>
                    </div>

                </div>
                <div id="my-m2-submodule1-chart2" class="my-m2-submodule1-chart"></div>
            </li>
            <li class="my-m2-submodule1-item">

                <div class="my-m2-submodule1-text">
                    <h1>平均下载速率</h1>
                    <div class="my-m2-submodule1-text-left">
                        <img src="web/module/cdn/images/rizhi.png" class="my-m2-submodule1-icon"/>
                        <h2 style="color : {{m1_avg_dl_speed_color}}">{{module1_data.avg_dl_speed}}<span>KB/s</span></h2>
                    </div>
                </div>
                <div id="my-m2-submodule1-chart3" class="my-m2-submodule1-chart"></div>
            </li>
            <li class="my-m2-submodule1-item">
                <div class="my-m2-submodule1-text">
                    <h1>流量</h1>
                    <div class="my-m2-submodule1-text-left">
                        <img src="web/module/cdn/images/rizhi.png" class="my-m2-submodule1-icon"/>
                        <h2 style="color : {{m1_bandwidth_color}}">{{module1_data.bandwidth}}<span>MB</span></h2>
                    </div>
                </div>
                <div id="my-m2-submodule1-chart4" class="my-m2-submodule1-chart"></div>
            </li>
            <li class="my-m2-submodule1-item">
                <div class="my-m2-submodule1-text">
                    <h1>请求数</h1>
                    <div class="my-m2-submodule1-text-left">
                        <img src="web/module/cdn/images/rizhi.png" class="my-m2-submodule1-icon"/>
                        <h2 style="color : {{m1_visit_cnt_color_color}}">{{module1_data.visit_cnt}}<span>个</span></h2>
                    </div>
                </div>
                <div id="my-m2-submodule1-chart5" class="my-m2-submodule1-chart"></div>
            </li>
            <li class="my-m2-submodule1-item">
                <div class="my-m2-submodule1-text">
                    <h1>首字节延迟</h1>
                    <div class="my-m2-submodule1-text-left">
                        <img src="web/module/cdn/images/rizhi.png" class="my-m2-submodule1-icon"/>
                        <h2 style="color : {{m1_avg_dl_speed_color}}">{{module1_data.delay}}<span>s</span></h2>
                    </div>
                </div>
                <div id="my-m2-submodule1-chart6" class="my-m2-submodule1-chart"></div>
            </li>
        </ul>
    </div>
    <div class="my-m2-submodule-parts-title">
        <div class="my-m2-submodule-left-title">地市实时监控</div>
        <div class="my-m2-submodule-right-title">
            <form class=" layui-form my-float-right">
                <label class="layui-form-label" style="margin-bottom: 0;">维度 :</label>
                <div class="layui-input-inline">
                    <select id="index2" name="dimensionchange" lay-verify="required" lay-filter="index2">
                        <option v-for="index in m2_indexes" value={{index.text}}>{{index.text}}</option>
                    </select>
                </div>
            </form>
        </div>
    </div>
    <div id="my-m2-submodule2" class="tbback-tabs">
        <ul class="tbback-nav-tabs">
            <li class="tbback-nav-tabs-selected">HTTP业务成功率</li>
            <li v-for="IPtype in m2_IPtypes" v-on:click="m2_switch_IPtype(IPtype.type)"
                v-bind:class="{'tbback-nav-tabs-selected':curr_m2_IPtype==IPtype.type,'tbback-nav-tabs-unselected':curr_m2_IPtype!=IPtype.type}"
                style="float: right;border-right:none;border-left:1px solid #BBBBBB ">{{IPtype.text}}
            </li>
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
                    <thead>
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
    <div class="my-m2-submodule-parts-title">
        <div class="my-m2-submodule-left-title">全网监控曲线</div>
        <div class="my-m2-submodule-right-title">
            <form class=" layui-form my-float-right">
                <div class="layui-form-item">
                  <label class="layui-form-label" style="margin-bottom: 0;">维度 :</label>
                  <div class="layui-input-inline">
                      <select id="index3" name="dimensionchange" lay-verify="required" lay-filter="index3">
                          <option v-for="index in m2_indexes" value={{index.text}}>{{index.text}}</option>
                      </select>
                  </div>
                </div>
            </form>
        </div>
    </div>
    <!--     <div id="my-m2-submodule3" class="tbback-tabs" style="border:none;">
            <ul class="tbback-nav-tabs" style="border-top:1px solid #BBBBBB;height:36px"> -->
    <div id="my-m2-submodule3" class="tbback-tabs">
        <ul class="tbback-nav-tabs">
            <li class="tbback-nav-tabs-selected">HTTP业务成功率</li>
        </ul>
        <div class="my-m2-submodule3-chart-box">
            <div class="my-m2-submodule3-chart-controller">
                <ul class="tbback-btns" style="margin-left:20px">
                    <li v-for="time in m3_times" v-on:click="m3_switch_time(time.type)"
                        v-bind:class="{'tbback-btns-selected':curr_m3_timeType==time.type,'tbback-btns-unselected':curr_m3_timeType!=time.type}">
                        {{time.text}}
                    </li>
                </ul>
                <div id="my-time-selector">
                    <p>自定义日期</p>

                    <div style="width:200px; float:left">
                        <div id="my-datetime1" class="input-group date form_datetime">
                            <input v-model="curr_m3_custom_startTime" class="form-control" size="16" type="text"
                                   readonly>
                            <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
                        </div>
                    </div>
                    <p>至</p>

                    <div style="width:200px; float:left">
                        <div id="my-datetime2" class="input-group date form_datetime">
                            <input v-model="curr_m3_custom_endTime" class="form-control" size="16" type="text" readonly>
                            <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
                        </div>
                    </div>
                    <div v-on:click="m3_custom_time_query()" class="tbback-btn my-float-left"
                         style="margin: 0 20px 0 10px">查询
                    </div>
                </div>
            </div>
            <div id="my-m2-submodule3-chart1"></div>
        </div>
    </div>
</div>
<!--链接文件导航-->
</body>
</html>