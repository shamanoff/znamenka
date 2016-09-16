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


    $('#calendar').fullCalendar({
        googleCalendarApiKey: 'AIzaSyCYSwkC8872Q0Y-UA0g6SWAORZ-Dvqte10',
        events: {
            googleCalendarId: '4jto0age6tsrrkuhveervcj0sk@group.calendar.google.com',
            className: 'gcal-event'
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