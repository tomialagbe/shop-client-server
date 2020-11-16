import { fetchUrl } from 'fetch';

export const ADD_TO_CART = 'ADD_TO_CART';
export const REMOVE_FROM_CART = 'REMOVE_FROM_CART';

export function addToCartAction(item) {
    return {
        type: ADD_TO_CART,
        payload: item
    }
}

export function removeFromCartAction(item) {
    return {
        type: REMOVE_FROM_CART,
        payload: item
    }
}

export const ITEMS_LOADED = 'ITEMS_LOADED'
export function loadProductsAction() {
    return dispatch => {
        fetchUrl("http://192.168.0.20:8080/products", (err, metadata, body) => {
            dispatch({
                type: ITEMS_LOADED,
                payload: JSON.parse(body.toString())
            })
        })
    }
}

export const ACKNOWLEDGE_ITEMS_ADDED_OOF = 'ACKNOWLEDGE_ITEMS_ADDED_OOF'
export function acknowledgeItemsOofAction(items) {
    return {
        type: ACKNOWLEDGE_ITEMS_ADDED_OOF,
        payload: items
    }
}