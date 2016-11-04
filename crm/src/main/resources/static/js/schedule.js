$(document).ready(function () {

    var $calendar = $('#calendar');

    var $trainingFormForClub = $('#trainingForm'); // форма для записи на тренировку клиента с клубной картой
    var $trainingFormForNew = $('#formCreate'); //форма для записи на тренировку нового клиента
    var $formForExistsTraining = $('#exists-training-form');
    var $changeTrainerInput = $('#select-trainer-for-exists');
    var $writeOffTrainingBtn = $('#statuses-write-off');
    var $writeOnTrainingBtn = $('#statuses-write-on');
    var $changeTrainerBtn = $('#change-trainer');

    var $selectClientForClub = $("#select-client-for-club");
    var $selectAbonForClub = $('#select-abonement-for-club');

    var $trainingModal = $('#training-modal');
    var $modalForExists = $('#exists-training-modal');
    var clientId = $selectClientForClub.val();

    if (clientId != '') {
        getAbon(clientId, $selectAbonForClub);
    }
    $selectClientForClub.change(function () {
        clientId = $selectClientForClub.val();
        if (clientId != '') {
            getAbon(clientId, $selectAbonForClub)
        }
    });

    $trainingModal
        .on('shown.bs.modal', function () {
            $selectClientForClub.val("");
            clearAbonSelect();
        });

    $changeTrainerInput.change(function (e) {
        var trainerId = $formForExistsTraining.find('[name="trainerId"]').val();
        var statusId = $formForExistsTraining.find('[name="statusId"]').val();
        $changeTrainerBtn.prop('disabled', trainerId == $changeTrainerInput.val() || statusId != 1);
    });

    $changeTrainerBtn.click(function () {
        var id = $formForExistsTraining.find('[name="id"]').val();
        var data = {
            trainerId: $formForExistsTraining.find('[name="trainer"]').val()
        };
        $.post("/training/" + id, data, function () {
            $modalForExists.modal('hide');
        });
    });

    $writeOffTrainingBtn.click(function () {
        changeStatusOfTraining(4);
    });

    $writeOnTrainingBtn.click(function () {
        changeStatusOfTraining(3);
    });

    function changeStatusOfTraining(statusId) {
        var id = $formForExistsTraining.find('[name="id"]').val();
        var data = {
            statusId: statusId
        };
        $.post("/training/" + id, data, function () {
            $modalForExists.modal('hide');
        });
    }

    var refetchEvents = function () {
        $calendar.fullCalendar('refetchEvents');
        $trainingModal.modal("hide");
    };
    Utils.submitAjax($trainingFormForClub, refetchEvents);
    Utils.submitAjax($trainingFormForNew, refetchEvents);

    /* initialize the calendar
     -----------------------------------------------------------------*/

    $calendar.fullCalendar({
        navLinks: true, // can click day/week names to navigate views
        selectable: true,
        minTime: "06:00:00",
        selectHelper: true,
        select: function (start) {
            $trainingModal.modal("show");
            $trainingFormForClub.trigger('reset');
            $trainingFormForNew.trigger('reset');
            var startRU = start.format("DD/MM/YYYY HH:mm");
            $trainingFormForClub.find('[name="start"]').val(startRU).end();
            $trainingFormForNew.find('[name="start"]').val(startRU).end();
            $calendar.fullCalendar('unselect');
        },
        eventClick: function (calEvent, jsEvent, view) {
            $modalForExists.modal('show');
            var startRU = calEvent.start.format("DD/MM/YYYY HH:mm");
            $formForExistsTraining.find('[name="start"]').val(startRU).end();
            $writeOffTrainingBtn.prop('disabled', false);
            $writeOnTrainingBtn.prop('disabled', false);
            $.ajax({
                url: '/training/' + calEvent.id,
                method: 'GET'
            }).success(function (response) {
                $formForExistsTraining
                    .find('[name="id"]').val(response.id).end()
                    .find('[name="client"]').val(response.clientName).end()
                    .find('[name="trainer"]').val(response.trainerId).end()
                    .find('[name="trainerId"]').val(response.trainerId).end()
                    .find('[name="statuses"]').val(response.statusName).end()
                    .find('[name="comment"]').val(response.comment).end()
                    .find('[name="passForAuto"]').prop('checked', response.passForAuto).end()
                    .find('[name="statusId"]').val(response.statusId).end()
                    .find('[name="abonement"]').val(response.abonement).end();
                $changeTrainerBtn.prop('disabled', true);
                if (response.statusId != 1) {
                    $writeOffTrainingBtn.prop('disabled', true);
                    $writeOnTrainingBtn.prop('disabled', true);
                    $changeTrainerInput.prop('disabled', true);
                }
            });
        },
        editable: false,

        eventLimit: false,
        header: {
            left: 'prev,next today',
            center: 'title',
            right: 'agendaWeek,agendaDay' //month,
        },
        buttonText: {
            today: 'Сегодня',
            //month: 'month',
            week: 'по неделям',
            day: 'по дням'
        },
        events: {
            url: '/training/events',
            type: 'GET',
            error: function () {
                console.log('there was an error while fetching events!');
            },
            success: function (data) {
                console.log(data);
            }
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

    function getAbon(clientId, $select) {
        $.ajax({
            url: "/client/abonements",
            type: "get",
            data: {
                "clientId": clientId
            },
            success: function (data) {
                clearAbonSelect();
                $.each(data, function (i, abonements) {
                    $select
                        .append($("<option></option>")
                            .attr("value", abonements.purchaseId)
                            .text(abonements.productName));
                });
            }
        });
    }

    function clearAbonSelect() {
        $selectAbonForClub.children('option').remove();
        $selectAbonForClub
            .append($("<option></option>")
                .attr("value", "")
                .text("Выберите абонемент"));
    }

});


