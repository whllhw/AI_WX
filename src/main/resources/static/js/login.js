var Auth = {
    vars: {
        lowin: document.querySelector('.lowin'),
        lowin_brand: document.querySelector('.lowin-brand'),
        lowin_wrapper: document.querySelector('.lowin-wrapper'),
        lowin_login: document.querySelector('.lowin-login'),
        lowin_wrapper_height: 0,
        login_back_link: document.querySelector('.login-back-link'),
        forgot_link: document.querySelector('.forgot-link'),
        login_link: document.querySelector('.login-link'),
        login_btn: document.querySelector('.login-btn'),
        register_link: document.querySelector('.register-link'),
        password_group: document.querySelector('.password-group'),
        password_group_height: 0,
        lowin_register: document.querySelector('.lowin-register'),
        lowin_footer: document.querySelector('.lowin-footer'),
        box: document.getElementsByClassName('lowin-box'),
        option: {}
    },
    setHeight(height) {
        Auth.vars.lowin_wrapper.style.minHeight = height + 'px';
    },
    brand() {
        Auth.vars.lowin_brand.classList += ' lowin-animated';
        setTimeout(() = > {
            Auth.vars.lowin_brand.classList.remove('lowin-animated');
    },
        1000;
    )
    },
    init(option) {
        Auth.setHeight(Auth.vars.box[0].offsetHeight + Auth.vars.lowin_footer.offsetHeight);

        Auth.vars.password_group.style.height = Auth.vars.password_group.offsetHeight + 'px';
        Auth.vars.password_group_height = Auth.vars.password_group.offsetHeight;
        Auth.vars.lowin_wrapper_height = Auth.vars.lowin_wrapper.offsetHeight;


        var len = Auth.vars.box.length - 1;

        for (var i = 0; i <= len; i++) {
            if (i !== 0) {
                Auth.vars.box[i].className += ' lowin-flip';
            }
        }
    }
};
