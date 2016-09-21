$(document).ready(function () {

    var calendar = $('#calendar');
    var trainingForm = $('#trainingForm');
    var createScheduleBtn = $('#createScheduleBtn');
    var selectClient = $("#select-client");
    var selectAbon = $('#select-abonement');
    var myModal = $('#myModal');

    myModal
        .on('shown.bs.modal', function () {
            selectClient.val("");
            selectAbon.children('option').remove();
            selectAbon
                .append($("<option></option>")
                    .attr("value", "")
                    .text("Выберите абонемент"));
        });

    $('#startTime').datetimepicker({
        format: 'DD/MM/YYYY hh:mm'
    });
    $('#endTime').datetimepicker({
        format: 'DD/MM/YYYY hh:mm'
    });

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

    trainingForm
        .submit(function (e) {
            // Prevent form submission
            e.preventDefault();

            // Get the form instance
            var $form = $(e.target);
            // Use Ajax to submit form data
            $.post($form.attr('action'), $form.serialize(), function (result) {
                console.log('success ' + result);
                calendar.fullCalendar('refetchEvents');
            }, 'json');
            myModal.modal("hide");
        });


    /* initialize the calendar
     -----------------------------------------------------------------*/

    calendar.fullCalendar({
        navLinks: true, // can click day/week names to navigate views
        selectable: true,
        selectHelper: true,
        select: function (start, end) {
            myModal.modal("show");

            startRU = start.format("DD/MM/YYYY hh:mm");
            endRU = end.format("DD/MM/YYYY hh:mm");
            trainingForm
                .find('[name="start"]').val(startRU).end()
                .find('[name="end"]').val(endRU).end();

            calendar.fullCalendar('unselect');
        },
        editable: true,

        eventLimit: true,
        header: {
            left: 'prev,next today',
            center: 'title',
            right: 'agendaWeek,agendaDay' //month,
        },
        buttonText: {
            today: 'today',
            //month: 'month',
            week: 'week',
            day: 'day'
        }, googleCalendarApiKey: 'AIzaSyCJhfsFEpthpzFVseRU-BKKsnLn3SxZ4Z0', //'AIzaSyCYSwkC8872Q0Y-UA0g6SWAORZ-Dvqte10',
        events: {
            googleCalendarId: 'znamenka.crm@gmail.com', //'4jto0age6tsrrkuhveervcj0sk@group.calendar.google.com',
            className: 'gcal-event',
            editable: true
        },
        annotations: [{
            start: new Date(2016, 8, 20, 13, 30),
            end: new Date(2016, 8, 22, 14, 0),
            title: 'Blocked Day',
            cls: 'open',
            color: '#777777',
            background: '#000'
        }],
        defaultView: 'agendaWeek'
    });

    function getAbon(clientId) {
        $.ajax({
            url: "/schedule/subscriptions",
            type: "get",
            data: {
                "clientId": clientId
            },
            success: function (data) {
                $.each(data, function (i, subscription) {
                    selectAbon
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

});


