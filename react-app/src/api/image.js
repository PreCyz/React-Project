export const addImage = async (fileName, imageBlob) => {
  let formData = new FormData()
  formData.append(fileName, imageBlob)
  try {
    return axios.post(`${API_NAME}/${API_ENDPOINTS.imnage}`, formData)
  } catch (err) {
    throw err
  }
}
