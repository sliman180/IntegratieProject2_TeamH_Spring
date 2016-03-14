(function () {

    "use strict";

    var gulp = require("gulp");
    var $ = require("gulp-load-plugins")();

    var paths = {
        app: {
            fonts: "app/fonts",
            images: "app/images",
            scripts: "app/scripts",
            styles: "app/styles",
            views: "app/views"
        },
        dist: {
            fonts: "dist/fonts",
            images: "dist/images",
            scripts: "dist/scripts",
            styles: "dist/styles",
            views: "dist/views"
        }
    };

    gulp.task("clean-fonts", function () {

        return gulp.src([paths.dist.fonts], {read: false})
            .pipe($.plumber())
            .pipe($.rimraf({force: true}));

    });
    gulp.task("clean-images", function () {

        return gulp.src([paths.dist.images], {read: false})
            .pipe($.plumber())
            .pipe($.rimraf({force: true}));

    });
    gulp.task("clean-scripts", function () {

        return gulp.src([paths.dist.scripts], {read: false})
            .pipe($.plumber())
            .pipe($.rimraf({force: true}));

    });
    gulp.task("clean-styles", function () {

        return gulp.src([".sass-cache", paths.dist.styles], {read: false})
            .pipe($.plumber())
            .pipe($.rimraf({force: true}));

    });
    gulp.task('clean-views', function () {

        return gulp.src([paths.dist.views], {read: false})
            .pipe($.plumber())
            .pipe($.rimraf({force: true}));

    });

    gulp.task("fonts", ["clean-fonts"], function () {

        var files = [
            paths.app.fonts + "/**/*"
        ];

        return gulp.src(files)
            .pipe($.plumber())
            .pipe(gulp.dest(paths.dist.fonts));

    });
    gulp.task("images", ["clean-images"], function () {

        var files = [
            paths.app.images + "/**/*"
        ];

        return gulp.src(files)
            .pipe($.plumber())
            .pipe($.imagemin({
                progressive: true,
                interlaced: true,
                svgoPlugins: [{cleanupIDs: false}]
            }))
            .pipe(gulp.dest(paths.dist.images));

    });
    gulp.task("scripts", ["clean-scripts"], function () {

        var files = {
            vendor: [
                paths.app.scripts + "/vendor/jquery.js",
                paths.app.scripts + "/vendor/material.js",
                paths.app.scripts + "/vendor/angular.js",
                paths.app.scripts + "/vendor/angular-route.js",
                paths.app.scripts + "/vendor/angular-storage.js"
            ],
            app: [
                paths.app.scripts + "/plugins.js",
                paths.app.scripts + "/module.js",
                paths.app.scripts + "/constants.js",
                paths.app.scripts + "/routes.js",
                paths.app.scripts + "/filters/**/*",
                paths.app.scripts + "/interceptors/**/*",
                paths.app.scripts + "/services/**/*",
                paths.app.scripts + "/controllers/**/*"
            ]
        };

        gulp.src(files.vendor)
            .pipe($.plumber())
            .pipe($.concat(paths.dist.scripts + "/vendor.js"))
            .pipe($.ngAnnotate())
            .pipe(gulp.dest(""))
            .pipe($.rename({suffix: ".min"}))
            .pipe($.uglify())
            .pipe(gulp.dest(""));

        return gulp.src(files.app)
            .pipe($.plumber())
            .pipe($.concat(paths.dist.scripts + "/main.js"))
            .pipe($.ngAnnotate())
            .pipe(gulp.dest(""))
            .pipe($.rename({suffix: ".min"}))
            .pipe($.uglify())
            .pipe(gulp.dest(""));

    });
    gulp.task("styles", ["clean-styles"], function () {

        var files = [
            paths.app.styles + "/main.scss"
        ];

        return $.plumber()
            .pipe($.rubySass(files, {'style': "expanded", 'sourcemap=none': true}))
            .pipe($.autoprefixer("last 2 versions"))
            .pipe(gulp.dest(paths.dist.styles))
            .pipe($.rename({suffix: ".min"}))
            .pipe($.cssnano())
            .pipe(gulp.dest(paths.dist.styles));

    });
    gulp.task('views', ['clean-views'], function () {

        var files = [
            paths.app.views + '/**/*'
        ];

        return gulp.src(files)
            .pipe($.plumber())
            .pipe($.angularHtmlify())
            .pipe($.htmlclean())
            .pipe(gulp.dest(paths.dist.views));

    });

    gulp.task("default", ["fonts", "images", "scripts", "styles", "views"]);
    gulp.task("watch", ["default"], function () {

        gulp.watch(paths.app.fonts + "/**/*", {interval: 500}, ["fonts"]);
        gulp.watch(paths.app.images + "/**/*", {interval: 500}, ["images"]);
        gulp.watch(paths.app.scripts + "/**/*", {interval: 500}, ["scripts"]);
        gulp.watch(paths.app.styles + "/**/*", {interval: 500}, ["styles"]);
        gulp.watch(paths.app.views + "/**/*", {interval: 500}, ["views"]);

    });

})();
