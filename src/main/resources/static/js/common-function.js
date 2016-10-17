var Utils = {

    submit: function ($form) {
        $form.validator().on('submit', this.submitForm);
    },
    submitAjax: function ($form, success) {
        $form.validator().on('submit', function (e) {
            if (!e.isDefaultPrevented()) {
                e.preventDefault();
                $.post($form.attr('action'), $form.serialize(), success, 'json');
            }
        });
    },
    submitWithData: function ($form, data, success) {
        $form.validator().on('submit', function (e) {
            if (!e.isDefaultPrevented()) {
                e.preventDefault();
                $.post($form.attr('action'), data, success, 'json');
            }
        });
    },

    submitForm: function (e) {
        if (!e.isDefaultPrevented()) {
            $(e.target).submit();
        }
    }
};


