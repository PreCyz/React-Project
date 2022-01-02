import React from 'react'

export default function BookingListItem({
  id,
  firstName,
  lastName,
  bookingDate,
  name,
  model,
  location,
}) {
  const handleBookingManage = () => {
    console.log('yes yes szur kill human things')
  }
  return (
    <tr>
      <td>{id}</td>
      <td>{firstName}</td>
      <td>{lastName}</td>
      <td>{bookingDate}</td>
      <td>{name}</td>
      <td>{model}</td>
      <td>{location}</td>
      <td>
        <button
          className="btn"
          onClick={() => {
            handleBookingManage()
          }}
        >
          Manage
        </button>
      </td>
    </tr>
  )
}
