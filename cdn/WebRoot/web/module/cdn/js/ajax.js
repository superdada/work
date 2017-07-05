/**
 * 对ajax二次封装，统一处理失败，会话失效问题
 */
$(function () {
    jQuery.extend({
        cattAjax: function (config) {

            var defaultOptions = {
                type: 'POST',
                dataType: 'json',
                success: function (data, textStatus) {
                    //统计处理异常错误信息 TODO
//                    if (data == null || data.SUCCESS == "false") {
//                    }
                    if ($.isFunction(config.callback)) {
                        config.callback(data, textStatus, config.data);
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {

                    if (textStatus == 'timeout') {
                        MessageBox.show({
                            type: "fail",
                            text: "请求超时，请稍候重试！"
                        });
                        return;
                    }
                    //session超时，开迷你登录窗
                    if (typeof(XMLHttpRequest.status) == 'number' && 9999 == XMLHttpRequest.status) {
                        //开窗登录
                        SystemUtil.openMinLoginWin(function(){
                            $.cattAjax(config);
                        });

                        return;
                    }
                    MessageBox.show({
                        type: "fail",
                        text: "服务器繁忙，请稍候重试！"
                    });
                    return;
                }
            };

            var options = $.extend({}, defaultOptions, config);
            $.ajax(options);
        }
    });
});
