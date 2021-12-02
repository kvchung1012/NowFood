const baseUrl = 'http://localhost:8081'
function Login(){
    $('#btn-form-login').click(()=>{
        var data = $('#form-log-in').serializeArray().reduce(function(obj, item) {
            obj[item.name] = item.value;
            return obj;
        }, {});

        console.log(data)

        $.ajax({
            url: `${baseUrl}/api/auth/login`,
            type: 'post',
            dataType: 'json',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function (res) {
                console.log(res);
                localStorage.setItem('token',res.data.token);
                GetInfo();
            },
            error : function(res){
                console.log(res);
            }
        })
    })
}



function GetInfo(){
    $.ajax({
        url: `${baseUrl}/api/users`,
        type: 'get',
        dataType: 'json',
        contentType: 'application/json',
        headers: {
            "Authorization": "Bearer " + localStorage.getItem('token'),
          },
        data: {},
        success: function (res) {
            console.log(res);
            localStorage.setItem('info',JSON.stringify(res.data));
            location.href = '/home.html'
        },
        error : function(res){
            console.log(res);
        }
    })
}
Login()