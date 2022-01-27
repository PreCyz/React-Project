import { useEffect } from 'react'
import { Tab, TabList, TabPanel, Tabs } from 'react-tabs'
import { Bookings, Assets, Manage } from '../components/'
import { useState } from 'react'
import { Navigate } from 'react-router-dom'
import { getToken, logOut } from '../utils/jwt'
import { getBookings } from '../api/booking'
import { toast, ToastContainer } from 'react-toastify'
function Dashboard() {
  const [currentTab, setCurrentTab] = useState('assets')
  const [manageViewOpen, setManageViewOpen] = useState(false)
  const [manageData, setManageData] = useState({})
  const handleLogout = () => {
    logOut()
    window.location.reload()
  }
  useEffect(() => {
    const fetchBookings = async () => {
      try {
        await getBookings()
      } catch (err) {
        if (err.response.status == 401) {
          handleLogout()
        }
      }
    }
    fetchBookings()
  }, [])
  return getToken() ? (
    <>
      <ToastContainer />
      {manageViewOpen ? (
        <Manage setManageViewOpen={setManageViewOpen} manageData={manageData} />
      ) : (
        <div className="wrapper">
          <div className="container my-3 my-md-4">
            <span className="logout" onClick={() => handleLogout()}>
              Logout
            </span>
            <ul className="nav nav-tabs">
              <li className="nav-item">
                <button
                  className={`nav-link ${
                    currentTab === 'assets?' ? 'active' : ''
                  }`}
                  data-bs-toggle="tab"
                  data-bs-target="#assets-page"
                  onClick={() => setCurrentTab('assets')}
                >
                  Assets
                </button>
              </li>
              <li className="nav-item">
                <button
                  className={`nav-link ${
                    currentTab === 'bookings?' ? 'active' : ''
                  }`}
                  data-bs-toggle="tab"
                  data-bs-target="#bookings-page"
                  onClick={() => setCurrentTab('bookings')}
                >
                  Bookings
                </button>
              </li>
            </ul>
            <div className="tab-content" id="myTabContent">
              {currentTab === 'assets' ? (
                <Assets
                  active={currentTab === 'assets'}
                  setManageViewOpen={setManageViewOpen}
                  setManageData={setManageData}
                />
              ) : (
                <Bookings active={currentTab === 'bookings'} />
              )}
            </div>
          </div>
        </div>
      )}
    </>
  ) : (
    <Navigate to="/" />
  )
}

export default Dashboard
