/**
 * Created by WildMrZhang on 2017/2/21.
 */
define(['ext','jQuery'], function(Ext, $){
    Ext.onReady(function(){

        $(window).bind('event_moreinfo', eventCreateGrids);

        /**
         * 创建弹出框
         *
         1. vm.$data.dimension : 当前数据维度, -- 表
         2. _id : 目标id -- 数据中心
         3. _time : 查询时间 -- 时间
         */
        function eventCreateGrids(){
            var obj = arguments[2];
            var dimension = arguments[1];
            var id = obj.id;
            //当前承载业务类型
            var service_type=obj.name;
            var time = arguments[3];
            //获取类型参数
            var cache_cdn=arguments[4];

            Ext.tip.QuickTipManager.init();
            Ext.create('Ext.window.Window', {
                title: Ext.String.format("{0} | {1} | {2}", obj.name, obj.belong_to, time,cache_cdn),
                height: 500,
                width: 800,
                layout: 'fit',
                maximizable :true, // 最大化按钮
                items: {  // Let's put an empty grid in just to illustrate fit layout
                    xtype: 'tabpanel',
                    items: (function(li, dimension){
                        for(var i=1; i<4; i++) {
                            var str;
                            switch (i){
                                case 1:
                                    //str = dimension == "data_center" ? "数据中心" : "ICP";
                                    if(dimension=="data_center"){
                                        str="数据中心";
                                    }else if(dimension=="ICP"){
                                        str="ICP";
                                    }else{
                                        str="承载业务";
                                    }
                                    break;
                                case 2:
                                    str = "服务器";
                                    break;
                                case 3:
                                    //str = dimension == "data_center" ? "域名" : "数据中心";
                                    if(dimension=="data_center"){
                                        str="域名";
                                    }else if(dimension=="ICP"){
                                        str="数据中心";
                                    }else{
                                        str="域名";
                                    }
                                    break;
                            }
                            li.push({
                                xtype: 'gridpanel',
                                title: Ext.String.format("{0} | {1} | {2}", obj.name, obj.belong_to, str),
                                store: Ext.create('Ext.data.Store', {
                                    proxy: {
                                        type: 'ajax',
                                        url: Ext.String.format('{0}/pfcsnapshot.do?method=request2&id={1}&dimension={2}&time={3}&type={4}&cache_cdn={5}&service_type={6}',AppBase, id, dimension, time, i,cache_cdn,service_type),
                                        reader: {
                                            type: 'json',
                                            rootProperty: 'data'
                                        }
                                    },
                                    autoLoad: true,
                                    fields: getFields(i, dimension)
                                }),
                                columns: getColumns(i, dimension)
                            });
                        }
                        return li;
                    })([], dimension)
                }
                ,
                listeners: {
                    show: function(){}
                }
            }).show();
        }

        function getFields(i, dimension) {
            switch (dimension) {
                case "data_center":
                    switch (i) {
                        case 1:
                            return [ 'dc_city', 'dc_name', 'ttime', 'health_rate', 'health_level', 'low_speed_rate', 'low_speed_level', 'hit_rate', 'hit_level','delay','delay_level', 'visit_cnt', 'bandwidth'];
                        case 2:
                            return ['dc_city','dc_name','server_address','ttime','health_rate','health_level','low_speed_rate','low_speed_level','hit_rate','hit_level','delay','delay_level','visit_cnt','bandwidth'];
                        case 3:
                            return ['dc_city','dc_name','domain_name','domain_visit_rank','ttime','health_rate','health_level','low_speed_rate','low_speed_level','hit_rate','hit_level','delay','delay_level','visit_cnt','bandwidth'];
                        default:
                            return [];
                    }

                case "ICP":
                    switch (i) {
                        case 1:
                            return ['icp_name','icp_address','ttime','health_rate','health_level','low_speed_rate','low_speed_level','hit_rate','hit_level','delay','delay_level','visit_cnt','bandwidth'];
                        case 2:
                            return ['server_address','icp_name','icp_address','ttime','health_rate','health_level','low_speed_rate','low_speed_level','hit_rate','hit_level','delay','delay_level','visit_cnt','bandwidth'];
                        case 3:
                            return ['dc_city','dc_name','icp_name','icp_address','ttime','health_rate','health_level','low_speed_rate','low_speed_level','hit_rate','hit_level','delay','delay_level','visit_cnt','bandwidth'];
                        default:
                            return [];
                    }
                case "service_type":
                    switch(i){
                        case 1:
                            return ['service_type','cache_cdn','ttime','health_rate','health_level','low_speed_rate','low_speed_level','hit_rate','hit_level','delay','delay_level','visit_cnt','bandwidth'];
                        case 2:
                            return ['dc_city','dc_name','dc_id','server_address','service_type','cache_cdn','ttime','health_rate','health_level','low_speed_rate','low_speed_level','hit_rate','hit_level','delay','delay_level','visit_cnt','bandwidth'];
                        case 3:
                            return ['icp_name','icp_address','domain_name','service_type','cache_cdn','ttime','health_rate','health_level','low_speed_rate','low_speed_level','hit_rate','hit_level','delay','delay_level','visit_cnt','bandwidth'];
                        default:
                            return [];
                    }
            }
        }

        function transIndex(d) {
            switch (d) {
                case "1":
                    return "正常";
                case "2":
                    return "一般告警";
                case "3":
                    return "严重告警";
                case null:
                    return "无数据";
                default:
                    return d;
            }
        }
        //保留3位小数，加上流量单位
        function getFixtFlow(a){
            return (a).toFixed(3)+"MB"
        }

        //保留3位小数，加上百分比符号
        function getFixtPercent(a){
            return (a*100).toFixed(3)+"%"
        }
        //保留3位小数，加上百分比符号
        function getFixtMs(a){
            return (a*1000).toFixed(3)+"ms"
        }

        function getColumns(i, dimension) {
            switch (dimension) {
                case "data_center":
                    switch (i) {
                        case 1:
                            return [
                                { text: '数据中心位置', dataIndex: 'dc_city' },
                                { text: '数据中心', dataIndex: 'dc_name' },
                                { text: '时间(小时)', dataIndex: 'ttime' , width: 150, renderer: function(d){
                                    return Ext.Date.format(new Date(d.time), 'Y-m-d H:i');
                                }},
                                { text: '健康度', dataIndex: 'health_rate' ,renderer:getFixtPercent},
                                { text: '健康度等级', dataIndex: 'health_level', renderer: transIndex },
                                { text: '低速率比例', dataIndex: 'low_speed_rate' ,renderer:getFixtPercent},
                                { text: '低速率等级', dataIndex: 'low_speed_level', renderer: transIndex },
                                { text: '命中率', dataIndex: 'hit_rate' ,renderer:getFixtPercent},
                                { text: '命中率等级', dataIndex: 'hit_level', renderer: transIndex },
                                { text:'延迟',    dataIndex:'delay',renderer:getFixtMs},
                                { text:'延迟等级',dataIndex:'delay_level',renderer: transIndex},
                                { text: '访问量', dataIndex: 'visit_cnt' },
                                { text: '带宽', dataIndex: 'bandwidth',renderer:getFixtFlow , width: 150}
                            ];
                        case 2:
                            return [
                                { text: '数据中心位置', dataIndex: 'dc_city' },
                                { text: '数据中心', dataIndex: 'dc_name' },
                                { text: '服务器IP', dataIndex: 'server_address' },
                                { text: '时间(小时)', dataIndex: 'ttime' , width: 150, renderer: function(d){
                                    return Ext.Date.format(new Date(d.time), 'Y-m-d H:i');
                                }},
                                { text: '健康度', dataIndex: 'health_rate' ,renderer:getFixtPercent},
                                { text: '健康度等级', dataIndex: 'health_level', renderer: transIndex },
                                { text: '低速率比例', dataIndex: 'low_speed_rate' ,renderer:getFixtPercent},
                                { text: '低速率等级', dataIndex: 'low_speed_level', renderer: transIndex },
                                { text: '命中率', dataIndex: 'hit_rate' ,renderer:getFixtPercent},
                                { text: '命中率等级', dataIndex: 'hit_level', renderer: transIndex },
                                { text:'延迟',    dataIndex:'delay',renderer:getFixtMs},
                                { text:'延迟等级',dataIndex:'delay_level',renderer: transIndex},
                                { text: '访问量', dataIndex: 'visit_cnt' },
                                { text: '带宽', dataIndex: 'bandwidth' ,renderer:getFixtFlow, width: 150}
                            ];
                        case 3:
                            return [
                                { text: '数据中心位置', dataIndex: 'dc_city' },
                                { text: '数据中心', dataIndex: 'dc_name' },
                                { text: '域名', dataIndex: 'domain_name' },
                                { text: '域名访问量排行', dataIndex: 'domain_visit_rank' },
                                { text: '时间(小时)', dataIndex: 'ttime' , width: 150, renderer: function(d){
                                    return Ext.Date.format(new Date(d.time), 'Y-m-d H:i');
                                }},
                                { text: '健康度', dataIndex: 'health_rate' ,renderer:getFixtPercent},
                                { text: '健康度等级', dataIndex: 'health_level', renderer: transIndex },
                                { text: '低速率比例', dataIndex: 'low_speed_rate' ,renderer:getFixtPercent},
                                { text: '低速率等级', dataIndex: 'low_speed_level', renderer: transIndex },
                                { text: '命中率', dataIndex: 'hit_rate' ,renderer:getFixtPercent},
                                { text: '命中率等级', dataIndex: 'hit_level', renderer: transIndex },
                                { text:'延迟',    dataIndex:'delay',renderer:getFixtMs},
                                { text:'延迟等级',dataIndex:'delay_level',renderer: transIndex},
                                { text: '访问量', dataIndex: 'visit_cnt' },
                                { text: '带宽', dataIndex: 'bandwidth' ,renderer:getFixtFlow, width: 150}
                            ];
                        default:
                            return [];
                    }

                case "ICP":
                    switch (i) {
                        case 1:
                            return [
                                { text: 'ICP名称', dataIndex: 'icp_name' },
                                { text: 'ICP', dataIndex: 'icp_address' },
                                { text: '时间(小时)', dataIndex: 'ttime' , width: 150, renderer: function(d){
                                    return Ext.Date.format(new Date(d.time), 'Y-m-d H:i');
                                }},
                                { text: '健康度', dataIndex: 'health_rate' ,renderer:getFixtPercent},
                                { text: '健康度等级', dataIndex: 'health_level', renderer: transIndex  },
                                { text: '低速率比例', dataIndex: 'low_speed_rate' ,renderer:getFixtPercent},
                                { text: '低速率等级', dataIndex: 'low_speed_level', renderer: transIndex  },
                                { text: '命中率', dataIndex: 'hit_rate',renderer:getFixtPercent },
                                { text: '命中率等级', dataIndex: 'hit_level', renderer: transIndex  },
                                { text:'延迟',    dataIndex:'delay',renderer:getFixtMs},
                                { text:'延迟等级',dataIndex:'delay_level',renderer: transIndex},
                                { text: '访问量', dataIndex: 'visit_cnt' },
                                { text: '带宽', dataIndex: 'bandwidth' ,renderer:getFixtFlow, width: 150}
                            ];
                        case 2:
                            return [
                                { text: '服务器IP', dataIndex: 'server_address' },
                                { text: 'ICP名称', dataIndex: 'icp_name' },
                                { text: 'ICP地址', dataIndex: 'icp_address' },
                                { text: '时间(小时)', dataIndex: 'ttime' , width: 150, renderer: function(d){
                                    return Ext.Date.format(new Date(d.time), 'Y-m-d H:i');
                                }},
                                { text: '健康度', dataIndex: 'health_rate' ,renderer:getFixtPercent},
                                { text: '健康度等级', dataIndex: 'health_level', renderer: transIndex  },
                                { text: '低速率比例', dataIndex: 'low_speed_rate' ,renderer:getFixtPercent},
                                { text: '低速率等级', dataIndex: 'low_speed_level', renderer: transIndex  },
                                { text: '命中率', dataIndex: 'hit_rate' ,renderer:getFixtPercent},
                                { text: '命中率等级', dataIndex: 'hit_level', renderer: transIndex  },
                                { text:'延迟',    dataIndex:'delay',renderer:getFixtMs},
                                { text:'延迟等级',dataIndex:'delay_level',renderer: transIndex},
                                { text: '访问量', dataIndex: 'visit_cnt' },
                                { text: '带宽', dataIndex: 'bandwidth' ,renderer:getFixtFlow, width: 150}
                            ];
                        case 3:
                            return [
                                { text: '数据中心位置', dataIndex: 'dc_city' },
                                { text: '数据中心', dataIndex: 'dc_name' },
                                { text: 'ICP名称', dataIndex: 'icp_name' },
                                { text: 'ICP地址', dataIndex: 'icp_address' },
                                { text: '时间(小时)', dataIndex: 'ttime' , width: 150, renderer: function(d){
                                    return Ext.Date.format(new Date(d.time), 'Y-m-d H:i');
                                }},
                                { text: '健康度', dataIndex: 'health_rate' ,renderer:getFixtPercent},
                                { text: '健康度等级', dataIndex: 'health_level', renderer: transIndex  },
                                { text: '低速率比例', dataIndex: 'low_speed_rate' ,renderer:getFixtPercent},
                                { text: '低速率等级', dataIndex: 'low_speed_level', renderer: transIndex  },
                                { text: '命中率', dataIndex: 'hit_rate' ,renderer:getFixtPercent},
                                { text: '命中率等级', dataIndex: 'hit_level', renderer: transIndex  },
                                { text:'延迟',    dataIndex:'delay',renderer:getFixtMs},
                                { text:'延迟等级',dataIndex:'delay_level',renderer: transIndex},
                                { text: '访问量', dataIndex: 'visit_cnt' },
                                { text: '带宽', dataIndex: 'bandwidth', width: 150 ,renderer:getFixtFlow}
                            ];
                        default:
                            return [];
                    }
                case "service_type":
                        switch(i){
                            case 1:
                                //{text:'域名访问量排行',dataIndex:'domain_visit_rank'},
                                return [{text:'承载业务',dataIndex:'service_type'},{text:'服务器类型（CACHE/CDN/ALL）',dataIndex:'cache_cdn'},
                                    {text:'时间(小时)',dataIndex:'ttime',renderer:function(d){
                                        return Ext.Date.format(new Date(d.time), 'Y-m-d H:i');
                                    }},{text:'健康度',dataIndex:'health_rate',renderer:getFixtPercent},{text:'健康度等级',dataIndex:'health_level', renderer: transIndex},
                                    {text:'低速率比例',dataIndex:'low_speed_rate',renderer:getFixtPercent},{text:'低速率等级',dataIndex:'low_speed_level', renderer: transIndex },
                                    {text:'命中率',dataIndex:'hit_rate',renderer:getFixtPercent},{text:'命中率等级',dataIndex:'hit_level', renderer: transIndex},
                                    {text:'延迟',dataIndex:'delay',renderer:getFixtMs},
                                    {text:'延迟等级',dataIndex:'delay_level',renderer: transIndex},{text:'访问量',dataIndex:'visit_cnt'},{text:'带宽',dataIndex:'bandwidth', width: 150 ,renderer:getFixtFlow}];
                            case 2:
                                return [{text:'数据中心位置',dataIndex:'dc_city'},{text:'数据中心名称',dataIndex:'dc_name'},{text:'数据中心编号',dataIndex:'dc_id'},
                                    {text:'服务器IP',dataIndex:'server_address'},{text:'承载业务',dataIndex:'service_type'},
                                    {text:'服务器类型（CACHE/CDN/ALL）',dataIndex:'cache_cdn'},{text:'时间(小时)',dataIndex:'ttime',renderer:function(d){
                                        return Ext.Date.format(new Date(d.time), 'Y-m-d H:i');
                                    }},
                                    {text:'健康度',dataIndex:'health_rate',renderer:getFixtPercent},{text:'健康度等级',dataIndex:'health_level', renderer: transIndex},{text:'低速率',dataIndex:'low_speed_rate',renderer:getFixtPercent},
                                    {text:'低速率等级',dataIndex:'low_speed_level', renderer: transIndex},{text:'命中率',dataIndex:'hit_rate',renderer:getFixtPercent},{text:'命中率等级',dataIndex:'hit_level', renderer: transIndex},{text:'延迟',dataIndex:'delay',renderer:getFixtMs},
                                    {text:'延迟等级',dataIndex:'delay_level',renderer: transIndex},
                                    {text:'访问量',dataIndex:'visit_cnt'},{text:'带宽',dataIndex:'bandwidth', width: 150 ,renderer:getFixtFlow}];
                            case 3:
                                //{text:'域名访问量排行',dataIndex:'domain_visit_rank'},
                                return [{text:'ICP名称',dataIndex:'icp_name'},
                                          {text:'ICP',dataIndex:'icp_address'},
                                         { text: '域名', dataIndex: 'domain_name' },
                                         {text:'承载业务',dataIndex:'service_type'},
                                         {text:'服务器类型（CACHE/CDN/ALL）',dataIndex:'cache_cdn'},
                                         {text:'时间(小时)',dataIndex:'ttime',renderer:function(d){
                                        return Ext.Date.format(new Date(d.time), 'Y-m-d H:i');
                                         }},
                                    {text:'健康度',dataIndex:'health_rate',renderer:getFixtPercent},{text:'健康度等级',dataIndex:'health_level', renderer: transIndex},
                                    {text:'低速率比例',dataIndex:'low_speed_rate',renderer:getFixtPercent},{text:'低速率等级',dataIndex:'low_speed_level', renderer: transIndex},
                                    {text:'命中率',dataIndex:'hit_rate',renderer:getFixtPercent},{text:'命中率等级',dataIndex:'hit_level', renderer: transIndex},{text:'延迟',dataIndex:'delay',renderer:getFixtMs},
                                    {text:'延迟等级',dataIndex:'delay_level',renderer: transIndex},{text:'访问量',dataIndex:'visit_cnt'},{text:'带宽',dataIndex:'bandwidth', width: 150 ,renderer:getFixtFlow}];
                            default:
                                return [];
                        }
            }
        }
    });

});