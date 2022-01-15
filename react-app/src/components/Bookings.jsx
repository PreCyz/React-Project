import React, { useEffect, useState } from 'react'
import { BookingListItem } from '.'
import { getCars, getCarsFilter } from '../api/car'
import { getBookings, getBookingsFilter } from '../api/booking'
import { toast } from 'react-toastify'
import moment from 'moment'

export default function Bookings({ active }) {
  const [pageNum, setPageNum] = useState(1)
  const [sortingMode, setSortingMode] = useState('name')
  const [searchFilter, setSearchFilter] = useState('')
  const [booking, setBooking] = useState([])
  let sortedBookings
  const handleSearch = async () => {
    if (searchFilter === '') {
      const response = await getBookings(pageNum - 1, 10)
      setBooking(response.data)
    } else {
      const response = await getBookingsFilter(
        pageNum - 1,
        10,
        searchFilter.toLowerCase()
      )
      setBooking(response.data)
    }
  }

  useEffect(() => {
    const fetchBooking = async () => {
      try {
        const response = await getBookings(pageNum - 1, 10)
        console.log(response)
        setBooking(response.data)
      } catch (err) {
        toast.error('Fetch booking failed', {
          position: 'top-left',
          autoClose: 5000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
        })
      }
    }
    fetchBooking()
  }, [])
  useEffect(() => {
    handleSearch()
  }, [pageNum])
  // ---- sorting ---
  const sortByModel = () => {
    if (sortingMode === 'model') {
      setSortingMode('modelReverse')
    } else {
      setSortingMode('model')
    }
  }

  const sortByFirstName = () => {
    if (sortingMode === 'firstName') {
      setSortingMode('firstNameReverse')
    } else {
      setSortingMode('firstName')
    }
  }

  const sortByLastName = () => {
    if (sortingMode === 'lastName') {
      setSortingMode('lastNameReverse')
    } else {
      setSortingMode('lastName')
    }
  }
  const sortById = () => {
    if (sortingMode === 'id') {
      setSortingMode('idReverse')
    } else {
      setSortingMode('id')
    }
  }

  const sortByDate = () => {
    if (sortingMode === 'date') {
      setSortingMode('dateReverse')
    } else {
      setSortingMode('date')
    }
  }

  const sortByCarName = () => {
    if (sortingMode === 'carName') {
      setSortingMode('carNameReverse')
    } else {
      setSortingMode('carName')
    }
  }
  const sortByLocation = () => {
    if (sortingMode === 'location') {
      setSortingMode('cationReverse')
    } else {
      setSortingMode('location')
    }
  }
  const compareNames = (a, b) => {
    let nameA = a.carName.toUpperCase() // ignore upper and lowercase
    let nameB = b.carName.toUpperCase() // ignore upper and lowercase
    if (nameA < nameB) {
      return -1
    }
    if (nameA > nameB) {
      return 1
    }

    return 0
  }
  const compareModels = (a, b) => {
    let nameA = a.carModel.toUpperCase() // ignore upper and lowercase
    let nameB = b.carModel.toUpperCase() // ignore upper and lowercase
    if (nameA < nameB) {
      return -1
    }
    if (nameA > nameB) {
      return 1
    }

    return 0
  }

  const compareLocation = (a, b) => {
    let nameA = a.location.toUpperCase() // ignore upper and lowercase
    let nameB = b.location.toUpperCase() // ignore upper and lowercase
    if (nameA < nameB) {
      return -1
    }
    if (nameA > nameB) {
      return 1
    }

    return 0
  }
  const compareId = (a, b) => {
    let nameA = a.orderId // ignore upper and lowercase
    let nameB = b.orderId // ignore upper and lowercase
    if (nameA < nameB) {
      return -1
    }
    if (nameA > nameB) {
      return 1
    }

    return 0
  }
  const compareDates = (a, b) => {
    let nameA = a.startDate // ignore upper and lowercase
    let nameB = b.startDate // ignore upper and lowercase
    if (nameA < nameB) {
      return -1
    }
    if (nameA > nameB) {
      return 1
    }

    return 0
  }

  const compareCarName = (a, b) => {
    let nameA = a.carName // ignore upper and lowercase
    let nameB = b.carName // ignore upper and lowercase
    if (nameA < nameB) {
      return -1
    }
    if (nameA > nameB) {
      return 1
    }

    return 0
  }

  const compareFirstName = (a, b) => {
    let nameA = a.firstName.toUpperCase() // ignore upper and lowercase
    let nameB = b.firstName.toUpperCase() // ignore upper and lowercase
    if (nameA < nameB) {
      return -1
    }
    if (nameA > nameB) {
      return 1
    }

    return 0
  }

  const compareLastName = (a, b) => {
    let nameA = a.lastName.toUpperCase() // ignore upper and lowercase
    let nameB = b.lastName.toUpperCase() // ignore upper and lowercase
    if (nameA < nameB) {
      return -1
    }
    if (nameA > nameB) {
      return 1
    }

    return 0
  }
  useEffect(() => {
    switch (sortingMode) {
      case 'firstName':
        sortedBookings = booking.sort(compareFirstName)
        break
      case 'firstNameReverse':
        sortedBookings = booking.sort(compareFirstName).reverse()
        break

      case 'lastName':
        sortedBookings = booking.sort(compareLastName)
        break
      case 'lastNameReverse':
        sortedBookings = booking.sort(compareLastName).reverse()
        break

      case 'id':
        sortedBookings = booking.sort(compareId)
        break
      case 'idReverse':
        sortedBookings = booking.sort(compareId).reverse()
        break
      case 'model':
        sortedBookings = booking.sort(compareModels)
        break
      case 'modelReverse':
        sortedBookings = booking.sort(compareModels).reverse()

        break
      case 'date':
        sortedBookings = booking.sort(compareDates)
        break
      case 'dateReverse':
        sortedBookings = booking.sort(compareDates).reverse()
        break
      case 'carName':
        sortedBookings = booking.sort(compareCarName)
        break
      case 'carNameReverse':
        sortedBookings = booking.sort(compareCarName).reverse()

        break
      case 'location':
        sortedBookings = booking.sort(compareLocation)
        break
      case 'locationReverse':
        sortedBookings = booking.sort(compareLocation).reverse()
        break
      default:
        sortedBookings = booking
    }
  }, [sortingMode])

  // ---- end of sorting---

  const bookingsToRender = booking.map((booking, index) => (
    <BookingListItem
      key={index}
      id={booking.orderId}
      firstName={booking.firstName}
      lastName={booking.lastName}
      name={booking.carName}
      model={booking.carModel}
      location={booking.location}
      bookingDate={moment(booking.startDate).format('DD-MM-YYYY')}
      booklyId={booking.booklyId}
      carId={booking.carId}
    />
  ))
  return (
    <div id="assets-page" className={`tab-pane ${active ? 'active' : ''}`}>
      <div className="row">
        <div className="col-md-6 col-lg-4 mb-2">
          <div className="input-group">
            <span className="input-group-text">🔍</span>
            <input
              className="form-control"
              type="text"
              placeholder="Search"
              onChange={(e) => setSearchFilter(e.target.value)}
              value={searchFilter}
            />
            <button onClick={() => handleSearch()} className="btn btn-primary">
              Search
            </button>
          </div>
        </div>
      </div>

      <div className="table-responsive">
        <table className="table table-striped">
          <thead>
            <tr>
              <th>
                <button className="btn" onClick={sortById}>
                  ID &#8595;{' '}
                </button>
              </th>
              <th>
                <button className="btn" onClick={() => sortByFirstName()}>
                  First name &#8693;
                </button>
              </th>
              <th>
                <button className="btn" onClick={() => sortByLastName()}>
                  Last name &#8693;
                </button>
              </th>
              <th>
                <button className="btn" onClick={() => sortByDate()}>
                  Booking date &#8693;
                </button>
              </th>
              <th>
                <button className="btn" onClick={() => sortByCarName()}>
                  Name &#8693;
                </button>
              </th>
              <th>
                <button className="btn" onClick={() => sortByModel()}>
                  Model &#8693;
                </button>
              </th>
              <th>Location</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>{bookingsToRender}</tbody>
        </table>
      </div>

      <nav>
        <ul className="pagination justify-content-end">
          <li className="page-item">
            <button
              className="page-link"
              onClick={() => setPageNum((prev) => (prev > 1 ? prev - 1 : prev))}
            >
              <span>&laquo;</span>
            </button>
          </li>
          {pageNum > 1 ? (
            <li
              className="page-item "
              onClick={() => setPageNum((prev) => prev - 1)}
            >
              <span className="page-link">{pageNum - 1}</span>
            </li>
          ) : (
            <></>
          )}
          <li className="page-item active">
            <span className="page-link">{pageNum}</span>
          </li>
          <li
            className="page-item "
            onClick={() => setPageNum((prev) => prev + 1)}
          >
            <span className="page-link">{pageNum + 1}</span>
          </li>
          <li className="page-item">
            <button
              className="page-link"
              onClick={() => setPageNum((prev) => prev + 1)}
            >
              <span>&raquo;</span>
            </button>
          </li>
        </ul>
      </nav>
    </div>
  )
}
