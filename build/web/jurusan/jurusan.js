$(document).ready(function(){
    var kodejurusan,namajurusan,page;
    function getInputValue(){
        kodejurusan = $("#kodejurusan").val();
        namajurusan = $("#namajurusan").val();
    }
    function clearData(){
        $("#kodejurusan").val('');
        $("#namajurusan").val('');
    }
    //PRCEDURE TAMBAH DATA
    $('#btnAdd').click(function (){
        $('#myModal').show();
        $('#title1').show();
        $('#title2').hide();
        $('#kodejurusan').prop('disabled',false);
        page="tambah";  
    });
    
    //PROCEDURE HAPUS DATA
    $("#tabeljurusan tbody").on('click', '#btnDel', function(){
        var baris = $(this).closest('tr');
        var kodejurusan = baris.find("td:eq(0)").text();
        var namajurusan = baris.find("td:eq(1)").text();
        page = "hapus";
        
        if(confirm("Data : '"+kodejurusan+" - "+namajurusan+"' ingin dihapus?")){
            $.post("/nilai/JurusanCtr",
            {
                page: page,
                kodejurusan: kodejurusan
            },
                function(data, status){
                    alert(data);
                    location.reload();
                }
            );
        }
    });
    
    //PROCEDURE EDIT DATA
    $("#tabeljurusan tbody").on('click', '#btnEdit', function(){
        $("#myModal").show();
        $("#title1").hide();
        $("#title2").show();
        $("#kodejurusan").prop('disabled', true);
        
        page = "tampil";
        var baris = $(this).closest('tr');
        var kodejurusan = baris.find("td:eq(0)").text();
        $.post("/nilai/JurusanCtr",
            {
                page: page,
                kodejurusan: kodejurusan
            },
            function(data, status){
                $("#kodejurusan").val(data.kodejurusan);
                $("#namajurusan").val(data.namajurusan);
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
        }else if(namajurusan === ""){
            alert("Nama jurusan harus diisi");
            $("#namajurusan").focus();
        }else{
                $.post("/nilai/JurusanCtr",
                {
                    page: page,
                    kodejurusan: kodejurusan,
                    namajurusan: namajurusan
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
      url:"/nilai/JurusanCtr",
      method:"GET",
      dataType:"json",
      
      success:
        function(data){
            $("#tabeljurusan").dataTable({
               serverside:true,
               processing:true,
               data:data,
               sort:true,
               searching:true,
               paging:true,
               columns:[
                   {'data':'kodejurusan','name':'kodejurusan','type':'string'},
                   {'data':'namajurusan'},
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