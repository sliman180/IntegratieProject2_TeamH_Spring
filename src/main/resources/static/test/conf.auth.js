exports.config = {
    framework: 'jasmine',
    seleniumAddress: 'http://localhost:4444/wd/hub',
    specs: ['specs/loginTest.js','specs/logoffTest.js','specs/registertest.js']
}
