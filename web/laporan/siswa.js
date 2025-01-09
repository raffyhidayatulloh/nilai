$(document).ready(function(){
    var nis,nama,jenkel,telepon,alamat,page;
    
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
                   {'data':'alamat'}
               ]
            });
        }
   });
});