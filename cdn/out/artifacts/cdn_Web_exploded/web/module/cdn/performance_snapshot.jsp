<%--
  Created by IntelliJ IDEA.
  User: xzl
  Date: 2017/6/6
  Time: 17:18
  To change this template use File | Settings | File Templates.
--%>
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
    <script src="web/module/cdn/components/layer/layer.min.js"></script>
    <script src="web/module/cdn/components/layui/layui.js"></script>
    <!-- 私有js文件引入 -->
    <!-- bootstrap样式库引入 -->
    <link href="web/module/cdn/css/bootstrap.min.css" rel="stylesheet"/>

    <!-- 开关控件引入 -->
    <link rel="stylesheet" type="text/css" href="web/module/cdn/css/mycss/icon/iconfont.css">
    <!-- layui样式引入 -->
    <link rel="stylesheet" type="text/css" href="web/module/cdn/components/layui/css/layui.css">
    <!-- 私有样式引入 -->
    <link href="web/module/cdn/css/mycss/cdn.css" rel="stylesheet" type="text/css"/>
    <!-- 测试数据引入 -->
    <%--<script src="web/module/cdn/testData/itemsData.js"></script>--%>
    <script data-main="web/lib/nav.min" src="web/lib/RequireJS/require-2.3/require.min.js"></script>
    <script src="web/module/cdn/js/performancesnapshot.js"></script>

    <script>
        /**
         * @type: // 请求配置（无配置默认请求所有库文件）
         * @动态库(reqLibraries)有：['jQuery', 'ext', 'd3', 'eCharts', 'highCharts']
         * @插件(reqPlugins)有：['bootstrap', 'highCharts3d']
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
<body>
    <div id="loading">
        <div class="progress my-progress">
            <div class="progress-bar progress-bar-striped active" role="progressbar" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100" style="width: 100%">
            </div>
       </div>
       </div>
    <div id="vm" style="display: none" >
        <!--添加数据来源选项框-->
        <form id="first_dir" class=" layui-form my-switch-box">
            <div class="my-switch-item">
                <label class="layui-form-label">数据源 :</label>
                <ul class="my-title-options">
                    <li v-for="data in datasource" v-on:click="switch('datasource',data.type)" v-bind:class="{ 'my-options-on': curr_datasource == data.type, 'my-options-off': curr_datasource != data.type}">{{data.text}}</li>
                </ul>
            </div>
            <!--添加start-->
            <div class="my-switch-item">
                <label class="layui-form-label">类型 :</label>
                <ul class="my-title-options">
                    <li v-for="cdn in cdn_cache_all" v-on:click="switch('cdn',cdn.type)" v-bind:class="{ 'my-options-on': curr_cdn == cdn.type, 'my-options-off': curr_cdn != cdn.type}">{{cdn.text}}</li>
                </ul>
            </div>
            <div class="my-switch-item">
                <label class="layui-form-label">维度 :</label>
                <div class="layui-input-inline">
                    <select id="dimension" name="dimensionchange" lay-verify="required">
                        <option value= 'data_center'  selected >内容中心</option>
                        <option value= 'fringe_node' >边缘节点</option>
                        <option value= 'ICP' >ICP</option>
                        <option value= 'service_type' >业务类型</option>
                    </select>
                </div>
            </div>
            <div class="my-switch-item">
                <label class="layui-form-label">指标 :</label>
                <div class="layui-input-inline">
                    <input id="indexes" v-on:click="choose_table" type="text" name="title" required  lay-verify="required"  autocomplete="off" class="layui-input">
                    <div id ="index_table"class="layui-form  index_box" style="display: none;">
                        <table class="layui-table index_table" lay-skin="nob" >
                            <thead>
                            <tr>
                                <th><input type="checkbox" name="" lay-skin="primary" lay-filter="allChoose"></th>
                                <th>全选</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr v-for="index in indexes" >
                                <td><input type="checkbox" name="" lay-skin="primary" lay-filter="*"></td>
                                <td>{{index.text}}</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <li style="float:right;margin:5px 0 0 0"><span class="glyphicon glyphicon-share my-icons my-icons-on" style="font-size:20px;" aria-hidden="true"></span></li>
            <li style="float:right;margin:5px 2px 0 0"><span class="glyphicon glyphicon-edit my-icons my-icons-on" style="font-size:20px;" aria-hidden="true"></span></li>
            <li style="float:right;margin:6px 2px 0 0"><span class="glyphicon glyphicon-tasks my-icons my-icons-on" style="font-size:20px;" aria-hidden="true"></span></li>
            <li style="float:right;margin:5px 4px 0 0"><span class="glyphicon glyphicon-random my-icons my-icons-off" style="font-size:20px;" aria-hidden="true"></span></li>
        </form>
        <div  id="second_dir" class="my-switch-box" style="display: none">
            <div v-on:click="goup" class="returnbtn">返回</div>
        </div>
        <div class="my-show-box" >
            <div id ="listrow3" style="display: block">
              <ul class=" my-list-titles my-list-row3">
                <li v-for="title in list_titles"><p>{{title}}</p></li>
            </ul>
              <ul class="my-list-items" >
                <li v-for="(index,item) in items">
                    <ul id="indexlist3" class="my-list-row3">
                       <li v-on:click="goin(item.id)">{{item.id}}</li>
                       <li>
                          <ul>
                            <li v-for="index in item.indexes" v-on:click="moreinfo(item, index)" >
                            <div v-show="index.type === 'danger'" class="my-state-block state-danger"></div>
                            <div v-show="index.type === 'warning'" class="my-state-block state-warning"></div>
                            <div v-show="index.type === 'normal'" class="my-state-block state-normal"></div>
                            <div v-show="index.type === null" class="my-state-block state-null"></div>
                            </li>
                          </ul>
                       </li>
                        <li>{{time_show(item.indexes)}}</li>
                        <li  v-on:click="share(item)" style="float:right">
                            <span class="glyphicon glyphicon-share-alt my-icons my-icons-on" aria-hidden="true"></span>
                        </li>
                        <li  v-on:click="download(item)" style="float:right">
                            <span class="glyphicon glyphicon-download-alt my-icons my-icons-on" aria-hidden="true"></span>
                        </li>
                        <li  v-on:click="changeState('alarm',item.id, item.monitor_flag, item.alarm_flag, index)" style="float:right">
                            <span class="glyphicon glyphicon-bell my-icons my-icons-on" v-bind:class="{ 'my-icons-on': item.alarm_flag, 'my-icons-off': (!item.alarm_flag) }" aria-hidden="true"></span>
                        </li>
                        <li  v-on:click="changeState('monitor',item.id, item.monitor_flag, item.alarm_flag, index)" style="padding-top:12px;float:right;">
                            <i class="iconfont my-icons2" v-bind:class="{ 'icon-huadongkaiqi': item.monitor_flag, 'icon-huadongguanbi': (!item.monitor_flag) }"></i>
                        </li>
                     </ul>
                </li>
            </ul>
            </div>
            <div id ="listrow5" style="display: none">
                <ul class=" my-list-titles my-list-row5">
                    <li v-for="title in list_titles"><p>{{title}}</p></li>
                </ul>
                <ul class="my-list-items" >
                    <li v-for="(index,item) in items">
                        <ul id="indexlist5" class="my-list-row5">
                            <li  v-on:click="goin(item.id)">{{item.id}}</li>
                            <li>{{item.name}}</li>
                            <li>{{item.belong_to}}</li>
                            <li>
                                <ul>
                                    <li v-for="index in item.indexes" v-on:click="moreinfo(item, index)" >
                                        <div v-show="index.type === 'danger'" class="my-state-block state-danger"></div>
                                        <div v-show="index.type === 'warning'" class="my-state-block state-warning"></div>
                                        <div v-show="index.type === 'normal'" class="my-state-block state-normal"></div>
                                        <div v-show="index.type === null" class="my-state-block state-null"></div>
                                    </li>
                                </ul>
                            </li>
                            <li>{{time_show(item.indexes)}}</li>
                            <li  v-on:click="share(item)" style="float:right">
                                <span class="glyphicon glyphicon-share-alt my-icons my-icons-on" aria-hidden="true"></span>
                            </li>
                            <li  v-on:click="download(item)" style="float:right">
                                <span class="glyphicon glyphicon-download-alt my-icons my-icons-on" aria-hidden="true"></span>
                            </li>
                            <li  v-on:click="changeState('alarm',item.id, item.monitor_flag, item.alarm_flag, index)" style="float:right">
                                <span class="glyphicon glyphicon-bell my-icons my-icons-on" v-bind:class="{ 'my-icons-on': item.alarm_flag, 'my-icons-off': (!item.alarm_flag) }" aria-hidden="true"></span>
                            </li>
                            <li  v-on:click="changeState('monitor',item.id, item.monitor_flag, item.alarm_flag, index)" style="padding-top:12px;float:right;">
                                <i class="iconfont my-icons2" v-bind:class="{ 'icon-huadongkaiqi': item.monitor_flag, 'icon-huadongguanbi': (!item.monitor_flag) }"></i>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>
            <div id= "my-footer">
                <ul class="my-time-line">
                    <li v-for="timePoint in timeLine" class="my-time-point">
                        <div class="my-triangle-up"></div>
                        <div class="my-time-text">{{timePoint}}</div>
                    </li>
                </ul>
                <div class="my-Legend-box">
                    <div class="my-Legend-normal"></div>
                    <p class="my-legend-text">正常</p>
                    <div class="my-Legend-warning"></div>
                    <p class="my-legend-text">一般告警</p>
                    <div class="my-Legend-danger"></div>
                    <p class="my-legend-text">严重告警</p>
                    <div class="my-Legend-null"></div>
                    <p class="my-legend-text">无数据</p>
                </div>
                <div class="my-page-control-box">
                    <div class="my-page-goto">
                        <div class="input-group">
                            <input id="my-page-num" type="text" class="form-control" placeholder="页数">
                    <span class="input-group-btn">
                        <button v-on:click="changePage('jump')"  class="btn btn-default" type="button">跳转</button>
                    </span>
                        </div>
                    </div>
                    <div v-on:click="changePage('next')" class="my-page-controller"><span class="glyphicon glyphicon-triangle-right" aria-hidden="true"></span></div>
                    <p class="my-page-text">{{curr_page}}/{{total_pages}}</p>
                    <div v-on:click="changePage('Previous')" class="my-page-controller"><span class="glyphicon glyphicon-triangle-left" aria-hidden="true"></span></div>
                </div>
            </div>
        </div>
        </div>
    </div>
    <iframe id="hidden-down-iframe" style="display:none"></iframe>
</body>
</html>
