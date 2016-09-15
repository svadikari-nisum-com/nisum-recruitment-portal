module.exports = function(config) {
    config.set({
        basePath: '',
        frameworks: ['jasmine'],
        files: [
			'bower_components/angular/angular.js',
			'bower_components/angular-ui-router/release/angular-ui-router.js',
			'bower_components/angular-mocks/angular-mocks.js',
            'src/main/webapp/js/app.js',
            'src/main/webapp/js/admin/admin.js',
            'src/main/webapp/test/**/*.js'
        ],
        exclude: [

        ],
        preprocessors: {},
        reporters: ['progress'],
        port: 9876,
        colors: true,
        logLevel: config.LOG_INFO,
        autoWatch: true,
        browsers: ['Chrome'],
        singleRun: false,
        concurrency: Infinity,
        htmlReporter: {
            outputFile: 'src/main/webapp/test/units.html',

            // Optional
            pageTitle: 'Unit Tests',
            subPageTitle: 'A sample project description'
          }
    })
}