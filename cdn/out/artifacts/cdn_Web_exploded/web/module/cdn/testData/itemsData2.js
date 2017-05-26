var __request = {
    m1_param : '',
    m2_param : { 
        index : 'healthy',//枚举值 : 'healthy', 'hit_rate', 'data_rate'
        IPtype : 'data_center',//枚举值 : 'data_center', 'user'
    },
    m3_param : {
        index : 'healthy', //枚举值 : 'healthy', 'hit_rate', 'data_rate'
        timeType:'3hours',//枚举值 : '3hours', 'today', '7days', '30days', 'custom'
        startTime:'',//当timeType为'custom'时取值，格式待定
        endTime:'',//当timeType为'custom'时取值，格式待定
    }
}

var __testData = {
    /*
    //返回最新数据，地市维度
    */
    'module1Data': {
        'healthy':94.6, 
        'hit_rate':96.6,
        'data_rate':38,
        'time':'2017-2-9'
    },
    /*
    m2_param:{index:'healthy'//枚举值 : 'healthy', 'hit_rate', 'data_rate'
    type: 'data_center'//枚举值 : 'data_center', 'user'
    //返回最新数据，地市维度
    */
    'module2Data': {
        'time': '2017-2-9',
        'data':[
            {name: '广州市',value: 98 },
            {name: '佛山市',value: 97 },
            {name: '深圳市',value: 96 },
            {name: '东莞市',value: 95 },
            {name: '清远市',value: 32 },
            {name: '汕头市',value: 88 },
            {name: '潮州市',value: 87 },
            {name: '揭阳市',value: 86 },
            {name: '梅州市',value: 85 },
            {name: '河源市',value: 85 },
            {name: '韶关市',value: 92 },
            {name: '江门市',value: 94 },
            {name: '阳江市',value: 96 },
            {name: '茂名市',value: 98 },
            {name: '湛江市',value: 99 },
            {name: '珠海市',value: 99 },
            {name: '肇庆市',value: 97 },
            {name: '云浮市',value: 87 },
            {name: '中山市',value:23 },
            {name: '惠州市',value: 56 },
            {name: '汕尾市',value: 87 }
            ]
        },
    /*
    index:'healthy'//枚举值 : 'healthy', 'hit_rate', 'data_rate'
    timeType:'3hours'//枚举值 : '3hours', 'today', '7days', '30days', 'custom'
    startTime:''//当timeType为'custom'时取值，格式待定
    endTime:''//当timeType为'custom'时取值，格式待定
    */
    'module3Data' : {
        'x':["2010","2011","2012","2013","2014","2015","2010","2011","2012","2013","2014","2015","2010","2011","2012","2013","2014","2015"],
        'y':[3325, 2230, 1236, 1011, 809, 405,3325, 2230, 1236, 1011, 809, 405,3325, 2230, 1236, 1011, 809, 405]
    }
}