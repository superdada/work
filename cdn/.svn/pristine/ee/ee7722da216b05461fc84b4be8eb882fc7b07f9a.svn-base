/**
 * Created by sir.ZhangWei on 2017/3/9.
 */
define(['ext','jQuery'], function(Ext, $){
    Ext.onReady(function () {
        Ext.create('Ext.container.Viewport', {
            layout: 'fit',
            items: {
                id: "ext-grid",
                xtype: 'grid',
                title: Ext.String.format("{0}|{1}", transDimension(dimension), transIndex(index)),
                store: Ext.create('Ext.data.Store', {
                    proxy: {
                        type: 'ajax',
                        url: Ext.String.format('{0}/pfcsnapshot.do?method=request7&id={1}&dimension={2}&index={3}&cache_cdn={4}&datasource={5}&directory_level={6}&time={7} ',AppBase, request_id,dimension,index,cache_cdn,datasource,directory_level,time),
                        reader: {
                            type: 'json',
                            rootProperty: 'data'
                        }
                    },
                    autoLoad: true,
                    fields: getFields(dimension,directory_level)
                }),
                columns: getColumns(dimension,directory_level)
            }
        });
    });

   function getFields(dimension,directory_level){
       switch (dimension) {
           case "data_center":
               if(directory_level ==1){
                      return [ 'id', 'time', 'type'];
               }else{
                   return [ 'id', 'name','belong_to','time', 'type'];
               }

           case "fringe_node":
               if(directory_level ==1){
                   return [ 'id', 'time', 'type'];
               }else{
                   return [ 'id', 'name','belong_to','time', 'type'];
               }

           case "ICP":
                 return [ 'id', 'time', 'type'];

           default:
               return [ 'id', 'time', 'type'];
       }
   }
    function getColumns(dimension,directory_level) {
        switch (dimension) {
            case "data_center":
                if(directory_level ==1){
                    return [
                        { text: 'id', dataIndex: 'id' , flex: 1 },
                        { text: '时间(小时)', dataIndex: 'time' , flex: 1, width: 150, renderer: function(d){
                            return Ext.Date.format(new Date(d.time), 'Y-m-d H:i');
                        }},
                        { text: '指标等级', dataIndex: 'type', flex: 1 , renderer: function (d) {
                            switch (d) {
                                case "danger":
                                    return "严重告警";
                                case "warning":
                                    return "一般告警";
                                case "normal":
                                    return "正常";
                                default:
                                    return "无数据";
                            }
                        }}
                    ];
                }else{
                    return [
                        { text: '设备名称', dataIndex: 'id', flex: 1 },
                        { text: 'IP地址', dataIndex: 'name', flex: 1 },
                        { text: '承载业务', dataIndex: 'belong_to', flex: 1 },
                        { text: '时间(小时)', dataIndex: 'time' , flex: 1, width: 150, renderer: function(d){
                            return Ext.Date.format(new Date(d.time), 'Y-m-d H:i');
                        }},
                        { text: '指标等级', dataIndex: 'type', flex: 1 , renderer: function (d) {
                            switch (d) {
                                case "danger":
                                    return "严重告警";
                                case "warning":
                                    return "一般告警";
                                case "normal":
                                    return "正常";
                                default:
                                    return "无数据";
                            }
                        } }
                    ];
                }

            case "fringe_node":
                if(directory_level ==1){
                    return [
                        { text: '地市', dataIndex: 'id', flex: 1 },
                        { text: '时间(小时)', dataIndex: 'time' , flex: 1, width: 150, renderer: function(d){
                            return Ext.Date.format(new Date(d.time), 'Y-m-d H:i');
                        }},
                        { text: '指标等级', dataIndex: 'type', flex: 1 , renderer: function (d) {
                            switch (d) {
                                case "danger":
                                    return "严重告警";
                                case "warning":
                                    return "一般告警";
                                case "normal":
                                    return "正常";
                                default:
                                    return "无数据";
                            }
                        } }
                    ];
                }else{
                    return [
                        { text: '设备名称', dataIndex: 'id', flex: 1 },
                        { text: 'IP地址', dataIndex: 'name', flex: 1 },
                        { text: '承载业务', dataIndex: 'belong_to', flex: 1 },
                        { text: '时间(小时)', dataIndex: 'time' , flex: 1, width: 150, renderer: function(d){
                            return Ext.Date.format(new Date(d.time), 'Y-m-d H:i');
                        }},
                        { text: '指标等级', dataIndex: 'type', flex: 1 , renderer: function (d) {
                            switch (d) {
                                case "danger":
                                    return "严重告警";
                                case "warning":
                                    return "一般告警";
                                case "normal":
                                    return "正常";
                                default:
                                    return "无数据";
                            }
                        } }
                    ];
                }

            case "ICP":
                    return [
                        { text: 'ICP', dataIndex: 'id', flex: 1 },
                        { text: '时间(小时)', dataIndex: 'time' , flex: 1, width: 150, renderer: function(d){
                            return Ext.Date.format(new Date(d.time), 'Y-m-d H:i');
                        }},
                        { text: '指标等级', dataIndex: 'type', flex: 1 , renderer: function (d) {
                            switch (d) {
                                case "danger":
                                    return "严重告警";
                                case "warning":
                                    return "一般告警";
                                case "normal":
                                    return "正常";
                                default:
                                    return "无数据";
                            }
                        } }
                    ];

            default:
                    return [
                        { text: '业务类型', dataIndex: 'id' , flex: 1},
                        { text: '时间(小时)', dataIndex: 'time' , flex: 1, width: 150, renderer: function(d){
                            return Ext.Date.format(new Date(d.time), 'Y-m-d H:i');
                        }},
                        { text: '指标等级', dataIndex: 'type', flex: 1 , renderer: function (d) {
                            switch (d) {
                                case "danger":
                                    return "严重告警";
                                case "warning":
                                    return "一般告警";
                                case "normal":
                                    return "正常";
                                default:
                                    return "无数据";
                            }
                        } }
                    ];
        }
    }

    function transDimension(dimension){
        switch (dimension) {
            case "data_center":
                return "内容中心";
            case "ICP":
                return "ICP";
            case "fringe_node":
                return "边缘节点";
            case "service_type":
                return "业务类型";
        }
    }

    function transIndex(index){
        var result=index.split(",");
        var indexchange = "";
        for(var i in result){
            switch (result[i]) {
                case "healthy":
                    indexchange+= "HTTP业务成功率";
                case "hit_rate":
                    indexchange+= "缓存命中率";
                case "data_rate":
                    indexchange+= "平均下载速率";
                case "bandwidth":
                    indexchange+= "流量";
                case "visit_cnt":
                    indexchange+= "请求数";
                case "delay":
                    indexchange+= "首字节延迟";
                default:
                    indexchange+= ",";
            }
        }
        indexchange = indexchange.substr(0,indexchange.length-1);
        return indexchange;
    }

});