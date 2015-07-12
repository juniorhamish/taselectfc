var taselectfcControllers = angular.module('taselectfcControllers', []);

taselectfcControllers.controller('FixtureListController', ['$scope', '$http', function($scope, $http) {
    $http.get('fixtures.json').success(function(data){
        $scope.fixtures = data;
    });
}]);