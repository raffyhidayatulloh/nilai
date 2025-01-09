$(document).ready(function(){
    var idmapel,namamapel,page;
    function getInputValue(){
        idmapel = $("#idmapel").val();
        namamapel = $("#namamapel").val();
    }
    function clearData(){
        $("#idmapel").val('');
        $("#namamapel").val('');
    }
    //PRCEDURE TAMBAH DATA
    $('#btnAdd').click(function (){
        $('#myModal').show();
        $('#title1').show();
        $('#title2').hide();
        $('#idmapel').prop('disabled',false);
        page="tambah";  
    });
    
    //PROCEDURE HAPUS DATA
    $("#tabelmapel tbody").on('click', '#btnDel', function(){
        var baris = $(this).closest('tr');
        var idmapel = baris.find("td:eq(0)").text();
        var namamapel = baris.find("td:eq(1)").text();
        page = "hapus";
        
        if(confirm("Data : '"+idmapel+" - "+namamapel+"' ingin dihapus?")){
            $.post("/nilai/MapelCtr",
            {
                page: page,
                idmapel: idmapel
            },
                function(data, status){
                    alert(data);
                    location.reload();
                }
            );
        }
    });
    
    //PROCEDURE EDIT DATA
    $("#tabelmapel tbody").on('click', '#btnEdit', function(){
        $("#myModal").show();
        $("#title1").hide();
        $("#title2").show();
        $("#idmapel").prop('disabled', true);
        
        page = "tampil";
        var baris = $(this).closest('tr');
        var idmapel = baris.find("td:eq(0)").text();
        $.post("/nilai/MapelCtr",
            {
                page: page,
                idmapel: idmapel
            },
            function(data, status){
                $("#idmapel").val(data.idmapel);
                $("#namamapel").val(data.namamapel);
            }
        );
            page = "edit";
    });

    //MENYIMPAN DATA
    $('#btnSave').on('click',function(){
        getInputValue();
        
        if(idmapel === ""){
            alert("ID Mapel harus diisi");
            $("#idmapel").focus();
        }else if(namamapel === ""){
            alert("Nama mapel harus diisi");
            $("#namamapel").focus();
        }else{
                $.post("/nilai/MapelCtr",
                {
                    page: page,
                    idmapel: idmapel,
                    namamapel: namamapel
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
      url:"/nilai/MapelCtr",
      method:"GET",
      dataType:"json",
      
      success:
        function(data){
            $("#tabelmapel").dataTable({
               serverside:true,
               processing:true,
               data:data,
               sort:true,
               searching:true,
               paging:true,
               columns:[
                   {'data':'idmapel','name':'idmapel','type':'string'},
                   {'data':'namamapel'},
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