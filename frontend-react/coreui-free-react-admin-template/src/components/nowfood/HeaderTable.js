import { cilPencil } from "@coreui/icons"
import CIcon from "@coreui/icons-react"
import {CFormInput, CButton, COffcanvasBody, CCloseButton, COffcanvasTitle, COffcanvas, COffcanvasHeader, CFormCheck } from "@coreui/react"
import { React, useEffect, useState } from "react"
const HeaderTable = (data) => {
    const [header, setHeader] = useState(data.prop);
    const [keyWord, setKeyWord] = useState('');
    const [visible, setVisible] = useState(false);
    useEffect(() => {
        data.callbackHeader(header);
    }, [header])


    useEffect(() => {
        data.callbackKeyWord(keyWord);
    }, [keyWord])


    useEffect(() => {
        setHeader(data.prop)
    }, [data])
    return (<div className="d-flex justify-content-end p-2 pe-0">

        <CFormInput type="text" className="w-25" placeholder="Nhập sau đó enter tìm kiếm" onKeyDown={(e)=>{ if(e.keyCode===13) setKeyWord(e.target.value)}}/>

        <CButton
            onClick={() => setVisible(!visible)}
            color=""
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
                                    <div className="d-flex justify-content-lg-between">
                                        <CFormCheck label={data.field} checked={!data.hide} onChange={() => {
                                            data.hide = !data.hide
                                            setHeader([...header])
                                        }} />
                                        <div>
                                        {
                                            ["left", "", "right"].map((o, j) =>
                                                <CFormCheck key={j} inline type="radio" name={data.field} value={o} label={o === "" ? "none" : o} checked={data.pinned === o} onChange={(e) => {
                                                    data.pinned = e.target.value;
                                                    setHeader([...header])
                                                }} />)
                                        }
                                        </div>
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