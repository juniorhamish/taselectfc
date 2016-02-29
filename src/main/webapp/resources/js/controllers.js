var taselectfcControllers = angular.module('taselectfcControllers', []);

taselectfcControllers.controller('FixtureListController', [ '$scope', '$http', function($scope, $http) {
    $http.get('fixtures?projection=full').success(function(data) {
        if (data._embedded != undefined) {
            $scope.fixtures = data._embedded.fixtures;
        } else {
            $scope.fixtures = [];
        }
    });
} ]);

taselectfcControllers.controller('FixtureController', [ '$scope', '$http', '$routeParams',
        function($scope, $http, $routeParams) {
            $http.get('fixtures/' + $routeParams.id + '?projection=full').success(function(data) {
                $scope.fixture = data;
            });
        } ]);

taselectfcControllers.controller("PlayerListController", [ '$scope', '$http', function($scope, $http) {
    $http.get('players?projection=full').success(function(data) {
        if (data._embedded != undefined) {
            $scope.players = data._embedded.players;
        } else {
            $scope.players = [];
        }
    });
} ]);

taselectfcControllers.controller('PlayerController', [ '$scope', '$http', '$routeParams',
        function($scope, $http, $routeParams) {
            $http.get('players/' + $routeParams.id + '?projection=full').success(function(data) {
                $scope.player = data;
            });
        } ]);