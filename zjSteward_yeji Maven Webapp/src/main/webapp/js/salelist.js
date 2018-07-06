
function iniUpLoadify() {
    $('#file_upload').uploadify({
        'method': "post",
        'height': '20',
        'width': '100',
        'multi': false,
        'fileTypeDesc': 'Excel Files',
        'fileTypeExts': '*.xls',
        'swf': '../js/uploadify/uploadify.swf',
        'uploader': '../js/uploadify/uploadify.ashx?action=upload',
        'removeTimeout': '1',
        'auto': false,
        'fileSizeLimit': '5000KB',             //限制上传图片大小
        'buttonText': '请选择导入文件',           //按钮名称
        'onUploadSuccess': function (file, data, response) {
            setTimeout(function () { $("#uploadMsg").show(); }, 1000);
            setTimeout(function () { cardExplor(data); }, 2000);
        },
        'onUploadStart': function (file) {
        }
    });
}




//导出
function OutExplor() {
    var tids = "";
    
    var row = $('#dg').datagrid('getSelections');
    if (row) {
        for (var i = 0; i < row.length; i++) {
            if (i == 0) tids = row[i].Tid;
            else tids += "," + row[i].Tid;
        }
    }
   
    load("正在导出数据，请稍等........");
    $.ajax({
        type: "post",
        url: "../ashx/DataHandler.ashx",
        data: 'action=businesslist&Execl=dataexplor&stype=' + sytpe + '&saledate=' + $("#salesdate").datebox('getValue') + '&merchname=' + $("#merchantname").val() + '&mobile=' + $("#phone").val() + '&salemonth=' + $("#salemonth").val(),
        complete: function () { }, //AJAX请求完成时隐藏loading提示
        success: function (data) {
            disLoad();
            if (data == "error") {
                datagrid.datagrid('reload');
                $.messager.alert("导出失败");

            } else {
                $('#dg').datagrid('reload');
                //$.messager.alert("导出成功");
                $("#explorPath").val(data);
                $("#Button1").click();
            }

        },
        error: function () {
            disLoad();
        }
    });

}


