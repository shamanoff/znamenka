  $(document).ready(function() {
          
   $('#startTime').datetimepicker({
        defaultDate: '05/09/2016 08:00:00',
        format: 'DD/MM/YYYY'
    });

    
    $('#new_client_form').bootstrapValidator({
        // To use feedback icons, ensure that you use Bootstrap v3.1.0 or later
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            first_name: {
                validators: {
                        stringLength: {
                        min: 2,
                    },
                        notEmpty: {
                        message: 'Введите имя'
                    }
                }
            },
             last_name: {
                validators: {
                     stringLength: {
                        min: 2
                       
                    },
                    notEmpty: {
                        message: 'Введите фамилию'
                    }
                }
            },
            email: {
                validators: {
                 stringLength: {
                        min: 2,
                   message: 'Должно быть больше символов'
                    },
                    emailAddress: {
                        message: 'Введите корректный email'
                    }
                }
            },
            phone: {
                validators: {
                   stringLength: {
                        min: 11,
                       message: 'Телефон должен содержать 11 символов'
                    },
                    notEmpty: {
                        message: 'Телефон должен содержать 11 символов'
                    }
                   
                }
            },
            
          
            comment: {
                validators: {
                      stringLength: {
                        
                        max: 200,
                        message:'Не может содержать больше 200 символов'
                    }
                    }
                }
            }
        })
        .on('success.form.bv', function(e) {
            $('#success_message').slideDown({ opacity: "show" }, "slow") // Do something ...
                $('#new_client_form').data('bootstrapValidator').resetForm();

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