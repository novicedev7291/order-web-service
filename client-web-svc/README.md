# Ecommerce service

## General Steps

* A potential customer goes to the site address (Index page)
* The backend will check if session exist, if exist then the page will have 
customer Account Button & Logout along with Cart button otherwise Login.

### Customer is not logged in
* Customer will see all the items with `Login to add` button.
* Customer will press the button, he will be redirected to the page `Login Page`.
* The page will show `Login with google`.
* Customer will press the `Login with google` and it will redirect to google authentication
page and then after successful login will re-directed to items page again.

### Customer not found in database
* Fill the form with minimal possible info got from google i.e. email and redirect to
to `Complete Registration` page.
* If customer fills the form and submit, on success redirect to Index page.