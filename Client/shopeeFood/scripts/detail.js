const params = new window.URLSearchParams(window.location.search);
const idShop = params.get('Id');
const productId = params.get('productId');
const baseUrl = 'http://localhost:8081'

GetCategoryByShop();
GetShop();
LoadListOrder();
function GetShop() {
    $.ajax({
        url: `${baseUrl}/api/shops/${idShop}/products`,
        type: 'get',
        dataType: 'json',
        contentType: 'application/json',
        data: {},
        success: function (res) {
            console.log(res);
            $('#image_product').attr('src', baseUrl + res.data.products.filter(x => x.id == productId)[0].image)
            $('#name_shop').text(res.data.shopName);
            $('#address_shop').text(res.data.shopAddress);
            $('#time_open').text(new Date(res.data.openTime * 1000).getUTCHours() + "00 - " + new Date(res.data.closeTime * 1000).getUTCHours() + ":00");
            $('#range_price').text(res.data.products.reduce(function (prev, curr) {
                return prev.price < curr.price ? prev : curr;
            }).price + "VND - " +
                res.data.products.reduce(function (prev, curr) {
                    return prev.price > curr.price ? prev : curr;
                }).price + "VND"
            );


            // danh sách món ăn

            $('.intro-menu').html('');
            res.data.products.forEach((val, index) => {
                let row = `<div class="item-menu mt-1 d-flex justify-content-between">
                                <div class="d-flex">
                                    <img class="item-restaurant-img img-item19833" src="${baseUrl + val.image}" alt="">
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


function GetCategoryByShop() {
    $.ajax({
        url: `${baseUrl}/api/shops/${idShop}/category-shop`,
        type: 'get',
        dataType: 'json',
        contentType: 'application/json',
        data: {},
        success: function (res) {
            $('.list-menu').html('');
            res.data.forEach((val, index) => {
                let row = `<li class="menu-item">
                                <a href="">${val.name}</a>
                            </li>`;
                $('.list-menu').append(row);
            });
        }
    })
}


function OrderFood(id) {
    $.ajax({
        url: `${baseUrl}/api/products/${id}`,
        type: 'get',
        dataType: 'json',
        contentType: 'application/json',
        data: {},
        success: function (res) {
            console.log(res);
            $('.btnOrder').off('click');
            // sản phẩm không size
            if (res.data.productSizes.length == 0 && res.data.productOptions.length == 0) {
                var cart = localStorage.getItem('cart') == null ? [] : JSON.parse(localStorage.getItem('cart'));
                if (cart.filter(x => x.productId == id).length == 0) {
                    cart.push({
                        productId: id,
                        sizeId: null,
                        sizeName: '',
                        stock: 1,
                        price: res.data.price,
                        product: res.data
                    })
                    localStorage.setItem('cart', JSON.stringify(cart))
                    LoadListOrder();
                }
                else {
                    alert("Đã tồn tại trong giỏ hàng")
                }
            }
            else if (res.data.productSizes.length > 0) {
                let row = ``;
                res.data.productSizes.forEach((val, i) => {
                    row += `<div class="mt-2">
                                <input type="radio" ${i == 0 ? 'checked' : ''} name="ChooseSize" value="${val.size.id}" id="size_${val.size.id}">
                                <label for="size_${val.size.id}">Size ${val.size.name}</label>
                                <span class="ms-2"><b>${val.price} VND</b></span>
                            </div>`
                });
                $('.list-option').html('');
                $('.list-option').append(row);

                $('.btnOrder').on('click', function () {
                    let val = $('input[name=ChooseSize]').val();
                    var cart = localStorage.getItem('cart') == null ? [] : JSON.parse(localStorage.getItem('cart'));
                    if (cart.filter(x => x.productId === id).length == 0) {
                        cart.push({
                            productId: id,
                            sizeId: val,
                            sizeName: res.data.productSizes.filter(x => x.size.id == val)[0].name,
                            stock: 1,
                            price: res.data.productSizes.filter(x => x.size.id == val)[0].price,
                            product: res.data,
                        })
                        localStorage.setItem('cart', JSON.stringify(cart))
                        LoadListOrder();
                    }
                    else {
                        alert("Đã tồn tại trong giỏ hàng")
                    }
                    $('#exampleModalCenter').modal('hide');
                })

                $('#exampleModalCenter').modal('show');
            }
            else {
                let row = ``;
                res.data.productOptions.forEach((val, e) => {
                    row += `<div class="item-menu mt-1 d-flex justify-content-between">
                                <div class="d-flex">
                                    <img class="item-restaurant-img img-item19833" src="${baseUrl + val.image}" alt="">
                                    <div class="item-restaurant-name">
                                        <p class="name-item19833">${val.name}</p>
                                    </div>
                                </div>
                                <div class="price-btn d-flex">
                                    <div class="food-price">
                                        <p class="initial-price">${val.price} <sup>VND</sup></p>
                                    </div>
                                </div>
                            </div>`
                });
                $('.list-option').html('');
                $('.list-option').append(row);

                $('.btnOrder').on('click', function () {
                    var cart = localStorage.getItem('cart') == null ? [] : JSON.parse(localStorage.getItem('cart'));
                    if (cart.filter(x => x.productId == id).length == 0) {
                        cart.push({
                            productId: id,
                            sizeId: null,
                            sizeName: '',
                            stock: 1,
                            price: res.data.price,
                            product: res.data
                        })
                        localStorage.setItem('cart', JSON.stringify(cart))
                        LoadListOrder();
                    }
                    else {
                        alert("Đã tồn tại trong giỏ hàng")
                    }
                    $('#exampleModalCenter').modal('hide');

                })

                $('#exampleModalCenter').modal('show');
            }
        }
    })
}


function LoadListOrder() {
    var cart = localStorage.getItem('cart') == null ? [] : JSON.parse(localStorage.getItem('cart'));
    let row = ``;
    cart.forEach((val, i) => {
        row += `<div class="item-menu mt-1 d-flex justify-content-between align-items-center">
                    <div class="d-flex align-items-center">
                        <img class="item-restaurant-img img-item19833 me-1" src="${baseUrl + val.product.image}" alt="">
                        <div class="item-restaurant-name">
                            <p>${val.product.name}</p>
                            <p class="mt-1">${val.price}</p>
                            <p class="mt-1">${val.sizeName == undefined ? '' : val.sizeName}</p>
                        </div>
                    </div>
                    <div class="price-btn d-flex">
                        <button class="btn-order m-1 text-center" onClick="SubStock(${val.productId})"><i class="fas fa-minus"></i></button>
                        <div class="food-price m-1">
                            <p>${val.stock}</p>
                        </div>
                        <button class="btn-order m-1 text-center" onClick="AddStock(${val.productId})"><i class="fas fa-plus"></i></button>
                    </div>
                </div>`;
    })
    $('.list-order').html('');
    $('.list-order').append(row);

    const sum = cart.reduce((partial_sum, a) => partial_sum + a.price*a.stock, 0);
    $('.total-price').text(sum);
}


function AddStock(id) {
    let cart = JSON.parse(localStorage.getItem('cart'));
    var index = cart.findIndex(x=>x.productId == id);
    cart[index].stock++;
    localStorage.setItem('cart', JSON.stringify(cart));
    LoadListOrder();  
}

function SubStock(id) {
    let cart = JSON.parse(localStorage.getItem('cart'));
    var index = cart.findIndex(x=>x.productId == id);
    cart[index].stock--;
    if(cart[index].stock===0)
        cart.splice(index,1);
    localStorage.setItem('cart', JSON.stringify(cart));
    LoadListOrder();  
}

Order()
function Order(){
    $('.get-order').click(()=>{
        var cart = localStorage.getItem('cart') == null ? [] : JSON.parse(localStorage.getItem('cart'));
        if(cart.length==0){
            alert("Bạn cần đặt sản phẩm");
            return;
        }
        else{
              let mobie = prompt("Please enter your phone:", "");
              let address = prompt("Please enter your phone:", "");
              let note = prompt("Please enter your phone:", "");

              var request = {
                "cartItems": [
                    cart.map((val,key)=>{
                        return {
                            "productId": val.productId,
                            "quantity": val.stock,
                            "sizeId": val.sizeId
                          }
                    })
                ],
                "note": note,
                "orderType": "DELIVERY",
                "paymentMethod": "ATM_PAYMENT",
                "shipAddress": address,
                "shipMobile": mobie,
                "shipName": ""
              }
        }
    })
}