exports.config = {
    framework: 'jasmine',
    seleniumAddress: 'http://localhost:4444/wd/hub',
    suites: {
        auth: ['auth/*.js','auth/*.js'],
        reg: 'registration/t_registratie.js',
        org: 'organisation/t_organisatie.js'
    }
}
