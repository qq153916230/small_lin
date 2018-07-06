//获取article对象
$(function(){
	var aid = getURLParameter("aid");
    $.ajax({
        type:'post',
        url:'http://mobile.rczjgj.com:7897/zjgj2tj/article/appSelectByAid?aid=' + aid,
        dataType:'json',
        success:function(data){
            $('title').html(data.title.replace("【破解攻略】",""));
            $('#tit').html(data.title.replace("【破解攻略】","")); //标题
            $('.p1').html(formatDate(new Date(data.date))); //创建时间
            $('.p2').html("综金管家"); //文章作者
            $('.p3').html(data.programa); //栏目

			var cont = data.content.replace(new RegExp("<br/>","gm"),"").replace(new RegExp("nowrap","gm"),"0");

            $('#content').html(cont); //文章内容
        },
        error:function(data){
            //alert('获取数据失败');
        }
    });
})


//获取url地址的参数
function getURLParameter(name)
{
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);
     if(r!=null)return  unescape(r[2]); return null;
}

//格式化时间
function formatDate(now) {
	var year = now.getFullYear(),
	month = now.getMonth() + 1,
	date = now.getDate();
	/*hour = now.getHours(),
	minute = now.getMinutes(),
	second = now.getSeconds();*/
	 
	return year + "-" + month + "-" + date;/* + " " + hour + ":" + minute + ":" + second;*/
}