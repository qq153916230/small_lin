//102信用卡使用技巧 展示列表
$(function(){
	var tags = '';
	var ctime = formatDate(new Date()); //当前日期
	var gtime;
    $.ajax({
    	async:false, //取消异步
        type:'post',
        url:'http://mobile.rczjgj.com:7897/zjgj2tj/article/appSelectTitle?type=102',
        dataType:'json',
        success:function(data){
        	
            for(var i=0; i<data.length; i++){
            	gtime = formatDate(new Date(data[i].gdate)); //文章创建日期
				var stitle = data[i].title.replace("【破解攻略】","");            	
            	if(gtime == ctime){
            		tags += '<li style="display:none;"><div class="item-content"><img style="margin-right:3px;" alt="" src="images/new.gif"><div class="item-inner"><div class="label"><a href="content.html?aid='+data[i].aid+'">' + stitle + '</a></div></div></div></li>';            	
            	} else{
            		tags += '<li style="display:none;"><div class="item-content"><div class="item-inner"><div class="label"><a href="content.html?aid='+data[i].aid+'">' + stitle + '</a></div></div></div></li>';
            	}
            }
            $('ul').html(tags);
        },
        error:function(data){
            //alert('ajax执行了error,获取数据失败');
        }
    });
})

function formatDate(now) {
	var year = now.getFullYear(),
	month = now.getMonth() + 1,
	date = now.getDate();
	/*hour = now.getHours(),
	minute = now.getMinutes(),
	second = now.getSeconds();*/
	 
	return year + "-" + month + "-" + date; /* + " " + hour + ":" + minute + ":" + second*/
}


//下拉刷新
$(document).ready(function(){  

    var range = 5;             //距下边界长度/单位px  
    var elemt = 500;           //插入元素高度/单位px  
    var maxnum = 20;            //设置加载最多次数  
    var num = 1;  
    var totalheight = 0;   
    var main = $("#content");                     //主体元素  
    $("ul").children().slice(0,num*30).css("display","block");
    $(window).scroll(function(){  
        var srollPos = $(window).scrollTop();    //滚动条距顶部距离(页面超出窗口的高度)  
          
        //console.log("滚动条到顶部的垂直高度: "+$(document).scrollTop());  
        //console.log("页面的文档高度 ："+$(document).height());  
        //console.log('浏览器的高度：'+$(window).height());  
          
        totalheight = parseFloat($(window).height()) + parseFloat(srollPos);  
        if(($(document).height()-range) <= totalheight  && num != maxnum) {  
            $("ul").children().slice(0,num*30).css("display","block");
            num++;  
            /*alert("刷新");*/
        }  
    });  
}); 