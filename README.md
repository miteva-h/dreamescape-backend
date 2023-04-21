# Dream Escape
The main idea of this project is to make the online booking more accessible to the users. It can be perceived as an online travel agency that offers the possibility of renting elite villas.

There are two roles: Admin, User.

This project has authentication, authorization, login & register, and other options like listing destinations and villas.

It offers users the opportunity to search certain vacation destinations, to search certain luxury villas which can be rented, as well as leave reviews for the villas. By choosing some of the dates when the villa is not booked, corresponding amount of money is calculated which has to be paid if the user rents the villa. Payment is implemented using Stripe.

The application offers additional possibilities for admin users, i.e. adding, deleting and editing the information related to the destinations and the villas that the agency presents.

Many tests are provided for testing the service(evaluating the coverage using Pitest) and web layer of the backend.

What is used: Spring Boot(multi-module project) + PostgreSQL + JUnit 5 + Mockito + React + Bootstrap + Stripe.
