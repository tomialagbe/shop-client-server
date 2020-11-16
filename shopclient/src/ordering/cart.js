import React, { Component } from 'react'
import { Modal } from 'antd'
import { connect } from 'react-redux'
import { acknowledgeItemsOofAction } from '../inventory/product-list-actions'

class Cart extends Component {

    render() {

        let numInCart = 0
        const productsAddedWhileOutOfFocus = this.props.cart.filter(i => i.addWhileInFocus === false)    
        let totProductsAdded = 0;
        for (let p of productsAddedWhileOutOfFocus) {
            totProductsAdded += p.qtyToAdd;            
        }
        const numItemsAddedWhileOutOfFocus = productsAddedWhileOutOfFocus.map(c => c.qtyToAdd).reduce((a, b) => a+b, 0);
        if (this.props.cart.length > 0) {
            numInCart = this.props.cart.map(c => c.qtyToAdd).reduce((a, b) => a + b, 0)
        }

        return (
            <>
                <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
                    <a className="navbar-brand" href="#">Phone Shop</a>
                    <button className="navbar-toggler" type="button" datatoggle="collapse" data-target="#navbarText"
                        aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
                        <span className="navbar-toggler-icon"></span>
                    </button>
                    <div className="collapse navbar-collapse mr-auto" id="navbarText">
                        <div className="mr-auto"></div>
                        <span className="navbar-text">
                            ({numInCart}) Items in cart
                        </span>
                    </div>
                </nav>

                <Modal
                    title={`${totProductsAdded} items were added to cart`}
                    visible={productsAddedWhileOutOfFocus.length > 0}
                    onOk={() => this.props.acknowlegde(productsAddedWhileOutOfFocus)}>
                    {
                        productsAddedWhileOutOfFocus.map(item => {
                            return (
                                <div key={item.id}>{item.name} was added</div>
                            )
                        })
                    }
                </Modal>
            </>
        )
    }
}

const mapStateToProps = state => ({
    cart: state.cart
})

const mapDispatchToProps = dispatch => ({
    acknowlegde: items => dispatch(acknowledgeItemsOofAction(items))
})

export default connect(mapStateToProps, mapDispatchToProps)(Cart);