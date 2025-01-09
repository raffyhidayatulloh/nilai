$(document).ready(function(){
    var kodejurusan,tahunpel,kodekelas,nis,idmapel,nilaisiswa,page;
    var kodeguru = sessionStorage.getItem('username');
    
    // -- AJAX GET ID MAPEL DARI KODE GURU
    $.ajax({
        url: '/nilai/GuruCtr?page=tampil&kodeguru='+kodeguru,
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            idmapel = data.idmapel;
            $("#mapelajar").text(idmapel);
            
            // -- SORTIR BERDASAR ID MAPEL
            $.ajax({
                url: "/nilai/NilaiCtr",
                method: "GET",
                dataType: "json",
                data: { page: 'tampilmapel', idmapel: idmapel }, // SEND ID MAPEL TO PARAMETER
                success: function(data) {
                    $("#tabelnilai").DataTable().clear().destroy();

                    $("#tabelnilai").DataTable({
                        serverside: true,
                        processing: true,
                        data: data,
                        sort: true,
                        searching: true,
                        paging: true,
                        columns:[
                            {'data':'kodejurusan','name':'kodejurusan','type':'string'},
                            {'data':'tahunpel'},
                            {'data':'kodekelas'},
                            {'data':'nis'},
                            {'data':'idmapel'},
                            {'data':'nilaisiswa'},
                            {'data':null,'className':'dt-right','mRender':function(o){
                                return "<a class='btn btn-outline-success btn-sm' id='btnEdit'>Edit</a>"
                                +"&nbsp;&nbsp;"
                                +"<a class='btn btn-outline-danger btn-sm' id='btnDel'>Hapus</a>"
                                }
                            }
                        ]
                    });
                },
                error: function(xhr, status, error) {
                    console.log(textStatus, errorThrown);
                }
            });
            
            // -- AJAX GET NAMA MAPEL
            $.ajax({
                url: '/nilai/MapelCtr?page=tampil&idmapel='+idmapel,
                type: 'GET',
                dataType: 'json',
                success: function(data) {
                    $('#namaajar').text(data.namamapel);
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    console.log(textStatus, errorThrown);
                }
            });
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus, errorThrown);
        }
    });
    
    // -- GET DATA
    function getInputValue() {
        kodejurusan = $("#kodejurusan").val();
        tahunpel = $("#tahunpel").children("option:selected").val();
        kodekelas = $("#kodekelas").val();
        nis = $("#nis").val();
        idmapel = $("#idmapel").val();
        nilaisiswa = $("#nilaisiswa").val();
    }
    
    // -- CLEAR DATA
    function clearData() {
        $("#kodejurusan").val('');
        $("#tahunpel").val('');
        $("#kodekelas").val('');
        $("#nis").val('');
        $("#nilaisiswa").val('');
    }
    
    // -- BUTTON ADD DATA
    $('#btnAdd').click(function() {
        $('#myModal').show();
        $('#title1').show();
        $('#title2').hide();
        $('#kodejurusan').prop('disabled',false);
        $("#tahunpel").prop('disabled', false);
        $("#kodekelas").prop('disabled', false);
        $("#nis").prop('disabled', false);
        $("#idmapel").prop('disabled', true);
        page="tambah";  
    });
    
    // -- BUTTON DELETE DATA
    $("#tabelnilai tbody").on('click', '#btnDel', function() {
        var baris = $(this).closest('tr');
        var kodejurusan = baris.find("td:eq(0)").text();
        var tahunpel = baris.find("td:eq(1)").text();
        var kodekelas = baris.find("td:eq(2)").text();
        var nis = baris.find("td:eq(3)").text();
        var idmapel = baris.find("td:eq(4)").text();
        page = "hapus";
        
        if(confirm("Anda yakin data : ' "+kodejurusan+" - "+tahunpel+" - "+kodekelas+" - "+nis+" - "+idmapel+" ' akan dihapus?")){
            $.post("/nilai/NilaiCtr",
            {
                page: page,
                kodejurusan: kodejurusan,
                tahunpel: tahunpel,
                kodekelas: kodekelas,
                nis: nis,
                idmapel: idmapel
            },
                function(data, status){
                    alert(data);
                    location.reload();
                }
            );
        }
    });
    
    // -- BUTTON EDIT DATA
    $("#tabelnilai tbody").on('click', '#btnEdit', function() {
        $("#myModal").show();
        $("#title1").hide();
        $("#title2").show();
        $("#kodejurusan").prop('disabled', true);
        $("#tahunpel").prop('disabled', true);
        $("#kodekelas").prop('disabled', true);
        $("#nis").prop('disabled', true);
        $("#idmapel").prop('disabled', true);
        
        page = "tampil";
        var baris = $(this).closest('tr');
        var kodejurusan = baris.find("td:eq(0)").text();
        var tahunpel = baris.find("td:eq(1)").text();
        var kodekelas = baris.find("td:eq(2)").text();
        var nis = baris.find("td:eq(3)").text();
        var idmapel = baris.find("td:eq(4)").text();
        $.post("/nilai/NilaiCtr",
            {
                page: page,
                kodejurusan: kodejurusan,
                tahunpel: tahunpel,
                kodekelas: kodekelas,
                nis: nis,
                idmapel: idmapel
            },
            function(data, status){
                $("#kodejurusan").val(data.kodejurusan);
                $("#tahunpel").val(data.tahunpel);
                $("#kodekelas").val(data.kodekelas);
                $("#nis").val(data.nis);
                $("#idmapel").val(data.idmapel);
                $("#nilaisiswa").val(data.nilaisiswa);
            }
        );
            page = "edit";
    });

    // -- BUTTON SAVE PADA MODAL
    $('#btnSave').on('click',function() {
        getInputValue();
        
        if(kodejurusan === ""){
            alert("Kode jurusan harus diisi");
            $("#kodejurusan").focus();
        }else if(tahunpel === ""){
            alert("Tahun pelajaran harus diisi");
            $("#tahunpel").focus();
        }else if(kodekelas === ""){
            alert("Kode kelas harus diisi");
            $("#kodekelas").focus();
        }else if(nis === ""){
            alert("NIS harus diisi");
            $("#nis").focus();
        }else if(idmapel === ""){
            alert("ID mapel harus diisi");
            $("#idmapel").focus();
        }else if(nilaisiswa < 0 || nilaisiswa > 100){
            alert("Masukkan nilai antara 0 - 100!");
            $("#nilaisiswa").focus();
        }else{
                $.post("/nilai/NilaiCtr",
                {
                    page: page,
                    kodejurusan: kodejurusan,
                    tahunpel: tahunpel,
                    kodekelas: kodekelas,
                    nis: nis,
                    idmapel: idmapel,
                    nilaisiswa: nilaisiswa
                },
                function(data, status){
                    alert(data);
                    location.reload();
                }
            );
        }
    });

    // -- BUTTON CANCEL
    $('#btnCancel').on('click',function() {
        clearData();
        $('#myModal').hide();
    });
    
    // -- BUTTON BACK
    $('#btnKembali').click(function() {
        window.location.href = '/nilai/beranda.html';
    });
   
   // -- AJAX FILL OPTION KODE JURUSAN
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
    
    // AJAX FILL OPTION TAHUN PELAJARAN
    $.ajax({
        url: '/nilai/DaftarKelasCtr',
        type: 'GET',
        data: { page: 'tahunpel' },
        dataType: 'json',
        success: function(data) {
            var select = $('#tahunpel');
            var existingYears = []; // Array untuk menyimpan tahun pelajaran yang sudah muncul
            data.forEach(function(item) {
                // Jika tahun pelajaran belum ada dalam array existingYears, tambahkan sebagai opsi
                if (!existingYears.includes(item.tahunpel)) {
                    $('<option>', {
                        value: item.tahunpel,
                        text: item.tahunpel
                    }).appendTo(select);
                    existingYears.push(item.tahunpel); // Tambahkan tahun pelajaran ke dalam array
                }
            });
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus, errorThrown);
        }
    });
    
   // -- AJAX FILL OPTION KODE KELAS
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
    
   // -- AJAX FILL OPTION NIS
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
    
   // -- AJAX FILL OPTION ID MAPEL
    $.ajax({
        url: '/nilai/MapelCtr',
        type: 'GET',
        data: { idmapel: idmapel },
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