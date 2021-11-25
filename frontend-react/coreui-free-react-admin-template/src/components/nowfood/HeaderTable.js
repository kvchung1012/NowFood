import { cilPencil } from "@coreui/icons"
import CIcon from "@coreui/icons-react"
import { BsFillEyeFill,BsFillEyeSlashFill,BsPinAngleFill,BsPin,BsPinFill } from "react-icons/bs";
import {CFormInput, CButton, COffcanvasBody, CCloseButton, COffcanvasTitle, COffcanvas, COffcanvasHeader } from "@coreui/react"
import { React, useEffect, useState } from "react"
const HeaderTable = (data) => {
    const [header, setHeader] = useState(data.prop);
    const [keyWord, setKeyWord] = useState('');
    const [visible, setVisible] = useState(false);
    useEffect(() => {
        console.log(header)
        data.callbackHeader(header);
    }, [header])


    useEffect(() => {
        data.callbackKeyWord(keyWord);
    }, [keyWord])


    useEffect(() => {
        setHeader(data.prop)
    }, [data])


    const btnCreateClick = ()=>{
        data.btnCreateClick()
    }

    return (<div className="d-flex justify-content-end p-2 pe-0">

        <CFormInput type="text" className="w-25" placeholder="Nhập sau đó enter tìm kiếm" onKeyDown={(e)=>{ if(e.keyCode===13) setKeyWord(e.target.value)}}/>
        <CButton
            onClick={() => btnCreateClick()}
            color="primary"
            variant={"outline"}
            className="ms-1"
            size="sm">Thêm mới
        </CButton>
        <CButton
            onClick={() => setVisible(!visible)}
            color="primary"
            variant="outline"
            className="ms-1"
            size="sm">
            <CIcon icon={cilPencil} className="" />
        </CButton>
        <COffcanvas placement="end" visible={visible} onHide={() => setVisible(false)}>
            <COffcanvasHeader>
                <COffcanvasTitle>Thông tin bảng</COffcanvasTitle>
                <CCloseButton className="text-reset" onClick={() => setVisible(false)} />
            </COffcanvasHeader>
            <COffcanvasBody>
                {
                    header.map((data, i) => {
                        if (data.field !== 'Action')
                            return (
                                <div key={i}>
                                    <div className="d-flex">
                                        
                                        <div className="m-1">
                                            {
                                                data.pinned==="" || data.pinned==null?
                                                (<BsPin onClick={()=>{
                                                    data.pinned = "left"
                                                    setHeader([...header])
                                                }}/>):
                                                data.pinned==="left"?
                                                <BsPinFill onClick={()=>{
                                                    data.pinned = "right"
                                                    setHeader([...header])
                                                }}/>:
                                                <BsPinAngleFill onClick={()=>{
                                                    data.pinned = ""
                                                    setHeader([...header])
                                                }}/>
                                            }
                                        </div>

                                        <div className="m-1">
                                            {
                                                data.hide?(<BsFillEyeSlashFill onClick={()=>{
                                                    data.hide = false
                                                    setHeader([...header])
                                                }}/>):
                                                (<BsFillEyeFill onClick={()=>{
                                                    data.hide = true
                                                    setHeader([...header])
                                                }}/>)
                                            }
                                        </div>

                                        <span className="m-1 ms-2">{data.field}</span>
                                    </div>
                                    <hr/>
                                </div>
                            )
                    })
                }
            </COffcanvasBody>
        </COffcanvas>
    </div>)
}
export default HeaderTable