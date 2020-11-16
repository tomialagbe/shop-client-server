import { render, screen } from '@testing-library/react';
import React from "react";
import { shallow, mount } from "enzyme";
import ProductListItem from './product-list-item';

it('renders list item without crashing', () => {
    const url = 'http://test-url.com';
    const name = 'product name';
    const price = 100;
    const desc = 'product description';
    const numInStock = 12;
    const wrapper = mount(<ProductListItem url={url} name={name} key={name} price={price} desc={desc} qtyInStock={numInStock} />);
    expect(wrapper.props().url).toEqual(url);
    expect(wrapper.props().name).toEqual(name);
    expect(wrapper.props().price).toEqual(price);
    expect(wrapper.props().desc).toEqual(desc);
    expect(wrapper.props().qtyInStock).toEqual(numInStock);
    
});