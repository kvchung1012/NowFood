import { createStore } from 'redux'

const initialState = {
  sidebarShow: true,
  isLogin: false,
  baseUrl : 'http://localhost:8081/'
}

const changeState = (state = initialState, { type, ...rest }) => {
  switch (type) {
    case 'set':
      return { ...state, ...rest }
    default:
      return state
  }
}

const store = createStore(changeState)
export default store
