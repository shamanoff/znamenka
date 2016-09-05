$(document).ready(function () {

    $('#startTime').datetimepicker({
        defaultDate: '05/09/2016 08:00:00',
        format: 'DD/MM/YYYY HH:mm:ss'
    });

    var clientId = $("#select-client").val();
    if (clientId != '') {
        getAbon(clientId)
    }

    $("#select-client").change(function () {
        var clientId = $("#select-client").val();
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