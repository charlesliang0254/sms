$('document').ready(function(){
   $.ajax({
       url:"/user",
       type:"get",
       dataType:"json",
       success:function(obj){
           if(obj.state===1){
               $('#login-username').html(obj.data.username);
               $('#login-uid').html(obj.data.uid);
           }
       }
   });
});

function logout(){
    $.ajax({
        url:"/user/logout",
        type:"post",
        dataType:"json",
        success:function(){
            location.href="/page/login.html";
        },
        error:function(){
            alert("未知错误导致退出失败，请重试！");
        }
    });
}