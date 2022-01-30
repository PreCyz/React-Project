import React from 'react'
import { cancelBooking } from '../api/booking'
import { toast } from 'react-toastify'

export default function BookingListItem({
  id,
  firstName,
  lastName,
  bookingDate,
  name,
  model,
  location,
  carId,
  booklyId,
  status,
}) {
  const handleBookingManage = async () => {
    let data = {
      carId: carId,
      status: 2,
      orderId: id,
      booklyId,
    }
    try {
      await cancelBooking(data)
    } catch (err) {
      toast.error('Cancel booking failed', {
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
        {status == 1 ? (
          <button
            className="btn btn-primary"
            onClick={() => {
              handleBookingManage()
            }}
          >
            Cancel
          </button>
        ) : (
          <p className="p">Cancelled</p>
        )}
      </td>
    </tr>
  )
}
