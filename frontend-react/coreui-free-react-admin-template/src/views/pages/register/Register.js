import React,{useState,useEffect} from 'react'
import { useSelector } from 'react-redux'
import {
  CButton,
  CCard,
  CCardBody,
  CCol,
  CContainer,
  CForm,
  CFormFeedback,
  CFormInput,
  CInputGroup,
  CInputGroupText,
  CRow,
} from '@coreui/react'
import CIcon from '@coreui/icons-react'
import { cilLockLocked, cilUser } from '@coreui/icons'

import axios from 'axios'

const Register = () => {
  const baseUrl = useSelector((state) => state.baseUrl);
  const [fullName,setFullName] = useState('')
  const [phone,setPhone] = useState('')
  const [userName,setUserName] = useState('')
  const [email,setEmail] = useState('')
  const [password,setPassWord] = useState('')
  const [passwordConfirm,setPassWordConfirm] = useState('')

  const [validated, setValidated] = useState(false)

  const ourRequest = axios.CancelToken.source()

  const handleSubmit = (event) => {
    setValidated(true)
    const form = event.currentTarget
    if (form.checkValidity() === false) {
      event.preventDefault()
      event.stopPropagation()

    }
     else {
      if(password === passwordConfirm){
        var obj = {
          fullName :fullName,
          username: userName,
          password: password,
          email : email,
          phoneNumber : phone
        }
        axios.post(baseUrl + "api/auth/register",obj, {
          cancelToken: ourRequest.token,
        }).then(res => {
          console.log(res.data);
          if(res.data.code===200){
              alert("Đăng ký thành công vui lòng trở lại trang đăng nhập")
          }
          else{
              alert(res.data.message)
          }
        }).catch(err => {
          console.log(err);
          alert("Thông tin còn thiếu");
        })
      }
      else{
        alert("Mật khẩu xác nhận không đúng")
      }
     
    }

    
  }

  useEffect(() => {
    return () => {
      ourRequest.cancel()
    }
  })


  return (
    <div className="bg-light min-vh-100 d-flex flex-row align-items-center">
      <CContainer>
        <CRow className="justify-content-center">
          <CCol md={9} lg={7} xl={6}>
            <CCard className="mx-4">
              <CCardBody className="p-4">
                <CForm  className="row g-3 needs-validation"
                    noValidate
                    validated={validated}
                    onSubmit={handleSubmit}>
                  <h1>Register</h1>
                  <p className="text-medium-emphasis">Create your account</p>
                  <CInputGroup className="mb-3">
                    <CInputGroupText>
                      <CIcon icon={cilUser} />
                    </CInputGroupText>
                    <CFormInput 
                        required
                        placeholder="Full Name" 
                        value={fullName}
                        onChange={(e) => setFullName(e.target.value)}
                       />
                      <CFormFeedback invalid>Bạn cần nhập tên của bạn</CFormFeedback>

                  </CInputGroup>
                  <CInputGroup className="mb-3">
                    <CInputGroupText>
                      <CIcon icon={cilUser} />
                    </CInputGroupText>
                    <CFormInput 
                        required
                        placeholder="Username" 
                        value={userName}
                        onChange={(e) => setUserName(e.target.value)}
                       />
                      <CFormFeedback invalid>Bạn cần nhập tài khoản</CFormFeedback>

                  </CInputGroup>
                  <CInputGroup className="mb-3">
                    <CInputGroupText>@</CInputGroupText>
                    <CFormInput
                        required 
                        placeholder="Email" 
                        onChange={(e) => setEmail(e.target.value)}
                      />
                      <CFormFeedback invalid>Bạn cần nhập email</CFormFeedback>
                  </CInputGroup>

                  <CInputGroup className="mb-3">
                    <CInputGroupText>
                      <CIcon icon={cilUser} />
                    </CInputGroupText>
                    <CFormInput 
                        required
                        placeholder="Phone number" 
                        autoComplete="phone number"
                        value={phone}
                        onChange={(e) => setPhone(e.target.value)}
                       />
                      <CFormFeedback invalid>Bạn cần nhập số điện thoại của bạn</CFormFeedback>
                  </CInputGroup>

                  <CInputGroup className="mb-3">
                    <CInputGroupText>
                      <CIcon icon={cilLockLocked} />
                    </CInputGroupText>
                    <CFormInput
                    required
                      type="password"
                      placeholder="Password"
                      autoComplete="new-password"
                      onChange={(e) => setPassWord(e.target.value)}
                    />

                  <CFormFeedback invalid>Bạn cần nhập mật khẩu</CFormFeedback>

                  </CInputGroup>
                  <CInputGroup className="mb-4">
                    <CInputGroupText>
                      <CIcon icon={cilLockLocked} />
                    </CInputGroupText>
                    <CFormInput
                      required
                      type="password"
                      placeholder="Repeat password"
                      autoComplete="new-password"
                      onChange={(e) => setPassWordConfirm(e.target.value)}

                    />
                  <CFormFeedback invalid>Bạn cần nhập mật khẩu</CFormFeedback>

                  </CInputGroup>
                  <div className="d-grid">
                    <CButton color="success" type="submit">Create Account</CButton>
                  </div>
                </CForm>
              </CCardBody>
            </CCard>
          </CCol>
        </CRow>
      </CContainer>
    </div>
  )
}

export default Register
