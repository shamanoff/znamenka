var Utils = {

    submit : function($form) {
        $form.validator().on('submit', this.submitForm);
    },

    submitFormAjax : function (e, success) {
        if (!e.isDefaultPrevented) {
            var $form = $(e.target);
            $.post($form.attr('action'), $form.serialize(), success, 'json');
        }
    },

    submitForm : function (e) {
        if (!e.isDefaultPrevented) {
            $(e.target).submit();
        }
    }
};