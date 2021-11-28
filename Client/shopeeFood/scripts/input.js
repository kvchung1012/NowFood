$(document).ready(function() {
    var cart = [];
    var totalCount = 0;
    if (JSON.parse(localStorage.getItem("item")) == []) {
        cart = [];
        totalCount = 1
    } else {
        cart = JSON.parse(localStorage.getItem("item"));
        cart.forEach(item => {
            totalCount += item.count;
        })
    }
    $(".cart-count").text(totalCount);
    var check = 0;
    var listData = [{
            id: "f01",
            src: "https://images.foody.vn/res/g2/19069/prof/s280x175/foody-mobile-xoi-ran2-jpg-227-635775001980948985.jpg",
            name: "Bún cá Trung Vũ",
            address: "14 - 16 Ngõ 81 Láng Hạ, P. Thành Công, Ba Đình, Hà Nội",
            saleForm: "FREESHIP"
        },
        {
            id: "f02",
            src: "https://images.foody.vn/res/g101/1003994/prof/s280x175/file_restaurant_photo_ml9d_16212-bf0dcbeb-210517102640.jpg",
            name: "Bún cá Trung Vũ",
            address: "14 - 16 Ngõ 81 Láng Hạ, P. Thành Công, Ba Đình, Hà Nội",
            saleForm: "FREESHIP"
        },
        {
            id: "f03",
            src: "https://images.foody.vn/res/g106/1050043/prof/s280x175/foody-upload-api-foody-mobile-12-201130111421.jpg",
            name: "Bún cá Trung Vũ",
            address: "14 - 16 Ngõ 81 Láng Hạ, P. Thành Công, Ba Đình, Hà Nội",
            saleForm: "FREESHIP"
        },
        {
            id: "f04",
            src: "https://images.foody.vn/res/g9/81938/prof/s280x175/foody-mobile-bm-jpg-301-635972888258628204.jpg",
            name: "Kim Oanh - Bánh Mì Bơ Mật Ong & Thịt Xiên Nướng",
            address: "127 Đặng Tiến Đông, Đống Đa, Hà Nội",
            saleForm: "FREESHIP"
        },
        {
            id: "f05",
            src: "https://images.foody.vn/res/g9/81938/prof/s280x175/foody-mobile-bm-jpg-301-635972888258628204.jpg",
            name: "Kim Oanh - Bánh Mì Bơ Mật Ong & Thịt Xiên Nướng",
            address: "127 Đặng Tiến Đông, Đống Đa, Hà Nội",
            saleForm: "FREESHIP"
        },
    ]

    function render() {
        var html = listData.map(item => {
            return `  <div class="col-3 item-food" id=${item.id}>
                        <a href="" class="restaurent">
                            <div class="status-circle"></div>
                            <img class="image-food" src=${item.src} alt="">
                            <div class="intro-food">
                                <div class="name-food" title="${item.name}">
                                    <span>${item.name}</span>
                                </div>
                                <p class="info-address" title="59 Khương Đình,Hà Nội">
                                   ${item.address}
                                </p>
                                <div class="sale-food d-flex align-items-center">
                                    <i class="fas fa-tag pe-1"></i>
                                    <span>${item.saleForm}</span>
                                </div>
                            </div>
                          </a>
                 </div>`
        }).join("");
        $(".row-res").append(html)
    }
    render();
    //mo phan tim kiem
    searchRes = () => {
        console.log($("#inp-search").focus());
        $(".search-banner").addClass("show-search");
        $(".action-search-res").addClass("show-action-search");
    }
    $("#btn-close,.overlay").click(function() {
        $(".search-banner").removeClass("show-search");
        $(".action-search-res").removeClass("show-action-search");
    })


    $(".list-restauren-address").hide();
    showAdress = () => {
        (check == 0) ? check = 1: check = 0;
        (check == 1) ? $(".list-restauren-address").show(): $(".list-restauren-address").hide();
        console.log(check);
    }
    $(".main").click(function() {
        $(".list-restauren-address").hide();
        check = 0;
    })

    //xem đơn hàng 
    $(".btn-order-b").click(function() {
        $(".black-over").addClass("show-black-over");
        $(".notification").addClass("show-notification");
        $(".close-notifi").addClass("show-close-notifi");
    })
    $(".btn-ok,.black-over,.close-notifi ").click(function() {
        $(".black-over").removeClass("show-black-over");
        $(".notification").removeClass("show-notification");
        $(".close-notifi").removeClass("show-close-notifi");
    })

    //giỏ hàng 

    orderFood = function Food(id) {
        totalCount = 0;
        let nameFood = $(".name-" + id).text(),
            price = $(".current-price-" + id).text(),
            count = 1,
            obj = { id, nameFood, price, count };
        if (cart.length == 0)
            cart.push(obj)
        else {
            cart.map(item => {
                if (item.id == id)
                    item.count += count
                else
                    cart.push(obj)
            })
            cart.forEach(item => {
                totalCount += item.count;
            })
        }
        $(".cart-count").text(totalCount);
        localStorage.setItem("item", JSON.stringify(cart));
        (JSON.parse(localStorage.getItem("item")) == []) ? cart = []: cart = JSON.parse(localStorage.getItem("item"));
        var offset = $("#btn-order-" + id).offset();
        var top = offset.top - 20;
        var left = offset.left + 8;
        $('<div class="fly-item">1</div>').appendTo('body').css({
            "top": top,
            "left": left,
        })
        setTimeout(function() {
            $(".fly-item").css({
                "top": $(".txt-acount-cart").offset().top - 10,
                "left": $(".txt-acount-cart").offset().left + 30,
                "opacity": "0",
                "visibility": "hidden"
            })
            setTimeout(function() {
                $(document).find(".fly-item").remove();
            }, 1100)
        }, 100);
    }
});