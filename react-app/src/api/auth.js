import axios from 'axios'
import { API_NAME, API_ENDPOINTS } from './constants'

import MD5 from 'crypto-js/md5'
export const register = async (username, password) => {
  const body = {
    username,
    password,
    userType: 'admin',
  }
  try {
    return axios.post(
      `${API_NAME}/${API_ENDPOINTS.register}`,
      JSON.stringify(body),
      {
        headers: { 'Content-Type': 'application/json' },
      }
    )
  } catch (err) {
    throw err
  }
}

export const signIn = async (username, password) => {
  const body = {
    username,
    password: MD5(password).toString(),
  }
  try {
    return axios.post(
      `${API_NAME}/${API_ENDPOINTS.login}`,
      JSON.stringify(body),
      { headers: { 'Content-Type': 'application/json' } }
    )
  } catch (err) {
    throw err
  }
}
