import React, { Component } from 'react'
import { Modal, Button, Input } from 'antd'
import { connect } from 'react-redux'
import { UpOutlined, DownOutlined } from "@ant-design/icons"

import ProductListItem from './product-list-item'
import { addToCartAction, removeFromCartAction, loadProductsAction } from './product-list-actions'

class ProductList extends Component {

    state = {
        addToCartModalVisible: false,
        selectedItem: {},
    }

    componentDidMount() {
        this.props.loadItems()
    }

    addToCartClicked = (item) => {
        this.setState({
            selectedItem: item,
            addToCartModalVisible: true,
        });
    };

    removeFromCartClicked = (item) => {
        this.props.removeFromCart(item);
    }

    handleOk = () => {
        this.setState({
            addToCartModalVisible: false,
            selectedItem: {},
        });

        this.props.addToCart(this.state.selectedItem);
    };

    handleCancel = e => {
        console.log(e);
        this.setState({
            addToCartModalVisible: false,
            selectedItem: {}
        });
    };

    changeSelectedItmQty = qty => {
        let currentQtyToAdd = 0
        if (this.state.selectedItem.qtyToAdd !== undefined)
            currentQtyToAdd = this.state.selectedItem.qtyToAdd

        const newQty = currentQtyToAdd + qty

        if (newQty > this.state.selectedItem.qtyInStock || newQty <= 0)
            return

        this.setState({
            selectedItem: { ...this.state.selectedItem, qtyToAdd: newQty }
        })
    }

    render() {
        return (
            <>
                <div className="container py-2">
                    <div className="row text-center text-white mb-1">
                        <div className="col-lg-7 mx-auto">
                            <h1 className="display-4 text-white">Product List</h1>
                        </div>
                    </div>
                    <div className="row">
                        <div className="col-lg-8 mx-auto">
                            <ul className="list-group shadow">
                                {
                                    this.props.items.map(item => {
                                        return (
                                            <ProductListItem
                                                url={item.pictureUrl}
                                                key={item.name}
                                                name={item.name}
                                                price={item.price}
                                                desc={item.description}
                                                qtyInStock={item.qtyInStock}
                                                addToCartClicked={() => this.addToCartClicked(item)}
                                                removeFromCartClicked={() => this.removeFromCartClicked(item)} />
                                        )
                                    })
                                }
                            </ul>
                        </div>
                    </div>

                </div>
                
                <Modal
                    title={`Add ${this.state.selectedItem.name} to Cart`}
                    visible={this.state.addToCartModalVisible}
                    onOk={this.handleOk}
                    onCancel={this.handleCancel}>
                    <h2>
                        {this.state.selectedItem.name}
                    </h2>
                    <p>
                        {this.state.selectedItem.qtyInStock} in stock

                        <p>
                            <div className="flex">
                                <Button icon={<UpOutlined />} onClick={() => this.changeSelectedItmQty(1)} />
                                <Input type="number" value={this.state.selectedItem.qtyToAdd} />
                                <Button icon={<DownOutlined />} onClick={() => this.changeSelectedItmQty(-1)} />
                            </div>
                        </p>
                    </p>
                </Modal>
            </>
        )
    }
}

const mapDispatchToProps = dispatch => ({
    addToCart: cartItem => dispatch(addToCartAction(cartItem)),
    removeFromCart: cartItem => dispatch(removeFromCartAction(cartItem)),
    loadItems: () => dispatch(loadProductsAction())
})

const mapStateToProps = state => ({
    items: (state.items) ? state.items : []
})

export default connect(mapStateToProps, mapDispatchToProps)(ProductList)