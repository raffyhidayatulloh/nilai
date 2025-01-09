$(document).ready(function(){
    var nis,nama,jenkel,telepon,alamat,page;
    function getInputValue(){
        nis = $("#nis").val();
        nama = $("#nama").val();
        jenkel = $("#jenkel").children("option:selected").val();
        telepon = $("#telepon").val();
        alamat = $("#alamat").val();
    }
    function clearData(){
        $("#nis").val('');
        $("#nama").val('');
        $("#jenkel").val('');
        $("#telepon").val('');
        $("#alamat").val('');
    }
    //PRCEDURE TAMBAH DATA
    $('#btnAdd').click(function (){
        $('#myModal').show();
        $('#title1').show();
        $('#title2').hide();
        $('#nis').prop('disabled',false);
        page="tambah";  
    });
    
    //PROCEDURE HAPUS DATA
    $("#tabelsiswa tbody").on('click', '#btnDel', function(){
        var baris = $(this).closest('tr');
        var nis = baris.find("td:eq(0)").text();
        var Nama = baris.find("td:eq(1)").text();
        page = "hapus";
        
        if(confirm("Data : '"+nis+" - "+Nama+"' ingin dihapus?")){
            $.post("/nilai/SiswaCtr",
            {
                page: page,
                nis: nis
            },
                function(data, status){
                    alert(data);
                    location.reload();
                }
            );
        }
    });
    
    //PROCEDURE EDIT DATA
    $("#tabelsiswa tbody").on('click', '#btnEdit', function(){
        $("#myModal").show();
        $("#title1").hide();
        $("#title2").show();
        $("#nis").prop('disabled', true);
        
        page = "tampil";
        var baris = $(this).closest('tr');
        var nis = baris.find("td:eq(0)").text();
        $.post("/nilai/SiswaCtr",
            {
                page: page,
                nis: nis
            },
            function(data, status){
                $("#nis").val(data.nis);
                $("#nama").val(data.nama);
                $("#jenkel").val(data.jenkel);
                $("#telepon").val(data.telepon);
                $("#alamat").val(data.alamat);
            }
        );
            page = "edit";
    });

    //MENYIMPAN DATA
    $('#btnSave').on('click',function(){
        getInputValue();
        if(nis === ""){
            alert("NIS harus diisi");
            $("#nis").focus();
        }else if(nama === ""){
            alert("NAMA siswa harus diisi");
            $("#nama").focus();
        }else{
                $.post("/nilai/SiswaCtr",
                {
                    page: page,
                    nis: nis,
                    nama: nama,
                    jenkel: jenkel,
                    telepon: telepon,
                    alamat: alamat
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
      url:"/nilai/SiswaCtr",
      method:"GET",
      dataType:"json",
      
      success:
        function(data){
            $("#tabelsiswa").dataTable({
               serverside:true,
               processing:true,
               data:data,
               sort:true,
               searching:true,
               paging:true,
               columns:[
                   {'data':'nis','name':'nis','type':'string'},
                   {'data':'nama'},
                   {'data':'jenkel'},
                   {'data':'telepon'},
                   {'data':'alamat'},
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