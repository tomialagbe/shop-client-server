import { ADD_TO_CART, addToCartAction, REMOVE_FROM_CART, removeFromCartAction, ACKNOWLEDGE_ITEMS_ADDED_OOF, acknowledgeItemsOofAction } from './product-list-actions';

describe('actions', () => {
    it('should create an action to add a product to cart', () => {
        
        const item = {
            description: "lorem ipsup dolor sit amet lorem ipsum dolor sit",
            id: 1,
            name: "Samsung Galaxy A20",
            pictureUrl: "https://i.imgur.com/KFojDGa.jpg",
            price: 450,
            qtyInStock: 15,
            qtyToAdd: 1
        };

        const expectedAction = {
            type: ADD_TO_CART,
            payload: item
        };
        expect(addToCartAction(item)).toEqual(expectedAction);
    });

    it('should create an action to remove a product from the cart', () => {
        const item = {
            description: "lorem ipsup dolor sit amet lorem ipsum dolor sit",
            id: 1,
            name: "Samsung Galaxy A20",
            pictureUrl: "https://i.imgur.com/KFojDGa.jpg",
            price: 450,
            qtyInStock: 15
        };
        const expectedAction = {
            type: REMOVE_FROM_CART,
            payload: item
        };
        expect(removeFromCartAction(item)).toEqual(expectedAction);
    });

    it('should create an action to acknowledge items added to cart', () => {
        const items = [
            {
                description: "lorem ipsup dolor sit amet lorem ipsum dolor sit",
                id: 1,
                name: "Samsung Galaxy A20",
                pictureUrl: "https://i.imgur.com/KFojDGa.jpg",
                price: 450,
                qtyInStock: 15,
                qtyToAdd: 1
            },
            {
                description: "lorem ipsup dolor sit amet lorem ipsum dolor sit",
                id: 1,
                name: "iPhone 12",
                pictureUrl: "https://i.imgur.com/KFojDGa.jpg",
                price: 900,
                qtyInStock: 4,
                qtyToAdd: 1
            }
        ];
        const expectedAction = {
            type: ACKNOWLEDGE_ITEMS_ADDED_OOF,
            payload: items
        }
        expect(acknowledgeItemsOofAction(items)).toEqual(expectedAction);
    });
});