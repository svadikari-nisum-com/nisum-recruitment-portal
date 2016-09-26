var gulp = require('gulp'),
	gutil = require('gulp-util'),
	newer = require('gulp-newer'),
	concat = require('gulp-concat');
	sass = require('gulp-sass');

gulp.task('build-scss', function() {
	return gulp.src('src/main/webapp/resources/scss/**/*.scss')
			   .pipe(sass())
			   .pipe(gulp.dest('src/main/webapp/static/css'))
});

gulp.task('watch', function () {
    return gulp.watch(['src/main/webapp/resources/scss/**/*.scss'], ['build-scss']);
});

var jsLibDir = 'app/lib/js';

var jsLib = [
             'bower_components/angular/angular.min.js',
             'bower_components/angular-animate/angular-animate.min.js',
             'bower_components/angular-aria/angular-aria.min.js',
             'bower_components/angular-block-ui/dist/angular-block-ui.min.js',
             'bower_components/angular-bootstrap-datetimepicker/src/js/datetimepicker.js',
             'bower_components/angular-file-upload/angular-file-upload-shim.min.js',
             'bower_components/angular-file-upload/angular-file-upload.min.js',
             'bower_components/angular-input-masks/releases/masks.min.js',
             'bower_components/angular-material/angular-material.min.js',
             'bower_components/angular-messages/angular-messages.min.js',
             'bower_components/angular-route/angular-route.min.js',
             'bower_components/angular-strap/dist/angular-strap.min.js',
             'bower_components/angular-strap/dist/angular-strap.tpl.min.js',
             'bower_components/angular-ui-router/release/angular-ui-router.min.js',
             'bower_components/angular-xeditable/dist/js/xeditable.min.js',
             'bower_components/bootstrap/dist/js/bootstrap.min.js',
             'bower_components/highcharts/highcharts.js',
             'bower_components/highcharts-ng/dist/highcharts-ng.min.js',
             'bower_components/jquery/dist/jquery.min.js',
             'bower_components/moment/moment.js',
             'bower_components/ng-grid/ng-grid-1.3.2.js',
             'bower_components/ui-select/dist/select.min.js',
             'bower_components/underscore/underscore-min.js',
             'bower_components/angular-ui-grid/ui-grid.min.js',
             'bower_components/select2/dist/js/select2.min.js',
             'bower_components/ng-notify/dist/ng-notify.min.js'
         ];

gulp.task('vendor-js', ['vendor-css'], function() {
    return gulp.src(jsLib)
        .pipe(gulp.dest(jsLibDir))
});

var cssLibDir = 'app/lib/css';

var cssLib = [
              'bower_components/angular-material/angular-material.min.css'
              
             ];

gulp.task('vendor-css', function() {
    return gulp.src(cssLib)
        .pipe(gulp.dest(cssLibDir))
});

gulp.task('default', ['watch', 'vendor-js', 'build-scss']);