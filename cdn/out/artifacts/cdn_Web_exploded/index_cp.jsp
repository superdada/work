<%--
  Created by IntelliJ IDEA.
  User: WildMrZhang
  Date: 2017/1/19
  Time: 16:45
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
    <!-- 私有js文件引入 -->
    <!-- bootstrap样式库引入 -->
    <link href="web/module/cdn/css/bootstrap.min.css" rel="stylesheet"/>
    <!-- 私有样式引入 -->
    <link href="web/module/cdn/css/mycss/cdn.css" rel="stylesheet" type="text/css"/>
    <!-- 开关控件引入 -->
    <link rel="stylesheet" type="text/css" href="web/module/cdn/css/mycss/icon/iconfont.css">
    <!-- 测试数据引入 -->
    <%--<script src="web/module/cdn/testData/itemsData.js"></script>--%>
    <script data-main="web/lib/nav.min" src="web/lib/RequireJS/require-2.3/require.min.js"></script>

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

<body style="background:#E7E8EB">

<%--<div class="my-header">
    <div class="my-header-container">
        <div ><img src="web/module/cdn/images/logo_1.png" class="my-logo1" /></div>
        <a href="index.jsp"><div id="xingnengkuaizhao" class="my-header-item-selected">性能快照</div></a>
        <a href="web/module/cdn/realtime_monitoring.jsp"><div class="my-header-item">实时监控</div></a>
        <a href="#"><div class="my-header-item">分析报表</div></a>
        <a href="#"><div class="my-header-item">告警报表</div></a>
        <a href="#"><div class="my-header-item">日志管理</div></a>
        <a href="#"><div class="my-header-item">我的账号</div></a>
        <div onclick="quit()" ><span class="glyphicon glyphicon-log-out my-quit my-float-right" aria-hidden="true"></span></div>
    </div>
</div>--%>

<div id="loading">
    <div class="progress my-progress">
        <div class="progress-bar progress-bar-striped active" role="progressbar" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100" style="width: 100%">
        </div>
    </div>
</div>

