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
        url: "/schedule/abonement",
        type: "get",
        data: {
            "clientId": clientId
        },
        success: function (data) {
            $('#select-abonement').children('option').remove();
            $.each(data, function (key, value) {
                $('#select-abonement')
                    .append($("<option></option>")
                        .attr("value", key)
                        .text(value));
            });
        },
        error: function () {
            console.log("There was an error");
        }

    });
}