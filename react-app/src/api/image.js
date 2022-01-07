import axios from 'axios'
import { getToken } from '../utils/jwt'
import { Buffer } from 'buffer'
import { API_NAME, API_ENDPOINTS } from './constants'
export const addImage = async (filesArray) => {
  let formData = new FormData()
  filesArray.forEach((item) => console.log(item))
  filesArray.forEach((item) => {
    formData.append('files', item, item.name)
  })

  try {
    return axios.post(`${API_NAME}/${API_ENDPOINTS.image}`, formData, {
      headers: {
        Authorization: 'Bearer ' + getToken(),
      },
    })
  } catch (err) {
    throw err
  }
}
export const getImage = async (id) => {
  try {
    return axios
      .get(`${API_NAME}/${API_ENDPOINTS.image}/${id}`, {
        headers: {
          Authorization: 'Bearer ' + getToken(),
        },
        responseType: 'arraybuffer',
      })
      .then((response) =>
        new Buffer.from(response.data, 'binary').toString('base64')
      )
  } catch (err) {
    throw err
  }
}
