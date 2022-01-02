import axios from 'axios'
import { API_NAME, API_ENDPOINTS } from './constants'

export const getCars = async () => {
  try {
    return axios.get(`${API_NAME}/${API_ENDPOINTS.car}`)
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
  const body = {
    carId: null,
    carName: data.name,
    carModel: data.model,
    description: data.description,
    price: data.price,
    location: data.location,
  }
  try {
    return axios.put(`${API_NAME}/${API_ENDPOINTS.car}`, JSON.stringify(body))
  } catch (err) {
    throw err
  }
}
export const updateCar = async (data) => {
  const body = {
    carId: data.id,
    carName: data.name,
    carModel: data.model,
    description: data.description,
    price: data.price,
    location: data.location,
  }
  try {
    return axios.put(`${API_NAME}/${API_ENDPOINTS.car}`, JSON.stringify(body))
  } catch (err) {
    throw err
  }
}
