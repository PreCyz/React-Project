import React from 'react'

export default function AssetListItem({
  id,
  name,
  model,
  location,
  setManageViewOpen,
  setManageData,
}) {
  const handleManage = () => {
    console.log('xd')
    setManageData({
      id,
      name,
      model,
      location,
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
