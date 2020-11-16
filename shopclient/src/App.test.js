import { render, screen } from '@testing-library/react';
import App from './App';
import Cart from './ordering/cart';
import ProductList from './inventory/product-list';
import React from "react";
import { shallow, mount } from "enzyme";

it('renders app without crashing', () => {
  shallow(<App />);
});

it('renders Cart at the top of the app', () => {  
  const wrapper = shallow(<App />);
  const cart = <Cart />;
  expect(wrapper.contains(cart)).toEqual(true);
});

it('renders product list', () => {
  const wrapper = shallow(<App />);
  const productList = <ProductList />;
  expect(wrapper.contains(productList)).toEqual(true);
});