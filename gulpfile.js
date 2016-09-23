var gulp = require('gulp'),
	gutil = require('gulp-util'),
	sass = require('gulp-sass');

gulp.task('build-scss', function() {
	return gulp.src('src/main/webapp/resources/scss/**/*.scss')
			   .pipe(sass())
			   .pipe(gulp.dest('src/main/webapp/static/css'))
});

gulp.task('watch', function () {
    return gulp.watch(['src/main/webapp/resources/scss/**/*.scss'], ['build-scss']);
});

gulp.task('default', ['watch', 'build-scss']);