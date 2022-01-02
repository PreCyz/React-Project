import axios from 'axios'
import { API_NAME, API_ENDPOINTS } from './constants'

export const getBookings = async () => {
  try {
    return axios.get(`${API_NAME}/${API_ENDPOINTS.booking}`)
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

export const postBooking = async (data) => {
  const body = {
    status: data.status,
    orderId: data.orederId,
    booklyId: data.booklyId,
  }
  try {
    return axios.patch(
      `${API_NAME}/${API_ENDPOINTS.booking}`,
      JSON.stringify(body)
    )
  } catch (err) {
    throw err
  }
}
