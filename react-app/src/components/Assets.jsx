import React, { useState, useEffect } from 'react'
import { AssetListItem } from '.'
import { getCars, getCarsFilter } from '../api/car'
import { toast } from 'react-toastify'
export default function Assets({ active, setManageViewOpen, setManageData }) {
  // TODO
  // Fetch assets from API

  const [assets, setAssets] = useState([])
  const [sortingMode, setSortingMode] = useState('name')
  const [searchFilter, setSearchFilter] = useState('')
  const [pageNum, setPageNum] = useState(1)

  useEffect(() => {
    const fetchCars = async () => {
      try {
        const response = await getCars(pageNum - 1, 10)
        console.log(response)
        setAssets(response.data)
      } catch (err) {
        toast.error('Fetching cars failed', {
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
    fetchCars()
  }, [])
  let sortedAssets = assets
  const sortByName = () => {
    if (sortingMode === 'name') {
      setSortingMode('nameReverse')
    } else {
      setSortingMode('name')
    }
  }

  const handleSearch = async () => {
    if (searchFilter === '') {
      const response = await getCars(pageNum - 1, 10)
      setAssets(response.data)
    } else {
      const response = await getCarsFilter(
        pageNum - 1,
        10,
        searchFilter.toLowerCase()
      )
      setAssets(response.data)
    }
  }

  useEffect(() => {
    handleSearch()
  }, [pageNum])
  const sortByModel = () => {
    if (sortingMode === 'model') {
      setSortingMode('modelReverse')
    } else {
      setSortingMode('model')
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
  useEffect(() => {
    switch (sortingMode) {
      case 'name':
        sortedAssets = assets.sort(compareNames)
        break
      case 'nameReverse':
        sortedAssets = assets.sort(compareNames).reverse()
        break
      case 'model':
        sortedAssets = assets.sort(compareModels)
        break
      case 'modelReverse':
        sortedAssets = assets.sort(compareModels).reverse()
    }
  }, [sortingMode])
  const assetsToRender = sortedAssets.map((asset, index) => (
    <AssetListItem
      key={index}
      carId={asset.carId}
      name={asset.carName}
      model={asset.carModel}
      location={asset.location}
      setManageData={setManageData}
      setManageViewOpen={setManageViewOpen}
      startDateTime={asset.starDateTime}
      endDateTime={asset.endDateTime}
      orders={asset.orders}
      price={asset.price}
      description={asset.description}
      images={asset.images}
      active={asset.active}
    />
  ))
  const handleAddNewAsset = () => {
    setManageData({})
    setManageViewOpen(true)
  }
  return (
    <>
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
              <button
                onClick={() => handleSearch()}
                className="btn btn-primary"
              >
                Search
              </button>
            </div>
          </div>

          <div className="col text-end mb-2">
            <button
              className="btn btn-primary"
              onClick={() => handleAddNewAsset()}
            >
              Add
            </button>
          </div>
        </div>

        <div className="table-responsive">
          <table className="table table-striped">
            <thead>
              <tr>
                <th>
                  <button
                    onClick={() => sortByName()}
                    className="btn btn-light"
                  >
                    Name &#8693;
                  </button>
                </th>
                <th>
                  <button
                    onClick={() => sortByModel()}
                    className="btn btn-light"
                  >
                    Model &#8693;
                  </button>
                </th>
                <th>Location</th>
                <th>Action</th>
              </tr>
            </thead>
            <tbody>{assetsToRender}</tbody>
          </table>
        </div>

        <nav>
          <ul className="pagination justify-content-end">
            <li className="page-item">
              <button
                className="page-link"
                onClick={() =>
                  setPageNum((prev) => (prev > 1 ? prev - 1 : prev))
                }
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
    </>
  )
}
