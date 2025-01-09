$(document).ready(function() {
    var username = sessionStorage.getItem('username');
    var level = sessionStorage.getItem('level');
    
    $("#username").text(username);
    $("#level").text(level);

    switch (level) {
        case "admin":
            $("#nilaisiswa").hide();
            $("#nilai").hide();
            $("#lapsiswa").hide();
            break;
        case "guru":
            $("#siswa").hide();
            $("#jurusan").hide();
            $("#mapel").hide();
            $("#kelas").hide();
            $("#daftarkelas").hide();
            $("#guru").hide();
            $("#user").hide();
            $("#lapsiswa").show();
            $("#nilai").hide();
            break;
        case "siswa":
            $("#dropdata").hide();
            $("#lapsiswa").hide();
            $("#nilai").show();
            break;
        default:
            alert("Level tidak dikenali");
            $("#dropdata").hide();
            $("#lapsiswa").hide();
            $("#nilai").hide();
    }
    
    $("#btnLogout").click(function() {
        sessionStorage.removeItem('username');
        sessionStorage.removeItem('level');
        sessionStorage.clear();
        alert("Logout sukses");
        window.location.href = 'login.html';
    });
    
    function displayTime() {
        var time = new Date();
        var hour = ('0' + time.getHours()).slice(-2);
        var min = ('0' + time.getMinutes()).slice(-2);
        document.getElementById("time").innerHTML = hour + "." + min;
    }
    function displayDate() {
        var time = new Date();
        var day = time.getDay();
        var date = time.getDate();
        var month = time.getMonth();
        var year = time.getFullYear();
        var dataHari = ["Minggu", "Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu"];
        var dataBulan = ["Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"];
        document.getElementById("date").innerHTML = dataHari[day] + ", " + date + " " + dataBulan[month] + " " + year;
    }
    setInterval(displayDate, 500);
    setInterval(displayTime, 500);
});