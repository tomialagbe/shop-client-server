const ProductListItem = (props) => {
    return (

        <li className="list-group-item">
            <div className="media align-items-lg-center flex-column flex-lg-row p-3">
                <div className="media-body order-2 order-lg-1">
                    <h5 className="mt-0 font-weight-bold mb-2">{props.name}</h5>
                    <p className="font-italic text-muted mb-0 small">
                        {props.desc}
                    </p>
                    <div className="row">
                        <div className="col-6 d-flex align-items-center justify-content-between mt-1">
                            <h6 className="font-weight-bold my-2">€ {props.price}</h6>
                        </div>
                    </div>
                    <div className="row">
                        <div className="col-6 d-flex align-items-center text-right">
                            <span>{props.qtyInStock} in stock</span>
                        </div>
                    </div>
                    <div className="row mt-2">
                        <button onClick={props.addToCartClicked} type="button" className="btn btn-primary btn-sm">Add to cart</button>
                        <button onClick={props.removeFromCartClicked} type="button" className="ml-2 btn btn-danger btn-sm">Remove from cart</button>
                    </div>

                </div>
                <img src={props.url} alt="Generic placeholder image" width="80"
                    className="order-lg-2" />
            </div>
        </li>
    )
}

export default ProductListItem