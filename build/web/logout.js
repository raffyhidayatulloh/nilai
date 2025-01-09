$(document).ready(function() {
    sessionStorage.removeItem('username');
    sessionStorage.removeItem('level');
    sessionStorage.clear();
    alert("Logout sukses");
    window.location.href = 'login.html';
});