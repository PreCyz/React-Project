import React, { useState, useEffect } from 'react'
import { updateCar, addCar } from '../api/car'
import { addImage, getImage } from '../api/image'
import moment from 'moment'
import { cancelBooking } from '../api/booking'
export default function Manage({
  bookingsArray,
  manageData,
  setManageViewOpen,
}) {
  const [nameInput, setNameInput] = useState(manageData.name)
  const [modelInput, setModelInput] = useState(manageData.model)
  const [locationInput, setLocationInput] = useState(manageData.location)
  const [editMode, setEditMode] = useState(false)
  const [imagesArray, setImagesArray] = useState([])
  const [currentImage, setCurrentImage] = useState()
  const [imagesIdArray, setImagesIdArray] = useState(manageData.images | [])
  const [startDateInput, setStartDateInput] = useState(
    moment(manageData.startDate).format('DD-MM-YYYY') |
      moment(new Date()).format('DD-MM-YYYY')
  )
  const [endDateInput, setEndDateInput] = useState(
    moment(manageData.endDate).format('DD-YY-MMMM') |
      moment(new Date()).format('DD-MM-YYYY')
  )
  const [activeInput, setActiveInput] = useState(manageData.active | false)
  const [descInput, setDescInput] = useState(manageData.description | '')
  const [priceInput, setPriceInput] = useState(manageData.price | '')
  const [filteredOrders, setFilteredOrders] = useState([])

  const handleSearch = (e) => {
    if (e.target.value === '') {
      setFilteredOrders(manageData.orders)
    } else {
      setFilteredOrders(
        manageData.orders.filter(
          (order) =>
            order.lastName
              .toUpperCase()
              .includes(e.target.value.toUpperCase()) ||
            order.firstName.toUpperCase().includes(e.target.value.toUpperCase())
        )
      )
    }
  }
  useEffect(() => {
    setFilteredOrders(manageData.orders)
    manageData?.description && setDescInput(manageData.description)
    if (manageData?.carName) {
      setEditMode(true)
    } else {
      setEditMode(false)
    }
    manageData.images.forEach(async (image, index) => {
      'use strict'
      const response = await getImage(image)
      fetch('data:image/png;base64,' + response)
        .then((res) => {
          return res.blob()
        })
        .then((blob) =>
          setImagesArray([
            ...imagesArray,
            new File([blob], `image-${index}.png`, { type: 'image/png' }),
          ])
        )
      console.log(imagesArray)
    })
  }, [])
  useEffect(() => {
    console.log(descInput)
  }, [descInput])
  const handleUpload = async () => {
    try {
      const response = await addImage(imagesArray)
      setImagesIdArray(response.data.map((item) => item.fileId))
    } catch (err) {
      console.log(err)
    }
  }
  const handleCancel = async (order) => {
    let data = {
      carId: manageData.carId,
      status: 2,
      orderId: order.orderId,
      booklyId: order.booklyId,
    }
    cancelBooking(data)
  }
  useEffect(() => {
    console.log(imagesIdArray)
  }, [imagesIdArray])

  const handleDelete = () => {
    // TODO Delete Api Call
  }
  const submitForm = async (e) => {
    e.preventDefault()
    let data
    if (editMode) {
      //TODO Add image posting
      data = {
        carName: nameInput,
        carModel: modelInput,
        description: descInput,
        price: Number(priceInput),
        location: locationInput,
        images: imagesIdArray,
        startDateTime: moment(startDateInput),
        endDateTime: moment(endDateInput),
        active: activeInput,
        createTime: null,
        updateTime: null,
        orders: manageData.orders,
      }
      try {
        await updateCar(data)
      } catch (error) {
        console.log(error)
      }
    } else {
      data = {
        id: manageData.id,
        carName: nameInput,
        carModel: modelInput,
        description: descInput,
        price: Number(priceInput),
        location: locationInput,
        images: imagesIdArray,
        startDateTime: moment(startDateInput),
        endDateTime: moment(endDateInput),
        active: activeInput,
        createTime: null,
        updateTime: null,
        orders: [],
      }
      console.log(data)
      try {
        await addCar(data)
      } catch (error) {
        console.log(error)
      }
    }
  }
  return (
    <>
      <div className="container my-3 my-md-4">
        <div className="mb-2">
          <button className="btn" onClick={() => setManageViewOpen(false)}>
            &#8592; Go back
          </button>
        </div>

        <div className="row">
          <div className="col-md-6 col-lg-3 mb-3">
            <img
              className="img-fluid img-thumbnail"
              src={
                currentImage
                  ? currentImage
                  : 'https://dummyimage.com/600x400/fff/000'
              }
            />
          </div>

          <div className="col-md-6 col-lg-3 mb-4">
            <div className="mb-2">
              <input
                id="upload-photo"
                className="form-control form-control-sm"
                type="file"
                onChange={(e) =>
                  setImagesArray([...imagesArray, e.target.files[0]])
                }
              />
            </div>

            <ul className="thumbnail-selector">
              {imagesArray &&
                imagesArray.map((image) => (
                  <li
                    className="active"
                    onClick={() => setCurrentImage(URL.createObjectURL(image))}
                  >
                    <div className="row">
                      <div className="col-auto">
                        <button
                          className="btn"
                          onClick={() =>
                            setImagesArray(
                              imagesArray.filter((i) => image.name !== i.name)
                            )
                          }
                        >
                          x
                        </button>
                      </div>

                      <div className="col">
                        <button className="btn text-start w-100">
                          {image.name}
                        </button>
                      </div>
                    </div>
                  </li>
                ))}
            </ul>
            <button className="btn btn-primary" onClick={() => handleUpload()}>
              Upload
            </button>
          </div>

          <div className="col-lg-6">
            <form>
              <div className="form-floating mb-3">
                <input
                  id="input-name"
                  className="form-control"
                  type="text"
                  placeholder="&nbsp;"
                  value={nameInput}
                  onChange={(e) => {
                    setNameInput(e.target.value)
                  }}
                />
                <label htmlFor="input-name">Name</label>
              </div>

              <div className="form-floating mb-3">
                <input
                  id="input-model"
                  className="form-control"
                  type="text"
                  placeholder="&nbsp;"
                  value={modelInput}
                  onChange={(e) => {
                    setModelInput(e.target.value)
                  }}
                />
                <label htmlFor="input-model">Model</label>
              </div>

              <div className="form-floating mb-3">
                <input
                  id="input-location"
                  className="form-control"
                  type="text"
                  placeholder="&nbsp;"
                  value={locationInput}
                  onChange={(e) => {
                    setLocationInput(e.target.value)
                  }}
                />
                <label for="input-location">Location</label>
              </div>
              <div className="form-floating mb-3">
                <input
                  id="input-location"
                  className="form-control"
                  type="text"
                  placeholder="&nbsp;"
                  value={descInput}
                  onChange={(e) => {
                    setDescInput(e.target.value)
                  }}
                />
                <label htmlFor="input-location">Description</label>
              </div>
              <div className="form-floating mb-3">
                <input
                  id="input-location"
                  className="form-control"
                  type="text"
                  placeholder="&nbsp;"
                  value={priceInput}
                  onChange={(e) => {
                    setPriceInput(e.target.value)
                  }}
                />
                <label for="input-location">Price</label>
              </div>
              <div className="form-floating mb-3">
                <input
                  id="input-location"
                  className="form-control"
                  type="date"
                  placeholder="&nbsp;"
                  value={startDateInput}
                  onChange={(e) => {
                    setStartDateInput(e.target.value)
                  }}
                />
                <label htmlFor="input-location">Start Date</label>
              </div>
              <div className="form-floating mb-3">
                <input
                  id="input-location"
                  className="form-control"
                  type="date"
                  placeholder="&nbsp;"
                  value={endDateInput}
                  onChange={(e) => {
                    setEndDateInput(e.target.value)
                  }}
                />
                <label htmlFor="input-location">End Date</label>
              </div>
              <div
                style={{
                  display: 'flex',
                  alignItems: 'center',
                  marginBottom: '20px',
                }}
              >
                <input
                  style={{ marginRight: '15px' }}
                  type="checkbox"
                  value={activeInput}
                  onChange={(e) => {
                    setActiveInput(e.target.checked)
                  }}
                />
                <label for="input-location">Active</label>
              </div>

              <div className="d-flex justify-content-between">
                <button
                  className="btn btn-outline-danger"
                  onClick={() => handleDelete()}
                >
                  Delete
                </button>
                <button
                  className="btn btn-primary"
                  onClick={(e) => submitForm(e)}
                >
                  Save
                </button>
              </div>
            </form>
          </div>
        </div>

        <div className="row">
          <div className="col-md-6 col-lg-4 mb-2">
            <div className="input-group">
              <span className="input-group-text">🔍</span>
              <input
                onChange={(e) => handleSearch(e)}
                className="form-control"
                type="text"
                placeholder="Search"
              />
            </div>
          </div>
        </div>

        <div className="table-responsive">
          <table className="table table-striped">
            <thead>
              <tr>
                <th>
                  <button className="btn">ID &#8595;</button>
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
                <th>Action</th>
              </tr>
            </thead>
            <tbody>
              {filteredOrders?.map((item) => (
                <tr>
                  <td>{item.orderId}</td>
                  <td>{item.firstName}</td>
                  <td>{item.lastName}</td>
                  <td>{moment(item.startDate).format('DD-MM-YYYY')}</td>
                  <td>
                    <button
                      className="btn btn-primary"
                      onClick={() => handleCancel(item)}
                    >
                      Cancel
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>

        {/* <nav>
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
        </nav> */}
      </div>
    </>
  )
}
