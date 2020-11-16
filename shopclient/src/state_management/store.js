import { createStore, applyMiddleware, compose } from 'redux'
import { createStateSyncMiddleware, initStateWithPrevTab, withReduxStateSync } from 'redux-state-sync'
import thunk from 'redux-thunk'

const initalState = {
    cart: []
}

function reducer(state = initalState, action) {
    switch (action.type) {
        case "ADD_TO_CART":
            const tabIsFocused = document.hasFocus()
            return {
                ...state,
                cart: [
                    ...state.cart,
                    {
                        ...action.payload,
                        addWhileInFocus: tabIsFocused
                    }
                ]
            }

        case "REMOVE_FROM_CART":
            let newCart = state.cart.filter((el) => el.id !== action.payload.id)
            return {
                ...state,
                cart: [
                    ...newCart
                ]
            }

        case "ITEMS_LOADED":
            return {
                ...state,
                items: action.payload
            }

        case "ACKNOWLEDGE_ITEMS_ADDED_OOF":
            const idsToAcknowledge = action.payload.map(i => i.id)
            console.log(idsToAcknowledge)
            return {
                ...state,
                cart: state.cart.map(item => {
                    if (idsToAcknowledge.includes(item.id)) {
                        delete item.addWhileInFocus
                        console.log(`Deleting flag for ${item.id}`)
                    }

                    return item
                })
            }

        default:
            return state
    }
}

const store = createStore(
    withReduxStateSync(reducer),
    compose(
        applyMiddleware(thunk),
        applyMiddleware(createStateSyncMiddleware()),
        // window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__()
    )
)

initStateWithPrevTab(store)

export default store