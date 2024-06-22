$(document).ready(function() {
    const quantities = $('.quantity');
    const prices = $('.price');
    const countItemElement = $('.count-item');
    const amountElement = $('.amount');
    const totalPriceElement = $('.total-price');

    function updateTotalPrice() {
        let total = 0;
        let count = 0;
        quantities.each(function(index) {
            const price = parseFloat($(prices[index]).data('price'));
            const amount = parseInt($(this).val());
            total += price * amount;
            count += amount;
        });
        countItemElement.text(count);
        amountElement.text(`$${total.toFixed(2)}`);
        totalPriceElement.text(`$${total.toFixed(2)}`);
    }

    quantities.on('input', updateTotalPrice);

    updateTotalPrice();
});