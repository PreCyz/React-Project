export const logOut = () => {
  localStorage.removeItem('user')
}
export const setToken = (token) => {
  localStorage.setItem('user', token)
}

export const getToken = () => {
  return localStorage.getItem('user')
}
