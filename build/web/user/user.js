$(document).ready(function(){
    var username,password,level,page;
    function getInputValue(){
        username = $("#username").val();
        password = $("#password").val();
        level = $("#level").children("option:selected").val();
    }
    function clearData(){
        $("#username").val('');
        $("#password").val('');
        $("#level").val('');
    }
    //PROCEDURE TAMBAH DATA
    $('#btnAdd').click(function (){
        $('#myModal').show();
        $('#title1').show();
        $('#title2').hide();
        $('#username').prop('disabled',false);
        $("#level").val('guru');
        $('#level').prop('disabled',true);
        page="tambah";
        
        // --- AJAX FILL OPTION USERNAME
        $.ajax({
            url: '/nilai/GuruCtr',
            type: 'GET',
            data: { page: 'kodeguru' },
            dataType: 'json',
            success: function(data) {
                var select = $('#username');
                select.empty();
                $('<option>', {
                    value: '',
                    text: 'Pilih :',
                    selected: true,
                    disabled: true
                }).appendTo(select);
                var kodegurus = []; // menyimpan kode guru
                data.forEach(function(item) {
                    // Jika kode guru belum ada dalam array, tambah opsi
                    if (!kodegurus.includes(item.kodeguru)) {
                        $('<option>', {
                            value: item.kodeguru,
                            text: item.kodeguru
                        }).appendTo(select);
                        kodegurus.push(item.kodeguru); // Tambah kode guru ke dalam array
                    }
                });
            },
            error: function(jqXHR, textStatus, errorThrown) {
                console.log(textStatus, errorThrown);
            }
        });
    });
    
    //PROCEDURE HAPUS DATA
    $("#tabeluser tbody").on('click', '#btnDel', function(){
        var baris = $(this).closest('tr');
        var username = baris.find("td:eq(0)").text();
        var password = baris.find("td:eq(1)").text();
        page = "hapus";
        
        if(confirm("Data : '"+username+"' ingin dihapus?")){
            $.post("/nilai/UserCtr",
            {
                page: page,
                username: username
            },
                function(data, status){
                    alert(data);
                    location.reload();
                }
            );
        }
    });
    
    //PROCEDURE EDIT DATA
    $("#tabeluser tbody").on('click', '#btnEdit', function(){
        // --- AJAX FILL OPTION USERNAME
        $.ajax({
            url: '/nilai/UserCtr',
            type: 'GET',
            dataType: 'json',
            success: function(data) {
                var select = $('#username');
                select.empty();
                data.forEach(function(item) {
                    $('<option>', {
                        value: item.username,
                        text: item.username
                    }).appendTo(select);
                });
            },
            error: function(jqXHR, textStatus, errorThrown) {
                console.log(textStatus, errorThrown);
            }
        });
        
        $("#myModal").show();
        $("#title1").hide();
        $("#title2").show();
        $("#username").prop('disabled', true);
        $("#level").prop('disabled', true);
        
        page = "tampil";
        var baris = $(this).closest('tr');
        var username = baris.find("td:eq(0)").text();
        $.post("/nilai/UserCtr",
            {
                page: page,
                username: username
            },
            function(data, status){
                $("#username").val(data.username);
                $("#password").val(data.password);
                $("#level").val(data.level);
            }
        );
            page = "edit";
    });

    //MENYIMPAN DATA
    $('#btnSave').on('click',function(){
        getInputValue();
        
        if(username === ""){
            alert("username harus diisi");
            $("#username").focus();
        }else if(password === ""){
            alert("Password harus diisi");
            $("#password").focus();
        }else{
                $.post("/nilai/UserCtr",
                {
                    page: page,
                    username: username,
                    password: password,
                    level: level
                },
                function(data, status){
                    alert(data);
                    location.reload();
                }
            );
        }
    });

    // TOMBOL BATAL PADA FORM EDIT DATA
    $('#btnCancel').on('click',function(){
        clearData();
        $('#myModal').hide();
    });
    
    $('#btnKembali').on('click',function(){
        window.location.href = '/nilai/beranda.html';
    });
    
   $.ajax({
      url:"/nilai/UserCtr",
      method:"GET",
      dataType:"json",
      
      success:
        function(data){
            $("#tabeluser").dataTable({
               serverside:true,
               processing:true,
               data:data,
               sort:true,
               searching:true,
               paging:true,
               columns:[
                   {'data':'username','name':'username','type':'string'},
                   {'data':'password'},
                   {'data':'level'},
                   {'data':null,'className':'dt-right','mRender':function(o){
                           return "<a class='btn btn-outline-success btn-sm' id='btnEdit'>Edit</a>"
                           +"&nbsp;&nbsp;"
                           +"<a class='btn btn-outline-danger btn-sm' id='btnDel'>Hapus</a>"
                       }
                   }
               ]
            });
        }
   });
   
});