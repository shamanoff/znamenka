$(document).ready(function () {

    var calendar = $('#calendar');

    var trainingFormForClub = $('#trainingForm'); // форма для записи на тренировку клиента с клубной картой
    var trainingFormForNew = $('#formCreate'); //форма для записи на тренировку нового клиента
    var submitBtnForClub = $('#training-submit-for-club');
    var submitBtnForNew = $('#training-submit-for-new');

    var selectClientForClub = $("#select-client-for-club");
    var selectAbonForClub = $('#select-abonement-for-club');

    var myModal = $('#training-modal');
    var clientId = selectClientForClub.val();

    if (clientId != '') {
        getAbon(clientId, selectAbonForClub);
    }
    selectClientForClub.change(function () {
        clientId = selectClientForClub.val();
        if (clientId != '') {
            getAbon(clientId, selectAbonForClub)
        }
    });

    myModal
        .on('shown.bs.modal', function () {
            selectClientForClub.val("");
            selectAbonForClub.children('option').remove();
            selectAbonForClub
                .append($("<option></option>")
                    .attr("value", "")
                    .text("Выберите абонемент"));
        });

    trainingFormForClub
        .submit(function (e) {
            submitForm(e);
        });

    function submitForm(e) {
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
    }

    /* initialize the calendar
     -----------------------------------------------------------------*/

    calendar.fullCalendar({
        navLinks: true, // can click day/week names to navigate views
        selectable: true,
        minTime: "06:00:00",
        selectHelper: true,
        select: function (start, end) {
            myModal.modal("show");

            startRU = start.format("DD/MM/YYYY hh:mm");
            endRU = end.format("DD/MM/YYYY hh:mm");
            trainingFormForClub
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
        },
        events: {
            url: '/training/events',
            type: 'GET',
            error: function () {
                console.log('there was an error while fetching events!');
            }
        }
        ,
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

    function getAbon(clientId, $select) {
        $.ajax({
            url: "/client/subscriptions",
            type: "get",
            data: {
                "clientId": clientId
            },
            success: function (data) {
                $.each(data, function (i, subscription) {
                    $select
                        .append($("<option></option>")
                            .attr("value", subscription.purchaseId)
                            .text(subscription.productName));
                });
            }
        });
    }

});


