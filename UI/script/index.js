$(document).ready(function(){

});

$('#login-button').click(function(){
    var loginData = {
        username : $('#username').val(),
        password : $('#password').val()
    };
    $.ajax({
        type: "POST",
        url: "https://localhost:16802/authenticate_user",
        dataType: "json",
        data: JSON.stringify(loginData),
        success: function(data){
            if(data){
                console.log("Token acquired");
                sessionStorage.setItem("token", data.token);
                sessionStorage.setItem("uid", data.uid);
                window.location.href = "pages/home.php";
            } else {
                console.log("authentication failed");
            }
        }
    });
});