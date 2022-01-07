import React, { useEffect } from 'react'
import styles from '../scss/modules/Auth.module.scss'
import { register, signIn } from '../api/auth'
import { getBookings } from '../api/booking'
import { setToken, getToken } from '../utils/jwt'
import { useNavigate } from 'react-router'
import { toast, ToastContainer } from 'react-toastify'

export default function Auth() {
  const [mode, setMode] = React.useState('login')
  const [loginInput, setLoginInput] = React.useState('')
  const [passwordInput, setPasswordInput] = React.useState('')
  let navigate = useNavigate()
  useEffect(() => {
    if (getToken()) {
      navigate('/dashboard')
    }
  }, [])
  const handleRegister = async () => {
    try {
      await register(loginInput, passwordInput)
      setLoginInput('')
      setPasswordInput('')
      alert('Registration successful, you can now log in to application')
    } catch (error) {
      console.log(error)
      toast.error('Registration failed', {
        position: 'top-left',
        autoClose: 5000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
      })
    }
  }
  const handleSignIn = async () => {
    try {
      const response = await signIn(loginInput, passwordInput)
      setToken(await response.data.jwttoken)
      navigate('/dashboard')
    } catch (err) {
      toast.error('login failed', {
        position: 'top-left',
        autoClose: 5000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
      })
      console.log(err)
    }
  }
  return (
    <div>
      <ToastContainer />
      {mode === 'login' ? (
        <div className={styles.FormWrapper}>
          <div className={`input-group ${styles.Flex}`}>
            <label>Login</label>
            <input
              type="text"
              className="form-control"
              onChange={(e) => setLoginInput(e.target.value)}
              value={loginInput}
            />
          </div>
          <div className={`input-group ${styles.Flex}`}>
            <label>Password</label>
            <input
              type="password"
              className="form-control"
              onChange={(e) => setPasswordInput(e.target.value)}
              value={passwordInput}
            />
          </div>
          <div className={`input-group ${styles.Flex}`}>
            <button className="btn btn-primary" onClick={() => handleSignIn()}>
              Sign In
            </button>
          </div>
          <div className={`input-group ${styles.Flex}`}>
            <span
              className={styles.RegisterSwitch}
              onClick={() => setMode('register')}
            >
              You don't have account? Register Now
            </span>
          </div>
        </div>
      ) : (
        <div className={styles.FormWrapper}>
          <div className={`input-group ${styles.Flex}`}>
            <label>Login</label>
            <input
              type="text"
              className="form-control"
              onChange={(e) => setLoginInput(e.target.value)}
              value={loginInput}
            />
          </div>
          <div className={`input-group ${styles.Flex}`}>
            <label>Password</label>
            <input
              type="password"
              className="form-control"
              onChange={(e) => setPasswordInput(e.target.value)}
              value={passwordInput}
            />
          </div>
          <div className={`input-group ${styles.Flex}`}>
            <button
              className="btn btn-primary"
              onClick={() => handleRegister()}
            >
              Register
            </button>
          </div>
          <div className={`input-group ${styles.Flex}`}>
            <span
              className={styles.RegisterSwitch}
              onClick={() => setMode('login')}
            >
              Already have account? Sign In
            </span>
          </div>
        </div>
      )}
    </div>
  )
}
