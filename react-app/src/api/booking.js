import axios from 'axios'
import { getToken } from '../utils/jwt'
import { API_NAME, API_ENDPOINTS } from './constants'

export const getBookings = async (pageNum,maxNum) => {
  try {
    return axios.get(`${API_NAME}/${API_ENDPOINTS.booking}?pageNum=${pageNum}&maxNum=${maxNum}`, {
      headers: {
        Authorization: 'Bearer ' + getToken(),
      },
    })
  } catch (err) {
    throw err
  }
}

export const getBookingsFilter = async (pageNum,maxNum,filter) => {
  try {
    return axios.get(`${API_NAME}/${API_ENDPOINTS.booking}?pageNum=${pageNum}&maxNum=${maxNum}&keyword=${filter}`, {
      headers: {
        Authorization: 'Bearer ' + getToken(),
      },
    })
  } catch (err) {
    throw err
  }
}
export const getBookingById = async (id) => {
  try {
    return axios.get(`${API_NAME}/${API_ENDPOINTS.booking}/${id}`)
  } catch (err) {
    throw err
  }
}
export const postBooking = async (data) => {
  const body = {
    carId: data.carId,
    firstName: data.firstName,
    lastName: data.lastName,
    startDate: data.startDate,
    endDate: data.endDate,
    status: data.status,
  }
  try {
    return axios.get(
      `${API_NAME}/${API_ENDPOINTS.booking}`,
      JSON.stringify(body)
    )
  } catch (err) {
    throw err
  }
}

export const cancelBooking = async (data) => {
  const body = {
    status: data.status,
    orderId: data.orderId,
    booklyId: data.booklyId,
  }
  try {
    return axios.patch(
      `${API_NAME}/${API_ENDPOINTS.car}/${data.carId}/${API_ENDPOINTS.booking}`,
      JSON.stringify(body),
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
