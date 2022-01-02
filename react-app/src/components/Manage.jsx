import React, { useState, useEffect } from 'react'
import { updateCar, addCar } from '../api/car'
export default function Manage({
  bookingsArray,
  manageData,
  assetImages = [{ src: 'szczur.png' }],
  setManageViewOpen,
}) {
  const [nameInput, setNameInput] = useState(manageData.name)
  const [modelInput, setModelInput] = useState(manageData.model)
  const [locationInput, setLocationInput] = useState(manageData.location)
  const [activeImageSrc, setActiveImageSrc] = useState(assetImages[0].src)
  const [editMode, setEditMode] = useState(false)

  useEffect(() => {
    if (manageData?.name) {
      setEditMode(true)
    } else {
      setEditMode(false)
    }
  }, [])

  const handleDelete = () => {
    // TODO Delete Api Call
  }
  const submitForm = async () => {
    let data
    if (editMode) {
      //TODO Add image posting
      data = {
        carId: manageData.id,
        carName: nameInput,
        carModel: modelInput,
        description: 'todo',
        price: 420,
        location: locationInput,
      }
      try {
        await updateCar(data)
      } catch (error) {
        console.log(error)
      }
    } else {
      data = {
        carName: nameInput,
        carModel: modelInput,
        description: 'todo',
        price: 420,
        location: locationInput,
      }
      try {
        await addCar(data)
      } catch (error) {
        console.log(error)
      }
    }
    setManageViewOpen(false)
  }
  return (
    <>
      <div class="container my-3 my-md-4">
        <div class="mb-2">
          <button className="btn" onClick={() => setManageViewOpen(false)}>
            &#8592; Go back
          </button>
        </div>

        <div class="row">
          <div class="col-md-6 col-lg-3 mb-3">
            <img
              class="img-fluid img-thumbnail"
              src={
                activeImageSrc
                  ? activeImageSrc
                  : 'https://dummyimage.com/600x400/fff/000'
              }
            />
          </div>

          <div class="col-md-6 col-lg-3 mb-4">
            <div class="mb-2">
              <input
                id="upload-photo"
                class="form-control form-control-sm"
                type="file"
              />
            </div>

            <ul class="thumbnail-selector">
              {assetImages &&
                assetImages.map((image) => (
                  <li class="active">
                    <div class="row">
                      <div class="col-auto">
                        <button class="btn">x</button>
                      </div>

                      <div class="col">
                        <button class="btn text-start w-100">
                          {image.src}
                        </button>
                      </div>
                    </div>
                  </li>
                ))}
            </ul>
          </div>

          <div class="col-lg-6">
            <form>
              <div class="form-floating mb-3">
                <input
                  id="input-name"
                  class="form-control"
                  type="text"
                  placeholder="&nbsp;"
                  value={nameInput}
                  onChange={(e) => {
                    setNameInput(e.target.value)
                  }}
                />
                <label for="input-name">Name</label>
              </div>

              <div class="form-floating mb-3">
                <input
                  id="input-model"
                  class="form-control"
                  type="text"
                  placeholder="&nbsp;"
                  value={modelInput}
                  onChange={(e) => {
                    setModelInput(e.target.value)
                  }}
                />
                <label for="input-model">Model</label>
              </div>

              <div class="form-floating mb-3">
                <input
                  id="input-location"
                  class="form-control"
                  type="text"
                  placeholder="&nbsp;"
                  value={locationInput}
                  onChange={(e) => {
                    setLocationInput(e.target.value)
                  }}
                />
                <label for="input-location">Location</label>
              </div>

              <div class="d-flex justify-content-between">
                <button
                  class="btn btn-outline-danger"
                  onClick={() => handleDelete()}
                >
                  Delete
                </button>
                <button class="btn btn-primary" onClick={() => submitForm()}>
                  Save
                </button>
              </div>
            </form>
          </div>
        </div>

        <div class="row">
          <div class="col-md-6 col-lg-4 mb-2">
            <div class="input-group">
              <span class="input-group-text">🔍</span>
              <input class="form-control" type="text" placeholder="Search" />
            </div>
          </div>
        </div>

        <div class="table-responsive">
          <table class="table table-striped">
            <thead>
              <tr>
                <th>
                  <button class="btn">ID &#8595;</button>
                </th>
                <th>
                  <button class="btn">First name &#8693;</button>
                </th>
                <th>
                  <button class="btn">Last name &#8693;</button>
                </th>
                <th>
                  <button class="btn">Booking date &#8693;</button>
                </th>
                <th>Action</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>315</td>
                <td>Alice</td>
                <td>Alice</td>
                <td>15-11-2022</td>
                <td>
                  <a href="javascript:">Cancel</a>
                </td>
              </tr>
              <tr>
                <td>314</td>
                <td>Bob</td>
                <td>Bob</td>
                <td>13-02-2022</td>
                <td>
                  <a href="javascript:">Cancel</a>
                </td>
              </tr>
            </tbody>
          </table>
        </div>

        <nav>
          <ul class="pagination justify-content-end">
            <li class="page-item">
              <a class="page-link" href="javascript:">
                <span>&laquo;</span>
              </a>
            </li>
            <li class="page-item">
              <a class="page-link" href="javascript:">
                1
              </a>
            </li>
            <li class="page-item active">
              <a class="page-link" href="javascript:">
                2
              </a>
            </li>
            <li class="page-item">
              <a class="page-link" href="javascript:">
                3
              </a>
            </li>
            <li class="page-item">
              <a class="page-link" href="javascript:">
                <span>&raquo;</span>
              </a>
            </li>
          </ul>
        </nav>
      </div>
    </>
  )
}
