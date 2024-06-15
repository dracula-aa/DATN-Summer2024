$(document).ready(function() {
    var cart = [];

    function updateCart() {
        var cartItemsElement = $("#cart-items");
        cartItemsElement.empty();

        var total = 0;

        cart.forEach(function(item) {
            var subtotal = item.quantity * item.price;
            total += subtotal;

            var row = `
            <tr>
                <td th:text="${item.name}"></td>
                <td><input type="number" class="form-control quantity" th:value="${item.quantity}" min="1"></td>
                <td th:text="'$' + ${#numbers.formatDecimal(item.price, 2, 'COMMA', 0, 'POINT')}"></td>
                <td th:text="'$' + ${#numbers.formatDecimal(item.quantity * item.price, 2, 'COMMA', 0, 'POINT')}"></td>
                <td><button class="btn btn-danger remove-from-cart">Remove</button></td>
            </tr>
        `;

            cartItemsElement.append(row);
        });

        $("#cart-total").text(total.toFixed(2));
    }


    function addToCart(product) {
        var existingItem = cart.find(item => item.id === product.id);
        if (existingItem) {
            existingItem.quantity += 1;
        } else {
            cart.push({...product, quantity: 1});
        }
        updateCart();
    }

    $(document).on("click", ".add-to-cart", function() {
        var button = $(this);
        var product = {
            id: button.data("product-id"),
            name: button.data("product-name"),
            price: parseFloat(button.data("product-price")),
            quantity: 1
        };

        addToCart(product);
    });


    $(document).on("click", ".remove-from-cart", function() {
        var productId = $(this).closest("tr").data("product-id");
        cart = cart.filter(item => item.id !== productId);
        updateCart();
    });

    $(document).on("change", ".quantity", function() {
        var productId = $(this).closest("tr").data("product-id");
        var quantity = parseInt($(this).val());
        var product = cart.find(item => item.id === productId);
        if (product) {
            product.quantity = quantity;
        }
        updateCart();
    });

    $(".clear-cart").on("click", function() {
        cart = [];
        updateCart();
    });

    // Gọi updateCart lần đầu khi trang được load
    updateCart();

    // Khai báo hàm updateCart ở global scope để có thể gọi lại từ bất kỳ đâu
    window.updateCart = updateCart;
});
