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

    trainingForm.submit(function (e) {
        e.preventDefault();
        // Get the form instance
        var $form = $(e.target);

        $.ajax({
            type: "POST",
            url: $form.attr('action'),
            data: JSON.stringify(toData($form)),
            success: function (result) {
                getTraining(startTime.data("DateTimePicker").date());
            },
            contentType: 'application/json',
            dataType:"json"
        });
    });

    function toData($form) {
        var statuses = [];
        var trainings = $form.find('[name="trainingId"]').toArray();
        $.each(trainings, function (index) {
            var id, status, obj = {};
            id = $(this).val();
            status = $form.find('[name="trainingStatus[' + id + ']"]').val();
            if (status != "") {
                obj.trainingId = id;
                obj.status = status;
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
            }
        });
    }

});

