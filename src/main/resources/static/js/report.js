$(document).ready(function () {
    
   $('#startTime').datetimepicker({
        defaultDate: '05/09/2016',
        format: 'DD/MM/YYYY'
    });
    
  $('#startDuty').datetimepicker({
        defaultDate: '05/09/2016',
        format: 'HH:mm'
    });
  
  $('#endDuty').datetimepicker({
        defaultDate: '05/09/2016',
        format: 'HH:mm'
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

});