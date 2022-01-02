import React from 'react'
import { BookingListItem } from '.'
export default function Bookings({ active }) {
  const placeholder = [
    {
      id: 1,
      firstName: 'szczur',
      lastName: 'ratjam',
      bookingDate: '13-12-2994',
      name: 'abc',
      model: 'cba',
      location: 'Warsaw',
    },
    {
      id: 2,
      firstName: 'szczur2',
      lastName: 'ratjam2',
      bookingDate: '13-12-2994',
      name: 'abc2',
      model: 'cba2',
      location: 'Twoj stary',
    },
  ]
  const bookingsFromApi = ''
  const bookings = bookingsFromApi || placeholder
  const bookingsToRender = bookings.map((booking, index) => (
    <BookingListItem
      key={index}
      id={booking.id}
      firstName={booking.firstName}
      lastName={booking.lastName}
      name={booking.name}
      model={booking.model}
      location={booking.location}
    />
  ))
  return (
    <div id="bookings-page" className={`tab-pane ${active ? 'active' : ''}`}>
      <div className="row">
        <div className="col-md-6 col-lg-4 mb-2">
          <div className="input-group">
            <span className="input-group-text">🔍</span>
            <input className="form-control" type="text" placeholder="Search" />
          </div>
        </div>
      </div>

      <div className="table-responsive">
        <table className="table table-striped">
          <thead>
            <tr>
              <th>
                <button className="btn">ID &#8595; </button>
              </th>
              <th>
                <button className="btn">First name &#8693;</button>
              </th>
              <th>
                <button className="btn">Last name &#8693;</button>
              </th>
              <th>
                <button className="btn">Booking date &#8693;</button>
              </th>
              <th>
                <button className="btn">Name &#8693;</button>
              </th>
              <th>
                <button className="btn">Model &#8693;</button>
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
            <a className="page-link" href="javascript:">
              <span>&laquo;</span>
            </a>
          </li>
          <li className="page-item">
            <a className="page-link" href="javascript:">
              1
            </a>
          </li>
          <li className="page-item active">
            <a className="page-link" href="javascript:">
              2
            </a>
          </li>
          <li className="page-item">
            <a className="page-link" href="javascript:">
              3
            </a>
          </li>
          <li className="page-item">
            <a className="page-link" href="javascript:">
              <span>&raquo;</span>
            </a>
          </li>
        </ul>
      </nav>
    </div>
  )
}
