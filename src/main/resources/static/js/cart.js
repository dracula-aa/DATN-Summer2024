$(document).ready(function() {
    let cart = JSON.parse(localStorage.getItem('cart')) || [];

    function formatDecimal(number) {
        return number.toFixed(0).replace(/\B(?=(\d{3})+(?!\d))/g, '.') + ' ₫';
    }

    function updateCartCount(count) {
        $(".cart-item-count").text(count);
    }

    function saveCart() {
        localStorage.setItem('cart', JSON.stringify(cart));
    }

    function updateCart() {
        let total = 0;
        let totalItems = 0;
        let cartItemsElement = $('#cart-items');
        cartItemsElement.empty();

        cart.forEach(function(item) {
            let subtotal = item.quantity * item.price;
            total += subtotal;
            totalItems += item.quantity;

            let row = `
                <tr data-product-id="${item.id}">
                    <td>
                        ${item.name}
                        <div style="max-width: 100px; max-height: 100px; overflow: hidden;">
                            <img src="/images/products/${item.image}" style="width: 100%; height: auto; display: block;"/>
                        </div> 
                    </td>
                    <td><input class="form-control w-50 quantity" value="${item.quantity}" min="1" type="number"/></td>
                    <td>${formatDecimal(item.price)}</td>
                    <td>${formatDecimal(subtotal)}</td>
                    <td><button class="btn btn-danger rounded-0 remove-from-cart"><i class="ti ti-trash"></i></button></td>
                </tr>
            `;
            cartItemsElement.append(row);
        });

        $('.count-item').text(totalItems);
        $('.amount').text(formatDecimal(total));
        $('.total-price').text(formatDecimal(total));
        $('.total-payment').val(total);

        updateCartCount(cart.length);
        $("#cart-total").text(formatDecimal(total));
        saveCart();
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
            image: button.data("product-image")
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

    updateCart();
});
