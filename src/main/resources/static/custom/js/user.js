function login(){
    var data = {
        "username":$("#username").val(),
        "password":$("#password").val()
    };
    $.ajax({
        "url":"/user/login",
        "type":"post",
        "dataType":"json",
        "data":data,
        "success":function(obj){
            if(obj.state===1){
                alert("登录成功");
            }
            else{
                alert(obj.message);
            }
        },
        "error":function(){
            alert("未知的错误");
        }
    });
}

function register(){
    var data = {
        "username":$("#username").val(),
        "password":$("#password").val(),
        "repeatedPassword":$("#repeated-password").val(),
        "type":2
    };
    $.ajax({
        "url":"/user/reg",
        "type":"post",
        "dataType": "json",
        "data":data,
        "success":function(obj){
            if(obj.state===1){
                alert(obj.message);
                //location.href="/pages/main.html";
            }
            else{
                alert(obj.message);
            }
        },
        "error":function () {
            alert("未知的错误");
        }
    });
}