import React, { useState, useEffect } from 'react'
import { useSelector, useDispatch } from 'react-redux'
import { Link } from 'react-router-dom'
import { Redirect } from 'react-router'
import { useHistory } from "react-router-dom"
import {
  CButton,
  CCard,
  CCardBody,
  CCardGroup,
  CCol,
  CContainer,
  CForm,
  CFormInput,
  CInputGroup,
  CInputGroupText,
  CRow,
  CFormFeedback,
} from '@coreui/react'
import CIcon from '@coreui/icons-react'
import { cilLockLocked, cilUser } from '@coreui/icons'
import axios from 'axios'

const Login = () => {
  const history = useHistory()

  const dispatch = useDispatch()
  const isLogin = useSelector((state) => state.isLogin)
  const baseUrl = useSelector((state) => state.baseUrl);

  const [userName, setUserName] = useState('admin12345')
  const [passWord, setPassWord] = useState('')
  const [validated, setValidated] = useState(false)

  const ourRequest = axios.CancelToken.source()

  const handleSubmit = (event) => {
    setValidated(true)
    const form = event.currentTarget
    if (form.checkValidity() === false) {
      event.preventDefault()
      event.stopPropagation()
    } else {
      var obj = {
        password: passWord,
        username: userName
      }
      axios.post(baseUrl + "api/auth/login",obj).then(res => {
        console.log(res);
        if(res.data.code===200){
          localStorage.setItem("token", res.data.data.token);
          localStorage.setItem("userName", res.data.data.username);
          dispatch({ type: 'set', isLogin: true })
          history.push("/")
        }
        else{
          alert(res.data.message);
        }

      }).catch(err => {
        console.log(err);
        //alert("Mật khẩu hoặc tài khoản không đúng");
      })
    }
    setValidated(true)
  }

  useEffect(() => {
    return () => {
      ourRequest.cancel()
    }
  })


  return isLogin ? (
    <Redirect to="/" />
    ) : (
    <div className="bg-light min-vh-100 d-flex flex-row align-items-center">
      <CContainer>
        <CRow className="justify-content-center">
          <CCol md={8}>
            <CCardGroup>
              <CCard className="p-4">
                <CCardBody>
                  <CForm
                    className="row g-3 needs-validation"
                    noValidate
                    validated={validated}
                    onSubmit={handleSubmit}
                  >
                    <h1>Login</h1>
                    <p className="text-medium-emphasis">Sign In to your account</p>
                    <CInputGroup className="mb-3">
                      <CInputGroupText>
                        <CIcon icon={cilUser} />
                      </CInputGroupText>
                      <CFormInput
                        required
                        placeholder="Username"
                        value={userName}
                        autoComplete="username"
                        onChange={(e) => setUserName(e.target.value)}
                      />
                      <CFormFeedback invalid>Bạn cần nhập tài khoản</CFormFeedback>
                    </CInputGroup>
                    <CInputGroup className="mb-4">
                      <CInputGroupText>
                        <CIcon icon={cilLockLocked} />
                      </CInputGroupText>
                      <CFormInput
                        required
                        type="password"
                        value={passWord}
                        placeholder="Password"
                        autoComplete="current-password"
                        onChange={(e) => setPassWord(e.target.value)}
                      />
                      <CFormFeedback invalid>Bạn cần nhập mật khẩu</CFormFeedback>
                    </CInputGroup>
                    <CRow>
                      <CCol xs={6}>
                        <CButton color="primary" className="px-4" type="submit">
                          Login
                        </CButton>
                      </CCol>
                      <CCol xs={6} className="text-right">
                        <CButton color="link" className="px-0">
                          Forgot password?
                        </CButton>
                      </CCol>
                    </CRow>
                  </CForm>
                </CCardBody>
              </CCard>
              <CCard className="text-white bg-primary py-5" style={{ width: '44%' }}>
                <CCardBody className="text-center">
                  <div>
                    <h2>Sign up</h2>
                    <p>
                      Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod
                      tempor incididunt ut labore et dolore magna aliqua.
                    </p>
                    <Link to="/register">
                      <CButton color="primary" className="mt-3" active tabIndex={-1}>
                        Register Now!
                      </CButton>
                    </Link>
                  </div>
                </CCardBody>
              </CCard>
            </CCardGroup>
          </CCol>
        </CRow>
      </CContainer>
    </div>
  )
}

export default Login
