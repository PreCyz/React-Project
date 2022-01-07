import { useEffect } from 'react'
import './App.css'
import Dashboard from './pages/dashboard'
import Auth from './pages/auth'
import './scss/style.scss'
import {
  BrowserRouter as Router,
  Routes,
  Route,
  Link,
  useNavigate,
} from 'react-router-dom'
import { getToken } from './utils/jwt'
import 'react-toastify/dist/ReactToastify.css'

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Auth />}></Route>
        <Route path="/dashboard" element={<Dashboard />}></Route>
      </Routes>
    </Router>
  )
}

export default App
