exports.config = {
    framework: 'jasmine',
    seleniumAddress: 'http://localhost:4444/wd/hub',
    onPrepare: 'preparation.js',
    specs: ['specs/mainTest.js']
}
