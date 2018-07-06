


$(function () {

    bindDatagrid();

})


function onClickRow(index) {
    if (editRow != index) {
        if (endEditing()) {
            datagrid.datagrid('selectRow', index)
            .datagrid('beginEdit', index);
            editRow = index;
        } else {
            $('#dg').datagrid('selectRow', editRow);
        }
    }
}
function accept() {
    if (endEditing()) {
    }
}


function endEditing() {

    if (editRow == undefined) { return true }
    if ($('#dg').datagrid('validateRow', editRow)) {
        var name = $('#dg').datagrid('getEditor', { index: editRow, field: 'Name' });
        ////$(name.target).val()<span></span> 是修改后的，写个请求到后台，修改就行了。  
        $('#dg').datagrid('endEdit', editRow);
        editRow = undefined;
        return true;
    } else {
        return false;
    }
}

var datagrid; //定义全局变量datagrid
var editRow = undefined;

function bindDatagrid() {
    var sheight = document.documentElement.clientHeight - 10 + "px";
    var stype = $("#hiddenstype").val();

    datagrid = $('#dg').datagrid({
        width: "100%",
        height: sheight,
        pageSize: 100, //默认选择的分页是每页5行数据  
        pageList: [100, 200, 500], //可以选择的分页集合  
        nowrap: true, //设置为true，当数据长度超出列宽时将会自动截取  
        striped: true, //设置为true将交替显示行背景。  
        collapsible: false, //显示可折叠按钮  
        //在添加 增添、删除、修改操作的按钮要用到这个  
        url: '../ASHX/datahandler.ashx?action=paraset', //url调用Action方法 ; 
        loadMsg: '正在加载信息，请稍等......',
        singleSelect: false, //为true时只能选择单行  
        fitColumns: false, //允许表格自动缩放，以适应父容器  
        //sortName : 'xh',//当数据表格初始化时以哪一列来排序  
        //sortOrder : 'desc',//定义排序顺序，可以是'asc'或者'desc'（正序或者倒序）。  
        remoteSort: false,
        columns: [[
            { field: '', checkbox: 'true' },
            { field: 'tid', title: '编号', width: 50, align: 'center', sortable: 'true' },
            {
                field: 'pgroup', title: '参数组', width: 100, align: 'center', sortable: 'true',
                editor: { type: 'validatebox', options: { required: true } }
            },
            {
                field: 'pname', title: '参数名', width: 100, align: 'center', sortable: 'true',
                editor: { type: 'validatebox', options: { required: true } }
            },
            {
                field: 'pvalue', title: '参数值', width: 100, align: 'center', sortable: 'true',
                editor: { type: 'validatebox', options: { required: true } }
            },
             {
                 field: 'remark', title: '备注', width: 800, align: 'center', sortable: 'true',
                 editor: { type: 'validatebox', options: { required: true } }
             }

        ]],
        pagination: true, //分页  
        rownumbers: true,//行数 
        toolbar: [{
            text: '添加(A)', iconCls: 'icon-add', handler: Adds
        }, '-',
{
    text: '删除', iconCls: 'icon-remove', handler: function () {
        //删除时先获取选择行

        var rows = datagrid.datagrid("getChecked");
        $.messager.defaults = { width: 280, height: 140, ok: "是", cancel: "否" };
        //getSelections选择要删除的行
        if (rows.length > 0) {
            $.messager.confirm("提示", "你确定要删除吗?", function (r) {
                if (r) {
                    var num = rows.length;
                    var ids = "";
                    //var ids = [];
                    for (var i = 0; i < rows.length; i++) {
                        ids = ids + "," + rows[i].tid;

                    }
                    //将选择到的行存入数组并用,分隔转换成字符串，
                    //本例只是前台操作没有与数据库进行交互所以此处只是弹出要传入后台的id


                    var deleteData = new Object();
                    deleteData.ids = ids;
                    deleteData.operiate = "delete";

                    if (ids.length > 0) {
                        $.ajax({
                            url: "../ASHX/datahandler.ashx?action=paraset",
                            type: "POST",
                            data: deleteData,
                            async: true,
                            dataType: "json",
                            success: function (data) {
                                datagrid.datagrid('loadData', data);
                                $.messager.alert("提示", data.msg, "info");
                            }

                        });

                    }
                }
            });



        }
        else {
            $.messager.alert("提示", "请选择要删除的行", "error");
        }
    }
}, '-',
{
    text: '修改', iconCls: 'icon-edit', handler: Update
}, '-',
{
    text: '保存(Enter)', iconCls: 'icon-save', handler: Save
}

, '-', {
    text: '<input type="text" id="keyWords"/>'
}, {
    id: 'search',
    text: '搜索',
    iconCls: 'icon-search',

    handler: function () {


        //查询
        var queryData = new Object();
        queryData.keyWords = $("#keyWords").val();
        $.ajax({
            url: "../ASHX/datahandler.ashx?action=paraset",
            type: "POST",
            data: queryData,
            async: true,
            dataType: "json",
            success: function (data) {
                datagrid.datagrid('loadData', data);
            }

        })





    }
}
, '-',

{
    text: '取消编辑', iconCls: 'icon-redo', handler: Cancel
}, '-'

        ], onDblClickRow: function (rowIndex, rowData) {
            //双击开启编辑行//onDblClickRow
            if (editRow != undefined) {
                datagrid.datagrid("endEdit", editRow);
                var row = datagrid.datagrid('getRows')[editRow];

            }
            if (editRow == undefined) {
                datagrid.datagrid("beginEdit", rowIndex);
                editRow = rowIndex;
            }
        },
        onAfterEdit: function (rowIndex, rowData, changes) {
            //endEdit该方法触发此事件
            console.info(rowData);
            editRow = undefined;
        },


    });




    //设置分页控件 
    var p = $('#dg').datagrid('getPager');
    $(p).pagination({
        beforePageText: '第', //页数文本框前显示的汉字 
        afterPageText: '页    共 {pages} 页',
        displayMsg: '当前显示 {from} - {to}  条记录   共 {total} 条记录'
    });
    //隐藏ID列
  

}
function Cancel() {
    //取消当前编辑行把当前编辑行罢undefined回滚改变的数据,取消选择的行
    editRow = undefined;
    datagrid.datagrid("rejectChanges");
    datagrid.datagrid("unselectAll");
}
function Save() {
    //保存时结束当前编辑的行，自动触发onAfterEdit事件如果要与后台交互可将数据通过Ajax提交后台
    var rows = datagrid.datagrid("getSelections");
    var updates = datagrid.datagrid('getChanges', "updated");
    var insertTexts = datagrid.datagrid('getChanges', 'inserted'); //插入和删除换成inserted、deleted,updated就可以了 var arr = datagrid.datagrid('getChanges');
    accept();
   
    $.messager.defaults = { width: 280, height: 140, ok: "是", cancel: "否" };
    var updateData = new Object();
    var insertData = new Object();

    updateData.tid = "";
    updateData.pgroup = "";
    updateData.pname = "";
    updateData.pvalue = "";
    updateData.remark = "";
    updateData.operiate = "update";

    insertData.pgroup = "";
    insertData.pname = "";
    insertData.pvalue = "";
    insertData.remark = "";
    insertData.operiate = "insert";
    ////ajax提交json
    
    for (var i = 0; i < updates.length; i++) {

        updateData.tid = updateData.tid + updates[i].tid + ",";
        updateData.pgroup = updateData.pgroup + updates[i].pgroup + ",";
        updateData.pname = updateData.pname + updates[i].pname + ",";
        updateData.pvalue = updateData.pvalue + updates[i].pvalue + ",";
        updateData.remark = updateData.remark + updates[i].remark + ",";
    }



    for (var i = 0; i < insertTexts.length; i++) {
        //alert(insertTexts[i].StuName);
        insertData.pgroup = insertData.pgroup + insertTexts[i].pgroup + ",";
        insertData.pname = insertData.pname + insertTexts[i].pname + ",";
        insertData.pvalue = insertData.pvalue + insertTexts[i].pvalue + ",";
        insertData.remark = insertData.remark + insertTexts[i].remark + ",";
    }




    datagrid.datagrid("endEdit", editRow);


    if (updates.length > 0) {

        $.ajax({
            url: "../ASHX/datahandler.ashx?action=paraset",
            type: "POST",
            data: updateData,
            async: true,
            dataType: "json",
            success: function (data) {
                datagrid.datagrid('loadData', data);
                $.messager.alert("提示", data.msg, "info");
            }

        });

    } 


    if (insertTexts.length > 0) {

        $.ajax({
            url: "../ASHX/datahandler.ashx?action=paraset",
            type: "POST",
            data: insertData,
            async: true,
            dataType: "json",
            success: function (data) {
                datagrid.datagrid('loadData', data);
                $.messager.alert("提示", data.msg, "info");
            }

        });
    }


}


