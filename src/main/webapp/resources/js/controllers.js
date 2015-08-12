var taselectfcControllers = angular.module('taselectfcControllers', []);

taselectfcControllers.controller('FixtureListController', ['$scope', '$http', function($scope, $http) {
    $http.get('fixtures.json').success(function(data){
        $scope.fixtures = data;
    });
}]);

taselectfcControllers.controller('FixtureController', ['$scope', '$http', '$routeParams', function($scope, $http, $routeParams) {
    $http.get('fixtures/'+ $routeParams.id +'.json').success(function(data) {
        $scope.fixture = data;
    });
}]);