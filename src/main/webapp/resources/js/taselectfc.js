var taselectfcApp = angular.module("taselectfcApp", [ 'ngRoute', 'taselectfcControllers' ]);

taselectfcApp.config([ '$routeProvider', function($routeProvider) {
    $routeProvider.when('/home', {
        templateUrl : 'resources/partials/home.jsp'
    }).when('/contact', {
        templateUrl : 'resources/partials/contact.jsp'
    }).when('/fixtures', {
        templateUrl : 'resources/partials/fixtures.jsp',
        controller : 'FixtureListController'
    }).when('/fixtures/:id', {
        templateUrl : 'resources/partials/fixture.jsp',
        controller : 'FixtureController'
    }).when('/results', {
        templateUrl : 'resources/partials/results.jsp'
    }).when('/players', {
        templateUrl : 'resources/partials/squad.jsp',
        controller : 'PlayerListController'
    }).when('/players/:id', {
        templateUrl : 'resources/partials/player-profile.jsp',
        controller : 'PlayerController'
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
var $menu = $("#topnavbar");

// To account for the affixed submenu being pulled out of the content flow.
var $placeholder = $menu.clone().addClass("affix-placeholder");
$menu.after($placeholder);
$menu.affix({
    offset : {
        top : offsetCalculator
    }
});