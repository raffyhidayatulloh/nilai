$(document).ready(function(){
    var nis,kodekelas,thpel,thpel2,tahunpel,page, parts;
    
    function getInputValue(){
        nis = $("#nis").val();
        kodekelas = $("#kodekelas").val();
        thpel = $("#thpel").val();
        thpel2 = $("#thpel2").val();
        tahunpel = thpel.toString() + "-" + thpel2.toString();
    }
    function clearData(){
        $("#nis").val('');
        $("#kodekelas").val('');
        $("#thpel").val('');
        $("#thpel2").val('');
    }
    
    // --- PROCEDURE TAMBAH DATA
    $('#btnAdd').click(function (){
        $('#myModal').show();
        $('#title1').show();
        $('#title2').hide();
        $('#nis').prop('disabled',false);
        $('#kodekelas').prop('disabled',false);
        page="tambah";
    });
    
    //PROCEDURE HAPUS DATA
    $("#tabeldaftarkelas tbody").on('click', '#btnDel', function(){
        var baris = $(this).closest('tr');
        var nis = baris.find("td:eq(0)").text();
        var kodekelas = baris.find("td:eq(1)").text();
        var tahunpel = baris.find("td:eq(2)").text();
        page = "hapus";
        
        if(confirm("Data : '"+nis+" - "+kodekelas+" - "+tahunpel+ "' ingin dihapus?")){
            $.post("/nilai/DaftarKelasCtr",
            {
                page: page,
                nis: nis,
                kodekelas: kodekelas,
                tahunpel : tahunpel
            },
                function(data, status){
                    alert(data);
                    location.reload();
                }
            );
        }
    });
    
    //PROCEDURE EDIT DATA
    $("#tabeldaftarkelas tbody").on('click', '#btnEdit', function(){
        $("#myModal").show();
        $("#title1").hide();
        $("#title2").show();
        $("#nis").prop('disabled', true);
        $('#kodekelas').prop('disabled',true);
        
        page = "tampil";
        var baris = $(this).closest('tr');
        var nis = baris.find("td:eq(0)").text();
        var kodekelas = baris.find("td:eq(1)").text();
        $.post("/nilai/DaftarKelasCtr",
            {
                page: page,
                nis: nis,
                kodekelas: kodekelas
            },
            function(data, status){
                $("#nis").val(data.nis);
                $("#kodekelas").val(data.kodekelas);
                $("#tahunpel").val(data.tahunpel);
                tahunpel = data.tahunpel;
                parts = tahunpel.split("-");
                thpel = parseInt(parts[0]);
                thpel2 = parseInt(parts[1]);
                $("#thpel").val(thpel);
                $("#thpel2").val(thpel2);
            }
        );
            page = "edit";
    });

    // --- MENYIMPAN DATA
    $('#btnSave').on('click',function(){
        getInputValue();
        
        if(nis === ""){
            alert("NIS harus diisi");
            $("#nis").focus();
        }else if(kodekelas === ""){
            alert("Kode kelas harus diisi");
            $("#kodekelas").focus();
        }else{
                $.post("/nilai/DaftarKelasCtr",
                {
                    page: page,
                    nis: nis,
                    kodekelas: kodekelas,
                    tahunpel: tahunpel
                },
                function(data, status){
                    alert(data);
                    location.reload();
                }
            );
        }
    });

    // --- FUNCTION AUTOFILL TH PELAJARAN
    $('#thpel').on('input',function(){
        thpel = $("#thpel").val();
        thpel2 = parseInt(thpel) + 1;
        $("#thpel2").val(thpel2);
    });

    // --- FUNCTION BACK AND CLEAR BTN CANCEL
    $('#btnCancel').on('click',function(){
        clearData();
        $('#myModal').hide();
    });
    
    // --- FUNCTION BACK TO HOMEPAGE
    $('#btnKembali').click(function (){
        window.location.href = '/nilai/beranda.html';
    });
    
    // --- AJAX FILL DATA IN TABLE
    $.ajax({
      url:"/nilai/DaftarKelasCtr",
      method:"GET",
      dataType:"json",
      
      success:
        function(data){
            $("#tabeldaftarkelas").dataTable({
               serverside:true,
               processing:true,
               data:data,
               sort:true,
               searching:true,
               paging:true,
               columns:[
                   {'data':'nis','name':'nis','type':'string'},
                   {'data':'kodekelas'},
                   {'data':'tahunpel'},
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
   
   // --- AJAX FILL OPTION NIS
    $.ajax({
        url: '/nilai/SiswaCtr',
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            var select = $('#nis');
            data.forEach(function(item) {
                $('<option>', {
                    value: item.nis,
                    text: item.nis
                }).appendTo(select);
            });
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus, errorThrown);
        }
    });
   
   // --- AJAX FILL OPTION KODE KELAS
    $.ajax({
        url: '/nilai/KelasCtr',
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            var select = $('#kodekelas');
            data.forEach(function(item) {
                $('<option>', {
                    value: item.kodekelas,
                    text: item.kodekelas
                }).appendTo(select);
            });
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus, errorThrown);
        }
    });
});