<div id="vm" style="display: none" >
    <div class="my-switch-box">
        <div class="my-switch-item">
            <div class= "my-switch-text">维度 :</div>
            <ul class="my-switch-options">
                <li v-for="dimension in dimensions" v-on:click="switch('dimension',dimension.type)" v-bind:class="{ 'my-options-on': curr_dimension == dimension.type, 'my-options-off': dimension != dimension.type}">{{dimension.text}}</li>
            </ul>
        </div>
        <div class="my-switch-item" style="width:370px;">
            <div class= "my-switch-text">指标 :</div>
            <ul class="my-switch-options">
                <li v-for="index in indexes" v-on:click="switch('index',index.type)" v-bind:class="{ 'my-options-on': curr_index == index.type, 'my-options-off': curr_index != index.type}">{{index.text}}</li>
            </ul>
        </div>
        <!--添加start-->
        <div class="my-switch-item">
            <div class= "my-switch-text">类型 :</div>
            <ul class="my-switch-options">
                <li v-for="cdn in cdn_cache_all" v-on:click="switch('cdn',cdn.type)" v-bind:class="{ 'my-options-on': curr_cdn == cdn.type, 'my-options-off': curr_cdn != cdn.type}">{{cdn.text}}</li>
            </ul>
        </div>
        <!--添加cdn开关end-->

        <li style="float:right;margin:5px 0 0 0"><span class="glyphicon glyphicon-share my-icons my-icons-on" style="font-size:20px;" aria-hidden="true"></span></li>
        <li style="float:right;margin:5px 2px 0 0"><span class="glyphicon glyphicon-edit my-icons my-icons-on" style="font-size:20px;" aria-hidden="true"></span></li>
        <li style="float:right;margin:6px 2px 0 0"><span class="glyphicon glyphicon-tasks my-icons my-icons-on" style="font-size:20px;" aria-hidden="true"></span></li>
        <li style="float:right;margin:5px 4px 0 0"><span class="glyphicon glyphicon-random my-icons my-icons-off" style="font-size:20px;" aria-hidden="true"></span></li>
    </div>
    <div class="my-show-box">
        <ul class=" my-list-titles my-list-row">
            <li v-for="title in list_titles"><p>{{title}}</p></li>
        </ul>
        <ul class="my-list-items">
            <li v-for="(index,item) in items"><ul class="my-list-row">
                <li >{{item.name}}</li>
                <li>{{item.belong_to}}</li>
                <li ><ul>
                    <li v-for="index in item.indexes" v-on:click="moreinfo(item, index)" >
                        <div v-show="index.type === 'danger'" class="my-state-block state-danger"></div>
                        <div v-show="index.type === 'warning'" class="my-state-block state-warning"></div>
                        <div v-show="index.type === 'normal'" class="my-state-block state-normal"></div>
                        <div v-show="index.type === null" class="my-state-block state-null"></div>
                    </li>
                </ul></li>
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
            </ul></li>
        </ul>
        <div id = "my-footer">
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
<iframe id="hidden-down-iframe" style="display:none"></iframe>
<script type="text/javascript">
    $(function () {
        /*初始化页面vm*/
        $.getJSON(AppBase + '/pfcsnapshot.do?method=request1', {'max_rows': 10,'curr_page' : 1, 'dimension' : 'data_center', 'index' : 'healthy','cache_cdn':'all'}, function (Response) {var data = Response.data;
            var _data = Response.data;
            var _max_rows = 10;
            var _vmData = {};
            if(_data.length <= _max_rows ) {
                _vmData["page1"] = _data;
            } else {
                for(var d = 1; d <= Math.ceil(_data.length/_max_rows) ; d++) {
                    var _li = [];
                    if(d!=Math.ceil(_data.length/_max_rows)){
                        for(var j=1; j < _max_rows+1 ; j++){
                            _li.push(_data[_max_rows*(d-1)+(j-1)]);
                        };
                    }else{
                        for(var j=1; j < _data.length%_max_rows+1 ; j++){
                            _li.push(_data[_max_rows*(d-1)+(j-1)]);
                        };
                    }
                    _vmData["page"+d] = _li;
                }
            };
            initVM(_vmData);
            /*setTimeout( function(){
             $('#loading').hide();}, 300 );*/

        });
    });

    function quit() {
        $.ajax(AppBase + '/logout.do', {
            success: function(){
                location.href = location.href;
            }
        });
    }

    function initVM(_itemsData) {
        var vm = new Vue({
            el: '#vm',
            data: {
                /*查询条件相关配置*/
                dimensions : [{'type':'data_center', 'text': '数据中心'},{'type':'ICP', 'text': 'ICP'},{'type':'service_type','text':'业务类型'}],
                indexes : [{'type':'healthy', 'text': '健康度'},{'type':'hit_rate', 'text': '命中率'},{'type':'data_rate', 'text': '速率'},{'type':'delay_rate','text':'延迟'}],
                cdn_cache_all:[{'type':'all','text':'ALL'},{'type':'cache','text':'CACHE'},{'type':'cdn','text':'CDN'}],
                curr_cdn:'all',
                curr_dimension : 'data_center',//当前维度类型，枚举值{'data_center', 'ICP'}
                curr_index : 'healthy',//当前指标类型，枚举值{'healthy', 'hit_rate', 'data_rate'}
                /*信息窗口相关配置*/
                all_items:_itemsData,
                curr_page:1
            },
            ready: function () {
                setInterval(this.refreshpage(), 60*60*1000);
            },
            computed:{
                timeLine : function () {
                    var _indexes = this.items[0].indexes;
                    var _timeLine = [];
                    var j = 0 ;
                    for (var i = 2 ; i < _indexes.length; i = i + 3) {
                        _timeLine[j] =  _indexes[i].time.substring(5) ;
                        j = j+1;
                    };
                    return _timeLine;
                },
                list_titles : function () {
                    var _list_titles =[];
                    if(this.curr_dimension =='data_center') {
                        _list_titles =  ['数据中心', '地市', '最近24小时状态', '最近监测时间'];
                    } else if (this.curr_dimension=='ICP') {
                        _list_titles = ['ICP', '域名', '最近24小时状态', '最近监测时间'];
                    } else if(this.curr_dimension=='service_type'){
                        _list_titles=['业务类型','','最近24小时状态','最近监测时间']
                    }else{
                        return 0;
                    }
                    return _list_titles;
                },
                total_pages:function () {
                    var _arr = Object.keys(this.all_items);
                    var _total_pages = _arr.length;
                    return _total_pages;
                },
                items  : function () {
                    var _items = this.all_items["page" + this.curr_page];
                    return _items;
                }

            },
            methods : {
                time_show:function(arr){
                    for(var i = arr.length-1;i>=0;i--){
                        if(arr[i].type !=null){
                            return arr[i].time;
                        }
                    }
                    return arr[0].time;
                },
                refreshpage:function(){
                    $.getJSON(AppBase + '/pfcsnapshot.do?method=request1', { 'dimension' : this.curr_dimension, 'index' : this.curr_index,'cache_cdn':this.curr_cdn}, function (Response) {
                        var _data = Response.data;
                        var _max_rows = 10;
                        var _vmData = {};
                        if(_data.length <= _max_rows ) {
                            _vmData["page1"] = _data;
                        } else {
                            for(var d = 1; d <=(_data.length/_max_rows+1) ; d++) {
                                var _li = [];
                                if(d!=(_data.length/_max_rows+1) ){
                                    for(var j=1; j < _max_rows+1 ; j++){
                                        _li.push(_data[_max_rows*(d-1)+(j-1)]);
                                    };
                                }else{
                                    for(var j=1; j < _data.length%_max_rows+1 ; j++){
                                        _li.push(_data[_max_rows*(d-1)+(j-1)]);
                                    };
                                }
                                _vmData["page"+d] = _li;
                            }
                        };
                        vm.$data.all_items = _vmData;
                        vm.$data.curr_page = 1;
                    });

                },
                moreinfo : function(_item, _index ) {
                    if(_index.type != null){
                        $(window).trigger('event_moreinfo', [ vm.$data.curr_dimension, _item, _index.time,vm.$data.curr_cdn]);
                    } else
                        return 0;
                },
//                moreinfo : function(_item, _index ) {
//                    if(_index.type != null){
//                        $(window).trigger('event_moreinfo', [ vm.$data.curr_dimension, _item, _index.time]);
//                    } else
//                        return 0;
//                },
                switch : function(_type, _option) {
                    //$('#loading').show();
                    if (_type=='dimension') {
                        this.curr_dimension = _option;
                    } else if (_type=='index') {
                        this.curr_index = _option;
                    } else if(_type=='cdn'){
                        this.curr_cdn=_option;
                    }else{
                        return 0;
                    }

                    $.getJSON(AppBase + '/pfcsnapshot.do?method=request1', { 'dimension' : this.curr_dimension, 'index' : this.curr_index,'cache_cdn':this.curr_cdn}, function (Response) {
//                    $.getJSON(AppBase + '/pfcsnapshot.do?method=request1', { 'dimension' : this.curr_dimension, 'index' : this.curr_index}, function (Response) {
                        var _data = Response.data;
                        var _max_rows = 10;
                        var _vmData = {};
                        if(_data.length <= _max_rows ) {
                            _vmData["page1"] = _data;
                        } else {
                            for(var d = 1; d <= Math.ceil(_data.length/_max_rows) ; d++) {
                                var _li = [];
                                if(d!=Math.ceil(_data.length/_max_rows) ){
                                    for(var j=1; j < _max_rows+1 ; j++){
                                        _li.push(_data[_max_rows*(d-1)+(j-1)]);
                                    };
                                }else{
                                    for(var j=1; j < _data.length%_max_rows+1 ; j++){
                                        _li.push(_data[_max_rows*(d-1)+(j-1)]);
                                    };
                                }
                                _vmData["page"+d] = _li;
                            }
                        };
                        vm.$data.all_items = _vmData;
                        //if (_type=='dimension') {
                        vm.$data.curr_page = 1;
                        //  };
                    });
                },
                changeState : function(_type, _id, _monitorFlag, _alarmFlag, _index) {
                    //$('#loading').show();
                    if (_type=='monitor') {
                        _monitorFlag = !_monitorFlag;
                        this.items[_index].monitor_flag = _monitorFlag;
                    } else if (_type=='alarm') {
                        _alarmFlag = ! _alarmFlag;
                        this.items[_index].alarm_flag = _alarmFlag;
                    } else
                        return 0;
                    $.post(AppBase+'/pfcsnapshot.do?method=request3',{
                        'monitor_flag' : _monitorFlag, //监控标示位
                        'alarm_flag' : _alarmFlag,//报警标示位
                        'dimension' : vm.$data.curr_dimension,//当前维度
                        'id' : _id//目标id
                    },function (Response) {
                        $.getJSON(AppBase + '/pfcsnapshot.do?method=request1', {'dimension' : vm.$data.curr_dimension, 'index' : vm.$data.curr_index,'cache_cdn':vm.$data.curr_cdn}, function (Response) {
//                        $.getJSON(AppBase + '/pfcsnapshot.do?method=request1', {'dimension' : vm.$data.curr_dimension, 'index' : vm.$data.curr_index}, function (Response) {
                            var _data = Response.data;
                            var _max_rows = 10;
                            var _vmData = {};
                            if(_data.length <= _max_rows ) {
                                _vmData["page1"] = _data;
                            } else {
                                for(var d = 1; d <= Math.ceil(_data.length/_max_rows) ; d++) {
                                    var _li = [];
                                    if(d!=Math.ceil(_data.length/_max_rows) ){
                                        for(var j=1; j < _max_rows+1 ; j++){
                                            _li.push(_data[_max_rows*(d-1)+(j-1)]);
                                        };
                                    }else{
                                        for(var j=1; j < _data.length%_max_rows+1 ; j++){
                                            _li.push(_data[_max_rows*(d-1)+(j-1)]);
                                        };
                                    }
                                    _vmData["page"+d] = _li;
                                }
                            };
                            vm.$data.all_items = _vmData;
                        });
                    },'JSON');
                },
                share: function(_item, index) {
                    $.post(AppBase+'/pfcsnapshot.do?method=request5',{ // 分享链接
                        'dimension' : vm.$data.curr_dimension, //当前维度
                        'index': vm.$data.curr_index,
                        'cache_cdn':vm.$data.curr_cdn,
                        'id' : _item.id,//目标id
                        'time': (_item.indexes)[23].time
                    },function (Response) {
                        function showMsg(Response){
                            Ext.Msg.show({
                                title: Response.msg,
                                msg: ' 请使用 [ Ctrl + C ] 复制该链接，点击 [ 预览 ] 预览分享页面 ',
                                width: 500,
                                buttonText : {
                                    ok: '预览',
                                    cancel: '关闭'
                                },
                                prompt:true,
                                multiline: false,
                                value: (~location.href.indexOf('?') ? location.href.substr(0, location.href.indexOf('?')).replace("index_cp.jsp","") : location.href.replace("index_cp.jsp","")) + Response.data,
                                fn: function (btn) {
                                    if (btn == 'ok') {
                                        window.open(Response.data);
                                        setTimeout(function(){
                                            showMsg(Response);
                                        }, 0);
                                    }
                                }
                            });
                        }

                        if(Response.success) {
                            if (Response.data) {
                                showMsg(Response);
                            } else{
                                Ext.Msg.alert("成功", Response.msg);
                            }
                        } else {
                            Ext.Msg.alert("失败", "分享失败，请重试！");
                        }
                    },'JSON');
                },
                download : function (_item) {
                    $.post(AppBase+'/pfcsnapshot.do?method=request6',{ // 查询数据
                        'dimension' : vm.$data.curr_dimension,//当前维度
                        'cache_cdn':vm.$data.curr_cdn,//当前类型
                        'id' : _item.id,//目标id
                        'time': (_item.indexes)[23].time,
                        timeout : 60*1000*10 //超时时间设置，单位毫秒
                    },function (Response) {
                        Ext.MessageBox.confirm(Response.msg, Ext.String.format(" [ {0} ] [ {1} ] ", _item.id, _item.name) + Response.data, function(btn){
                            if(btn == 'yes'){
                                $.post(AppBase+'/pfcsnapshot.do?method=request4',{
                                    'dimension' : vm.$data.curr_dimension,//当前维度
                                    'cache_cdn':vm.$data.curr_cdn,
                                    'id' : _item.id,//目标id
                                    'time': (_item.indexes)[23].time,
                                    'fileName': "test"
                                },function (Response) {
                                    $('iframe').attr("src", Response.data);
                                },'JSON');
                            }
                        });
                    },'JSON');
                },
                changePage : function(_type) {
                    /* if(this.curr_page == this.total_pages){
                     $('.my-page-controller').eq(0).removeClass("my-page-controller").addClass("my-page-controller-off");

                     }else if(this.curr_page == 1){
                     $('.my-page-controller').eq(1).removeClass("my-page-controller").addClass("my-page-controller-off");
                     }else {
                     $('.my-page-controller').eq(1).removeClass("my-page-controller-off").addClass("my-page-controller");
                     $('.my-page-controller').eq(0).removeClass("my-page-controller-off").addClass("my-page-controller");
                     }*/
                    if (_type=='Previous') {
                        if (this.curr_page < 2) {
                            return 0;
                        } else {
                            this.curr_page = this.curr_page - 1;
                        }
                    } else if (_type=='next') {
                        if (this.curr_page < this.total_pages ) {
                            this.curr_page = this.curr_page + 1;
                        } else {
                            return 0;
                        }
                    } else if (_type=='jump') {
                        var _pageNum_input =  $("input[id='my-page-num']").val();
                        var _pageNum = _pageNum_input-0;
                        _pageNum = _pageNum*1;
                        var re = /^\+?[1-9][0-9]*$/;　　//正整数
                        if ( (re.test(_pageNum)) && (_pageNum <= this.total_pages) ) {
                            this.curr_page = _pageNum;
                        } else {
                            alert("请输入1到"+this.total_pages+"的整数")
                            return 0;
                        }
                    } else
                        return 0;
                }
            }
        });
        $('#loading').hide()
        $('#vm').show();
    }
</script>
<!--链接文件导航-->
</body>
</html>