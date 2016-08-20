$(document).ready(init(new Date()));

function getTraining(date) {
    $.ajax({
        url: "/end-of-day/",
        type: "get",
        data: {
            "date": date.format('DD/MM/YYYY').toString()
        },
        success: function(data) {
            $('#container').replaceWith(data);
            init(date.toDate())
        }
    });
}

function init(date) {
    $('#startTime')
        .datetimepicker({
            defaultDate: date,
            format: 'DD/MM/YYYY'
        });
    $('#startTime').on('dp.change', function(e) {
        getTraining(e.date);
    });

    $('#contact_form').bootstrapValidator({
        // To use feedback icons, ensure that you use Bootstrap v3.1.0 or later
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            phone: {
                validators: {

                    notEmpty: {
                        message: 'Введите статус тренировки'
                    }
                }
            }
        }
    })
        .on('success.form.bv', function (e) {
            $('#success_message').slideDown({opacity: "show"}, "slow") // Do something ...
            $('#contact_form').data('bootstrapValidator').resetForm();

            // Prevent form submission
            e.preventDefault();

            // Get the form instance
            var $form = $(e.target);

            // Get the BootstrapValidator instance
            var bv = $form.data('bootstrapValidator');

            // Use Ajax to submit form data
            $.post($form.attr('action'), $form.serialize(), function (result) {
                console.log(result);
            }, 'json');
        });
}