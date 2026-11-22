# Test Scenarios

| ID | Module | Scenario | Expected result |
|---|---|---|---|
| LOGIN-01 | Login | Login with standard user | Inventory page opens |
| LOGIN-02 | Login | Login with invalid username | Authentication error appears |
| LOGIN-03 | Login | Login with invalid password | Authentication error appears |
| LOGIN-04 | Login | Submit empty username | Username required error appears |
| LOGIN-05 | Login | Submit empty password | Password required error appears |
| LOGIN-06 | Login | Login with locked user | Locked-out error appears |
| INV-01 | Inventory | Open inventory page | Six products are displayed |
| INV-02 | Inventory | Sort by price low to high | Prices appear in ascending order |
| INV-03 | Inventory | Add backpack to cart | Cart badge shows one |
| INV-04 | Inventory | Remove backpack from inventory | Cart badge returns to zero |
| INV-05 | Inventory | Logout | Login page is displayed |
| CART-01 | Cart | Open cart after adding backpack | Backpack is listed |
| CART-02 | Cart | Remove item from cart | Cart becomes empty |
| CART-03 | Cart | Add two products | Two products appear in cart |
| CHK-01 | Checkout | Continue without first name | First-name validation appears |
| CHK-02 | Checkout | Continue without postal code | Postal-code validation appears |
| CHK-03 | Checkout | Complete valid order | Order confirmation appears |

Note: These are the initial implemented scenarios, not exhaustive test coverage. Verify expected results against the live application.
