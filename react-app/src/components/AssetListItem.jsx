import React from 'react'

export default function AssetListItem({
  id,
  name,
  model,
  location,
  setManageViewOpen,
  setManageData,
  startDateTime,
  endDateTime,
  orders,
  price,
  description,
  images,
  active,
  carId,
}) {
  const handleManage = () => {
    console.log(description)
    setManageData({
      id,
      name,
      model,
      location,
      startDateTime,
      endDateTime,
      orders,
      price,
      description,
      images,
      active,
      carId,
    })
    setManageViewOpen(true)
  }
  return (
    <tr>
      <td>{name}</td>
      <td>{model}</td>
      <td>{location}</td>
      <td>
        <button onClick={() => handleManage()} className="btn">
          Manage
        </button>
      </td>
    </tr>
  )
}
