$(document).ready(function () {
    var startTime = $('#startTime');
    var trainingForm = $('#training-form');

    startTime.datetimepicker({
        defaultDate: new Date(),
        format: 'DD/MM/YYYY'
    });
    startTime.on('dp.change', function (e) {
        getTraining(e.date);
    });

    trainingForm.validator().on('submit', function (e) {
        trainingForm.validator('validate');
        if (!e.isDefaultPrevented()) {
            // Get the form instance
            var $form = $(e.target);
            var data = toData($form);
            $.ajax({
                type: "POST",
                url: $form.attr('action'),
                data: JSON.stringify(data),
                success: function (result) {
                    getTraining(startTime.data("DateTimePicker").date());
                },
                contentType: 'application/json',
                dataType: "json"
            });
        }
    });

    function toData($form) {
        var statuses = [];
        var trainings = $form.find('[name="trainingId"]').toArray();
        $.each(trainings, function (index) {
            var id, status, obj = {};
            id = $(this).val();
            status = $form.find('[name="trainingStatus[' + id + ']"]').val();
            if (status != "") {
                var comment = $form.find('[name="comment[' + id + ']"]').val();
                obj.trainingId = id;
                obj.status = status;
                obj.comment = comment;
                statuses.push(obj)
            }
        });
        return statuses;
    }

    function getTraining(date) {
        $.ajax({
            url: "/end-of-day/trainings",
            type: "get",
            data: {
                "date": date.format('DD/MM/YYYY').toString()
            },
            success: function (data) {
                $('#training-tbody').replaceWith(data);
                $('.select-status').each(function (indx, select) {
                    $(select).on('change', function (e) {
                        var statusId = $(e.target).val();
                        var statusName = $(e.target).attr('name');
                        var id = String(statusName).match(/\d+(?=\])/g)[0];
                        var trStatus = $('#tr' + id);
                        if (statusId > 2 || statusId=='') {
                            $('textarea[name="comment[' + id + ']"]').prop('required', true);
                            trStatus.removeClass("success").addClass('danger')
                        } else {
                            $('textarea[name="comment[' + id + ']"]').prop('required', false);
                            trStatus.removeClass("danger").addClass('success')
                        }
                    });
                });
                trainingForm.validator('update');
            }
        });
    }

});

