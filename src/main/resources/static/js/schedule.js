$(document).ready(function () {

    $('#startTime').datetimepicker({
        format: 'DD/MM/YYYY HH:mm'
    });



    var selectClient = $("#select-client");
    var clientId = selectClient.val();
    if (clientId != '') {
        getAbon(clientId)
    }

    selectClient.change(function () {
        var clientId = selectClient.val();
        if (clientId != '') {
            getAbon(clientId)
        }
    });

});

function getAbon(clientId) {
    $.ajax({
        url: "/schedule/subscriptions",
        type: "get",
        data: {
            "clientId": clientId
        },
        success: function (data) {
            $('#select-abonement').children('option').remove();
            $.each(data, function (i, subscription) {
                $('#select-abonement')
                    .append($("<option></option>")
                        .attr("value", subscription.purchaseId)
                        .text(subscription.productName));
            });
        },
        error: function () {
            console.log("There was an error");
        }

    });
}