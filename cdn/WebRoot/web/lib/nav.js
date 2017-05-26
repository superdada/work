/**
 * Created by Wz on 3/14/2016.
 * 本功能用于requirejs按需导入内容需要的lib包
 */
define(function(){
    /**
     * 接收参数：
     * @navScript:'' 动态库的nav文件
     * @libBaseURL:'' 动态库根路径
     * @reqLibraries:[] 需要请求的动态库
     * @reqPlugins:[] 需要请求的插件
     *
     * @queryString: 获取链接参数
     * @libBaseURL: 动态库库根路径
     * @isDebug: debug标志
     * @versions: 动态库文件版本号
     * @getVersion: 获取版本信息
     * @getBaseUrl: 获取动态库库根路径
     */
    var wd = window,
        config = wd.requireConfig || {
            navScript: wd.navScript,
            libBaseURL: wd.libBaseURL,
            reqLibraries: wd.reqLibraries,
            reqPlugins: wd.reqPlugins,
            version: wd.libVersions,
            versionURL: wd.versionURL,
            useMask: typeof wd.useMask == 'undefined' ? true : wd.useMask,
            style: {
                ext: wd.extStyle || 'triton' // aria/classic/crisp/gray/neptune/triton
            }
        },
        _versions = config.version || {},
        me = {
            queryString: wd.location.search,
            versionURL:  _versions.versionURL || AppBase+"/web/lib/version.json",
            libBaseURL: '',
            isDebug: false,
            library: {},
            versions: {
                requireCss: _versions.requireCss || '0.1.8',
                jQuery: _versions.jQuery || '2.1.1',
                bootstrap: _versions.bootstrap || '3.3.5',
                ext: _versions.ext || '6.0.0',
                d3: _versions.d3 || '3.5.14',
                eCharts: _versions.eCharts || '3.1.2',
                highCharts: _versions.highCharts || '4.1.5'
            },
            style: {
                ext: (config.style ? (config.style.ext || 'triton') : 'triton')
            },
            /**
             * 原生实现内容加载
             */
            loadMask: function(){
            	var head = document.getElementsByTagName('head')[0];
                var link = document.createElement('link');
                link.href = this.libBaseURL+'Mask/css/mask.css';
                link.rel = 'stylesheet';
                link.type = 'text/css';
                head.appendChild(link);
            	
            	var mask = document.createElement('div');
            	var maskSun = document.createElement('div');
            	mask.id = "mask";
            	mask.setAttribute('class','mask');
            	maskSun.setAttribute('class','masksun');
            	mask.appendChild(maskSun);
            	document.body.appendChild(mask);
            	return true;
            },
            unMask: function(){
                $('.mask').fadeOut(500).remove();
            },
            getVersion: function (name, split){
                var versions = this.versions, version = versions[name];
                return version ? (split || '') + version  : versions;
            },
            getBaseUrl: function (script){
                var thiz = this,
                    scripts = document.getElementsByTagName('script'),
                    navScript = (script || config.navScript || 'nav.min'),
                    scriptRegexNoDebug = new RegExp(navScript.replace(/\.js$/,'') + '\.js$'),
                    scriptRegexDebug = new RegExp(navScript.replace(/(\.min\.js$)|(\.min$)|(\.js$)/g,'')  + '\.js$');
                for (var i = scripts.length; i--;) {
                    var scriptSrc = scripts[i].src,
                        match = scriptSrc.match(scriptRegexNoDebug) || scriptSrc.match(scriptRegexDebug);
                    if (match) return scriptSrc.substring(0, scriptSrc.length - match[0].length);
                }
                return script || '';
            },
            getXmlHttpRequest: function(){
            	if (window.ActiveXObject) { 
                	return new ActiveXObject("Microsoft.XMLHTTP"); 
            	} else if (window.XMLHttpRequest) { 
            		return new XMLHttpRequest(); 
            	}
            },
            ajaxReadState: function(xmlHttp){
        		if ((xmlHttp.readyState == 4)) {
        			if(xmlHttp.status == 200){
	        			try {
	        				var da = JSON.parse(xmlHttp.responseText)
	        				return "v="+da.version;
	        			} catch(e) {
	        				return xmlHttp.responseText;
	        			}
        			} else {
        				return '';
        			}
    			}
            },
            ajaxGet: function(url,bol){
            	var _this = this;
            	var xmlHttp = this.getXmlHttpRequest(); 
            	xmlHttp.open("GET",url+"?t="+Math.random(),bol); 
        		xmlHttp.send(); 
        		if(bol){
        			xmlHttp.onreadystatechange = _this.ajaxReadState
        		} else {
        			while(true){
        				return _this.ajaxReadState(xmlHttp);
        			}
        		}
            },
            getAppVersion: function(){
            	var _this = this;
            	return _this.ajaxGet(_this.versionURL, false) || "";
            }
        };

	    me.isDebug = me.queryString.match('(\\?|&)debug') !== null ? true : false; // 获取debug信息
	    me.libBaseURL = config.libBaseURL || me.getBaseUrl(); // 获取动态库基础路径
	    if(config.useMask) me.loadMask();
		me.loadInit = window.loadInit || function(){
			/**
			 * body禁用右键菜单
			 */
			$(document.body).on('contextmenu', function(e){
				e.preventDefault();
			});
		}
	
    /**
     * 加载配置
     * @baseUrl: 根路径
     * @paths: 需要加载包的路径（相对于根路径）
     * @map: 告诉RequireJS在任何模块之前，都先载入这个模块，这样别的模块依赖于css!../style/1.css这样的模块都知道怎么处理了
     * @shim : { // 依赖引用配置
     *  *: { // 引用对象
     *      deps: [''], // 所需要的依赖（css!path 表示调用require-css插件加载css文件）
     *      exports: '' // 需要返回的模块对象
    *    }
     * }
     */
    require.config({
        baseUrl: me.libBaseURL,
        urlArgs: me.getAppVersion(),
        paths: {
            jQuery: 'JQuery/jquery' +
                        me.getVersion('jQuery','-') +
                            '/jquery' +
                                (me.isDebug ? '' : '.min'),
            bootstrap: 'Bootstrap/bootstrap' +
                            me.getVersion('bootstrap','-') +
                                '/dist/js/bootstrap' +
                                    (me.isDebug ? '' : '.min'),
            ext: 'Sencha/ext' +
                    me.getVersion('ext','-') +
                        '/build/ext-all' +
                            (me.isDebug ? '-debug' : ''),
            d3: 'D3/d3' +
                    me.getVersion('d3','-') +
                        '/d3' +
                            (me.isDebug ? '' : '.min'),
            eCharts: 'Echarts/echarts' +
                        me.getVersion('eCharts','-') +
                            '/echarts' +
                                (me.isDebug ? '' : '.min'),
            highCharts: 'Highcharts/highcharts' +
                            me.getVersion('highCharts','-') +
                                '/highcharts' +
                                    (me.isDebug ? '.src' : ''),
            highCharts3d: 'Highcharts/highcharts' +
                                me.getVersion('highCharts','-') +
                                    '/highcharts-3d' +
                                        (me.isDebug ? '.src' : '')
        },
        waitSeconds: 600,
        map: {
            '*': {
                'css': 'RequireJS/require-css' + me.getVersion('requireCss','-') + '/css' + (me.isDebug ? '' : '.min')
            }
        },
        shim: {
            jQuery: {
                exports: '$',
                init: function (e) {}
            },
            bootstrap: {
                deps: ['jQuery',
                        'css!Bootstrap/bootstrap' +
                            me.getVersion('bootstrap','-') +
                                '/dist/css/bootstrap' +
                                    (me.isDebug ? '' : '.min')
                ]
            },
            ext: {
                deps: ['css!Sencha/ext' +
                            me.getVersion('ext','-') +
                                '/build/classic/theme-' + me.style.ext + '/resources/theme-' + me.style.ext + '-all' +
                                    (me.isDebug ? '-debug' : '')
                ],
                exports: 'Ext',
                init: function(){
                    require(['Sencha/ext' +
                                 me.getVersion('ext','-') +
                                    '/build/classic/locale/locale-zh_CN' +
                                        (me.isDebug ? '-debug' : '')
                    ],function(){
                        console.info('Ext加载中文库完成 (load Ext CN done.)');
                    })
                }

            },
            d3: { exports: 'd3' },
            eCharts: { exports: 'echarts' },
            highCharts: {
                deps: ['jQuery'],
                exports: 'Highcharts'
            },
            highCharts3d: {
                deps: ['highCharts']
            }
        }
    });

    /**
     * 加载动态库
     */
    require(config.reqLibraries || ['jQuery', 'ext', 'd3', 'eCharts', 'highCharts'], function($, Ext, d3, echarts, Highcharts){
        me.library.jQuery = me.library.$ = $;
        me.library.Ext = Ext;
        me.library.d3 = d3;
        me.library.echarts = echarts;
        me.library.Highcharts = Highcharts;
        
        if(config.useMask) me.unMask();
		me.loadInit(me.library);

        console.info('加载动态库完成 (load library done.)');
        console.info(arguments);
    });

    /**
     * 加载插件库
     */
    require(config.reqPlugins || ['bootstrap', 'highCharts3d'], function(){
        console.info('加载插件库完成 (load plugin done.)')
    });

    console.info(wd.Z = me);

    return me;
});