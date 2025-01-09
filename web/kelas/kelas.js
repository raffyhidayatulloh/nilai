$(document).ready(function(){
    var kodejurusan,kodekelas,namakelas,page;
    function getInputValue(){
        kodejurusan = $("#kodejurusan").val();
        kodekelas = $("#kodekelas").val();
        namakelas = $("#namakelas").val();
    }
    function clearData(){
        $("#kodejurusan").val('');
        $("#kodekelas").val('');
        $("#namakelas").val('');
    }
    //PROCEDURE TAMBAH DATA
    $('#btnAdd').click(function (){
        $('#myModal').show();
        $('#title1').show();
        $('#title2').hide();
        $('#kodejurusan').prop('disabled',false);
        $('#kodekelas').prop('disabled',false);
        page="tambah";  
    });
    
    //PROCEDURE HAPUS DATA
    $("#tabelkelas tbody").on('click', '#btnDel', function(){
        var baris = $(this).closest('tr');
        var kodejurusan = baris.find("td:eq(0)").text();
        var kodekelas = baris.find("td:eq(1)").text();
        var namakelas = baris.find("td:eq(2)").text();
        page = "hapus";
        
        if(confirm("Anda yakin data : '"+kodejurusan+" - "+kodekelas+" - "+namakelas+"' akan dihapus?")){
            $.post("/nilai/KelasCtr",
            {
                page: page,
                kodejurusan: kodejurusan,
                kodekelas: kodekelas
            },
                function(data, status){
                    alert(data);
                    location.reload();
                }
            );
        }
    });
    
    //PROCEDURE EDIT DATA
    $("#tabelkelas tbody").on('click', '#btnEdit', function(){
        $("#myModal").show();
        $("#title1").hide();
        $("#title2").show();
        $("#kodejurusan").prop('disabled', true);
        $("#kodekelas").prop('disabled', true);
        
        page = "tampil";
        var baris = $(this).closest('tr');
        var kodejurusan = baris.find("td:eq(0)").text();
        var kodekelas = baris.find("td:eq(1)").text();
        $.post("/nilai/KelasCtr",
            {
                page: page,
                kodejurusan: kodejurusan,
                kodekelas: kodekelas
            },
            function(data, status){
                $("#kodejurusan").val(data.kodejurusan);
                $("#kodekelas").val(data.kodekelas);
                $("#namakelas").val(data.namakelas);
            }
        );
            page = "edit";
    });

    //MENYIMPAN DATA
    $('#btnSave').on('click',function(){
        getInputValue();
        
        if(kodejurusan === ""){
            alert("Kode jurusan harus diisi");
            $("#kodejurusan").focus();
        }else if(kodekelas === ""){
            alert("Kode kelas harus diisi");
            $("#kodekelas").focus();
        }else if(namakelas === ""){
            alert("Nama kelas harus diisi");
            $("#namakelas").focus();
        }else{
                $.post("/nilai/KelasCtr",
                {
                    page: page,
                    kodejurusan: kodejurusan,
                    kodekelas: kodekelas,
                    namakelas: namakelas,
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
    
    $('#btnKembali').click(function (){
        window.location.href = '/nilai/beranda.html';
    });
    
   $.ajax({
      url:"/nilai/KelasCtr",
      method:"GET",
      dataType:"json",
      
      success:
        function(data){
            $("#tabelkelas").dataTable({
               serverside:true,
               processing:true,
               data:data,
               sort:true,
               searching:true,
               paging:true,
               columns:[
                   {'data':'kodejurusan','name':'kodejurusan','type':'string'},
                   {'data':'kodekelas'},
                   {'data':'namakelas'},
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
   
   $.ajax({
        url: '/nilai/JurusanCtr',
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            var select = $('#kodejurusan');
            data.forEach(function(item) {
                $('<option>', {
                    value: item.kodejurusan,
                    text: item.kodejurusan
                }).appendTo(select);
            });
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus, errorThrown);
        }
    });
    
});