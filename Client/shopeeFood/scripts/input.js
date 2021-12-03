const params = new window.URLSearchParams(window.location.search);
const idShop = params.get('Id');
const productId = params.get('productId');
const baseUrl = 'http://localhost:8081'

function login(){
    location.href = "/login.html"
}

CheckLogin()
function CheckLogin(){
    var token = localStorage.getItem('token');
    if(token != null){
        $('.btn-login').css('display','none')
        $('.account-name').text(JSON.parse(localStorage.getItem('info')).phoneNumber);
        $('.account-name').css('display','block' )
    }
}
