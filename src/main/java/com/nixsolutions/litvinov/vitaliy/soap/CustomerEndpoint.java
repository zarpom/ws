package com.nixsolutions.litvinov.vitaliy.soap;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(serviceName = "customerService")
public class CustomerEndpoint {
    private CustomerService service;

    @WebMethod(exclude = true)
    public void setService(CustomerService service) {
        this.service = service;
    }

    @WebMethod(operationName = "getCustomer")
    public Customer getCustomerById(String customerId) {
        Customer customer = service.getCustomerById(customerId);
        return customer;
    }

}