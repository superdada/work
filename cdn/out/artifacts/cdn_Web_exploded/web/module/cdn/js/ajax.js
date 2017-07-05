/**
 * ��ajax���η�װ��ͳһ����ʧ�ܣ��ỰʧЧ����
 */
$(function () {
    jQuery.extend({
        cattAjax: function (config) {

            var defaultOptions = {
                type: 'POST',
                dataType: 'json',
                success: function (data, textStatus) {
                    //ͳ�ƴ����쳣������Ϣ TODO
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
                            text: "����ʱ�����Ժ����ԣ�"
                        });
                        return;
                    }
                    //session��ʱ���������¼��
                    if (typeof(XMLHttpRequest.status) == 'number' && 9999 == XMLHttpRequest.status) {
                        //������¼
                        SystemUtil.openMinLoginWin(function(){
                            $.cattAjax(config);
                        });

                        return;
                    }
                    MessageBox.show({
                        type: "fail",
                        text: "��������æ�����Ժ����ԣ�"
                    });
                    return;
                }
            };

            var options = $.extend({}, defaultOptions, config);
            $.ajax(options);
        }
    });
});
