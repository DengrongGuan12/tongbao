// var token = 0;
function checkLogin(){
    var token = getCookie("token");
	$.ajax({
		type:"POST",
        url:"/tongbao/admin/hasLogin",
        //提交的数据
        data:{token:token},
        //返回数据的格式
        datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
        //在请求之前调用的函数
        beforeSend:function(){
            // alert("beforeSend");
        },
        //成功返回之后调用的函数             
        success:function(data){
            // alert(data); 
            if(data.result == 1){
            	return true;
            }else{
            	return false;
            }
        },
        //调用执行后调用的函数
        complete: function(XMLHttpRequest, textStatus){
           // alert(XMLHttpRequest.responseText);
           // alert(textStatus);
            //HideLoading();
        },
        //调用出错执行的函数
        error: function(){
            //请求出错处理
            alert("error");
        } 
	});
}
function setToken(t){
	$.cookie("token",t);
}
function addCookie(name,value){
    document.cookie = name+"="+value;
}
function getCookie(name){
    var cookie = document.cookie.split(";");
    for (var i = cookie.length - 1; i >= 0; i--) {
        var kv = cookie[i].split("=");
        if(kv[0] == name){
            return kv[1];
        }
    };
    return null;

}