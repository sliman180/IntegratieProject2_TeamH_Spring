exports.config = {
    framework: 'jasmine',
    seleniumAddress: 'http://localhost:4444/wd/hub',
    suites: {
        auth: 'auth/t_*.js',
        reg: 'registratie/t_*.js',
        org: 'organisatie/t_*.js'
    }
}
