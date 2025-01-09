$(document).ready(function() {
    var level;
    
    // -- BUTTON LOGIN
    $("#btnLogin").click(function() {
        var username = $("#username").val();
        var password = $("#password").val();
        
        // -- AJAX VALIDASI LOGIN
        $.ajax({
            url: "UserCtr?page=tampil&username=" + username,
            type: "GET",
            dataType: "json",
            success: function(data) {
                if (data.username === username && data.password === password) {
                    username = data.username;
                    level = data.level;
                    sessionStorage.setItem('username', username);
                    sessionStorage.setItem('level', level);
                    alert("Login sukses");
                    window.location = "beranda.html";
                } else if (username === "" && password === ""){
                    alert("Masukkan username dan password!");
                } else {
                    alert("Username dan password salah");
                }
            },
            error: function(xhr, status, error) {
                alert("Login gagal");
                console.log(error);
            }
        });
    });
});