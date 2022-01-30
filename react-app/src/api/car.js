import axios from 'axios'
import { API_NAME, API_ENDPOINTS } from './constants'
import { getToken } from '../utils/jwt'

export const getCars = async (pageNum, maxNum) => {
  try {
    return axios.get(
      `${API_NAME}/${API_ENDPOINTS.car}?pageNum=${pageNum}&maxNum=${maxNum}`,
      {
        headers: { Authorization: 'Bearer ' + getToken() },
      }
    )
  } catch (err) {
    throw err
  }
}

export const getCarsFilter = async (pageNum, maxNum, filter) => {
  try {
    return axios.get(
      `${API_NAME}/${API_ENDPOINTS.car}?pageNum=${pageNum}&maxNum=${maxNum}&keyword=${filter}`,
      {
        headers: { Authorization: 'Bearer ' + getToken() },
      }
    )
  } catch (err) {
    throw err
  }
}
export const getCarById = async (id) => {
  try {
    return axios.get(`${API_NAME}/${API_ENDPOINTS.car}/${id}`)
  } catch (err) {
    throw err
  }
}
export const addCar = async (data) => {
  try {
    return axios.put(
      `${API_NAME}/${API_ENDPOINTS.car}`,
      JSON.stringify({ id: null, ...data }),
      {
        headers: {
          'Content-Type': 'application/json',
          Authorization: 'Bearer ' + getToken(),
        },
      }
    )
  } catch (err) {
    throw err
  }
}
export const updateCar = async (data) => {
  try {
    return axios.put(`${API_NAME}/${API_ENDPOINTS.car}`, JSON.stringify(data), {
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + getToken(),
      },
    })
  } catch (err) {
    throw err
  }
}
