/**
 * Created by xzl on 2017/6/8.
 */
$(function () {


        $('#my-datetime1').datetimepicker({
            format: "yyyy-mm-dd hh:ii",
            language: 'zh-CN',
            //weekStart: 1,
            todayBtn: 1,
            minView: 0,
            maxView: 2,
            autoclose: true,
            todayHighlight: 1,
            initialDate: '2017-02-21 00:00'
            //startView: 2,
            //forceParse: 0,
            //showMeridian: 1
        });
        $('#my-datetime2').datetimepicker({
            format: "yyyy-mm-dd hh:ii",
            language: 'zh-CN',
            //weekStart: 1,
            todayBtn: 1,
            minView: 0,
            maxView: 2,
            autoclose: true,
            todayHighlight: 1,
            //startView: 2,
            //forceParse: 0,
            //showMeridian: 1
        });
        $("#my-datetime1").on("changeDate", function (e) {
            $('#my-datetime2').datetimepicker('setStartDate', e.date);
        });
        $("#my-datetime2").on("changeDate", function (e) {
            $('#my-datetime1').datetimepicker('setEndDate', e.date);
        });
        /*初始化页面vm*/
        var _vmData = {};
        $.ajax({
            url: AppBase + '/realtimeonline.do?method=request1',
            data: {
                cache_cdn: 'cdn'
            },
            cache: false,
            async: false,
            type: "GET",
            dataType: 'json',
            success: function (Response) {
                _vmData.module1Data = Response.data;
            }
        });

        $.ajax({
            url: AppBase + '/realtimeonline.do?method=request2',
            data: {
                index: 'health_rate',
                IPtype: 'user',
                cache_cdn: 'cdn'
            },
            cache: false,
            async: false,
            type: "GET",
            dataType: 'json',
            success: function (Response) {
                _vmData.module2Data = Response.data;
            }
        });
        $.ajax({
            url: AppBase + '/realtimeonline.do?method=request3',
            data: {
                index: 'health_rate',
                timeType: 'today',
                startTime: '',
                endTime: '',
                cache_cdn: 'cdn'
            },
            cache: false,
            async: false,
            type: "GET",
            dataType: 'json',
            success: function (Response) {
                _vmData.module3Data = Response.data;
            }
        });

        initVM(_vmData);
        $('#loading').hide();
    });

/**
 * 功能：
 *     过滤条件单击事件，实现刷新全网实时监控、地市实时监控和全网监控曲线公共请求
 * */
function requestUtil(url, dataParams, vm, moduleId) {
    $.ajax({
        url: AppBase + url,
        data: dataParams,
        cache: false,
        async: false,
        type: "GET",
        dataType: 'json',
        success: function (Response) {
            //                _vmData= Response.data;
            //更新图表数据
            if (moduleId == 1) {
                vm.$data.module1_data = Response.data;
                vm.updatemodule1chart();
            } else if (moduleId == 2) {
                vm.$data.module2_data = Response.data.data;
                vm.updatemodule2chart();
            } else {
                vm.$data.module3_data = Response.data;
                vm.updatemodule3chart();
            }
        }
    });
}

