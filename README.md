<h1 align="center">🛒 Store API</h1>

<p align="center">
  A backend e-commerce application built with <b>Spring Boot</b>, featuring user authentication, shopping cart, checkout, and 
  <b>Stripe integration</b> for payments.
</p>

<hr/>

<h2>✨ Features</h2>

<ul>
  <li>🔐 <b>JWT Authentication</b> for secure user login & session management</li>
  <li>🛍️ <b>Shopping Cart</b>: add, update, and clear items</li>
  <li>📦 <b>Order Management</b> with pending/confirmed statuses</li>
  <li>💳 <b>Stripe Checkout Integration</b>
    <ul>
      <li>Create checkout sessions</li>
      <li>Handle payment success & cancellation</li>
      <li>Webhook endpoint to process Stripe events</li>
    </ul>
  </li>
  <li>⚡ Clean architecture with services, repositories, and controllers</li>
</ul>

<hr/>

<h2>🏗️ Tech Stack</h2>

<p>
  <b>Languages & Frameworks:</b> Java 17, Spring Boot, Spring Security (JWT) <br/>
  <b>Payment:</b> Stripe Java SDK <br/>
  <b>Database:</b> MySQL <br/>
  <b>Build Tool:</b> Maven
</p>

<hr/>

<h2>📂 Project Structure</h2>

<pre>

 ├── config        # App and Stripe configuration
 ├── controllers   # REST API controllers
 ├── dtos          # Request/response DTOs
 ├── entities      # JPA entities (User, Cart, Order, etc.)
 ├── filters       # JWT authentication filter
 ├── repositories  # Spring Data JPA repositories
 ├── services      # Business logic (Auth, Cart, Checkout, etc.)
</pre>

<hr/>


