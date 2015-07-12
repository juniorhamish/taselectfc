var taselectfcApp = angular.module("taselectfcApp", [ 'ngRoute', 'taselectfcControllers' ]);

taselectfcApp.config([ '$routeProvider', function($routeProvider) {
    $routeProvider.when('/home', {
        templateUrl : 'resources/partials/home.jsp'
    }).when('/contact', {
        templateUrl : 'resources/partials/contact.jsp'
    }).when('/fixtures', {
        templateUrl : 'resources/partials/fixtures.jsp',
        controller : 'FixtureListController'
    }).when('/results', {
        templateUrl : 'resources/partials/results.jsp'
    }).when('/squad', {
        templateUrl : 'resources/partials/squad.jsp'
    }).when('/stats', {
        templateUrl : 'resources/partials/stats.jsp'
    }).when('/gallery', {
        templateUrl : 'resources/partials/gallery.jsp'
    }).otherwise({
        redirectTo : '/home'
    });
} ]);

var offsetCalculator = function() {
    return $('#banner').outerHeight(true);
}
$('#topnavbar').affix({
    offset : {
        top : offsetCalculator
    }
});