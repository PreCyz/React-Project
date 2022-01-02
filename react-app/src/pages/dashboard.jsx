import { Tab, TabList, TabPanel, Tabs } from 'react-tabs'
import { Bookings, Assets, Manage } from '../components/'
import { useState } from 'react'
function Dashboard() {
  const [currentTab, setCurrentTab] = useState('assets')
  const [manageViewOpen, setManageViewOpen] = useState(false)
  const [manageData, setManageData] = useState({})
  return (
    <>
      {manageViewOpen ? (
        <Manage setManageViewOpen={setManageViewOpen} manageData={manageData} />
      ) : (
        <div className="container my-3 my-md-4">
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
      )}
    </>
  )
}

export default Dashboard
