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
                        url: Ext.String.format('{0}/pfcsnapshot.do?method=request7&id={1}',AppBase, request_id),
                        reader: {
                            type: 'json',
                            rootProperty: 'data'
                        }
                    },
                    autoLoad: true,
                    fields: [ 'name', 'belong_to', 'time', 'type']
                }),
                columns: getColumns(dimension)
            }
        });
    });


    function getColumns(dimension) {
        switch (dimension) {
            case "data_center":
                return [
                    { text: 'id', dataIndex: 'd_id' , flex: 1, hidden: true },
                    { text: '数据中心', dataIndex: 'name' , flex: 1},
                    { text: '数据中心位置', dataIndex: 'belong_to' , flex: 1},
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
            case "ICP":
                return [
                    { text: 'id', dataIndex: 'd_id' , flex: 1, hidden: true },
                    { text: '域名', dataIndex: 'name', flex: 1 },
                    { text: 'ICP', dataIndex: 'belong_to', flex: 1 },
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
                    { text: 'id', dataIndex: 'd_id' , flex: 1, hidden: true},
                    { text: '数据中心位置', dataIndex: 'name' , flex: 1},
                    { text: '数据中心', dataIndex: 'belong_to' , flex: 1},
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
                return "数据中心";
            case "ICP":
                return "ICP";
        }
    }

    function transIndex(index){
        switch (index) {
            case "healthy":
                return "健康度";
            case "hit_rate":
                return "命中率";
            case "data_rate":
                return "速率";
        }
    }

});