function initVM(_data) {
    var vm = new Vue({
        el: '#my-m2-vm',
        data: {
            cdn_cache_all: [
                {'type': 'cdn', 'text': 'CDN'},
                {'type': 'cache', 'text': 'CACHE'}
            ],
            curr_cdn: 'cdn',
            thresholds: {
                health_rate: [[70, '#cc0033'], [80, '#FF9900'], [90, '#FFcc33'], [95, '#99CC33'], [100, '#00CD00']],
                hit_rate: [[70, '#cc0033'], [80, '#FF9900'], [90, '#FFcc33'], [95, '#99CC33'], [100, '#00CD00']],
                avg_dl_speed: [[1000, '#cc0033'], [2000, '#FF9900'], [3000, '#FFcc33'], [4000, '#99CC33'], [5000, '#00CD00']],
                bandwidth:[[1000, '#cc0033'], [2000, '#FF9900'], [3000, '#FFcc33'], [4000, '#99CC33'], [5000, '#00CD00']],//待修改
                visit_cnt:[[1000, '#cc0033'], [2000, '#FF9900'], [3000, '#FFcc33'], [4000, '#99CC33'], [5000, '#00CD00']],//待修改
                delay:[[1000, '#cc0033'], [2000, '#FF9900'], [3000, '#FFcc33'], [4000, '#99CC33'], [5000, '#00CD00']] //待修改
            },//图例门限配置
            charts_config: {
                chart1: {'domID': 'my-m2-submodule1-chart1', 'type': 'ring'},
                chart2: {'domID': 'my-m2-submodule1-chart2', 'type': 'ring'},
                chart3: {'domID': 'my-m2-submodule1-chart3', 'type': 'gauge'},
                chart4: {'domID': 'my-m2-submodule1-chart4', 'type': 'bar'},
                chart5: {'domID': 'my-m2-submodule1-chart5', 'type': 'bar'},
                chart6: {'domID': 'my-m2-submodule1-chart6', 'type': 'bar'},
                chart7: {'domID': 'my-m2-submodule2-chart1', 'type': 'map'},
                chart8: {'domID': 'my-m2-submodule3-chart1', 'type': 'line'},
            },//图表配置
            m2_indexes: [
                {type: 'health_rate', text: 'HTTP业务成功率', unit: '%'},
                {type: 'hit_rate', text: '缓存命中率', unit: '%'},
                {type: 'avg_dl_speed', text: '平均下载速率', unit: 'KB/s'},
                {type: 'bandwidth', text: '流量', unit: 'MB'},
                {type: 'visit_cnt', text: '请求数', unit: '次'},
                {type: 'delay', text: '首字节延迟', unit: 's'},
            ],
            m2_index_units: {
                health_rate: '%',
                hit_rate: '%',
                avg_dl_speed: 'KB/s',
                bandwidth:'MB',
                visit_cnt:'次',
                delay:'s'
            },
            m2_IPtypes: [
                {type: 'user', text: '用户所在地'},
                {type: 'data_center', text: '数据中心所在地'},
            ],
            /*m3_indexes: [
                {type: 'health_rate', text: '健康度', unit: '%'},
                {type: 'hit_rate', text: '缓存命中率', unit: '%'},
                {type: 'avg_dl_speed', text: '平均下载速率', unit: 'KB/s'},
            ],
            m3_index_units: {
                health_rate: '%',
                hit_rate: '%',
                avg_dl_speed: 'KB/s',
            },*/
            m3_times: [
                {type: '3hours', text: '最近3小时'},
                {type: 'today', text: '今天'},
                {type: '7days', text: '最近7天'},
                {type: '30days', text: '最近30天'},
            ],
            module1_data: _data.module1Data,
            module2_data: _data.module2Data.data,
            module3_data: _data.module3Data,
            charts: {},
            curr_m2_index: 'health_rate',//枚举值 : 'health_rate', 'hit_rate', 'avg_dl_speed'
            curr_m2_IPtype: 'user', //枚举值 : 'data_center', 'user'
            curr_m3_index: 'health_rate',//枚举值 : 'health_rate', 'hit_rate', 'avg_dl_speed'
            curr_m3_timeType: 'today',//枚举值 : '3hours', 'today', '7days', '30days', 'custom'
            curr_m3_custom_startTime: '',//
            curr_m3_custom_endTime: '',//
        },
        computed: {
            m1_health_rate_color: function () {
                var _color = '';
                for (var i = 0; i < this.thresholds.health_rate.length; i++) {
                    if (this.module1_data.health_rate <= this.thresholds.health_rate[i][0]) {
                        _color = this.thresholds.health_rate[i][1];
                        return _color;
                    }
                }
            },
            m1_hit_rate_color: function () {
                var _color = '';
                for (var i = 0; i < this.thresholds.hit_rate.length; i++) {
                    if (this.module1_data.hit_rate <= this.thresholds.hit_rate[i][0]) {
                        _color = this.thresholds.hit_rate[i][1];
                        return _color;
                    }
                }
            },
            m1_avg_dl_speed_color: function () {
                var _color = '';
                for (var i = 0; i < this.thresholds.avg_dl_speed.length; i++) {
                    if (this.module1_data.avg_dl_speed <= this.thresholds.avg_dl_speed[i][0]) {
                        _color = this.thresholds.avg_dl_speed[i][1];
                        return _color;
                    }
                }
            },
            m1_bandwidth_color:function(){
                var _color = '';
                for (var i = 0; i < this.thresholds.bandwidth.length; i++) {
                    if (this.module1_data.bandwidth <= this.thresholds.bandwidth[i][0]) {
                        _color = this.thresholds.bandwidth[i][1];
                        return _color;
                    }
                }
            },
            m1_visit_cnt_color:function(){
                var _color = '';
                for (var i = 0; i < this.thresholds.visit_cnt.length; i++) {
                    if (this.module1_data.visit_cnt <= this.thresholds.visit_cnt[i][0]) {
                        _color = this.thresholds.visit_cnt[i][1];
                        return _color;
                    }
                }
            },
            m1_delay_color:function(){
                var _color = '';
                for (var i = 0; i < this.thresholds.delay.length; i++) {
                    if (this.module1_data.delay <= this.thresholds.delay[i][0]) {
                        _color = this.thresholds.delay[i][1];
                        return _color;
                    }
                }
            }
        },
        created: function () {
        },
        ready: function () {
            //layui
            //设定初始值
            //$("#my-m2-submodule2").children("ul").children("li").eq(0).html($("#index2").find("option:selected").text());
           // $("#my-m2-submodule3").children("ul").children("li").eq(0).html($("#index3").find("option:selected").text());
            layui.use('form', function() {
                var form = layui.form();
                    form.on('select(index2)',function(data){
                        vm.$data.curr_m2_index=data.value;
                        $("#my-m2-submodule2").children("ul").children("li").eq(0).html(data.value);
                        var type ="";
                        $.each(vm.$data.m2_indexes, function (n, value) {
                            if(data.value == value.text){
                                type = value.type;
                            };
                        });
                        vm.m2_switch_index(type);
                        form.render('select');
                    });
                    form.on('select(index3)',function(data){
                        vm.$data.curr_m3_index=data.value;
                        $("#my-m2-submodule3").children("ul").children("li").eq(0).html(data.value);
                        var type ="";
                        $.each(vm.$data.m2_indexes, function (n, value) {
                            if(data.value == value.text){
                                type = value.type;
                            };
                        });
                        vm.m3_switch_index(type);
                        form.render('select');
                    });
            });
            this.updatemodule1chart();
            this.updatemodule2chart();
            this.updatemodule3chart();


        },
        methods: {
            updatemodule1chart:function(){
                var _data = {};
                /*子模块1 健康度 图表*/
                _data = {
                    'data': [
                        {'value': this.module1_data.health_rate, 'name': 'health'},
                        {'value': 100 - this.module1_data.health_rate, 'name': ''}
                    ],
                    'others': {
                        color: this.m1_health_rate_color
                    }
                };
                this.charts.chart1 = chartFactory(this.charts_config.chart1.type, this.charts_config.chart1.domID, _data);
                /*子模块1 命中率 图表*/
                _data = {
                    'data': [
                        {'value': this.module1_data.hit_rate, 'name': 'hit rate'},
                        {'value': 100 - this.module1_data.hit_rate, 'name': ''}
                    ],
                    'others': {
                        color: this.m1_hit_rate_color
                    }
                };
                this.charts.chart2 = chartFactory(this.charts_config.chart2.type, this.charts_config.chart2.domID, _data);
                /*子模块1 速率 图表*/
                _data = {
                    'data': [
                        {'value': this.module1_data.avg_dl_speed, 'name': '下载速率'}],
                    'others': {
                        thresholds: this.thresholds.avg_dl_speed
                    }
                };
                this.charts.chart3 = chartFactory(this.charts_config.chart3.type, this.charts_config.chart3.domID, _data);
                /*子模块1 流量 图表*/
                this.module1_data.bandwidth=2000;
                var value = new Array();
                value[0] =this.module1_data.bandwidth;
                _data = {
                    'data':
                    {'value': value, 'name': ['流量'],'color':this.m1_bandwidth_color},//类目都是数组
                    'others': {
                        thresholds: this.thresholds.bandwidth
                    }
                };
                this.charts.chart4 = chartFactory(this.charts_config.chart4.type, this.charts_config.chart4.domID, _data);
                /*子模块1 请求数 图表*/
                this.module1_data.visit_cnt=1000;
                var value = new Array();
                value[0] =this.module1_data.visit_cnt;
                _data = {
                    'data':
                    {'value': value, 'name': ['请求数'],'color':this.m1_visit_cnt_color},//类目都是数组
                    'others': {
                        thresholds: this.thresholds.visit_cnt
                    }
                };
                this.charts.chart5 = chartFactory(this.charts_config.chart5.type, this.charts_config.chart5.domID, _data);
                /*子模块1 首字节延迟 图表*/
                this.module1_data.delay=3000;
                var value = new Array();
                value[0] =this.module1_data.delay;
                _data = {
                    'data':
                    {'value': value, 'name': ['首字节延迟'],'color':this.m1_delay_color},//类目都是数组
                    'others': {
                        thresholds: this.thresholds.delay
                    }
                };
                this.charts.chart6 = chartFactory(this.charts_config.chart6.type, this.charts_config.chart6.domID, _data);
            },
            updatemodule2chart:function(){
                var _data = {};
                /*子模块2 地图*/
                _data = {
                    'data': this.module2_data,
                    'others': {
                        'thresholds': this.thresholds[this.curr_m2_index],
                        'unit': this.m2_index_units[this.curr_m2_index],
                    }
                };
                this.charts.chart7 = chartFactory(this.charts_config.chart7.type, this.charts_config.chart7.domID, _data);
            },
            updatemodule3chart:function(){
                var _data = {};
                /*子模块3 折线图*/
                _data = {
                    'data': this.module3_data,
                    'others': {
                        'unit': this.m2_index_units[this.curr_m3_index],
                    }
                };
                this.charts.chart8 = chartFactory(this.charts_config.chart8.type, this.charts_config.chart8.domID, _data);
            },
            switch: function (_type, _option) {
                if (_type == 'cdn') {
                    this.curr_cdn = _option;
                } else {
                    return 0;
                }
                var _vmData = {};
                var requestParams1 = {
                    cache_cdn: vm.$data.curr_cdn
                };
                var requestParams2 = {
                    index: vm.$data.curr_m2_index,//为什么只有2的指标？
                    IPtype: vm.$data.curr_m2_IPtype,
                    cache_cdn: vm.$data.curr_cdn
                };
                var requestParams3 = {
                    index: vm.$data.curr_m3_index,//为什么只有2的指标？
                    timeType: vm.$data.curr_m3_timeType,
                    startTime: vm.$data.curr_m3_custom_startTime,
                    endTime: vm.$data.curr_m3_custom_endTime,
                    cache_cdn: vm.$data.curr_cdn
                };
                requestUtil('/realtimeonline.do?method=request1', requestParams1, vm, 1);
                requestUtil('/realtimeonline.do?method=request2', requestParams2, vm, 2);
                requestUtil('/realtimeonline.do?method=request3', requestParams3, vm, 3);
            },
            m2_switch_index: function (_type) {
                $.getJSON(AppBase + '/realtimeonline.do?method=request2', {
                    index: _type,
                    IPtype: this.curr_m2_IPtype,
                    cache_cdn: this.curr_cdn
                }, function (Response) {
                    vm.$data.module2_data = Response.data.data;
                    vm.$data.curr_m2_index = _type;
                    vm.updatemodule2chart();

                });
            },
            m2_switch_IPtype: function (_type) {
                $.getJSON(AppBase + '/realtimeonline.do?method=request2', {
                    index: this.curr_m2_index,
                    IPtype: _type,
                    cache_cdn: this.curr_cdn
                }, function (Response) {
                    vm.$data.module2_data = Response.data.data;
                    vm.$data.curr_m2_IPtype = _type;
                    vm.updatemodule2chart();
                });
            },
            m3_switch_index: function (_type) {
                $.getJSON(AppBase + '/realtimeonline.do?method=request3', {
                    index: _type,
                    cache_cdn: this.curr_cdn,
                    timeType: this.curr_m3_timeType,
                    startTime: vm.$data.curr_m3_custom_startTime,
                    endTime: vm.$data.curr_m3_custom_endTime,
                }, function (Response) {
                    vm.$data.module3_data = Response.data;
                    vm.$data.curr_m3_index = _type;
                    vm.updatemodule3chart();
                });
            },
            m3_switch_time: function (_type) {
                $.getJSON(AppBase + '/realtimeonline.do?method=request3', {
                    index: this.curr_m3_index,
                    timeType: _type,
                    cache_cdn: this.curr_cdn
                }, function (Response) {
                    vm.$data.module3_data = Response.data;
                    vm.$data.curr_m3_timeType = _type;
                    vm.updatemodule3chart();
                    vm.$data.curr_m3_custom_startTime = '';
                    vm.$data.curr_m3_custom_endTime = '';
                });
            },
            m3_custom_time_query: function () {
                if (this.curr_m3_custom_startTime != '' && this.curr_m3_custom_endTime != '') {
                    $.getJSON(AppBase + '/realtimeonline.do?method=request3', {
                        index: this.curr_m3_index,
                        cache_cdn: this.curr_cdn,
                        timeType: 'custom',
                        startTime: vm.$data.curr_m3_custom_startTime,
                        endTime: vm.$data.curr_m3_custom_endTime,
                    }, function (Response) {
                        vm.$data.module3_data = Response.data;
                        vm.$data.curr_m3_timeType = 'custom';
                        vm.updatemodule3chart();
                    });
                }
            }
        }
    });
    $('#vm').show();
}

function quit() {
    $.ajax(AppBase + '/logout.do', {
        success: function () {
            location.href = location.href;
        }
    });
}
