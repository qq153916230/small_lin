//获取页面接收参数
function request(paras) {
    var url = location.href;
    var paraString = url.substring(url.indexOf("?") + 1, url.length).split("&");
    var paraObj = {};
    for (i = 0; j = paraString[i]; i++) {
        paraObj[j.substring(0, j.indexOf("=")).toLowerCase()] = j.substring(j.indexOf("=") + 1, j.length);
    }
    var returnValue = paraObj[paras.toLowerCase()];
    if (typeof (returnValue) == "undefined") {
        return "";
    } else {
        return decodeURI(returnValue);
    }
}

function myformatter(date) {
    var y = date.getFullYear();
    var m = date.getMonth() + 1;
    var d = date.getDate();
    return y + '-' + (m < 10 ? ('0' + m) : m) + '-' + (d < 10 ? ('0' + d) : d);
}

function myparser(s) {
    if (!s) return new Date();
    var ss = (s.split('-'));
    var y = parseInt(ss[0], 10);
    var m = parseInt(ss[1], 10);
    var d = parseInt(ss[2], 10);
    if (!isNaN(y) && !isNaN(m) && !isNaN(d)) {
        return new Date(y, m - 1, d);
    } else {
        return new Date();
    }
}

function sdelete(dgId,type) {
    var row = $('#dg').datagrid('getSelections');
    if (row) {
        $.messager.defaults = { ok: "是", cancel: "否" };
        $.messager.confirm("提示", '您是否确定要删除选中的记录？', function (r) {
            if (r) {
                var tids = "";
                for (var i = 0; i < row.length; i++) {
                    if (i == 0) tids = row[i].Tid;
                    else tids += "," + row[i].Tid;
                }
                $.ajax({
                    type: "post",
                    url: "../ASHX/hkyd.ashx",
                    data: 'action=del&type=' + type + '&tids=' + tids,
                    complete: function () { }, //AJAX请求完成时隐藏loading提示
                    dataType: "json",
                    success: function (data) {
                        if (data.status == "1") {
                            $('#dg').datagrid('reload');
                        } else {
                            $.messager.alert("提示", "删除数据失败！", "info");
                        }
                    },
                    error: function () {
                    }
                });
            }
        });
    }
}