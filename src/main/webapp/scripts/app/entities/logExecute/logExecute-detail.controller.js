'use strict';

angular.module('jhipsterApp')
    .controller('LogExecuteDetailController', function ($scope, $rootScope, $stateParams, entity, LogExecute) {
        $scope.logExecute = entity;
        $scope.load = function (id) {
            LogExecute.get({id: id}, function(result) {
                $scope.logExecute = result;
            });
        };
        $rootScope.$on('jhipsterApp:logExecuteUpdate', function(event, result) {
            $scope.logExecute = result;
        });
    });
