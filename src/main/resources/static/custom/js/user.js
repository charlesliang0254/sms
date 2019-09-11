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
            alert(obj.message);
        },
        "error":function(obj){
            alert(obj.message);
        }
    });
}

function register(){
    var data = {
        "username":$("#username").val(),
        "password":$("#password").val(),
        "repeatedPassword":$("#repeated-password").val(),
        "type":$("#type option:selected").val()
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
            alert("unknown error occur");
        }
    });
}