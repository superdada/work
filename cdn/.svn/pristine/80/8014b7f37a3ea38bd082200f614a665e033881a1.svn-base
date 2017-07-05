/**
 * Created by WildMrZhang on 2017/2/21.
 */
  define(['ext','jQuery'], function(Ext, $){
    Ext.onReady(function(){

        $(window).bind('event_moreinfo', eventCreateGrids);

        /**
         * 创建弹出框
         *
         *
         1.vm.$data.curr_datasource
         2、vm.$data.curr_cdn
         3. vm.$data.dimension : 当前数据维度, -- 表
         4. _id : 目标id -- 数据中心
         5. _time : 查询时间 -- 时间
         6、vm.$data.directory_level
         */
        function eventCreateGrids(){
            var datasource =  arguments[1];  //获取类型参数
            var cache_cdn=arguments[2];
            var dimension = arguments[3];
            var obj = arguments[4];
            var time = arguments[5];
            var directory_level =arguments[6];
            var id = obj.id;
            var len=3;
            if((dimension != 'ICP')&& (directory_level==2)){
                len=2;
            }else if (dimension == 'service_type'){
                len=4;
            }
            //当前承载业务类型
           // var service_type=obj.name;
            Ext.tip.QuickTipManager.init();
            Ext.create('Ext.window.Window', {
                title: Ext.String.format("{0} | {1}", obj.id, time),
                height: 500,
                width: 800,
                layout: 'fit',
                maximizable :true, // 最大化按钮
                items: {  // Let's put an empty grid in just to illustrate fit layout
                    xtype: 'tabpanel',
                    items: (function(li,len){
                        for(var i=1; i<len; i++) {
                            var str;
                           if( (i==1) && (directory_level ==1)){
                               if(dimension == 'ICP')  {
                                   str ='ICP';
                               }else if(dimension == 'data_center'){
                                   str ='内容中心';
                               }else if(dimension == 'service_type'){
                                   str ='业务类型';
                               }else {
                                   str ='边缘节点';
                               }
                           }else if((i==1) && (directory_level ==2)){
                               if(dimension == 'ICP')  {
                                   str ='域名';
                               }else{
                                   str ='服务器';
                               }
                           }else if(i ==2) {
                               if(dimension == 'ICP' && (directory_level ==1))  {
                                   str ='域名';
                               }else{
                                   str ='服务器';
                               }
                            }else if(i ==3){
                               str ='域名';
                           }
                            li.push({
                                xtype: 'gridpanel',
                                title: Ext.String.format("{0} | {1}", obj.id, str),
                                store: Ext.create('Ext.data.Store', {
                                    proxy: {
                                        type: 'ajax',
                                        url: Ext.String.format('{0}/pfcsnapshot.do?method=request2&id={1}&datasource={2}&cache_cdn={3}&dimension={4}&time={5}&type={6}&directory_level={7}',AppBase, id, datasource,cache_cdn,dimension, time,i,directory_level),
                                        reader: {
                                            type: 'json',
                                            rootProperty: 'data'
                                        }
                                    },
                                    autoLoad: true,
                                    fields: getFields(i, datasource,cache_cdn,dimension, directory_level)
                        }),
                                columns:  getColumns(i,datasource,cache_cdn,dimension, directory_level)
                            });
                        }
                        return li;
                    })([],len)
                }
                ,
                listeners: {
                    show: function(){}
                }
            }).show();
        }
 function  getss(){
     return ['content_center','cache_cdn','ttime','health_rate','health_level','hit_rate','hit_level','avg_dl_speed','avg_dl_level','delay','delay_level','bandwidth','bandwidth_level','visit_cnt','visit_level'];

 }

      function getFields(i, datasource,cache_cdn,dimension, directory_level) {
         /* var strs= new Array(); //定义一数组
          strs=combination.split(",");
          var datasource=strs[0];
          var cache_cdn =strs[1];
          var dimension =strs[2];
          var directory_level =strs[3];*/
            switch (i) {
                case 1:
                {
                    if (directory_level == 1) {
                        if (dimension == 'ICP') {
                            return ['icp_name','icp_rank','cache_cdn','ttime','health_rate','health_level','hit_rate','hit_level','avg_dl_speed','avg_dl_level','delay','delay_level','bandwidth','bandwidth_level','visit_cnt','visit_level'];
                        } else if(dimension == 'service_type'){
                            return ['service_name','cache_cdn','ttime','health_rate','health_level','hit_rate','hit_level','avg_dl_speed','avg_dl_level','delay','delay_level','bandwidth','bandwidth_level','visit_cnt','visit_level'];
                        }else if (dimension == 'data_center') {
                            return ['content_center','cache_cdn','ttime','health_rate','health_level','hit_rate','hit_level','avg_dl_speed','avg_dl_level','delay','delay_level','bandwidth','bandwidth_level','visit_cnt','visit_level'];
                        } else {
                            return ['city_name','cache_cdn','ttime','health_rate','health_level','hit_rate','hit_level','avg_dl_speed','avg_dl_level','delay','delay_level','bandwidth','bandwidth_level','visit_cnt','visit_level'];
                        }
                    } else {
                        if (dimension == 'ICP') {
                            return ['icp_name','icp_rank','domain_name','cache_cdn','ttime','health_rate','health_level','hit_rate','hit_level','avg_dl_speed','avg_dl_level','delay','delay_level','bandwidth','bandwidth_level','visit_cnt','visit_level'];
                        } else if (dimension == 'data_center') {
                            return ['content_center','server_name','server_address','service_name','cache_cdn','ttime','health_rate','health_level','hit_rate','hit_level','avg_dl_speed','avg_dl_level','delay','delay_level','bandwidth','bandwidth_level','visit_cnt','visit_level'];
                        }else {
                            return ['city_name','server_name','server_address','service_name','cache_cdn','ttime','health_rate','health_level','hit_rate','hit_level','avg_dl_speed','avg_dl_level','delay','delay_level','bandwidth','bandwidth_level','visit_cnt','visit_level'];

                        }
                    }
                }
                case 2:
                {
                    if (directory_level == 1) {
                        if (dimension == 'ICP') {
                            return ['icp_name','icp_rank','domain_name','cache_cdn','ttime','health_rate','health_level','hit_rate','hit_level','avg_dl_speed','avg_dl_level','delay','delay_level','bandwidth','bandwidth_level','visit_cnt','visit_level'];
                        } else if (dimension == 'data_center') {
                            return ['content_center','server_name','server_address','service_name','cache_cdn','ttime','health_rate','health_level','hit_rate','hit_level','avg_dl_speed','avg_dl_level','delay','delay_level','bandwidth','bandwidth_level','visit_cnt','visit_level'];
                        } else if (dimension == 'service_type') {
                            return ['server_name','server_address','service_name','cache_cdn','ttime','health_rate','health_level','hit_rate','hit_level','avg_dl_speed','avg_dl_level','delay','delay_level','bandwidth','bandwidth_level','visit_cnt','visit_level'];
                        } else {
                            return ['city_name','server_name','server_address','service_name','cache_cdn','ttime','health_rate','health_level','hit_rate','hit_level','avg_dl_speed','avg_dl_level','delay','delay_level','bandwidth','bandwidth_level','visit_cnt','visit_level'];
                        }
                    } else {
                        return ['server_name','server_address','icp_name','icp_rank','domain_name','service_name','cache_cdn','ttime','health_rate','health_level','hit_rate','hit_level','avg_dl_speed','avg_dl_level','delay','delay_level','bandwidth','bandwidth_level','visit_cnt','visit_level'];
                    }
                }

                case 3:
                {
                    return ['icp_name','icp_rank','domain_name','service_name','cache_cdn','ttime','health_rate','health_level','hit_rate','hit_level','avg_dl_speed','avg_dl_level','delay','delay_level','bandwidth','bandwidth_level','visit_cnt','visit_level'];
                }
                default:
                {
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

function  getmm(){
    return
    [
        { text: '内容中心', dataIndex: 'content_center' },
        { text: '服务器类型', dataIndex: 'cache_cdn' },
        { text: '时间(小时)', dataIndex: 'ttime' , width: 150, renderer: function(d){
            return Ext.Date.format(new Date(d.time), 'Y-m-d H:i');
        }},
        { text: 'HTTP业务成功率', dataIndex: 'health_rate' ,renderer:getFixtPercent},
        { text: 'HTTP业务成功率等级', dataIndex: 'health_level', renderer: transIndex },
        { text: '命中率', dataIndex: 'hit_rate' ,renderer:getFixtPercent},
        { text: '命中率等级', dataIndex: 'hit_level', renderer: transIndex },
        { text: '平均下载速率', dataIndex: 'avg_dl_speed' ,renderer:getFixtPercent},
        { text: '平均下载速率等级', dataIndex: 'avg_dl_level', renderer: transIndex },
        { text:'首字节延迟',    dataIndex:'delay'},
        { text:'首字节延迟等级',dataIndex:'delay_level',renderer: transIndex},
        { text: '流量', dataIndex: 'bandwidth' },
        { text: '流量等级', dataIndex: 'bandwidth_level',renderer:transIndex },
        { text: '请求数', dataIndex: 'visit_cnt' },
        { text: '请求数等级', dataIndex: 'visit_level',renderer:transIndex}
    ];
}
        function getColumns(i,datasource,cache_cdn,dimension, directory_level){
           /* var strs= new Array(); //定义一数组
            strs=combination.split(",");
            var datasource=strs[0];
            var cache_cdn =strs[1];
            var dimension =strs[2];
            var directory_level =strs[3];*/
            var columns= new Array(); //定义一数组
            switch (i) {
                case 1:
                {
                    if (directory_level == 1) {
                        if (dimension == 'ICP') {
                            columns= [
                                { text: 'ICP名称', dataIndex: 'icp_name' },
                                { text: 'ICP访问量排行', dataIndex: 'icp_rank' },
                                { text: '服务器类型', dataIndex: 'cache_cdn' },
                                { text: '时间(小时)', dataIndex: 'ttime' , width: 150, renderer: function(d){
                                    return Ext.Date.format(new Date(d.time), 'Y-m-d H:i');
                                }},
                                { text: 'HTTP业务成功率', dataIndex: 'health_rate' ,renderer:getFixtPercent},
                                { text: 'HTTP业务成功率等级', dataIndex: 'health_level', renderer: transIndex },
                                { text: '命中率', dataIndex: 'hit_rate' ,renderer:getFixtPercent},
                                { text: '命中率等级', dataIndex: 'hit_level', renderer: transIndex },
                                { text: '平均下载速率', dataIndex: 'avg_dl_speed' ,renderer:getFixtPercent},
                                { text: '平均下载速率等级', dataIndex: 'avg_dl_level', renderer: transIndex },
                                { text:'首字节延迟',    dataIndex:'delay'},
                                { text:'首字节延迟等级',dataIndex:'delay_level',renderer: transIndex},
                                { text: '流量', dataIndex: 'bandwidth' },
                                { text: '流量等级', dataIndex: 'bandwidth_level',renderer:transIndex },
                                { text: '请求数', dataIndex: 'visit_cnt' },
                                { text: '请求数等级', dataIndex: 'visit_level',renderer:transIndex}
                            ];
                            break;
                        } else if(dimension == 'service_type'){
                            columns= [
                                { text: '业务类型', dataIndex: 'service_name' },
                                { text: '服务器类型', dataIndex: 'cache_cdn' },
                                { text: '时间(小时)', dataIndex: 'ttime' , width: 150, renderer: function(d){
                                    return Ext.Date.format(new Date(d.time), 'Y-m-d H:i');
                                }},
                                { text: 'HTTP业务成功率', dataIndex: 'health_rate' ,renderer:getFixtPercent},
                                { text: 'HTTP业务成功率等级', dataIndex: 'health_level', renderer: transIndex },
                                { text: '命中率', dataIndex: 'hit_rate' ,renderer:getFixtPercent},
                                { text: '命中率等级', dataIndex: 'hit_level', renderer: transIndex },
                                { text: '平均下载速率', dataIndex: 'avg_dl_speed' ,renderer:getFixtPercent},
                                { text: '平均下载速率等级', dataIndex: 'avg_dl_level', renderer: transIndex },
                                { text:'首字节延迟',    dataIndex:'delay'},
                                { text:'首字节延迟等级',dataIndex:'delay_level',renderer: transIndex},
                                { text: '流量', dataIndex: 'bandwidth' },
                                { text: '流量等级', dataIndex: 'bandwidth_level',renderer:transIndex },
                                { text: '请求数', dataIndex: 'visit_cnt' },
                                { text: '请求数等级', dataIndex: 'visit_level',renderer:transIndex}
                            ];
                            break;
                        }else if (dimension == 'data_center') {
                            columns=
                                [
                                   { text: '内容中心', dataIndex: 'content_center' },
                                   { text: '服务器类型', dataIndex: 'cache_cdn' },
                                   { text: '时间(小时)', dataIndex: 'ttime' , width: 150, renderer: function(d){
                                       return Ext.Date.format(new Date(d.time), 'Y-m-d H:i');
                                   }},
                                   { text: 'HTTP业务成功率', dataIndex: 'health_rate' ,renderer:getFixtPercent},
                                   { text: 'HTTP业务成功率等级', dataIndex: 'health_level', renderer: transIndex },
                                   { text: '命中率', dataIndex: 'hit_rate' ,renderer:getFixtPercent},
                                   { text: '命中率等级', dataIndex: 'hit_level', renderer: transIndex },
                                   { text: '平均下载速率', dataIndex: 'avg_dl_speed' ,renderer:getFixtPercent},
                                   { text: '平均下载速率等级', dataIndex: 'avg_dl_level', renderer: transIndex },
                                   { text:'首字节延迟',    dataIndex:'delay'},
                                   { text:'首字节延迟等级',dataIndex:'delay_level',renderer: transIndex},
                                   { text: '流量', dataIndex: 'bandwidth' },
                                   { text: '流量等级', dataIndex: 'bandwidth_level',renderer:transIndex },
                                   { text: '请求数', dataIndex: 'visit_cnt' },
                                   { text: '请求数等级', dataIndex: 'visit_level',renderer:transIndex}
                           ];
                            break;
                        } else {
                            columns=
                            [
                                { text: '所属地市', dataIndex: 'city_name' },
                                { text: '服务器类型', dataIndex: 'cache_cdn' },
                                { text: '时间(小时)', dataIndex: 'ttime' , width: 150, renderer: function(d){
                                    return Ext.Date.format(new Date(d.time), 'Y-m-d H:i');
                                }},
                                { text: 'HTTP业务成功率', dataIndex: 'health_rate' ,renderer:getFixtPercent},
                                { text: 'HTTP业务成功率等级', dataIndex: 'health_level', renderer: transIndex },
                                { text: '命中率', dataIndex: 'hit_rate' ,renderer:getFixtPercent},
                                { text: '命中率等级', dataIndex: 'hit_level', renderer: transIndex },
                                { text: '平均下载速率', dataIndex: 'avg_dl_speed' ,renderer:getFixtPercent},
                                { text: '平均下载速率等级', dataIndex: 'avg_dl_level', renderer: transIndex },
                                { text:'首字节延迟',    dataIndex:'delay'},
                                { text:'首字节延迟等级',dataIndex:'delay_level',renderer: transIndex},
                                { text: '流量', dataIndex: 'bandwidth' },
                                { text: '流量等级', dataIndex: 'bandwidth_level',renderer:transIndex },
                                { text: '请求数', dataIndex: 'visit_cnt' },
                                { text: '请求数等级', dataIndex: 'visit_level',renderer:transIndex}
                            ];
                            break;
                        }
                    } else {
                        if (dimension == 'ICP') {
                            columns=  [
                                { text: 'ICP名称', dataIndex: 'icp_name' },
                                { text: 'ICP访问量排行', dataIndex: 'icp_rank' },
                                { text: '域名', dataIndex: 'domain_name' },
                                { text: '服务器类型', dataIndex: 'cache_cdn' },
                                { text: '时间(小时)', dataIndex: 'ttime' , width: 150, renderer: function(d){
                                    return Ext.Date.format(new Date(d.time), 'Y-m-d H:i');
                                }},
                                { text: 'HTTP业务成功率', dataIndex: 'health_rate' ,renderer:getFixtPercent},
                                { text: 'HTTP业务成功率等级', dataIndex: 'health_level', renderer: transIndex },
                                { text: '命中率', dataIndex: 'hit_rate' ,renderer:getFixtPercent},
                                { text: '命中率等级', dataIndex: 'hit_level', renderer: transIndex },
                                { text: '平均下载速率', dataIndex: 'avg_dl_speed' ,renderer:getFixtPercent},
                                { text: '平均下载速率等级', dataIndex: 'avg_dl_level', renderer: transIndex },
                                { text:'首字节延迟',    dataIndex:'delay'},
                                { text:'首字节延迟等级',dataIndex:'delay_level',renderer: transIndex},
                                { text: '流量', dataIndex: 'bandwidth' },
                                { text: '流量等级', dataIndex: 'bandwidth_level',renderer:transIndex },
                                { text: '请求数', dataIndex: 'visit_cnt' },
                                { text: '请求数等级', dataIndex: 'visit_level',renderer:transIndex}
                            ];
                            break;
                        } else if (dimension == 'data_center') {
                            columns=
                            [{ text: '内容中心', dataIndex: 'content_center' },
                                { text: '设备名称', dataIndex: 'server_name' },
                                { text: '服务器IP', dataIndex: 'server_address' },
                                { text: '业务类型', dataIndex: 'service_name' },
                                { text: '服务器类型', dataIndex: 'cache_cdn' },
                                { text: '时间(小时)', dataIndex: 'ttime' , width: 150, renderer: function(d){
                                    return Ext.Date.format(new Date(d.time), 'Y-m-d H:i');
                                }},
                                { text: 'HTTP业务成功率', dataIndex: 'health_rate' ,renderer:getFixtPercent},
                                { text: 'HTTP业务成功率等级', dataIndex: 'health_level', renderer: transIndex },
                                { text: '命中率', dataIndex: 'hit_rate' ,renderer:getFixtPercent},
                                { text: '命中率等级', dataIndex: 'hit_level', renderer: transIndex },
                                { text: '平均下载速率', dataIndex: 'avg_dl_speed' ,renderer:getFixtPercent},
                                { text: '平均下载速率等级', dataIndex: 'avg_dl_level', renderer: transIndex },
                                { text:'首字节延迟',    dataIndex:'delay'},
                                { text:'首字节延迟等级',dataIndex:'delay_level',renderer: transIndex},
                                { text: '流量', dataIndex: 'bandwidth' },
                                { text: '流量等级', dataIndex: 'bandwidth_level',renderer:transIndex },
                                { text: '请求数', dataIndex: 'visit_cnt' },
                                { text: '请求数等级', dataIndex: 'visit_level',renderer:transIndex}];
                            break;
                        }else {
                            columns=
                            [
                                { text: '所属地市', dataIndex: 'city_name' },
                                { text: '设备名称', dataIndex: 'server_name' },
                                { text: '服务器IP', dataIndex: 'server_address' },
                                { text: '业务类型', dataIndex: 'service_name' },
                                { text: '服务器类型', dataIndex: 'cache_cdn' },
                                { text: '时间(小时)', dataIndex: 'ttime' , width: 150, renderer: function(d){
                                    return Ext.Date.format(new Date(d.time), 'Y-m-d H:i');
                                }},
                                { text: 'HTTP业务成功率', dataIndex: 'health_rate' ,renderer:getFixtPercent},
                                { text: 'HTTP业务成功率等级', dataIndex: 'health_level', renderer: transIndex },
                                { text: '命中率', dataIndex: 'hit_rate' ,renderer:getFixtPercent},
                                { text: '命中率等级', dataIndex: 'hit_level', renderer: transIndex },
                                { text: '平均下载速率', dataIndex: 'avg_dl_speed' ,renderer:getFixtPercent},
                                { text: '平均下载速率等级', dataIndex: 'avg_dl_level', renderer: transIndex },
                                { text:'首字节延迟',    dataIndex:'delay'},
                                { text:'首字节延迟等级',dataIndex:'delay_level',renderer: transIndex},
                                { text: '流量', dataIndex: 'bandwidth' },
                                { text: '流量等级', dataIndex: 'bandwidth_level',renderer:transIndex },
                                { text: '请求数', dataIndex: 'visit_cnt' },
                                { text: '请求数等级', dataIndex: 'visit_level',renderer:transIndex}
                            ];
                            break;
                        }
                    }
                }
                case 2:
                {
                    if (directory_level == 1) {
                        if (dimension == 'ICP') {
                            columns=  [
                                { text: 'ICP名称', dataIndex: 'icp_name' },
                                { text: 'ICP访问量排行', dataIndex: 'icp_rank' },
                                { text: '域名', dataIndex: 'domain_name' },
                                { text: '服务器类型', dataIndex: 'cache_cdn' },
                                { text: '时间(小时)', dataIndex: 'ttime' , width: 150, renderer: function(d){
                                    return Ext.Date.format(new Date(d.time), 'Y-m-d H:i');
                                }},
                                { text: 'HTTP业务成功率', dataIndex: 'health_rate' ,renderer:getFixtPercent},
                                { text: 'HTTP业务成功率等级', dataIndex: 'health_level', renderer: transIndex },
                                { text: '命中率', dataIndex: 'hit_rate' ,renderer:getFixtPercent},
                                { text: '命中率等级', dataIndex: 'hit_level', renderer: transIndex },
                                { text: '平均下载速率', dataIndex: 'avg_dl_speed' ,renderer:getFixtPercent},
                                { text: '平均下载速率等级', dataIndex: 'avg_dl_level', renderer: transIndex },
                                { text:'首字节延迟',    dataIndex:'delay'},
                                { text:'首字节延迟等级',dataIndex:'delay_level',renderer: transIndex},
                                { text: '流量', dataIndex: 'bandwidth' },
                                { text: '流量等级', dataIndex: 'bandwidth_level',renderer:transIndex },
                                { text: '请求数', dataIndex: 'visit_cnt' },
                                { text: '请求数等级', dataIndex: 'visit_level',renderer:transIndex}
                            ];
                            break;
                        } else if (dimension == 'data_center') {
                            columns=
                            [
                                { text: '内容中心', dataIndex: 'content_center' },
                                { text: '设备名称', dataIndex: 'server_name' },
                                { text: '服务器IP', dataIndex: 'server_address' },
                                { text: '业务类型', dataIndex: 'service_name' },
                                { text: '服务器类型', dataIndex: 'cache_cdn' },
                                { text: '时间(小时)', dataIndex: 'ttime' , width: 150, renderer: function(d){
                                    return Ext.Date.format(new Date(d.time), 'Y-m-d H:i');
                                }},
                                { text: 'HTTP业务成功率', dataIndex: 'health_rate' ,renderer:getFixtPercent},
                                { text: 'HTTP业务成功率等级', dataIndex: 'health_level', renderer: transIndex },
                                { text: '命中率', dataIndex: 'hit_rate' ,renderer:getFixtPercent},
                                { text: '命中率等级', dataIndex: 'hit_level', renderer: transIndex },
                                { text: '平均下载速率', dataIndex: 'avg_dl_speed' ,renderer:getFixtPercent},
                                { text: '平均下载速率等级', dataIndex: 'avg_dl_level', renderer: transIndex },
                                { text:'首字节延迟',    dataIndex:'delay'},
                                { text:'首字节延迟等级',dataIndex:'delay_level',renderer: transIndex},
                                { text: '流量', dataIndex: 'bandwidth' },
                                { text: '流量等级', dataIndex: 'bandwidth_level',renderer:transIndex },
                                { text: '请求数', dataIndex: 'visit_cnt' },
                                { text: '请求数等级', dataIndex: 'visit_level',renderer:transIndex}
                            ];
                            break;
                        } else if (dimension == 'service_type') {
                            columns=
                            [
                                { text: '设备名称', dataIndex: 'server_name' },
                                { text: '服务器IP', dataIndex: 'server_address' },
                                { text: '业务类型', dataIndex: 'service_name' },
                                { text: '服务器类型', dataIndex: 'cache_cdn' },
                                { text: '时间(小时)', dataIndex: 'ttime' , width: 150, renderer: function(d){
                                    return Ext.Date.format(new Date(d.time), 'Y-m-d H:i');
                                }},
                                { text: 'HTTP业务成功率', dataIndex: 'health_rate' ,renderer:getFixtPercent},
                                { text: 'HTTP业务成功率等级', dataIndex: 'health_level', renderer: transIndex },
                                { text: '命中率', dataIndex: 'hit_rate' ,renderer:getFixtPercent},
                                { text: '命中率等级', dataIndex: 'hit_level', renderer: transIndex },
                                { text: '平均下载速率', dataIndex: 'avg_dl_speed' ,renderer:getFixtPercent},
                                { text: '平均下载速率等级', dataIndex: 'avg_dl_level', renderer: transIndex },
                                { text:'首字节延迟',    dataIndex:'delay'},
                                { text:'首字节延迟等级',dataIndex:'delay_level',renderer: transIndex},
                                { text: '流量', dataIndex: 'bandwidth' },
                                { text: '流量等级', dataIndex: 'bandwidth_level',renderer:transIndex },
                                { text: '请求数', dataIndex: 'visit_cnt' },
                                { text: '请求数等级', dataIndex: 'visit_level',renderer:transIndex}
                            ];
                            break;
                        } else {
                            columns=
                            [
                                { text: '所属地市', dataIndex: 'city_name' },
                                { text: '设备名称', dataIndex: 'server_name' },
                                { text: '服务器IP', dataIndex: 'server_address' },
                                { text: '业务类型', dataIndex: 'service_name' },
                                { text: '服务器类型', dataIndex: 'cache_cdn' },
                                { text: '时间(小时)', dataIndex: 'ttime' , width: 150, renderer: function(d){
                                    return Ext.Date.format(new Date(d.time), 'Y-m-d H:i');
                                }},
                                { text: 'HTTP业务成功率', dataIndex: 'health_rate' ,renderer:getFixtPercent},
                                { text: 'HTTP业务成功率等级', dataIndex: 'health_level', renderer: transIndex },
                                { text: '命中率', dataIndex: 'hit_rate' ,renderer:getFixtPercent},
                                { text: '命中率等级', dataIndex: 'hit_level', renderer: transIndex },
                                { text: '平均下载速率', dataIndex: 'avg_dl_speed' ,renderer:getFixtPercent},
                                { text: '平均下载速率等级', dataIndex: 'avg_dl_level', renderer: transIndex },
                                { text:'首字节延迟',    dataIndex:'delay'},
                                { text:'首字节延迟等级',dataIndex:'delay_level',renderer: transIndex},
                                { text: '流量', dataIndex: 'bandwidth' },
                                { text: '流量等级', dataIndex: 'bandwidth_level',renderer:transIndex },
                                { text: '请求数', dataIndex: 'visit_cnt' },
                                { text: '请求数等级', dataIndex: 'visit_level',renderer:transIndex}
                            ];
                            break;
                        }
                    } else {
                        columns= [
                            {text: '设备名称', dataIndex: 'server_name'},
                            {text: '服务器IP', dataIndex: 'server_address'},
                            {text: '设备名称', dataIndex: 'icp_name'},
                            {text: '服务器IP', dataIndex: 'icp_rank'},
                            {text: '服务器IP', dataIndex: 'domain_name'},
                            {text: '业务类型', dataIndex: 'service_name'},
                            {text: '服务器类型', dataIndex: 'cache_cdn'},
                            {
                                text: '时间(小时)', dataIndex: 'ttime', width: 150, renderer: function (d) {
                                return Ext.Date.format(new Date(d.time), 'Y-m-d H:i');
                            }
                            },
                            {text: 'HTTP业务成功率', dataIndex: 'health_rate', renderer: getFixtPercent},
                            {text: 'HTTP业务成功率等级', dataIndex: 'health_level', renderer: transIndex},
                            {text: '命中率', dataIndex: 'hit_rate', renderer: getFixtPercent},
                            {text: '命中率等级', dataIndex: 'hit_level', renderer: transIndex},
                            {text: '平均下载速率', dataIndex: 'avg_dl_speed', renderer: getFixtPercent},
                            {text: '平均下载速率等级', dataIndex: 'avg_dl_level', renderer: transIndex},
                            {text: '首字节延迟', dataIndex: 'delay'},
                            {text: '首字节延迟等级', dataIndex: 'delay_level', renderer: transIndex},
                            {text: '流量', dataIndex: 'bandwidth'},
                            {text: '流量等级', dataIndex: 'bandwidth_level', renderer: transIndex},
                            {text: '请求数', dataIndex: 'visit_cnt'},
                            {text: '请求数等级', dataIndex: 'visit_level', renderer: transIndex}
                        ];
                        break;
                    }
                }

                case 3:
                {
                    columns=
                    [
                         {text: '设备名称', dataIndex: 'icp_name'},
                         {text: '服务器IP', dataIndex: 'icp_rank'},
                         {text: '服务器IP', dataIndex: 'domain_name'},
                         {text: '业务类型', dataIndex: 'service_name'},
                         {text: '服务器类型', dataIndex: 'cache_cdn'},
                         {
                             text: '时间(小时)', dataIndex: 'ttime', width: 150, renderer: function (d) {
                             return Ext.Date.format(new Date(d.time), 'Y-m-d H:i');
                         }
                         },
                         {text: 'HTTP业务成功率', dataIndex: 'health_rate', renderer: getFixtPercent},
                         {text: 'HTTP业务成功率等级', dataIndex: 'health_level', renderer: transIndex},
                         {text: '命中率', dataIndex: 'hit_rate', renderer: getFixtPercent},
                         {text: '命中率等级', dataIndex: 'hit_level', renderer: transIndex},
                         {text: '平均下载速率', dataIndex: 'avg_dl_speed', renderer: getFixtPercent},
                         {text: '平均下载速率等级', dataIndex: 'avg_dl_level', renderer: transIndex},
                         {text: '首字节延迟', dataIndex: 'delay'},
                         {text: '首字节延迟等级', dataIndex: 'delay_level', renderer: transIndex},
                         {text: '流量', dataIndex: 'bandwidth'},
                         {text: '流量等级', dataIndex: 'bandwidth_level', renderer: transIndex},
                         {text: '请求数', dataIndex: 'visit_cnt'},
                         {text: '请求数等级', dataIndex: 'visit_level', renderer: transIndex}
                     ];
                    break;
                }
                default:
                {
                    return [];
                }

            }
            return columns;
        }
  });

});