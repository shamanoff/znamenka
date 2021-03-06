var Utils = {

    submit: function ($form) {
        $form.validator().on('submit', this.submitForm);
    },
    submitAjax: function ($form, success) {
        $form.validator().on('submit', function (e) {
            if (!e.isDefaultPrevented()) {
                $.post($form.attr('action'), $form.serialize(), success, 'json');
                return false;
            }
        });
    },
    submitWithData: function ($form, data, success) {
        $form.validator().on('submit', function (e) {
            if (!e.isDefaultPrevented()) {
                $.post($form.attr('action'), data, success, 'json');
                return false;
            }
        });
    },

    submitForm: function (e, callback) {
        if (!e.isDefaultPrevented()) {
            $(e.target).unbind('submit').submit();
            callback();
            return false;
        }
    }
};


