window.onload = function () {

    $.ajax({
        type:'GET',
        url: AppBase + '/login.do?method=request2',
        dataType: 'json',
        success: function (data) {
            var html = new treeMenu(data.data).init(0);
            $("#headerLogo").after(html);
            menuchange();
            initiframe();
            $(window).resize(function () {
                initiframe();
            });
        }
    });
};
function treeMenu(a){
    this.tree=a||[];
    this.groups={};
};
treeMenu.prototype={
    init:function(mparentId){
        this.group();
        return this.getDom(this.groups);
    },
    group:function(){
        for(var i=0;i<this.tree.length;i++){
            if(this.groups[this.tree[i].mparentId]){
                this.groups[this.tree[i].mparentId].push(this.tree[i]);
            }else{
                this.groups[this.tree[i].mparentId]=[];
                this.groups[this.tree[i].mparentId].push(this.tree[i]);
            }
        }
    },
    getDom:function(a) {
        menuHtml = "";
        if (!a) {
            return '';
        }
        else{
            for (var i in a) {
                if (i == 0) { //第一层目录的摆放位置
                    for (var j = 0; j <a[0].length; j++) {
                        if (j === 0) {
                            className = "my-header-item-selected tab";
                            $("#changeTab").attr("src",AppBase+a[0][j].menuURL);
                        } else {
                            className = "my-header-item tab";
                        }
                        menuHtml +="<div class='"+className+"' id='"+a[0][j].menuid+"' url='"+AppBase+a[0][j].menuURL+"'>"+a[0][j].menuname+"</div>";
                    }
                }
            }
            return menuHtml;
        }

    }
};
function menuchange(){

    $(".tab").click(function (e) {
        var url = $(e.target).attr("url");
        $("#changeTab").attr("src", url);
        $(".my-header-item-selected").addClass('my-header-item').removeClass('my-header-item-selected');
        $(e.target).addClass('my-header-item-selected');
        $(e.target).removeClass('my-header-item');
    });

}

//窗口重置,调整iframe可见
function initiframe() {
    var windowWidth = $(window).width();
    if (windowWidth < 1200) {
        $("iframe").css("width", "1200px");
    } else {
        $("iframe").css("width", "100%");
    }
}
//退出
function signOutEvent() {
    layer.open({
        icon: 7, title: '退出', content: '确定要退出吗？',
        btn: ['确定', '取消'],
        yes: function (index, layero) {
            location.href = AppBase + '/';
        }
    });
}