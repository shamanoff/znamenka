$(document).ready(function () {

    $('#startTime').datetimepicker({
        defaultDate: '05/09/2016 08:00:00',
        format: 'DD/MM/YYYY HH:mm:ss'
    });


    $('#contact_form').bootstrapValidator({
        // To use feedback icons, ensure that you use Bootstrap v3.1.0 or later
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            сlient: {
                validators: {
                    notEmpty: {
                        message: 'Выберите клиента'
                    }
                }
            },
            abonement: {
                validators: {

                    notEmpty: {
                        message: 'Выберите абонемент'
                    }
                }
            }
        }
    })
        .on('success.form.bv', function(e) {
            $('#success_message').slideDown({ opacity: "show" }, "slow") // Do something ...
            $('#contact_form').data('bootstrapValidator').resetForm();

            // Prevent form submission
            e.preventDefault();

            // Get the form instance
            var $form = $(e.target);

            // Get the BootstrapValidator instance
            var bv = $form.data('bootstrapValidator');

            // Use Ajax to submit form data
            $.post($form.attr('action'), $form.serialize(), function(result) {
                console.log(result);
            }, 'json');
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