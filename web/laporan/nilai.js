$(document).ready(function(){
    var nis;
    nis = sessionStorage.getItem('username');
    
    // -- BUTTON KEMBALI
    $('#btnKembali').click(function (){
        window.location.href = '/nilai/beranda.html';
    });
    
   $.ajax({
      url:"/nilai/NilaiCtr",
      method:"GET",
      data: { page: 'tampilnis', nis: nis },
      dataType:"json",
      
      success:
        function(data){
            $("#tabelnilai").dataTable({
               serverside:true,
               processing:true,
               data:data,
               sort:true,
               searching:true,
               paging:true,
               columns:[
                   {'data':'kodejurusan','name':'kodejurusan','type':'string'},
                   {'data':'tahunpel'},
                   {'data':'kodekelas'},
                   {'data':'nis'},
                   {'data':'idmapel'},
                   {'data':'nilaisiswa'}
               ]
            });
        }
   });
});