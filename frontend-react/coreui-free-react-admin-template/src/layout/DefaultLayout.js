import React from 'react'
import { useSelector } from 'react-redux'
import { AppContent, AppSidebar, AppFooter, AppHeader } from '../components/index'
import { Redirect } from 'react-router'

const DefaultLayout = () => {
  const isLogin = useSelector((state) => state.isLogin)

  return isLogin ? (
    <div>
      <AppSidebar />
      <div className="wrapper d-flex flex-column min-vh-100 bg-light">
        <AppHeader />
        <div className="body flex-grow-1 px-3">
          <AppContent />
        </div>
        <AppFooter />
      </div>
    </div>
  ) : (
    <Redirect to="/login" />
  )
}

export default DefaultLayout
