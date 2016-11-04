$(document).ready(function () {
    $('#purchase-form').validator()
        .on('submit', function (e) {
            if (!e.isDefaultPrevented) {
                var $form = $(e.target);
                $.post($form.attr('action'), $form.serialize());
            }
        });

    var clientSelector = $("#select-client");

    var clientId = clientSelector.val();
    if (clientId != '') {
        getPurchases(clientId)
    }

    clientSelector.change(function () {
        var clientId = clientSelector.val();
        if (clientId != '') {
            getPurchases(clientId)
        }
    });

});

function getPurchases(clientId) {
    $.ajax({
        url: "/sale/purchases",
        type: "get",
        data: {
            "clientId": clientId
        },
        success: function (data) {
            $('#purchases-table').replaceWith(data);
        },
        error: function () {
            console.log("There was an error");
        }

    });
}
