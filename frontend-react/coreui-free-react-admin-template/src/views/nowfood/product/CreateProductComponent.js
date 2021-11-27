import { React, useState,useEffect, useRef } from 'react'
import { useSelector } from 'react-redux';
import {CToaster,CToast,CToastBody,CToastHeader,CBadge,CButton,CForm,CCol,CFormInput, CFormLabel, CFormTextarea, CFormCheck, CFormSelect } from "@coreui/react"
import axios from 'axios'

const CreateProductComponent = () => {
    const baseUrl = useSelector((state) => state.baseUrl);
    const [formData,setFormData] = useState(null)
    const [listProducts,setListProducts] = useState([])
    const [listComboProducts,setListComboProducts] = useState([])

    const [listCategory,setListCategory] = useState([])
    const [listCategorySelected,setListCategorySelected] = useState([])
    
    const [listCategoryShop,setListCategoryShop] = useState([])
    const [listCategoryShopSelected,setListCategoryShopSelected] = useState([])
    
    const [listSize,setListSize] = useState([])

    const [currentSize,setCurrentSize] = useState({
        idSize : 0,
        stockInDay : 0,
        price : 0,
        sizeName : '',
    })

    const [listSizeSelected,setListSizeSelected] = useState([])

    const [visibleCombo,setVisibleCombo] = useState(false)
    const [product,setProduct] = useState({
        id : 0,
        name : '',
        price : 0,
        description :'',
        image : 'https://tcvn.info/Content/images/no-image.jpg',
        shopId:null,
        isMain : true,
        sizes : [],
        options : [],
        categories : [],
        shopCategories : []
    })
    
    const inputFile = useRef(null) 
    const [toast, addToast] = useState(0)
    const toaster = useRef()
    const exampleToast = (
        <CToast title="CoreUI for React.js " autohide={true} visible={true} delay={3000}>
            <CToastHeader close>
            <svg
                className="rounded me-2"
                width="20"
                height="20"
                xmlns="http://www.w3.org/2000/svg"
                preserveAspectRatio="xMidYMid slice"
                focusable="false"
                role="img"
            >
            <rect width="100%" height="100%" fill="red"></rect>
            </svg>
            <strong className="me-auto">Cảnh báo</strong>
            <small>1 min ago</small>
            </CToastHeader>
            <CToastBody>Chọn rồi thì thôi.</CToastBody>
        </CToast>
    )
    const chooseImage = ()=>{
        inputFile.current.click()
    }

    const getFileFromDom = (e)=>{
        debugger
        const file = e.target.files[0];
        const objectURL =window.URL.createObjectURL(file);
        setProduct({...product,image:objectURL})
        setFormData(file)
    }

    const selectProductCombo = (e)=>{
        if(e.target.value==0) return;
        if(listComboProducts.filter(x=>x.id == e.target.value).length===0){
            let records = listProducts.filter(x=>x.id==e.target.value);
            if(records.length>0)
                setListComboProducts([...listComboProducts,records[0]])
        }
        else{
            addToast(exampleToast)
        }
    }

    const selectCategory = (e)=>{
        console.log(e);
        if(e.target.value==0) return;
        if(listCategorySelected.filter(x=>x.id == e.target.value).length===0){
            let records = listCategory.filter(x=>x.id==e.target.value);
            if(records.length>0)
                setListCategorySelected([...listCategorySelected,records[0]])
        }
        else{
            addToast(exampleToast)
        }
    }

    const selectCategoryShop = (e)=>{
        if(e.target.value == 0) return;
        if(listCategoryShopSelected.filter(x=>x.id == e.target.value).length===0){
            let records = listCategoryShop.filter(x=>x.id==e.target.value);
            if(records.length>0)
                setListCategoryShopSelected([...listCategoryShopSelected,records[0]])
        }
        else{
            addToast(exampleToast)
        }
    }

    const clickChooseSize = ()=>{
        if(currentSize.idSize==0 || currentSize.stockInDay == 0 || currentSize.price == 0 || currentSize.stockInDay == "" || currentSize.price == ""){
            alert("Điền đầy đủ vào");
            return
        }
        else{
            let check = listSizeSelected.filter(x=>x.idSize == currentSize.idSize).length == 0
            if(check){
                setListSizeSelected([...listSizeSelected,currentSize]);
            }
            else{
                addToast(exampleToast)
            }
        }
    }


    const createProduct = ()=>{
        if(formData === null){
            alert("Bạn cần chọn ảnh");
            return;
        }
        if(listCategoryShopSelected.length==0){
            alert("Bạn cần danh mục sản phẩm shop");
            return;
        }
        if(listCategorySelected.length==0){
            alert("Bạn cần danh mục sản phẩm");
            return;
        }
        if(visibleCombo && listComboProducts.length == 0){
            alert("Bạn cần điền combo");
            return;
        }
        if(!visibleCombo && listSizeSelected.length == 0){
            alert("Bạn cần chọn size cho món");
            return;
        }
        if(product.name === '' || product.price === ''){
            alert("Điền đầy đủ thông tin sản phẩm");
            return;
        }

        setProduct({...product,
                    categories : listCategorySelected.map((val,key)=>val.id),
                    shopCategories : listCategoryShopSelected.map((val,key)=>val.id),
                    options : visibleCombo?listComboProducts.map((val,key)=>val.id):[],
                    sizes : !visibleCombo?listSizeSelected.map((val,key)=>{return{
                        stockInDay : val.stockInDay,
                        price : val.price,
                        idSize : val.idSize
                    }}):[],
                    })
        let data = new FormData();
        data.append('imagesUrl', formData);
        data.append('form', JSON.stringify(product));
        console.log(data)
        axios({
            url: baseUrl+'api/products',
            method: 'post',
            headers: {
                "Authorization": "Bearer " + localStorage.getItem('token'),
                "Content-Type": 'multipart/form-data'
            },
            data: data,
        }).then(res => {
            console.log(res);
        }).catch(err => {
            console.log(err);
            alert("Đã có lỗi xảy ra");
        })
    }

    useEffect(() => {
        console.log("priduct",product)
    }, [product])


    // call data
    useEffect(() => {
        axios.get(baseUrl+'api/products',{
            "Authorization": "Bearer " + localStorage.getItem('token'),
            "Content-Type": 'application/json'
        }).then(res=>{
            console.log(res);
            setListProducts(res.data.data)
        })

        axios.get(baseUrl+'api/categories',{
            "Authorization": "Bearer " + localStorage.getItem('token'),
            "Content-Type": 'application/json'
        }).then(res=>{
            console.log(res);
            setListCategory(res.data.data)
        })

        axios.get(baseUrl+'api/shop-category',{
            "Authorization": "Bearer " + localStorage.getItem('token'),
            "Content-Type": 'application/json'
        }).then(res=>{
            console.log(res);
            setListCategoryShop(res.data.data)
        })


        axios.get(baseUrl+'api/sizes',{
            "Authorization": "Bearer " + localStorage.getItem('token'),
            "Content-Type": 'application/json'
        }).then(res=>{
            console.log(res);
            setListSize(res.data.data)
        })
    }, [])
    
    return(
        <div>
             <CForm className="row g-3 needs-validation"
                    noValidate>
                    <CCol md={9} className="row">
                        <CCol md={6} >
                            <CFormLabel htmlFor="Name">Tên sản phẩm</CFormLabel>
                            <CFormInput type="text" 
                                    id="Name" 
                                    value={product.name}
                                    onChange={(e)=>setProduct({...product,name : e.target.value})}
                                    required/>
                        </CCol>

                        <CCol md={6} >
                            <CFormLabel htmlFor="Price">Giá sản phẩm</CFormLabel>
                            <CFormInput type="number" 
                                    id="Price" 
                                    value={product.price}
                                    onChange={(e)=>setProduct({...product,price : e.target.value})}
                                    required/>
                        </CCol>

                        <CCol md={12}>
                            <CFormLabel htmlFor="Description">Mô tả sản phẩm</CFormLabel>
                            <CFormTextarea id="Description" rows="3"></CFormTextarea>
                        </CCol>

                        {/* danh mục */}
                        <CCol md={12} className="mt-3">
                                <CFormSelect aria-label="select" onChange={(e)=>selectCategory(e)}>
                                    <option value={0}>Danh mục món ăn</option>
                                    {
                                        listCategory.map((val,key)=><option key={key} value={val.id}>{val.name}</option>)
                                    }
                                </CFormSelect>

                                <div className="mt-2">
                                    {
                                        listCategorySelected.map((val,key)=><>
                                            <CButton variant="outline" color="primary me-2 mt-2 position-relative">
                                                {val.name}
                                                <CBadge className="border border-light p-2" 
                                                        color="danger" position="top-end" 
                                                        shape="rounded-circle" 
                                                        onClick={()=>setListCategorySelected(listCategorySelected.filter(x=>x.id !== val.id))}>
                                                    <span className="visually-hidden">x</span>
                                                </CBadge>
                                            </CButton>
                                        </>)
                                    }
                                </div>
                        </CCol>


                        {/* danh mục shop*/}
                        <CCol md={12} className="mt-3">
                                <CFormSelect aria-label="select" onChange={(e)=>selectCategoryShop(e)}>
                                    <option value={0}>Danh mục món ăn quán</option>
                                    {
                                        listCategoryShop.map((val,key)=><option key={key} value={val.id}>{val.name}</option>)
                                    }
                                </CFormSelect>

                                <div className="mt-2">
                                    {
                                        listCategoryShopSelected.map((val,key)=><>
                                            <CButton variant="outline" val={key} color="primary me-2 mt-2 position-relative">
                                                {val.name}
                                                <CBadge className="border border-light p-2" 
                                                        color="danger" position="top-end" 
                                                        shape="rounded-circle" 
                                                        onClick={()=>setListCategoryShopSelected(listCategoryShopSelected.filter(x=>x.id !== val.id))}>
                                                    <span className="visually-hidden">x</span>
                                                </CBadge>
                                            </CButton>
                                        </>)
                                    }
                                </div>
                        </CCol>

                        {/* combo */}
                        <CCol md={12} className="mt-3">
                            <CFormCheck label="Là món chính?" checked={product.isMain} onChange={(e)=>setProduct({...product,isMain : !product.isMain})}/>

                            <CFormCheck id="flexCheckDefault" label="Sản phẩm combo ?" checked={visibleCombo} onChange={(e)=>setVisibleCombo(!visibleCombo)}/>
                            {
                                visibleCombo?
                            <div>
                                <CFormSelect aria-label="select" onChange={(e)=>selectProductCombo(e)}>
                                    <option value={0}>Chọn combo</option>
                                    {
                                        listProducts.map((val,key)=><option key={key} value={val.id}>{val.name}</option>)
                                    }
                                </CFormSelect>

                                <div className="mt-2">
                                    {
                                        listComboProducts.map((val,key)=><>
                                            <CButton variant="outline" key={key} color="primary me-2 mt-2 position-relative">
                                                {val.name}
                                                <CBadge className="border border-light p-2" color="danger" position="top-end" shape="rounded-circle" onClick={()=>setListComboProducts(listComboProducts.filter(x=>x.id !== val.id))}>
                                                    <span className="visually-hidden">x</span>
                                                </CBadge>
                                            </CButton>
                                        </>)
                                    }
                                </div>
                            </div>:
                            // kích thước
                            <div className="row mt-3">
                                <CCol md={12}>
                                    <CFormSelect aria-label="select" onChange={(e)=>setCurrentSize({...currentSize,sizeName:e.target.options[e.target.selectedIndex].text,idSize:e.target.value})}>
                                        <option value={0}>Chọn size</option>
                                        {
                                            listSize.map((val,key)=><option key={key} value={val.id}>{val.name}</option>)
                                        }
                                    </CFormSelect>
                                </CCol>

                                <CCol md={12} className="row mt-3">
                                    <CCol md={4} >
                                        <CFormInput type="number" 
                                                //value={product.name}
                                                placeholder="Nhập đơn giá"
                                                value ={currentSize.stockInDay}
                                                onChange={(e)=>setCurrentSize({...currentSize,stockInDay :e.target.value})}
                                                maxLength={10}
                                                required/>
                                    </CCol>   
                                    <CCol md={4} >
                                        <CFormInput type="number"
                                                maxLength={4} 
                                                //value={product.name}
                                                onChange={(e)=>setCurrentSize({...currentSize,price:e.target.value})}
                                                placeholder="Nhập số lượng món hôm nay"
                                                required/>
                                    </CCol>

                                    <CCol md={4}>
                                        <CButton variant="outline" color="primary" onClick={()=>clickChooseSize()}>
                                            Thêm tùy chọn
                                        </CButton>
                                    </CCol>

                                    <div className="mt-2">
                                    {
                                        listSizeSelected.map((val,key)=><>
                                            <CButton variant="outline" key={key} color="primary me-2 mt-2 position-relative">
                                                {val.sizeName +" - "+ val.price +"VND - "+ val.stockInDay}
                                                <CBadge className="border border-light p-2" 
                                                        color="danger" position="top-end" 
                                                        shape="rounded-circle" 
                                                        onClick={()=>setListSizeSelected(listSizeSelected.filter(x=>x.idSize !== val.idSize))}>
                                                    <span className="visually-hidden">x</span>
                                                </CBadge>
                                            </CButton>
                                        </>)
                                    }
                                </div>
                                </CCol>
                            </div>
                            }
                        </CCol>
                    </CCol>


                    <CCol md={3} className="mt-0">
                        <CFormLabel htmlFor="Code">Mã danh mục</CFormLabel>
                        <img src={product.image} 
                                className="w-100 border rounded" 
                                style={{objectFit:'cover',height:220}} 
                                alt="" onClick={()=>chooseImage()}/>
                        <input type='file' id='file' ref={inputFile} style={{display: 'none'}} onChange={(e)=>getFileFromDom(e)}/>
                    </CCol>
                </CForm>
                <CButton color="success" className="mt-3" onClick={()=>createProduct()}>
                    Thêm mới sản phẩm
                </CButton>
                
                
                <CToaster ref={toaster} push={toast} placement="top-end" autohide={true} />
        </div>)
}
export default CreateProductComponent