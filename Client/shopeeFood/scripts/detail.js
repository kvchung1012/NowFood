const params = new window.URLSearchParams(window.location.search);
const idShop = params.get('Id');
const productId = params.get('productId');
const baseUrl = 'http://localhost:8081'

GetCategoryByShop();
GetShop();
function GetShop(){
    $.ajax({
        url : `${baseUrl}/api/shops/${idShop}/products`,
        type:'get',
        dataType : 'json',
        contentType:'application/json',
        data: {},
        success : function(res){
            console.log(res);
            $('#image_product').attr('src',baseUrl+res.data.products.filter(x=>x.id==productId)[0].image)
            $('#name_shop').text(res.data.shopName);
            $('#address_shop').text(res.data.shopAddress);
            $('#time_open').text(new Date(res.data.openTime * 1000).getUTCHours()+"00 - "+new Date(res.data.closeTime * 1000).getUTCHours()+":00");
            $('#range_price').text(res.data.products.reduce(function(prev, curr) {
                return prev.price < curr.price ? prev : curr;
            }).price+"VND - " + 
            res.data.products.reduce(function(prev, curr) {
                return prev.price > curr.price ? prev : curr;
            }).price+"VND"
            );


            // danh sách món ăn

            $('.intro-menu').html('');
            res.data.products.forEach((val,index)=>{
                let row = `<div class="item-menu mt-1 d-flex justify-content-between">
                                <div class="d-flex">
                                    <img class="item-restaurant-img img-item19833" src="${baseUrl+val.image}" alt="">
                                    <div class="item-restaurant-name">
                                        <p class="name-item19833">${val.name}</p>
                                    </div>
                                </div>
                                <div class="price-btn d-flex">
                                    <div class="food-price">
                                        <p class="initial-price"><del>${val.price} <sup>đ</sup></del></p>
                                    </div>
                                    <button class="btn-order text-center" id="btn-order-item19833" onclick="OrderFood(${val.id})"><i class="fas fa-plus"></i></button>
                                </div>
                            </div>`;
                $('.intro-menu').append(row);
            })
        }
    })
}


function GetCategoryByShop(){
    $.ajax({
        url : `${baseUrl}/api/shops/${idShop}/category-shop`,
        type:'get',
        dataType : 'json',
        contentType:'application/json',
        data: {},
        success : function(res){
            $('.list-menu').html('');
            res.data.forEach((val,index)=>{
                let row = `<li class="menu-item">
                                <a href="">${val.name}</a>
                            </li>`;
                $('.list-menu').append(row);
            });
        }
    })
}


function OrderFood(id){
    $.ajax({
        url : `${baseUrl}/api/products/${id}`,
        type:'get',
        dataType : 'json',
        contentType:'application/json',
        data: {},
        success : function(res){
            $('#exampleModalCenter').modal('show');
            console.log(res);
        }
    })
}