/*=======================API-1===========================*/
/*快照整体数据获取*/
$.getJSON(
    AppBase + '/pfcsnapshot.do?method=request1',
    {
        dimension : 'data_center',//当前维度类型，枚举值{'data_center'(数据中心), 'ICP'(ICP)}
        index : 'healthy'//当前指标类型，枚举值{'healthy'(健康度), 'hit_rate'(命中率), 'data_rate'(速率)}
    },
    funOK
）
var response = [
    {
		'id': 123,
        'name' : "广州-1", 
        /*1. dimension : 'data_center'时，返回【数据中心】,
           2. dimension : 'ICP'时，返回【域名】,*/
        'belong_to' : "广州",    
        /*1. dimension : 'data_center'时，返回【归属城市】,
           2. dimension : 'ICP'时，返回【归属icp】,*/
        'indexes' : [
            {'time':"2017-3-7 0:00",'type':"danger"},
            {'time':"2017-3-7 1:00",'type':"danger"},
            /*...*/
            {'time':"2017-3-7 23:00",'type':"danger"}
        ],
        'monitor_flag' : false, //监控标示位
        'alarm_flag' : true//报警标示位
    }
];

/*=======================API-2===========================*/
/*弹窗数据获取*/
$(window).trigger('event_moreinfo', [ vm.$data.dimension, _id, _time]);
/*1. vm.$data.dimension : 当前数据维度,
   2. _id : 目标id
   3. _time : 查询时间*/

   
   
/*=======================API-3===========================*/
/*操作监控&报警*/
$.postJSON(
    '/api/control',
    {
        'monitor_flag' : false, //监控标示位
        'alarm_flag' : true,//报警标示位
        dimension : 'data_center',//当前维度
        id : ''//目标id
    },
    funOK//
)