$(document).ready(function(){
    var kodeguru,namaguru,idmapel,page;
    
    function getInputValue(){
        kodeguru = $("#kodeguru").val();
        namaguru = $("#namaguru").val();
        idmapel = $("#idmapel").children("option:selected").val();
    }
    function clearData(){
        $("#kodeguru").val('');
        $("#namaguru").val('');
        $("#idmapel").val('');
    }
    //PRCEDURE TAMBAH DATA
    $('#btnAdd').click(function (){
        $('#myModal').show();
        $('#title1').show();
        $('#title2').hide();
        $('#kodeguru').prop('disabled',false);
        page="tambah";  
    });
    
    //PROCEDURE HAPUS DATA
    $("#tabelguru tbody").on('click', '#btnDel', function(){
        var baris = $(this).closest('tr');
        var kodeguru = baris.find("td:eq(0)").text();
        var namaguru = baris.find("td:eq(1)").text();
        page = "hapus";
        
        if(confirm("Data : '"+kodeguru+" - "+namaguru+"' ingin dihapus?")){
            $.post("/nilai/GuruCtr",
            {
                page: page,
                kodeguru: kodeguru
            },
                function(data, status){
                    alert(data);
                    location.reload();
                }
            );
        }
    });
    
    //PROCEDURE EDIT DATA
    $("#tabelguru tbody").on('click', '#btnEdit', function(){
        $("#myModal").show();
        $("#title1").hide();
        $("#title2").show();
        $("#kodeguru").prop('disabled', true);
        
        page = "tampiledit";
        var baris = $(this).closest('tr');
        var kodeguru = baris.find("td:eq(0)").text();
        var namaguru = baris.find("td:eq(1)").text();
        var idmapel = baris.find("td:eq(2)").text();
        $.post("/nilai/GuruCtr",
            {
                page: page,
                kodeguru: kodeguru,
                namaguru: namaguru,
                idmapel: idmapel
            },
            function(data, status){
                $("#kodeguru").val(data.kodeguru);
                $("#namaguru").val(data.namaguru);
                $("#idmapel").val(data.idmapel);
            }
        );
            page = "edit";
    });

    //MENYIMPAN DATA
    $('#btnSave').on('click',function(){
        getInputValue();
        if(kodeguru === ""){
            alert("Kode guru harus diisi");
            $("#kodeguru").focus();
        }else if(namaguru === ""){
            alert("Nama guru harus diisi");
            $("#namaguru").focus();
        }else{
            $.post("/nilai/GuruCtr",
                {
                    page: page,
                    kodeguru: kodeguru,
                    namaguru: namaguru,
                    idmapel: idmapel,
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
      url:"/nilai/GuruCtr",
      method:"GET",
      dataType:"json",
      
      success:
        function(data){
            $("#tabelguru").dataTable({
               serverside:true,
               processing:true,
               data:data,
               sort:true,
               searching:true,
               paging:true,
               columns:[
                   {'data':'kodeguru','name':'kodeguru','type':'string'},
                   {'data':'namaguru'},
                   {'data':'idmapel'},
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
   
   // --- AJAX FILL OPTION ID MAPEL
    $.ajax({
        url: '/nilai/MapelCtr',
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            var select = $('#idmapel');
            data.forEach(function(item) {
                $('<option>', {
                    value: item.idmapel,
                    text: item.idmapel
                }).appendTo(select);
            });
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus, errorThrown);
        }
    });
});