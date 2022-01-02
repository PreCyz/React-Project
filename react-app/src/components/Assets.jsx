import React from 'react'
import { AssetListItem } from '.'
export default function Assets({ active, setManageViewOpen, setManageData }) {
  // TODO
  // Fetch assets from API
  const assetsFromApi = ''
  const placeholder = [
    {
      id: 1,
      name: 'ABC',
      model: 'Model1',
      location: 'Warsaw',
    },
    {
      id: 2,
      name: 'cba',
      model: 'Model2',
      location: 'Krakow',
    },
  ]
  const assets = assetsFromApi || placeholder
  const assetsToRender = assets.map((asset, index) => (
    <AssetListItem
      key={index}
      id={asset.id}
      name={asset.name}
      model={asset.model}
      location={asset.location}
      setManageData={setManageData}
      setManageViewOpen={setManageViewOpen}
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
              />
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
                  <button className="btn btn-light">Name &#8595;</button>
                </th>
                <th>
                  <button className="btn btn-light">Model &#8693;</button>
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
    </>
  )
}
