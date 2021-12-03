import { React, useState,useEffect } from 'react'
import { useSelector } from 'react-redux';
import {CButton,CForm,CCol,CFormInput, CFormLabel, COffcanvasBody, CCloseButton, COffcanvasTitle, COffcanvas, COffcanvasHeader } from "@coreui/react"
import axios from 'axios'
import swal from 'sweetalert';

const CreateShopCategoryComponent = (props) => {
    const baseUrl = useSelector((state) => state.baseUrl);
    // eslint-disable-next-line react/prop-types
    const [visible,setVisible]= useState(props.data.visible)
    const [validated, setValidated] = useState(false)

    // eslint-disable-next-line react/prop-types
    const [size,setSize]= useState(props.data.data)

    const createsize = (event)=>{
        const form = event.currentTarget
        if (form.checkValidity() === false) {
          event.preventDefault()
          event.stopPropagation()
          setValidated(true)
        }
        else{
            axios({
                url: baseUrl+'api/shop-category',
                method: size.id===0?'post':'put',
                headers: {
                    "Authorization": "Bearer " + localStorage.getItem('token'),
                    "Content-Type": 'application/json'
                },
                data: size,
            }).then(res => {
                swal("Thành công");
                setSize({
                    id : 0,
                    code :'',
                    name :''
                });
                setValidated(false)
            }).catch(err => {
                swal("Đã có lỗi xảy ra");
            })
        }
    }

    useEffect(() => {
        // eslint-disable-next-line react/prop-types
        props.changeVisible(visible)
    }, [visible])

    useEffect(() => {
        // eslint-disable-next-line react/prop-types
        setVisible(props.data.visible)
        // eslint-disable-next-line react/prop-types
        setSize(props.data.data)
    }, [props])

    
    return(
        <div>
            <COffcanvas placement="end" visible={visible} onHide={()=>setVisible(false)} >
                <COffcanvasHeader>
                    <COffcanvasTitle>Thông tin bảng</COffcanvasTitle>
                    <CCloseButton className="text-reset" onClick={()=>setVisible(false)}/>
                </COffcanvasHeader>
                <COffcanvasBody>
                <CForm
                    className="row g-3 needs-validation"
                    noValidate
                    validated={validated}
                    onSubmit={createsize}
                >

                    <CCol md={12} className="mb-3">
                        <CFormLabel htmlFor="Code">Mã danh mục</CFormLabel>
                        <CFormInput type="text" 
                                    id="Code" 
                                    placeholder="Mã danh mục" 
                                    value={size.code}
                                    onChange={(e)=>setSize({...size,code : e.target.value})}
                                    required/>
                    </CCol>

                    <CCol md={12} className="mb-3">
                        <CFormLabel htmlFor="Name">Tên danh mục</CFormLabel>
                        <CFormInput type="text" id="Name" 
                                    placeholder="Tên danh mục" 
                                    value={size.name}
                                    onChange={(e)=>setSize({...size,name : e.target.value})}
                                    required/>
                    </CCol>
                    
                    <CCol md={12}>
                    <CButton color="primary" type="submit">
                        Cập nhật
                    </CButton>
                    </CCol>
                </CForm>
                    
                </COffcanvasBody>
            </COffcanvas>
        </div>)

}
export default CreateShopCategoryComponent