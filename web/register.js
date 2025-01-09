$(document).ready(function() {
    var username,password,page;
    
    // -- BUTTON SUBMIT
    $("#btnSubmit").click(function() {
        username = $("#username").val();
        password = $("#password").val();
        
        // -- AJAX CHECK USERNAME SESUAI NIS
        $.ajax({
            url: 'SiswaCtr',
            type: 'GET',
            data: {
                page: 'tampil',
                nis: username
            },
            success: function(data) {
                // -- IF NIS TIDAK ADA DI DATABASE
                if (data.nis == null) {
                    alert("Username tidak sesuai dengan NIS, harap masukkan NIS yang valid");
                } else {
                    // -- ELSE NIS ADA, TAMBAH USER
                    $.ajax({
                        url: 'UserCtr',
                        type: 'POST',
                        data: {
                            page: 'tambah',
                            username: username,
                            password: password,
                            level: 'siswa'
                        },
                        success: function(response) {
                            alert(response);
                            window.location.href = '/nilai/login.html';
                        },
                        error: function(xhr, status, error) {
                            console.error(status, error);
                        }
                    });
                }
            },
            error: function(xhr, status, error) {
                console.error(status, error);
            }
        });
    });
    
    // -- BUTTON KEMBALI
    $('#btnKembali').on('click',function(){
        window.location.href = '/nilai/login.html';
    });
});