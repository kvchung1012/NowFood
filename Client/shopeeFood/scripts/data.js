var requestProduct = {
    categoryId: [],
    categoryShopId: [],
    isMain: true,
    keyword: "",
    pageIndex: 1,
    pageSize: 12,
    desc : "totalOrder",
  }

// call data
GetCategories();
GetProduct();
function GetCategories(){
    $.ajax({
        url : `${baseUrl}/api/categories`,
        type:'get',
        dataType : 'json',
        data:{
            
        },
        success : function(res){
            $('.list-category').append('');
            res.data.forEach((val,index)=>{
                let row =  `<li class="pt-1">
                                <input type="checkbox" name="categoryId" value="${val.id}" onChange="ChangeCategory(this)">
                                <label for="cream">${val.name}</label>
                            </li>`;
                $('.list-category').append(row);
            })
        }
    })
}



function GetProduct(){
    $.ajax({
        url : `${baseUrl}/api/products/search-adv`,
        type:'post',
        dataType : 'json',
        contentType:'application/json',
        data: JSON.stringify(requestProduct)
        ,
        success : function(res){
            console.log("p",res)
            $('.list-product').html('');
            res.data.content.forEach((val,index)=>{
                    let row =  `<div class="col-3 item-food" id="f01">
                                    <a href="/detail.html?Id=${val.shop.id}&productId=${val.id}" class="restaurent">
                                        <div class="status-circle"></div>
                                        <img class="image-food" src="${baseUrl+val.image}" alt="" />
                                        <div class="intro-food">
                                            <div class="name-food">
                                                <span>${val.name}</span>
                                            </div>
                                            <p class="info-address" title="59 Khương Đình,Hà Nội">
                                                ${val.shop.shopAddress}
                                            </p>
                                            
                                            <p class="info-address text-center text-dark ${val.totalOrder>0?"":"d-none"}">
                                                Đã được đặt ${val.totalOrder} lần
                                            </p>
                                        </div>
                                    </a>
                                </div>`;
                $('.list-product').append(row);
            })


            // phân trang tại đây
            $('.pagination').html('');            
            if(res.data.totalPages<=3){
                for (let index = 1; index <= res.data.totalPages; index++) {
                    let row  = `<li class="page-item">
                                    <a class="page-link" onClick="ChangePageIndex(${index})">${index}</a>
                                </li>`;
                 
                    $('.pagination').append(row);            
                }
            }
            else if(requestProduct.pageIndex<=2){
                for (let index = 1; index <= 3; index++) {
                    let row  = `<li class="page-item">
                                    <a class="page-link" onClick="ChangePageIndex(${index})">${index}</a>
                                </li>`;
                    $('.pagination').append(row);            
                }
            }
            else if(requestProduct.pageIndex>=res.data.totalPages-3){
                for (let index = requestProduct.pageIndex; index <= res.data.totalPages; index++) {
                    let row  = `<li class="page-item">
                                    <a class="page-link" onClick="ChangePageIndex(${index})">${index}</a>
                                </li>`;
                    $('.pagination').append(row);            
                }
            }
            else{
                for (let index = requestProduct.pageIndex-1; index <= requestProduct.pageIndex+1; index++) {
                    let row  = `<li class="page-item">
                                    <a class="page-link" onClick="ChangePageIndex(${index})">${index}</a>
                                </li>`;
                    $('.pagination').append(row);            
                }
            }
        }
    })
}



// filter event
function ChangeCategory(e){
    let val = $(e).val();
    if(requestProduct.categoryId.filter(x=>x==val).length==0){
        requestProduct.categoryId.push(val);
    }
    else{
        requestProduct.categoryId.splice(requestProduct.categoryId.findIndex(x=>x==val),1)
    }
    requestProduct.pageIndex = 1;
    GetProduct();
}


function ChangePageIndex(index){
    requestProduct.pageIndex = index;
    GetProduct();
}