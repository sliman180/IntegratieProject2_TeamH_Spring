exports.config = {
    framework: 'jasmine',
    seleniumAddress: 'http://localhost:4444/wd/hub',
    onPrepare: 'preparation.js',
    specs: ['specs/organisationTest.js','specs/mainthemeTest.js','specs/subthemeTest.js','specs/sessionTest.js']
}