function Update() {
    //修改时要获取选择到的行
    var rows = datagrid.datagrid("getSelections");
   
    //如果只选择了一行则可以进行修改，否则不操作
    if (rows.length == 1) {
        //修改之前先关闭已经开启的编辑行，当调用endEdit该方法时会触发onAfterEdit事件
        if (editRow != undefined) {
            datagrid.datagrid("endEdit", editRow);
        }
        //当无编辑行时
        if (editRow == undefined) {
            //获取到当前选择行的下标
            var index = datagrid.datagrid("getRowIndex", rows[0]);
            //开启编辑
            datagrid.datagrid("beginEdit", index);
            //把当前开启编辑的行赋值给全局变量editRow
            editRow = index;
            //当开启了当前选择行的编辑状态之后，
            //应该取消当前列表的所有选择行，要不然双击之后无法再选择其他行进行编辑
            datagrid.datagrid("unselectAll");
        }
    }
}

function Adds() {//添加列表的操作按钮添加，修改，删除等
    //添加时先判断是否有开启编辑的行，如果有则把开户编辑的那行结束编辑

    if (editRow != undefined) {
        datagrid.datagrid("endEdit", editRow);
    }
    //添加时如果没有正在编辑的行，则在datagrid的第一行插入一行
    if (editRow == undefined) {
        datagrid.datagrid("insertRow", {
            index: 0, // index start with 0
            row: {

            }
        });
        //将新插入的那一行开户编辑状态
        datagrid.datagrid("beginEdit", 0);
        //给当前编辑的行赋值
        editRow = 0;
    }

}